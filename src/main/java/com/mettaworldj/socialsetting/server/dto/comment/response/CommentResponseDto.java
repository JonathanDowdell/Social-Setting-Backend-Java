package com.mettaworldj.socialsetting.server.dto.comment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private long id;
    private long postId;
    private String text;
    private String username;
    private String duration;
}
