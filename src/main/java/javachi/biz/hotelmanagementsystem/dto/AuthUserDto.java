package javachi.biz.hotelmanagementsystem.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDto {

    private Integer id;

    //    @NotBlank(message = "Firstname cannot be null!")
    private String firstname;

    //    @NotBlank(message = "Lastname cannot be null!")
    private String lastname;

    @Email(message = "Email is not valid!")
    @NotBlank(message = "Email cannot be null!")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Username cannot be null!")
    private String username;

    @NotBlank(message = "Password cannot be null!")
    private String password;

}
