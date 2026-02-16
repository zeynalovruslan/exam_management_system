package com.user.service.service;

import com.user.service.dto.request.UserRegisterRequestDto;
import com.user.service.dto.response.UserRegisterResponseDto;
import com.user.service.enums.UserRoleEnum;

public interface UserService {


    void setRoleAdmin(Long userId, UserRoleEnum role);


}
