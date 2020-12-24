package com.mettaworldj.socialsetting.server.dto.subSetting.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubSettingRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
