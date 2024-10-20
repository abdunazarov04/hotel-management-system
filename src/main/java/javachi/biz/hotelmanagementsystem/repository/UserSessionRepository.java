package javachi.biz.hotelmanagementsystem.repository;

import javachi.biz.hotelmanagementsystem.domain.UserSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, String> {
}
