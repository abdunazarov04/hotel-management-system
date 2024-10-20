package javachi.biz.hotelmanagementsystem.domain.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authuser_authrole")
public class AuthUserAuthRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")  // Fizik ustun nomi `user_id`
    private Integer userId;

    @Column(name = "role_id")  // Fizik ustun nomi `role_id`
    private Integer roleId;

}
