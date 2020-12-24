package com.mettaworldj.socialsetting.server.service.subSetting;

import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import com.mettaworldj.socialsetting.server.dto.subSetting.request.SubSettingRequestDto;
import com.mettaworldj.socialsetting.server.dto.subSetting.response.SubSettingResponseDto;
import com.mettaworldj.socialsetting.server.exception.SocialSettingException;
import com.mettaworldj.socialsetting.server.mapper.PostMapper;
import com.mettaworldj.socialsetting.server.mapper.SubSettingMapper;
import com.mettaworldj.socialsetting.server.model.SubSettingEntity;
import com.mettaworldj.socialsetting.server.model.SubscriptionEntity;
import com.mettaworldj.socialsetting.server.model.UserEntity;
import com.mettaworldj.socialsetting.server.repository.PostRepository;
import com.mettaworldj.socialsetting.server.repository.SubSettingRepository;
import com.mettaworldj.socialsetting.server.repository.SubscriptionRepository;
import com.mettaworldj.socialsetting.server.service.auth.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class SubSettingService implements ISubSettingService {

    private final SubSettingRepository subSettingRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PostRepository postRepository;

    private final IAuthService authService;
    private final SubSettingMapper subSettingMapper;
    private final PostMapper postMapper;

    @Override
    public SubSettingResponseDto createSubSetting(SubSettingRequestDto subSettingRequestDto) {
        final UserEntity userEntity = authService.currentUser();

        final SubSettingEntity subSettingEntity = SubSettingEntity.builder()
                .name(subSettingRequestDto.getName())
                .description(subSettingRequestDto.getDescription())
                .userEntity(userEntity)
                .build();

        final SubSettingEntity savedSubSetting = subSettingRepository.save(subSettingEntity);

        final SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                .subSettingId(savedSubSetting.getSubSettingId())
                .userId(userEntity.getUserId())
                .subSettingEntity(savedSubSetting)
                .userEntity(userEntity)
                .build();

        subscriptionRepository.save(subscriptionEntity);

        return subSettingMapper.mapToDto(savedSubSetting);
    }

    @Override
    public List<PostResponseDto> getSubSettingFeedByName(String subSettingName, int page, int amount, boolean info) {
        final UserEntity userEntity = authService.currentUser();
        final SubSettingEntity subSettingEntity = subSettingRepository.findByName(subSettingName)
                .orElseThrow(() -> new SocialSettingException("SubSetting Not Found", HttpStatus.NOT_FOUND));

        return postRepository.findAllBySubSettingId(subSettingEntity.getSubSettingId(), PageRequest.of(page, amount))
                .stream()
                .map(postMapper::mapPostToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void followSubSettingByName(String subSettingName) {
        final UserEntity userEntity = authService.currentUser();
        final SubSettingEntity subSettingEntity = subSettingRepository.findByName(subSettingName)
                .orElseThrow(() -> new SocialSettingException("SubSetting Not Found", HttpStatus.NOT_FOUND));
        final SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                .subSettingId(subSettingEntity.getSubSettingId())
                .userId(userEntity.getUserId())
                .subSettingEntity(subSettingEntity)
                .userEntity(userEntity)
                .build();
        subscriptionRepository.save(subscriptionEntity);
    }

    @Override
    public void unfollowSubSettingByName(String subSettingName) {
        final UserEntity userEntity = authService.currentUser();
        final SubSettingEntity subSettingEntity = subSettingRepository.findByName(subSettingName)
                .orElseThrow(() -> new SocialSettingException("SubSetting Not Found", HttpStatus.NOT_FOUND));
        subscriptionRepository.deleteById(new SubscriptionEntity.SubscriptionEntityId(subSettingEntity.getSubSettingId(), userEntity.getUserId()));
    }
}
