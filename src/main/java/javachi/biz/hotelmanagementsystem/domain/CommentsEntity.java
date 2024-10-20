package javachi.biz.hotelmanagementsystem.domain;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsEntity extends BaseEntity{

    private Integer userId;
    private String message;
    private Integer hotelId;
}
