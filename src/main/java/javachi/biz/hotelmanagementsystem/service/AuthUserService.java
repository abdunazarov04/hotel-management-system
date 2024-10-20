package javachi.biz.hotelmanagementsystem.service;

import javachi.biz.hotelmanagementsystem.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface AuthUserService {

    HttpApiResponse<AuthUserDto> registerAuthUser(AuthUserDto dto);

    HttpApiResponse<AuthUserDto> getAuthUserById(Integer authId);

    HttpApiResponse<AuthUserDto> confirmUserPassword(ConfirmUserDto dto);

    HttpApiResponse<TokenResponseDto> authLogin(LoginDto loginDto);

    HttpApiResponse<String> authLogout(LogoutDto logoutDto);

    HttpApiResponse<AuthUserDto> updateAuthUser(Integer authId, AuthUserDto userDto);

    HttpApiResponse<AuthUserDto> deleteUser(Integer authId);
}
