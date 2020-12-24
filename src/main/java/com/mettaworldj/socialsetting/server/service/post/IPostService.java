package com.mettaworldj.socialsetting.server.service.post;

import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import com.mettaworldj.socialsetting.server.dto.post.request.PostRequestDto;
import com.mettaworldj.socialsetting.server.model.SubSettingEntity;

import java.util.List;

public interface IPostService {
    PostResponseDto createPost(String subSettingName, PostRequestDto postRequestDto);
    List<PostResponseDto> getFeed(int page, int amount);

    long countAllPostBySubSettingId(SubSettingEntity subSettingEntity);
}
