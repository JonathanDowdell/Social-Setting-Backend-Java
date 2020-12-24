package com.mettaworldj.socialsetting.server.controller;

import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import com.mettaworldj.socialsetting.server.dto.subSetting.request.SubSettingRequestDto;
import com.mettaworldj.socialsetting.server.dto.subSetting.response.SubSettingResponseDto;
import com.mettaworldj.socialsetting.server.service.subSetting.ISubSettingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/subSetting")
@AllArgsConstructor
public class SubSettingController {

    private final ISubSettingService subSettingService;

    @PostMapping
    public ResponseEntity<SubSettingResponseDto> createSubSetting(@RequestBody SubSettingRequestDto subSettingRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subSettingService.createSubSetting(subSettingRequestDto));
    }

    @GetMapping("/{subSettingName}")
    public ResponseEntity<List<PostResponseDto>> getSubSettingByName(@PathVariable String subSettingName,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "5") int amount,
                                                                     @RequestParam(defaultValue = "true") boolean info) {
        return ResponseEntity.status(HttpStatus.OK).body(subSettingService.getSubSettingFeedByName(subSettingName, page, amount, info));
    }

    @PutMapping("/follow/{subSettingName}")
    public ResponseEntity<Boolean> followSubSettingByName(@PathVariable String subSettingName) {
        subSettingService.followSubSettingByName(subSettingName);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PutMapping("/unfollow/{subSettingName}")
    public ResponseEntity<Boolean> unfollowSubSettingByName(@PathVariable String subSettingName) {
        subSettingService.unfollowSubSettingByName(subSettingName);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

}
