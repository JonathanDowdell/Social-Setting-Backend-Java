package com.mettaworldj.socialsetting.server.controller;

import com.mettaworldj.socialsetting.server.dto.vote.request.VoteRequestDto;
import com.mettaworldj.socialsetting.server.service.vote.IVoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subSetting/{subSettingName}/post/{postId}/vote")
@AllArgsConstructor
public class VoteController {

    private final IVoteService voteService;

    @PostMapping
    public void vote(@PathVariable String subSettingName,
                     @PathVariable Long postId,
                     @RequestBody VoteRequestDto voteRequestDto) {
        voteService.vote(subSettingName, postId, voteRequestDto);
    }

}
