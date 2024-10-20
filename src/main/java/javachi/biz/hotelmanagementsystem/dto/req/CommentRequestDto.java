package javachi.biz.hotelmanagementsystem.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private Integer userId;
    @NotBlank(message = "Message cannot be null or blank")
    private String message;

    private Integer hotelId;
}
