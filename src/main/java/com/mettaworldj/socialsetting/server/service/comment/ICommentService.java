package com.mettaworldj.socialsetting.server.service.comment;

import com.mettaworldj.socialsetting.server.dto.comment.request.CommentRequestDto;
import com.mettaworldj.socialsetting.server.dto.comment.response.CommentResponseDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;

public interface ICommentService {
    CommentResponseDto createComment(String subSettingName, Long postId, CommentRequestDto commentRequestDto);
    List<CommentResponseDto> getCommentsFromPost(String subSettingName, Long postId, int page, int amount);
}
