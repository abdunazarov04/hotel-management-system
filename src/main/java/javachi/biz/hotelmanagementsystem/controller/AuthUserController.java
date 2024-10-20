package javachi.biz.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import javachi.biz.hotelmanagementsystem.dto.*;
import javachi.biz.hotelmanagementsystem.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthUserController {

    private final AuthUserService authUserService;

    @PostMapping("/register")
    @Operation(summary = "Create a new auth user")
    public HttpApiResponse<AuthUserDto> registerAuthUser(@RequestBody AuthUserDto dto) {
        return this.authUserService.registerAuthUser(dto);
    }

    @PostMapping("/confirm-password")
    @Operation(summary = "Confirm user password")
    public HttpApiResponse<AuthUserDto> confirmUserPassword(@RequestBody ConfirmUserDto dto) {
        return this.authUserService.confirmUserPassword(dto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login with user credentials")
    public HttpApiResponse<TokenResponseDto> authLogin(@RequestBody LoginDto loginDto) {
        return this.authUserService.authLogin(loginDto);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout the user")
    public HttpApiResponse<String> authLogout(@RequestBody LogoutDto logoutDto) {
        return this.authUserService.authLogout(logoutDto);
    }
}