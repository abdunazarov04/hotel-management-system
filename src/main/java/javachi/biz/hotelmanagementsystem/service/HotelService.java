package javachi.biz.hotelmanagementsystem.service;

import javachi.biz.hotelmanagementsystem.domain.CommentsEntity;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.HotelRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.HotelResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelService {

    HttpApiResponse<HotelResponseDto> createHotel(HotelRequestDto dto);

    HttpApiResponse<HotelResponseDto> getHotelById(Integer id);

    HttpApiResponse<HotelResponseDto> updateHotel(Integer id, HotelRequestDto userDto);

    HttpApiResponse<List<HotelResponseDto>> getAllHotels();

    HttpApiResponse<List<CommentsEntity>> getAllHotelComments(Integer id);

    HttpApiResponse<HotelResponseDto> deleteHotel(Integer id);

}
