package com.mettaworldj.socialsetting.server.repository;

import com.mettaworldj.socialsetting.server.model.SubSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubSettingRepository extends JpaRepository<SubSettingEntity, Long> {
    Optional<SubSettingEntity> findByName(String name);
    Integer countAllBySubSettingId(Long id);
}
