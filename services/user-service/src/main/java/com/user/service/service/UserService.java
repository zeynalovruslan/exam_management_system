package com.user.service.service;

import com.user.service.dto.response.UserResponseDto;
import com.user.service.enums.UserRoleEnum;

import java.util.List;

public interface UserService {

    void setRoleAdmin(Long userId);

    List<UserResponseDto> getUserList();

    UserResponseDto getUserById(Long userId);


}
