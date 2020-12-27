package com.mettaworldj.socialsetting.server.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.mettaworldj.socialsetting.server.dto.comment.response.CommentResponseDto;
import com.mettaworldj.socialsetting.server.model.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Mapping(target = "id", source = "commentEntity.commentId")
    @Mapping(target = "username", source = "commentEntity.userEntity.username")
    @Mapping(target = "duration", expression = "java(getDuration(commentEntity))")
    public abstract CommentResponseDto mapToDto(CommentEntity commentEntity);

    String getDuration(CommentEntity commentEntity) {
        return TimeAgo.using(commentEntity.getCreatedDate().toEpochMilli());
    }

}
