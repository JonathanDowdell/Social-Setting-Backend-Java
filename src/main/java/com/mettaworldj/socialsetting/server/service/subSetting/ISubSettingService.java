package com.mettaworldj.socialsetting.server.service.subSetting;

import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import com.mettaworldj.socialsetting.server.dto.subSetting.request.SubSettingRequestDto;
import com.mettaworldj.socialsetting.server.dto.subSetting.response.SubSettingResponseDto;

import java.util.List;

public interface ISubSettingService {

    SubSettingResponseDto createSubSetting(SubSettingRequestDto subSettingRequestDto);
    List<PostResponseDto> getSubSettingFeedByName(String subSettingName, int page, int amount, boolean profileInfo);
    void followSubSettingByName(String subSettingName);
    void unfollowSubSettingByName(String subSettingName);
}
