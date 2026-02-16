package com.user.service.service;

import com.user.service.dto.request.UserLoginRequestDto;
import com.user.service.dto.request.UserRegisterRequestDto;
import com.user.service.dto.response.UserLoginResponseDto;
import com.user.service.dto.response.UserResponseDto;

public interface AuthService {
    UserResponseDto register(UserRegisterRequestDto userRegisterRequestDto);

    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);
}
