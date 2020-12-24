package com.mettaworldj.socialsetting.server.service.post;

import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import com.mettaworldj.socialsetting.server.dto.post.request.PostRequestDto;
import com.mettaworldj.socialsetting.server.exception.SocialSettingException;
import com.mettaworldj.socialsetting.server.mapper.PostMapper;
import com.mettaworldj.socialsetting.server.model.FeedEntity;
import com.mettaworldj.socialsetting.server.model.PostEntity;
import com.mettaworldj.socialsetting.server.model.SubSettingEntity;
import com.mettaworldj.socialsetting.server.model.UserEntity;
import com.mettaworldj.socialsetting.server.repository.FeedRepository;
import com.mettaworldj.socialsetting.server.repository.PostRepository;
import com.mettaworldj.socialsetting.server.repository.SubSettingRepository;
import com.mettaworldj.socialsetting.server.repository.SubscriptionRepository;
import com.mettaworldj.socialsetting.server.service.auth.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PostService implements IPostService {

    private final SubSettingRepository subSettingRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PostRepository postRepository;
    private final FeedRepository feedRepository;

    private final IAuthService authService;
    private final PostMapper postMapper;

    @Override
    public PostResponseDto createPost(String subSettingName, PostRequestDto postRequestDto) {
        final UserEntity userEntity = authService.currentUser();
        final SubSettingEntity subSettingEntity = subSettingRepository.findByName(subSettingName)
                .orElseThrow(() -> new SocialSettingException("SubSetting Not Found", HttpStatus.NOT_FOUND));
        if (subSettingEntity.isProfile() && !userEntity.getUsername().equals(subSettingEntity.getName()))
            throw new SocialSettingException("Unauthorized", HttpStatus.UNAUTHORIZED);

        final PostEntity postEntity = PostEntity.builder()
                .subSettingId(subSettingEntity.getSubSettingId())
                .postName(postRequestDto.getPostName())
                .description(postRequestDto.getDescription())
                .userEntity(userEntity)
                .subSettingEntity(subSettingEntity)
                .url(postRequestDto.getUrl())
                .build();

        final PostEntity savedPost = postRepository.save(postEntity);

        final FeedEntity feedEntity = FeedEntity.builder()
                .userId(userEntity.getUserId())
                .postId(savedPost.getPostId())
                .subSettingId(subSettingEntity.getSubSettingId())
                .userEntity(userEntity)
                .postEntity(savedPost)
                .build();

        feedRepository.save(feedEntity);
        sendPostToFeeds(savedPost, subSettingEntity);
        return postMapper.mapPostToDto(savedPost);
    }

    @Async
    void sendPostToFeeds(PostEntity savedPost, SubSettingEntity subSettingEntity) {
        final List<FeedEntity> feedEntityList = new ArrayList<>();
        subscriptionRepository.findAllBySubSettingId(subSettingEntity.getSubSettingId()).forEach(subscriptionEntity -> {
            final UserEntity targetUser = subscriptionEntity.getUserEntity();
            final FeedEntity feedEntity = FeedEntity.builder()
                    .userId(targetUser.getUserId())
                    .postId(savedPost.getPostId())
                    .subSettingId(subSettingEntity.getSubSettingId())
                    .userEntity(targetUser)
                    .postEntity(savedPost)
                    .build();
            feedEntityList.add(feedEntity);
        });
        feedRepository.saveAll(feedEntityList);
    }

    @Override
    public long countAllPostBySubSettingId(SubSettingEntity subSettingEntity) {
        return postRepository.countAllBySubSettingId(subSettingEntity.getSubSettingId());
    }

    @Override
    public List<PostResponseDto> getFeed(int page, int amount) {
        final UserEntity userEntity = authService.currentUser();
        return feedRepository.findAllByUserIdOrderByPostIdDesc(userEntity.getUserId(), PageRequest.of(page, amount))
                .stream()
                .map(feedEntity -> postMapper.mapPostToDto(feedEntity.getPostEntity()))
                .collect(Collectors.toList());
    }
}
