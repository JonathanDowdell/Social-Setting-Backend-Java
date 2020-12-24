package com.mettaworldj.socialsetting.server.dto.post.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {

    private long subSettingId;
    private long postId;
    private String postName;
    private String description;
    private String url;
    private String userPublicId;
    private String username;
    private String subSettingName;
    private int voteCount;
    private int commentCount;
    private String duration;
    private boolean upVote;
    private boolean downVote;

}