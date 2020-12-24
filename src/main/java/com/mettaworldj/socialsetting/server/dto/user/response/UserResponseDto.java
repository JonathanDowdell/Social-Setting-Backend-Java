package com.mettaworldj.socialsetting.server.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String publicId;
    private String username;
    private String profileName;
    private String email;
    private String description;
    private String url;
    private int postCount;
    private int followerCount;
    private int followingCount;
}
