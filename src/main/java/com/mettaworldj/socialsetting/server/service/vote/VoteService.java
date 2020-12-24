package com.mettaworldj.socialsetting.server.service.vote;

import com.mettaworldj.socialsetting.server.dto.vote.request.VoteRequestDto;
import com.mettaworldj.socialsetting.server.exception.SocialSettingException;
import com.mettaworldj.socialsetting.server.model.PostEntity;
import com.mettaworldj.socialsetting.server.model.PostVoteEntity;
import com.mettaworldj.socialsetting.server.model.UserEntity;
import com.mettaworldj.socialsetting.server.repository.PostRepository;
import com.mettaworldj.socialsetting.server.repository.PostVoteRepository;
import com.mettaworldj.socialsetting.server.service.auth.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService implements IVoteService {

    private final PostVoteRepository postVoteRepository;
    private final PostRepository postRepository;

    private final IAuthService authService;

    @Override
    public void vote(long subSettingId, long postId, VoteRequestDto voteRequestDto) {
        final UserEntity currentUser = authService.currentUser();
        final PostEntity postEntity = postRepository.findById(new PostEntity.PostEntityId(postId, subSettingId))
                .orElseThrow(() -> new SocialSettingException("Post not found", HttpStatus.NOT_FOUND));
        final Optional<PostVoteEntity> voteEntity = postVoteRepository
                .findById(new PostVoteEntity.PostVoteEntityId(currentUser.getUserId(), postId, subSettingId));
        if (voteEntity.isPresent() && voteEntity.get().getVoteType().equals(voteRequestDto.getVoteType())) {
            throw new SocialSettingException("You have already " + voteRequestDto.getVoteType() + "'d for this post", HttpStatus.ALREADY_REPORTED);
        }

        if (PostVoteEntity.VoteType.UPVOTE.equals(voteRequestDto.getVoteType())) {
            postEntity.setVoteCount(postEntity.getVoteCount() + 1);
            final PostVoteEntity votedPost = PostVoteEntity.builder()
                    .userId(currentUser.getUserId())
                    .postId(postId)
                    .subSettingId(subSettingId)
                    .userEntity(currentUser)
                    .postEntity(postEntity)
                    .voteType(voteRequestDto.getVoteType())
                    .build();
            postVoteRepository.save(votedPost);
        } else {
            postEntity.setVoteCount(postEntity.getVoteCount() - 1);
            voteEntity.ifPresent(postVoteRepository::delete);
        }

        postRepository.save(postEntity);
    }
}
