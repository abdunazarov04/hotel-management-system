package javachi.biz.hotelmanagementsystem.config.security;

import javachi.biz.hotelmanagementsystem.domain.Status;
import javachi.biz.hotelmanagementsystem.domain.auth.AuthRole;
import javachi.biz.hotelmanagementsystem.domain.auth.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Getter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private AuthUser authUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (AuthRole authRole : authUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + authRole.getCode()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return authUser.getStatus().equals(Status.ACTIVE);
    }
}
