package javachi.biz.hotelmanagementsystem.domain;

import javachi.biz.hotelmanagementsystem.domain.auth.AuthUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@Builder
@RedisHash(timeToLive = 60 * 15)
public class UserSession {

    @Id
    private String sessionId;
    private String email;
    private AuthUser authUser;

}
