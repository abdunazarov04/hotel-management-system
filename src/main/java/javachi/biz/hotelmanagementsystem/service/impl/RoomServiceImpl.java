package javachi.biz.hotelmanagementsystem.service.impl;

import jakarta.transaction.Transactional;
import javachi.biz.hotelmanagementsystem.domain.RoomEntity;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.RoomRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.RoomResponseDto;
import javachi.biz.hotelmanagementsystem.exception.ResourceNotFoundException;
import javachi.biz.hotelmanagementsystem.repository.RoomRepository;
import javachi.biz.hotelmanagementsystem.service.RoomService;
import javachi.biz.hotelmanagementsystem.service.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public HttpApiResponse<RoomResponseDto> createRoom(RoomRequestDto dto) {
        RoomEntity entity = roomMapper.toEntity(dto);
        return HttpApiResponse.<RoomResponseDto>builder()
                .code(200)
                .success(true)
                .message("Room is created!")
                .content(roomMapper.toDto(roomRepository.save(entity)))
                .build();
    }

    @Override
    public HttpApiResponse<RoomResponseDto> getRoomById(Integer id) {
        return HttpApiResponse.<RoomResponseDto>builder()
                .success(true).message("OK")
                .code(HttpStatus.OK.value())
                .content(
                        this.roomMapper.toDto(
                                this.roomRepository.findById(id).orElseThrow(() ->
                                        new ResourceNotFoundException("Room is not found with %d id".formatted(id)
                                        )
                                )
                        )
                )
                .build();
    }

    @Override
    public HttpApiResponse<RoomResponseDto> updateRoom(Integer id, RoomRequestDto userDto) {

        RoomEntity entity = this.roomMapper.toEntity(userDto);
        if (userDto.getRoomNumber() != null) {
            entity.setRoomNumber(userDto.getRoomNumber());
        }
        if (userDto.getRoomCapacity() != null) {
            entity.setRoomCapacity(userDto.getRoomCapacity());
        }
        if (userDto.getRoomStatus() != null) {
            entity.setRoomStatus(userDto.getRoomStatus());
        }
        if (userDto.getRoomPrice() != null) {
            entity.setRoomPrice(userDto.getRoomPrice());
        }
        if (userDto.getRoomType() != null) {
            entity.setRoomType(userDto.getRoomType());
        }
        this.roomRepository.save(entity);

        return HttpApiResponse.<RoomResponseDto>builder()
                .code(200)
                .success(true)
                .message("Room is updated!")
                .content(roomMapper.toDto(entity))
                .build();
    }

    @Override
    public HttpApiResponse<List<RoomResponseDto>> getAllRooms() {
        return HttpApiResponse.<List<RoomResponseDto>>builder()
                .success(true)
                .message("OK")
                .code(HttpStatus.OK.value())
                .content(this.roomRepository.findAll().stream().map(roomMapper::toDto).toList())
                .build();
    }

    @Override
    @Transactional
    public HttpApiResponse<RoomResponseDto> deleteRoom(Integer id) {
        RoomEntity roomEntity = this.roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room is not found with %d id".formatted(id)));
        roomEntity.setDeletedAt(LocalDateTime.now());
        this.roomRepository.save(roomEntity);

        return HttpApiResponse.<RoomResponseDto>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("Room is deleted!")
                .content(roomMapper.toDto(roomEntity))
                .build();
    }
}
