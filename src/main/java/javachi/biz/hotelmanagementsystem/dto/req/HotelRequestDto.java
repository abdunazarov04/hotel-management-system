package javachi.biz.hotelmanagementsystem.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequestDto {
    @NotNull
    @NotBlank(message = "Hotel name cannot be blank or null")
    private String name;
    @NotNull
    @NotBlank(message = "Hotel name cannot be blank or null")
    private String phone;
    @NotNull
    @NotBlank(message = "Hotel name cannot be blank or null")
    private String address;
}
