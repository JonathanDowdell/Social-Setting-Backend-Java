package com.mettaworldj.socialsetting.server.dto.vote.request;

import com.mettaworldj.socialsetting.server.model.PostVoteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequestDto {

    private PostVoteEntity.VoteType voteType;

}