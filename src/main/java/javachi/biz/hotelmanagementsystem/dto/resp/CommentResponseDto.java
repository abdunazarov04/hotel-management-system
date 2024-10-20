package javachi.biz.hotelmanagementsystem.dto.resp;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Integer id;
    private String message;
    private Integer userId;
    private Integer hotelId;
}
