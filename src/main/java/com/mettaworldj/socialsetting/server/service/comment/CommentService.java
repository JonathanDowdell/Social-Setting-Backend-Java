package com.mettaworldj.socialsetting.server.service.comment;

import com.mettaworldj.socialsetting.server.dto.comment.request.CommentRequestDto;
import com.mettaworldj.socialsetting.server.dto.comment.response.CommentFeedResponseDto;
import com.mettaworldj.socialsetting.server.dto.comment.response.CommentResponseDto;
import com.mettaworldj.socialsetting.server.dto.post.reponse.PostResponseDto;
import com.mettaworldj.socialsetting.server.exception.SocialSettingException;
import com.mettaworldj.socialsetting.server.mapper.CommentMapper;
import com.mettaworldj.socialsetting.server.mapper.PostMapper;
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
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final SubSettingRepository subSettingRepository;
    private final PostRepository postRepository;

    private final PostMapper postMapper;
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
        postEntity.setCommentCount(postEntity.getCommentCount() + 1);
        postRepository.save(postEntity);
        return commentMapper.mapToDto(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentFeedResponseDto getCommentsFromPost(String subSettingName, Long postId, int page, int amount, boolean info) {
        final SubSettingEntity subSettingEntity = subSettingRepository.findByName(subSettingName)
                .orElseThrow(() -> new SocialSettingException("SubSetting not found", HttpStatus.NOT_FOUND));
        PostResponseDto postResponseDto = null;
        if (info) {
            final PostEntity postEntity = postRepository.findById(PostEntity.PostEntityId.builder()
                    .postId(postId)
                    .subSettingId(subSettingEntity.getSubSettingId())
                    .build())
                    .orElseThrow(() -> new SocialSettingException("Post Not Found", HttpStatus.NOT_FOUND));
            postResponseDto = postMapper.mapPostToDto(postEntity);
        }
        return CommentFeedResponseDto.builder()
                .post(postResponseDto)
                .comments(commentRepository.getAllBySubSettingIdAndPostId(subSettingEntity.getSubSettingId(), postId, PageRequest.of(page, amount))
                        .stream().map(commentMapper::mapToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public void deleteById(String subSettingName, Long postId, Long commentId) {
        final SubSettingEntity subSettingEntity = subSettingRepository.findByName(subSettingName)
                .orElseThrow(() -> new SocialSettingException("SubSetting not found", HttpStatus.NOT_FOUND));
        final PostEntity.PostEntityId postEntityId = PostEntity.PostEntityId.builder()
                .subSettingId(subSettingEntity.getSubSettingId())
                .postId(postId)
                .build();
        final PostEntity postEntity = postRepository.findById(postEntityId)
                .orElseThrow(() -> new SocialSettingException("Post not found", HttpStatus.NOT_FOUND));
        postEntity.setCommentCount(postEntity.getCommentCount() - 1);
        final CommentEntity.CommentEntityId commentEntityId = CommentEntity.CommentEntityId.builder()
                .subSettingId(subSettingEntity.getSubSettingId())
                .commentId(commentId)
                .postId(postId)
                .build();
        commentRepository.deleteById(commentEntityId);
    }
}
