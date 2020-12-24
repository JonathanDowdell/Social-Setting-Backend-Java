package com.mettaworldj.socialsetting.server.repository;

import com.mettaworldj.socialsetting.server.model.FeedEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<FeedEntity, FeedEntity.FeedEntityId> {
    List<FeedEntity> findAllByUserIdOrderByPostIdDesc(Long userId, Pageable pageable);
    void deleteAllBySubSettingId(Long subSettingId);
    void deleteAllByUserIdAndSubSettingId(Long userId, Long subSettingId);
}
