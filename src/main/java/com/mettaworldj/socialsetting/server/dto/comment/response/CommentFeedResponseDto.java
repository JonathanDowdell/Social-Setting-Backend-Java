package com.mettaworldj.socialsetting.server.dto.comment.response;

import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentFeedResponseDto {
    PostResponseDto post;
    List<CommentResponseDto> comments;
}
