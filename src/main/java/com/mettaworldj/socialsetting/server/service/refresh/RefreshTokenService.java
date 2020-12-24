package com.mettaworldj.socialsetting.server.service.refresh;

import com.mettaworldj.socialsetting.server.model.RefreshTokenEntity;
import com.mettaworldj.socialsetting.server.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService implements IRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshTokenEntity> validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokenEntity generateRefreshToken() {
        final RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setToken(UUID.randomUUID().toString());
        refreshTokenEntity.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshTokenEntity);
    }

}
