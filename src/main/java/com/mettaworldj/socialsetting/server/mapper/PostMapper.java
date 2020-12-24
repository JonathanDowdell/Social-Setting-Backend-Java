package com.mettaworldj.socialsetting.server.mapper;

import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import com.mettaworldj.socialsetting.server.model.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "userPublicId", source = "postEntity.userEntity.publicId")
    @Mapping(target = "username", source = "postEntity.userEntity.username")
    @Mapping(target = "subSettingName", source = "postEntity.subSettingEntity.name")
    PostResponseDto mapPostToDto(PostEntity postEntity);

}
