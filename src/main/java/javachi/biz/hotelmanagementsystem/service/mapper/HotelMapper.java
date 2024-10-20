package javachi.biz.hotelmanagementsystem.service.mapper;

import javachi.biz.hotelmanagementsystem.domain.HotelEntity;
import javachi.biz.hotelmanagementsystem.dto.req.HotelRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.HotelResponseDto;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {
    public HotelEntity toEntityWithoutComments(HotelRequestDto dto) {
        return HotelEntity.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .build();
    }
    public HotelEntity toEntityWithComments(HotelResponseDto dto) {
        return HotelEntity.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .comments(dto.getComments())
                .phone(dto.getPhone())

                .build();
    }

    public HotelRequestDto toDtoWithoutComments(HotelEntity entity) {
        return HotelRequestDto.builder()
                .name(entity.getName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .build();
    }

    public HotelResponseDto toDtoWithComments(HotelEntity entity) {
        return HotelResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .comments(entity.getComments())
                .build();
    }
}
