package com.mettaworldj.socialsetting.server.service.vote;

import com.mettaworldj.socialsetting.server.dto.vote.request.VoteRequestDto;

public interface IVoteService {
    void vote(long subSettingId, long postId, VoteRequestDto voteRequestDto);
}
