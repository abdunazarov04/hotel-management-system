package javachi.biz.hotelmanagementsystem.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import javachi.biz.hotelmanagementsystem.domain.RoomType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDto {
    private RoomType roomType;
    @Positive
    private Integer roomNumber;
    @Min(value = 1)
    @Max(value = 8)
    private Integer roomCapacity;
    @Min(value = 100_000)
    private Double roomPrice;
    private Boolean roomStatus;
    private Integer hotelId;
}
