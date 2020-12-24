package com.mettaworldj.socialsetting.server.service.user;

import com.mettaworldj.socialsetting.server.dto.user.response.UserResponseDto;

public interface IUserService {
    UserResponseDto findUserById(long id);
}
