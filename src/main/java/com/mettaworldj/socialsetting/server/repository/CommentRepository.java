package com.mettaworldj.socialsetting.server.repository;

import com.mettaworldj.socialsetting.server.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, CommentEntity.CommentEntityId> {
    List<CommentEntity> getAllBySubSettingIdAndPostId(Long subSettingId, Long postId, Pageable pageable);
}
