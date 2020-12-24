package com.mettaworldj.socialsetting.server.service.auth;

import com.mettaworldj.socialsetting.server.dto.auth.request.SignUpRequestDto;
import com.mettaworldj.socialsetting.server.dto.auth.response.AuthenticationResponseDto;
import com.mettaworldj.socialsetting.server.model.UserEntity;

import java.util.List;

public interface IAuthService {
    void signUp(SignUpRequestDto signUpRequestDto);
    AuthenticationResponseDto verifyAccount(int code);
    UserEntity currentUser();

    List<String> usernames();
}
