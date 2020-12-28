package com.mettaworldj.socialsetting.server.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import com.mettaworldj.socialsetting.server.model.CommentEntity;
import com.mettaworldj.socialsetting.server.model.PostEntity;
import com.mettaworldj.socialsetting.server.model.PostVoteEntity;
import com.mettaworldj.socialsetting.server.model.UserEntity;
import com.mettaworldj.socialsetting.server.repository.PostVoteRepository;
import com.mettaworldj.socialsetting.server.service.auth.IAuthService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Mapper(componentModel = "spring")
@DecoratedWith(PostMapper.PostMapperDecorator.class)
public interface PostMapper {

    PostResponseDto mapPostToDto(PostEntity postEntity);


    class PostMapperDecorator implements PostMapper {

        @Autowired
        private PostVoteRepository postVoteRepository;

        @Autowired
        private IAuthService authService;

        @Override
        public PostResponseDto mapPostToDto(PostEntity postEntity) {

            final boolean upVoted = postVoteRepository.existsById(PostVoteEntity.PostVoteEntityId.builder()
                    .postId(postEntity.getPostId())
                    .subSettingId(postEntity.getSubSettingId())
                    .userId(authService.currentUser().getUserId())
                    .build()
            );

            return PostResponseDto.builder()
                    .subSettingId(postEntity.getSubSettingId())
                    .postId(postEntity.getPostId())
                    .postName(postEntity.getPostName())
                    .description(postEntity.getDescription())
                    .url(postEntity.getUrl())
                    .userPublicId(postEntity.getUserEntity().getPublicId())
                    .username(postEntity.getUserEntity().getUsername())
                    .subSettingName(postEntity.getSubSettingEntity().getName())
                    .voteCount(postEntity.getVoteCount())
                    .commentCount(postEntity.getCommentCount())
                    .duration(getDuration(postEntity))
                    .upVote(upVoted)
                    .build();
        }

        String getDuration(PostEntity postEntity) {
            return TimeAgo.using(postEntity.getCreatedDate().toEpochMilli());
        }
    }

}
