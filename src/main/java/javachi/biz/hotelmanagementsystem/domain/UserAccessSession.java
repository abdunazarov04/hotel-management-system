package javachi.biz.hotelmanagementsystem.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(timeToLive = 60 * 3)
public class UserAccessSession {

    @Id
    private String sessionId;
    private String email;
    private Integer userId;

}
