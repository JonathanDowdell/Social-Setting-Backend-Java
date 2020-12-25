package com.mettaworldj.socialsetting.server.service.vote;

import com.mettaworldj.socialsetting.server.dto.vote.request.VoteRequestDto;

public interface IVoteService {
    void vote(String subSettingName, long postId, VoteRequestDto voteRequestDto);
}
