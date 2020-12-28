package com.mettaworldj.socialsetting.server.service.subSetting;

import com.mettaworldj.socialsetting.server.dto.subSetting.request.SubSettingRequestDto;
import com.mettaworldj.socialsetting.server.dto.subSetting.response.SubSettingFeedResponseDto;
import com.mettaworldj.socialsetting.server.dto.subSetting.response.SubSettingResponseDto;

public interface ISubSettingService {

    SubSettingResponseDto createSubSetting(SubSettingRequestDto subSettingRequestDto);
    SubSettingFeedResponseDto getSubSettingFeedByName(String subSettingName, int page, int amount, boolean profileInfo);
    void followSubSettingByName(String subSettingName);
    void unfollowSubSettingByName(String subSettingName);
}
