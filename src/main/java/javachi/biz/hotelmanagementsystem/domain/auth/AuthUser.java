package javachi.biz.hotelmanagementsystem.domain.auth;

import jakarta.persistence.*;
import javachi.biz.hotelmanagementsystem.domain.BaseEntity;
import javachi.biz.hotelmanagementsystem.domain.Status;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AuthUser extends BaseEntity {

    private String firstname;
    private String lastname;
    private String email;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "authuser_authrole",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<AuthRole> roles = new HashSet<>();


}
