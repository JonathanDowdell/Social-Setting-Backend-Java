package com.mettaworldj.socialsetting.server.dto.subSetting.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubSettingResponseDto {
    private long id;
    private String name;
    private String description;
    private long numberOfPost;
}
