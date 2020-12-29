package com.mettaworldj.socialsetting.server.mapper;

import com.mettaworldj.socialsetting.server.dto.subSetting.response.SubSettingResponseDto;
import com.mettaworldj.socialsetting.server.model.SubSettingEntity;
import com.mettaworldj.socialsetting.server.repository.PostRepository;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@DecoratedWith(SubSettingMapper.SubSettingMapperDecorator.class)
public interface SubSettingMapper {


    SubSettingResponseDto mapToDto(SubSettingEntity subSettingEntity);

    class SubSettingMapperDecorator implements SubSettingMapper {

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
