package com.user.service.service.impl;

import com.user.service.dto.response.UserResponseDto;
import com.user.service.entity.UserEntity;
import com.user.service.enums.UserRoleEnum;
import com.user.service.enums.UserStatusEnum;
import com.user.service.exception.BadRequestException;
import com.user.service.exception.UserNotFoundException;
import com.user.service.mapper.UserMapper;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getUserList() {
        List<UserEntity> users = userRepository.findAllByStatus(UserStatusEnum.ACTIVE);
        return users.stream().map(userMapper::toResponse).toList();
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        UserEntity user = userRepository.findByIdAndStatus(userId, UserStatusEnum.ACTIVE).orElseThrow(() ->
                new UserNotFoundException("User with id " + userId + " not found"));
        return userMapper.toResponse(user);
    }


    @Override
    public void setRoleAdmin(Long userId) {
        UserEntity user = userRepository.findByIdAndStatus(userId, UserStatusEnum.ACTIVE).orElseThrow(() ->
                new UserNotFoundException("User not found with userId : " + userId));
       user.setRole(UserRoleEnum.ADMIN);
       userRepository.save(user);
    }

}
