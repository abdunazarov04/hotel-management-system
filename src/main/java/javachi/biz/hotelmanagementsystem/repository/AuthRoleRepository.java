package javachi.biz.hotelmanagementsystem.repository;

import javachi.biz.hotelmanagementsystem.domain.auth.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthRoleRepository extends JpaRepository<AuthRole, Integer> {

    @Query(value = "select ar.* from authuser_authrole as auar inner join auth_role as ar " +
            "on ar.id = auar.role_id where auar.user_id = :userId",
            nativeQuery = true
    )
    Set<AuthRole> getAllAuthRoleByUserId(@Param(value = "userId") Integer userId);


    AuthRole findByCode(String user);
}
