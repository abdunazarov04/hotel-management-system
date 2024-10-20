package javachi.biz.hotelmanagementsystem.service;

import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.RoomRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.RoomResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {

    HttpApiResponse<RoomResponseDto> createRoom(RoomRequestDto dto);

    HttpApiResponse<RoomResponseDto> getRoomById(Integer id);

    HttpApiResponse<RoomResponseDto> updateRoom(Integer id, RoomRequestDto userDto);

    HttpApiResponse<List<RoomResponseDto>> getAllRooms();

    HttpApiResponse<RoomResponseDto> deleteRoom(Integer id);

}
