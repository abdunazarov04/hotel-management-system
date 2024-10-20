package javachi.biz.hotelmanagementsystem.dto.resp;

import javachi.biz.hotelmanagementsystem.domain.RoomType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDto {
    private Integer id;
    private RoomType roomType;
    private Integer roomNumber;
    private Integer roomCapacity;
    private Double roomPrice;
    private Boolean roomStatus;
    private Integer hotelId;
}
