package javachi.biz.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.RoomRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.RoomResponseDto;
import javachi.biz.hotelmanagementsystem.service.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class RoomController {

    private final RoomServiceImpl roomServiceImpl;

    @PostMapping("/create")
    @Operation(summary = "Create room!")
    public HttpApiResponse<RoomResponseDto> createRoom(@RequestBody @Valid RoomRequestDto dto) {
        return roomServiceImpl.createRoom(dto);
    }

    @GetMapping("/get-room")
    @Operation(summary = "Get room by id!")
    public HttpApiResponse<RoomResponseDto> getRoom(@RequestParam Integer id) {
        return roomServiceImpl.getRoomById(id);
    }

    @PatchMapping("/update")
    @Operation(summary = "Update room information!")
    public HttpApiResponse<RoomResponseDto> updateRoom(@RequestParam Integer id, @RequestBody @Valid RoomRequestDto dto) {
        return roomServiceImpl.updateRoom(id, dto);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete room!")
    public HttpApiResponse<RoomResponseDto> deleteRoom(@RequestParam Integer id) {
        return roomServiceImpl.deleteRoom(id);
    }
}
