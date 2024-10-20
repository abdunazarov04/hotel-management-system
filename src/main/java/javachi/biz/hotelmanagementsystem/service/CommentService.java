package javachi.biz.hotelmanagementsystem.service;

import javachi.biz.hotelmanagementsystem.dto.HttpApiResponse;
import javachi.biz.hotelmanagementsystem.dto.req.CommentRequestDto;
import javachi.biz.hotelmanagementsystem.dto.resp.CommentResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    HttpApiResponse<CommentResponseDto> createComment(CommentRequestDto dto);

    HttpApiResponse<CommentResponseDto> getCommentById(Integer id);

    HttpApiResponse<CommentResponseDto> updateComment(Integer id, CommentRequestDto userDto);

    HttpApiResponse<List<CommentResponseDto>> getAllComments();

    HttpApiResponse<CommentResponseDto> deleteComments(Integer id);

}
