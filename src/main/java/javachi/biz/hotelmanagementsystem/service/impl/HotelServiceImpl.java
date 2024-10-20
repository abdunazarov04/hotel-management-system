package javachi.biz.hotelmanagementsystem.service.impl;

import jakarta.transaction.Transactional;
import javachi.biz.hotelmanagementsystem.domain.CommentsEntity;
import javachi.biz.hotelmanagementsystem.domain.HotelEntity;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.HotelRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.HotelResponseDto;
import javachi.biz.hotelmanagementsystem.exception.ContentNotFoundException;
import javachi.biz.hotelmanagementsystem.exception.ResourceNotFoundException;
import javachi.biz.hotelmanagementsystem.repository.CommentRepository;
import javachi.biz.hotelmanagementsystem.repository.HotelRepository;
import javachi.biz.hotelmanagementsystem.service.HotelService;
import javachi.biz.hotelmanagementsystem.service.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;
    private final CommentRepository commentRepository;

    @Override
    public HttpApiResponse<HotelResponseDto> createHotel(HotelRequestDto dto) {
        HotelEntity entity = hotelMapper.toEntityWithoutComments(dto);
        entity.setCreatedAt(LocalDateTime.now());
        hotelRepository.save(entity);
        return HttpApiResponse.<HotelResponseDto>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("Hotel is successfully created!")
                .content(hotelMapper.toDtoWithComments(entity))
                .build();
    }

    @Override
    public HttpApiResponse<HotelResponseDto> getHotelById(Integer id) {
        Optional<HotelEntity> byId = this.hotelRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ContentNotFoundException("Hotel not found with %d id!".formatted(id));
        }

        return HttpApiResponse.<HotelResponseDto>builder()
                .content(this.hotelMapper.toDtoWithComments(byId.get()))
                .code(HttpStatus.OK.value())
                .message("OK")
                .success(true)
                .build();
    }

    @Override
    public HttpApiResponse<HotelResponseDto> updateHotel(Integer id, HotelRequestDto userDto) {
        HotelEntity hotelEntity = this.hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        if (userDto.getName() != null) {
            hotelEntity.setName(userDto.getName());
        }
        if (userDto.getPhone() != null) {
            hotelEntity.setPhone(userDto.getPhone());
        }
        if (userDto.getAddress() != null) {
            hotelEntity.setAddress(userDto.getAddress());
        }
        this.hotelRepository.save(hotelEntity);
        return HttpApiResponse.<HotelResponseDto>builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .content(hotelMapper.toDtoWithComments(hotelEntity))
                .build();
    }

    @Override
    public HttpApiResponse<List<HotelResponseDto>> getAllHotels() {
        return HttpApiResponse.<List<HotelResponseDto>>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("OK")
                .content(this.hotelRepository.findAll().stream()
                        .map(hotelMapper::toDtoWithComments)
                        .peek(hotel -> hotel.setComments(commentRepository.findByHotelId(hotel.getId())))
                        .toList())
                .build();
    }


    @Override
    public HttpApiResponse<List<CommentsEntity>> getAllHotelComments(Integer id) {
        Optional<HotelEntity> hotel = this.hotelRepository.findById(id);
        return HttpApiResponse.<List<CommentsEntity>>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("OK")
                .content(this.commentRepository.findByHotelId(hotel.get().getId()))
                .build();
    }

    @Override
    @Transactional
    public HttpApiResponse<HotelResponseDto> deleteHotel(Integer id) {
        Optional<HotelEntity> byId = this.hotelRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ContentNotFoundException("Hotel not found with %d id!".formatted(id));
        }
        HotelEntity hotelEntity = byId.get();
        hotelEntity.setDeletedAt(LocalDateTime.now());
        this.hotelRepository.save(hotelEntity);

        return HttpApiResponse.<HotelResponseDto>builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message("Hotel is deleted!")
                .content(hotelMapper.toDtoWithComments(hotelEntity))
                .build();
    }
}
