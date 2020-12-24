package com.mettaworldj.socialsetting.server.service.refresh;

import com.mettaworldj.socialsetting.server.model.RefreshTokenEntity;

import java.util.Optional;

public interface IRefreshTokenService {
    Optional<RefreshTokenEntity> validateRefreshToken(String token);
    RefreshTokenEntity generateRefreshToken();
}
