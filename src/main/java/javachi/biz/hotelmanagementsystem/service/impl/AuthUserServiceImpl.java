package javachi.biz.hotelmanagementsystem.service.impl;

import jakarta.transaction.Transactional;
import javachi.biz.hotelmanagementsystem.config.security.JwtUtil;
import javachi.biz.hotelmanagementsystem.domain.Status;
import javachi.biz.hotelmanagementsystem.domain.UserAccessSession;
import javachi.biz.hotelmanagementsystem.domain.UserRefreshSession;
import javachi.biz.hotelmanagementsystem.domain.UserSession;
import javachi.biz.hotelmanagementsystem.domain.auth.AuthUser;
import javachi.biz.hotelmanagementsystem.dto.*;
import javachi.biz.hotelmanagementsystem.exception.ContentNotFoundException;
import javachi.biz.hotelmanagementsystem.exception.ResourceNotFoundException;
import javachi.biz.hotelmanagementsystem.repository.*;
import javachi.biz.hotelmanagementsystem.service.AuthUserService;
import javachi.biz.hotelmanagementsystem.service.mapper.AuthUserMapper;
import javachi.biz.hotelmanagementsystem.service.validation.AuthUserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

    private final JwtUtil jwtUtil;
    private final JavaMailSender emailSender;
    private final AuthUserMapper authUserMapper;
    private final AuthUserValidation authUserValidation;
    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository authRoleRepository;
    private final UserSessionRepository userSessionRepository;
    private final UserAccessSessionRepository userAccessSessionRepository;
    private final UserRefreshSessionRepository userRefreshSessionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public HttpApiResponse<AuthUserDto> registerAuthUser(AuthUserDto dto) {
        AuthUser authUser = this.authUserMapper.toEntity(dto);
        String generatedPassword = String.valueOf(new Random().nextInt(10000, 99999));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getEmail());
        message.setSubject("Ro'yxatdan o'tish tasdiqlash");
        message.setText("Tasdiqlash uchun quyidagi kodni kiriting: " + generatedPassword);
        emailSender.send(message);

        this.userSessionRepository.save(
                UserSession.builder()
                        .sessionId(generatedPassword)
                        .email(authUser.getEmail())
                        .authUser(authUser)
                        .build()
        );

        return HttpApiResponse.<AuthUserDto>builder()
                .success(true)
                .message("Check your email and confirm password!")
                .code(HttpStatus.OK.value())
                .build();
    }

    @Override
    public HttpApiResponse<AuthUserDto> getAuthUserById(Integer authId) {
        return this.authUserRepository.findByIdAndDeletedAtIsNull(authId)
                .map(authUser -> HttpApiResponse.<AuthUserDto>builder()
                        .success(true)
                        .message("OK")
                        .code(HttpStatus.OK.value())
                        .content(authUserMapper.toDto(authUser))
                        .build())
                .orElseThrow(() -> new ContentNotFoundException("User with %d id is not found!".formatted(authId)));
    }

    @Override
    public HttpApiResponse<AuthUserDto> confirmUserPassword(ConfirmUserDto dto) {

        Optional<UserSession> optionalUserSession = this.userSessionRepository.findById(dto.getGeneratedPassword());
        if (optionalUserSession.isEmpty()) {
            throw new ContentNotFoundException("This email does not exist");
        }
        UserSession userSession = optionalUserSession.get();
        String generatedPassword = userSession.getSessionId();
        if (!(generatedPassword.equals(dto.getGeneratedPassword()))) {
            throw new BadCredentialsException("This generated password does not match");
        } else if (!dto.getEmail().equals(userSession.getEmail())) {
            throw new BadCredentialsException("This email does not match");
        }

        this.userSessionRepository.deleteById(userSession.getSessionId());

        AuthUser authUser = userSession.getAuthUser();
        authUser.setStatus(Status.ACTIVE);
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
//        Set<AuthRole> authRoles = this.authRoleRepository.getAllAuthRoleByUserId(authUser.getId());
        authUser.setRoles(Set.of(this.authRoleRepository.findByCode("USER")));

        return HttpApiResponse.<AuthUserDto>builder()
                .success(true)
                .message("OK")
                .content(
                        this.authUserMapper.toDto(
                                this.authUserRepository.save(authUser)
                        )
                )
                .build();
    }

    @Override
    public HttpApiResponse<TokenResponseDto> authLogin(LoginDto loginDto) {
        Optional<AuthUser> optionalAuthUser = this.authUserRepository.findByUsernameAndDeletedAtIsNull(loginDto.getUsername());
        if (optionalAuthUser.isEmpty()) {
            throw new ContentNotFoundException("This username does not exist");
        }
        AuthUser authUser = optionalAuthUser.get();
        if (!passwordEncoder.matches(loginDto.getPassword(), authUser.getPassword())){
            throw new BadCredentialsException("Username or password is incorrect");
        }
        String sessionId = UUID.randomUUID().toString();
        this.userAccessSessionRepository.save(
                UserAccessSession.builder()
                        .sessionId(sessionId)
                        .email(authUser.getEmail())
                        .userId(authUser.getId())
                        .build()
        );


        this.userRefreshSessionRepository.save(
                UserRefreshSession.builder()
                        .sessionId(sessionId)
                        .email(authUser.getEmail())
                        .userId(authUser.getId())
                        .build()
        );

        return HttpApiResponse.<TokenResponseDto>builder()
                .success(true)
                .message("OK")
                .code(HttpStatus.OK.value())
                .content(TokenResponseDto.builder()
                        .accessToken(this.jwtUtil.generateAccessToken(sessionId))
                        .refreshToken(this.jwtUtil.generateRefreshToken(sessionId))
                        .build())
                .build();
    }

    @Override
    public HttpApiResponse<String> authLogout(LogoutDto logoutDto) {
        String token = logoutDto.getToken();
        if (!this.jwtUtil.isTokenValid(token)) {
            throw new BadCredentialsException("Invalid token");
        }
        String sessionId = this.jwtUtil.getClaim("sub", token, String.class);
        this.userAccessSessionRepository.deleteById(sessionId);
        return HttpApiResponse.<String>builder()
                .success(true)
                .message("User successful logout!")
                .code(HttpStatus.OK.value())
                .build();
    }

    public HttpApiResponse<AuthUserDto> updateAuthUser(Integer authId, AuthUserDto userDto) {
        AuthUser existingUser = authUserRepository.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + authId));

        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }
        if (userDto.getUsername() != null) {
            existingUser.setUsername(userDto.getUsername());
        }
        if (userDto.getFirstname() != null) {
            existingUser.setFirstname(userDto.getFirstname());
        }
        if (userDto.getLastname() != null) {
            existingUser.setLastname(userDto.getLastname());
        }
        if (userDto.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        AuthUser updatedUser = authUserRepository.save(existingUser);

        return HttpApiResponse.<AuthUserDto>builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .content(authUserMapper.toDto(updatedUser))
                .build();
    }

    @Override
    @Transactional
    public HttpApiResponse<AuthUserDto> deleteUser(Integer authId) {
        AuthUser existingUser = authUserRepository.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + authId));

//        this.authUserRepository.delete(existingUser);
        existingUser.setDeletedAt(LocalDateTime.now());
        this.authUserRepository.save(existingUser);
        return HttpApiResponse.<AuthUserDto>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .content(authUserMapper.toDto(existingUser))
                .message("User deleted")
                .build();
    }


}
