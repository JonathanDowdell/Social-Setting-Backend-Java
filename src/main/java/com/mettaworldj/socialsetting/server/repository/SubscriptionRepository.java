package com.mettaworldj.socialsetting.server.repository;

import com.mettaworldj.socialsetting.server.model.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, SubscriptionEntity.SubscriptionEntityId> {
    List<SubscriptionEntity> findAllBySubSettingId(Long id);
}
