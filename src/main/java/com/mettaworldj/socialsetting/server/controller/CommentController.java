package com.mettaworldj.socialsetting.server.controller;

import com.mettaworldj.socialsetting.server.dto.comment.request.CommentRequestDto;
import com.mettaworldj.socialsetting.server.dto.comment.response.CommentFeedResponseDto;
import com.mettaworldj.socialsetting.server.dto.comment.response.CommentResponseDto;
import com.mettaworldj.socialsetting.server.service.comment.ICommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subSetting/{subSettingName}/post/{postId}/comment")
@AllArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable String subSettingName,
                                                            @PathVariable Long postId,
                                                            @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.createComment(subSettingName, postId, commentRequestDto));
    }

    @GetMapping
    public ResponseEntity<CommentFeedResponseDto> getComments(@PathVariable String subSettingName,
                                                              @PathVariable Long postId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int amount,
                                                              @RequestParam(defaultValue = "true") boolean info) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getCommentsFromPost(subSettingName, postId, page, amount, info));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable String subSettingName,
                                                 @PathVariable Long postId,
                                                 @PathVariable Long commentId) {
        commentService.deleteById(subSettingName, postId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

}
