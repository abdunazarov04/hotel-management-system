package javachi.biz.hotelmanagementsystem.service.mapper;
import javachi.biz.hotelmanagementsystem.domain.RoomEntity;
import javachi.biz.hotelmanagementsystem.dto.req.RoomRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.RoomResponseDto;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public RoomEntity toEntity(RoomRequestDto dto) {
        return RoomEntity.builder()
                .roomCapacity(dto.getRoomCapacity())
                .roomNumber(dto.getRoomNumber())
                .roomPrice(dto.getRoomPrice())
                .roomStatus(dto.getRoomStatus())
                .roomType(dto.getRoomType())
                .build();
    }

    public RoomResponseDto toDto(RoomEntity dto) {
        return RoomResponseDto.builder()
                .id(dto.getId())
                .roomCapacity(dto.getRoomCapacity())
                .roomNumber(dto.getRoomNumber())
                .roomPrice(dto.getRoomPrice())
                .roomStatus(dto.getRoomStatus())
                .roomType(dto.getRoomType())
                .build();
    }
}


