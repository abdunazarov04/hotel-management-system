package javachi.biz.hotelmanagementsystem.service.mapper;

import javachi.biz.hotelmanagementsystem.domain.CommentsEntity;
import javachi.biz.hotelmanagementsystem.dto.req.CommentRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.CommentResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentsEntity toEntity(CommentRequestDto dto) {
        return CommentsEntity.builder()
                .message(dto.getMessage())
                .userId(dto.getUserId())
                .hotelId(dto.getHotelId())
                .build();
    }

    public CommentResponseDto toDto(CommentsEntity dto) {
        return CommentResponseDto.builder()
                .id(dto.getId())
                .message(dto.getMessage())
                .userId(dto.getUserId())
                .hotelId(dto.getHotelId())
                .build();
    }
}
