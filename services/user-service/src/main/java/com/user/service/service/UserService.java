package com.user.service.service;

import com.user.service.dto.request.UserRegisterRequestDto;
import com.user.service.dto.response.UserRegisterResponseDto;

public interface UserService {

UserRegisterResponseDto register(UserRegisterRequestDto userRegisterRequestDto);


}
