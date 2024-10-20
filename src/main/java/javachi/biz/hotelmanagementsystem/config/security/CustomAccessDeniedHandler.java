package javachi.biz.hotelmanagementsystem.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javachi.biz.hotelmanagementsystem.domain.auth.AuthRole;
import javachi.biz.hotelmanagementsystem.domain.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {


        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> responseBody = new HashMap<>();

        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof AuthUser authUser)) {
            responseBody.put("error", "User is not authenticated or unauthorized");
        } else {

            StringJoiner authRoleJoiner = new StringJoiner(", ", "[", "]");
            if (authUser.getRoles() != null) {
                authUser.getRoles().stream().map(AuthRole::getCode).forEach(authRoleJoiner::add);
            }

            responseBody.put("username", authUser.getUsername());
            responseBody.put("error", "Access Denied");
            responseBody.put("role", authRoleJoiner.toString());
        }

        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
