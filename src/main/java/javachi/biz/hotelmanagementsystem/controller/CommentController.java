package javachi.biz.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.CommentRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.CommentResponseDto;
import javachi.biz.hotelmanagementsystem.service.impl.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@PreAuthorize(value = "hasRole('USER') or hasRole('ADMIN')")
public class CommentController {


    private final CommentServiceImpl commentService;

    @PostMapping("/create")
    @Operation(summary = "Create comment!")
    public HttpApiResponse<CommentResponseDto> createComment(@RequestBody @Valid CommentRequestDto dto) {
        return this.commentService.createComment(dto);
    }

    @GetMapping("/get-by-id")
    @Operation(summary = "Get comment by id!")
    public HttpApiResponse<CommentResponseDto> getCommentById(@RequestParam Integer commentId) {
        return this.commentService.getCommentById(commentId);
    }

}
