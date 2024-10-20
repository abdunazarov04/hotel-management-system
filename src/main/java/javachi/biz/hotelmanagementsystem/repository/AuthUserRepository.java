package javachi.biz.hotelmanagementsystem.repository;

import javachi.biz.hotelmanagementsystem.domain.auth.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {

    Optional<AuthUser> findByIdAndDeletedAtIsNull(Integer id);

    Optional<AuthUser> findByUsernameAndDeletedAtIsNull(String username);

    Optional<AuthUser> findByEmailAndDeletedAtIsNull(String email);

    Boolean existsByEmailAndDeletedAtIsNull(String email);

    Boolean existsByUsernameAndDeletedAtIsNull(String username);


}
