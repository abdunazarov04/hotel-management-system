package javachi.biz.hotelmanagementsystem.dto.resp;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import javachi.biz.hotelmanagementsystem.domain.RoomType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDto {
    private Integer id;
    @NotNull(message = "Room type cannot by blank or null")
    private RoomType roomType;
    @NotNull(message = "Room number cannot bu blank or null")
    private String roomNumber;
    @Size(min = 1, max = 4)
    private Integer roomCapacity;
    @Min(value = 100_000)
    private Double roomPrice;
    private Boolean roomStatus;
}
