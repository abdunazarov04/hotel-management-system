package javachi.biz.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.RoomRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.RoomResponseDto;
import javachi.biz.hotelmanagementsystem.service.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class RoomController {

    private final RoomServiceImpl roomServiceImpl;

    @PostMapping("/create")
    @Operation(summary = "Create room!")
    public HttpApiResponse<RoomResponseDto> createRoom(@RequestParam Integer hotelId, @RequestBody @Valid RoomRequestDto dto) {
        return roomServiceImpl.createRoom(hotelId, dto);
    }

    @GetMapping("/get-room")
    @Operation(summary = "Get room by id!")
    public HttpApiResponse<RoomResponseDto> getRoom(@RequestParam Integer id) {
        return roomServiceImpl.getRoomById(id);
    }

    @PostMapping("/update")
    @Operation(summary = "Update room information!")
    public HttpApiResponse<RoomResponseDto> updateRoom(@RequestParam Integer id, @RequestBody @Valid RoomRequestDto dto) {
        return roomServiceImpl.updateRoom(id, dto);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete room!")
    public HttpApiResponse<RoomResponseDto> deleteRoom(@RequestParam Integer id) {
        return roomServiceImpl.deleteRoom(id);
    }

    @GetMapping("/getAllRooms")
    @Operation(summary = "Get all rooms")
    public HttpApiResponse<List<RoomResponseDto>> getAllRooms() {
        return roomServiceImpl.getAllRooms();
    }
}
