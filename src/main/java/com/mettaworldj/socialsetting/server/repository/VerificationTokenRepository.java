package com.mettaworldj.socialsetting.server.repository;

import com.mettaworldj.socialsetting.server.model.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, VerificationTokenEntity.VerificationTokenEntityId> {
    Optional<VerificationTokenEntity> findByCode(int code);
    Optional<VerificationTokenEntity> findByVerificationTokenId(String token);
}
