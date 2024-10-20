package javachi.biz.hotelmanagementsystem.config.security;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javachi.biz.hotelmanagementsystem.domain.UserAccessSession;
import javachi.biz.hotelmanagementsystem.repository.UserAccessSessionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserAccessSessionRepository userAccessSessionRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isBlank(authorization) && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            if (jwtUtil.isTokenValid(token)) {
                String sessionId = this.jwtUtil.getClaim("sub", token, String.class);
                Optional<UserAccessSession> optionalUserAccessSession = this.userAccessSessionRepository.findById(sessionId);
                if (optionalUserAccessSession.isEmpty()) {
                    throw new BadCredentialsException("Invalid token!");
                }
                UserAccessSession userAccessSession = optionalUserAccessSession.get();
                CustomUserDetails customUserDetails = this.customUserDetailsService.loadUserByUsername(userAccessSession.getEmail());
                if (Objects.isNull(customUserDetails)) {
                    throw new BadCredentialsException("User not found!");
                }

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                customUserDetails.getAuthUser(),
                                customUserDetails.getAuthUser().getPassword(),
                                customUserDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
