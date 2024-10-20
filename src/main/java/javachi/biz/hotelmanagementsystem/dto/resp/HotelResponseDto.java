package javachi.biz.hotelmanagementsystem.dto.resp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javachi.biz.hotelmanagementsystem.domain.CommentsEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponseDto {
    private Integer id;
    @NotNull
    @NotBlank(message = "Hotel name cannot be blank or null")
    private String name;
    private List<CommentsEntity> comments;
    @NotNull
    @NotBlank(message = "Hotel name cannot be blank or null")
    private String phone;
    @NotNull
    @NotBlank(message = "Hotel name cannot be blank or null")
    private String address;
}
