package com.mettaworldj.socialsetting.server.repository;

import com.mettaworldj.socialsetting.server.model.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, PostEntity.PostEntityId> {
    List<PostEntity> findAllBySubSettingId(Long id, Pageable pageable);

    Long countAllBySubSettingId(Long id);
}
