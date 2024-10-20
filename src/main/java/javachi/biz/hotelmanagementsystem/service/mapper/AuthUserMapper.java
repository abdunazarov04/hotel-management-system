package javachi.biz.hotelmanagementsystem.service.mapper;

import javachi.biz.hotelmanagementsystem.domain.auth.AuthUser;
import javachi.biz.hotelmanagementsystem.dto.AuthUserDto;
import org.springframework.stereotype.Component;

@Component
public class AuthUserMapper {

    public AuthUser toEntity(AuthUserDto dto) {
        return AuthUser.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public AuthUserDto toDto(AuthUser authUser) {
        return AuthUserDto.builder()
                .id(authUser.getId())
                .firstname(authUser.getFirstname())
                .lastname(authUser.getLastname())
                .username(authUser.getUsername())
                .email(authUser.getEmail())
                .password(authUser.getPassword())
                .build();
    }
}
