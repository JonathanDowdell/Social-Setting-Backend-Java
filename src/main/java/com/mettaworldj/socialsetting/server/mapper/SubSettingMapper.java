package com.mettaworldj.socialsetting.server.mapper;

import com.mettaworldj.socialsetting.server.dto.subSetting.response.SubSettingResponseDto;
import com.mettaworldj.socialsetting.server.model.SubSettingEntity;
import com.mettaworldj.socialsetting.server.repository.PostRepository;
import com.mettaworldj.socialsetting.server.service.post.PostService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Mapper(componentModel = "spring")
@DecoratedWith(SubSettingMapper.SubSettingMapperDecorator.class)
public interface SubSettingMapper {


    SubSettingResponseDto mapToDto(SubSettingEntity subSettingEntity);

    class SubSettingMapperDecorator implements SubSettingMapper {

        @Autowired
        @Qualifier("delegate")
        private SubSettingMapper delegate;

        @Autowired
        private PostService postService;

        @Autowired
        private PostRepository postRepository;

        @Override
        public SubSettingResponseDto mapToDto(SubSettingEntity subSettingEntity) {
            return SubSettingResponseDto.builder()
                    .id(subSettingEntity.getSubSettingId())
                    .name(subSettingEntity.getName())
                    .description(subSettingEntity.getDescription())
                    .numberOfPost(postRepository.countAllBySubSettingId(subSettingEntity.getSubSettingId()))
                    .build();
        }
    }
}
