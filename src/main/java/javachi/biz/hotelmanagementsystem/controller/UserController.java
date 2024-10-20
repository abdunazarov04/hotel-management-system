package javachi.biz.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import javachi.biz.hotelmanagementsystem.dto.AuthUserDto;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class UserController {

    private final AuthUserService authUserService;

    @GetMapping("/get-info")
    @Operation(summary = "Get auth user by ID")
    public HttpApiResponse<AuthUserDto> getAuthUserById(@RequestParam(value = "id") Integer authId) {
        return this.authUserService.getAuthUserById(authId);
    }

    @PatchMapping("/update")
    @Operation(summary = "Update user information")
    public HttpApiResponse<AuthUserDto> updateUserInfo(@RequestParam(value = "id") Integer authId,
                                                       @RequestBody AuthUserDto userDto) {
        return this.authUserService.updateAuthUser(authId, userDto);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Update user information")
    public HttpApiResponse<AuthUserDto> deleteUserInfo(@RequestParam(value = "id") Integer authId) {
        return this.authUserService.deleteUser(authId);
    }

}

