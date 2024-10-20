package javachi.biz.hotelmanagementsystem.service.impl;

import javachi.biz.hotelmanagementsystem.domain.CommentsEntity;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.CommentRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.CommentResponseDto;
import javachi.biz.hotelmanagementsystem.repository.CommentRepository;
import javachi.biz.hotelmanagementsystem.service.CommentService;
import javachi.biz.hotelmanagementsystem.service.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    @Override
    public HttpApiResponse<CommentResponseDto> createComment(CommentRequestDto dto) {
        CommentsEntity entity = commentMapper.toEntity(dto);
        this.commentRepository.save(entity);
        return HttpApiResponse.<CommentResponseDto>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("Comment created!")
                .content(commentMapper.toDto(entity))
                .build();
    }

    @Override
    public HttpApiResponse<CommentResponseDto> getCommentById(Integer id) {
        return HttpApiResponse.<CommentResponseDto>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("Get comment by id!")
                .content(commentMapper.toDto(commentRepository.findById(id).get()))
                .build();
    }

    @Override
    public HttpApiResponse<CommentResponseDto> updateComment(Integer id, CommentRequestDto userDto) {
        return null;
    }

    @Override
    public HttpApiResponse<List<CommentResponseDto>> getAllComments() {
        return null;
    }

    @Override
    public HttpApiResponse<CommentResponseDto> deleteComments(Integer id) {
        return null;
    }
}
