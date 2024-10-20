package javachi.biz.hotelmanagementsystem.config.security;

import javachi.biz.hotelmanagementsystem.domain.auth.AuthRole;
import javachi.biz.hotelmanagementsystem.domain.auth.AuthUser;
import javachi.biz.hotelmanagementsystem.repository.AuthRoleRepository;
import javachi.biz.hotelmanagementsystem.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository authRoleRepository;


    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AuthUser> optionalAuthUser = this.authUserRepository.findByEmailAndDeletedAtIsNull(email);
        if (optionalAuthUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        AuthUser authUser = optionalAuthUser.get();
        Set<AuthRole> authRoles = this.authRoleRepository.getAllAuthRoleByUserId(authUser.getId());
        authUser.setRoles(authRoles);
        return new CustomUserDetails(authUser);
    }
}
