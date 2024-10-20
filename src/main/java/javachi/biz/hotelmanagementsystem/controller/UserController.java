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
public class UserController {

    private final AuthUserService authUserService;

    @GetMapping("/get-info")
    @Operation(summary = "Get auth user by ID")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public HttpApiResponse<AuthUserDto> getAuthUserById(@RequestParam(value = "id") Integer authId) {
        return this.authUserService.getAuthUserById(authId);
    }

    @PostMapping("/update")
    @Operation(summary = "Update user information")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public HttpApiResponse<AuthUserDto> updateUserInfo(@RequestParam(value = "id") Integer authId,
                                                       @RequestBody AuthUserDto userDto) {
        return this.authUserService.updateAuthUser(authId, userDto);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Update user information")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public HttpApiResponse<AuthUserDto> deleteUserInfo(@RequestParam(value = "id") Integer authId) {
        return this.authUserService.deleteUser(authId);
    }

}

