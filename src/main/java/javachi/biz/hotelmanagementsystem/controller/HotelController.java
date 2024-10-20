package javachi.biz.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import javachi.biz.hotelmanagementsystem.domain.CommentsEntity;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.HotelRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.HotelResponseDto;
import javachi.biz.hotelmanagementsystem.service.impl.HotelServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class HotelController {

    private final HotelServiceImpl hotelService;

    @GetMapping(value = "/get-by-id")
    @Operation(summary = "Get hotel with id")
    public HttpApiResponse<HotelResponseDto> getById(@RequestParam Integer id) {
        return this.hotelService.getHotelById(id);
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Create hotel")
    public HttpApiResponse<HotelResponseDto> createHotel(@RequestBody @Valid HotelRequestDto dto) {
        return this.hotelService.createHotel(dto);
    }

    @PatchMapping("/update")
    @Operation(summary = "Update user information")
    public HttpApiResponse<HotelResponseDto> updateHotelInfo(@RequestParam(value = "id") Integer authId,
                                                             @RequestBody HotelRequestDto dto) {
        return this.hotelService.updateHotel(authId, dto);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete hotel by id")
    public HttpApiResponse<HotelResponseDto> deleteHotel(@RequestParam(value = "id") Integer id) {
        return this.hotelService.deleteHotel(id);
    }

    @GetMapping(value = "/get-hotels")
    @Operation(summary = "Get all hotels")
    public HttpApiResponse<List<HotelResponseDto>> getAllHotels() {
        return this.hotelService.getAllHotels();
    }

    @GetMapping(value = "/get-comments")
    @Operation(summary = "Get all Hotel comments")
    public HttpApiResponse<List<CommentsEntity>> getHotelComments(@RequestParam Integer id) {
        return this.hotelService.getAllHotelComments(id);
    }

}