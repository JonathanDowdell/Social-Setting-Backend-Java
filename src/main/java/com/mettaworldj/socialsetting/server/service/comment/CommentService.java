package com.mettaworldj.socialsetting.server.service.comment;

import com.mettaworldj.socialsetting.server.dto.comment.request.CommentRequestDto;
import com.mettaworldj.socialsetting.server.dto.comment.response.CommentResponseDto;
import com.mettaworldj.socialsetting.server.exception.SocialSettingException;
import com.mettaworldj.socialsetting.server.mapper.CommentMapper;
import com.mettaworldj.socialsetting.server.model.CommentEntity;
import com.mettaworldj.socialsetting.server.model.PostEntity;
import com.mettaworldj.socialsetting.server.model.SubSettingEntity;
import com.mettaworldj.socialsetting.server.model.UserEntity;
import com.mettaworldj.socialsetting.server.repository.CommentRepository;
import com.mettaworldj.socialsetting.server.repository.PostRepository;
import com.mettaworldj.socialsetting.server.repository.SubSettingRepository;
import com.mettaworldj.socialsetting.server.service.auth.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CommentService implements ICommentService{

    private final CommentRepository commentRepository;
    private final SubSettingRepository subSettingRepository;
    private final PostRepository postRepository;

    private final IAuthService authService;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponseDto createComment(String subSettingName, Long postId, CommentRequestDto commentRequestDto) {
        final UserEntity currentUser = authService.currentUser();
        final SubSettingEntity subSettingEntity = subSettingRepository.findByName(subSettingName)
                .orElseThrow(() -> new SocialSettingException("SubSetting not found", HttpStatus.NOT_FOUND));
        final PostEntity postEntity = postRepository.findById(new PostEntity.PostEntityId(subSettingEntity.getSubSettingId(), postId))
                .orElseThrow(() -> new SocialSettingException("Post not found", HttpStatus.NOT_FOUND));

        final CommentEntity commentEntity = CommentEntity.builder()
                .subSettingId(subSettingEntity.getSubSettingId())
                .postId(postId)
                .text(commentRequestDto.getText())
                .userEntity(currentUser)
                .postEntity(postEntity)
                .createdDate(Instant.now())
                .build();

        final CommentEntity savedComment = commentRepository.save(commentEntity);
        return commentMapper.mapToDto(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsFromPost(String subSettingName, Long postId, int page, int amount) {
        final SubSettingEntity subSettingEntity = subSettingRepository.findByName(subSettingName)
                .orElseThrow(() -> new SocialSettingException("SubSetting not found", HttpStatus.NOT_FOUND));
        return commentRepository.getAllBySubSettingIdAndPostId(subSettingEntity.getSubSettingId(), postId, PageRequest.of(page, amount))
                .stream().map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
