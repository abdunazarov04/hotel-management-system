package javachi.biz.hotelmanagementsystem.service.impl;

import jakarta.transaction.Transactional;
import javachi.biz.hotelmanagementsystem.domain.CommentsEntity;
import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.CommentRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.CommentResponseDto;
import javachi.biz.hotelmanagementsystem.exception.ResourceNotFoundException;
import javachi.biz.hotelmanagementsystem.repository.CommentRepository;
import javachi.biz.hotelmanagementsystem.service.CommentService;
import javachi.biz.hotelmanagementsystem.service.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public HttpApiResponse<CommentResponseDto> updateComment(Integer id, CommentRequestDto dto) {
        CommentsEntity commentsEntity = this.commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found with %d id".formatted(id)));
        if (dto.getMessage() != null) {
            commentsEntity.setMessage(dto.getMessage());
        }
        if (dto.getHotelId() != null) {
            commentsEntity.setHotelId(dto.getHotelId());
        }

        if (dto.getUserId() != null) {
            commentsEntity.setUserId(dto.getUserId());
        }
        this.commentRepository.save(commentsEntity);

        return HttpApiResponse.<CommentResponseDto>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("Comment updated!")
                .content(commentMapper.toDto(commentsEntity))
                .build();
    }

    @Override
    public HttpApiResponse<List<CommentResponseDto>> getAllComments() {
        List<CommentsEntity> all = this.commentRepository.findAll();
        if (all.isEmpty()) {
            throw new ResourceNotFoundException("No comments are found!");
        }
        return HttpApiResponse.<List<CommentResponseDto>>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("Here is all comments!")
                .content(
                        all.stream().map(
                                commentMapper::toDto
                        ).toList()
                )
                .build();
    }

    @Override
    @Transactional
    public HttpApiResponse<CommentResponseDto> deleteComments(Integer id) {
        CommentsEntity commentsEntity = this.commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found with %d id".formatted(id)));
        commentsEntity.setDeletedAt(LocalDateTime.now());
        this.commentRepository.save(commentsEntity);
        return HttpApiResponse.<CommentResponseDto>builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("Comment deleted!")
                .content(commentMapper.toDto(commentsEntity))
                .build();
    }
}
