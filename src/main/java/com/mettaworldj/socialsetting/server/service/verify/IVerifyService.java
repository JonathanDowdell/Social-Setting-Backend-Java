package com.mettaworldj.socialsetting.server.service.verify;

import com.mettaworldj.socialsetting.server.model.UserEntity;
import com.mettaworldj.socialsetting.server.model.VerificationTokenEntity;

public interface IVerifyService {
    VerificationTokenEntity createVerificationToken(UserEntity userEntity);
}
