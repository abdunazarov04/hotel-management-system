package javachi.biz.hotelmanagementsystem.repository;

import javachi.biz.hotelmanagementsystem.domain.UserAccessSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessSessionRepository extends CrudRepository<UserAccessSession, String> {
}
