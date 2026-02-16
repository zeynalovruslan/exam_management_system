package com.user.service.service.impl;

import com.user.service.dto.request.UserRegisterRequestDto;
import com.user.service.dto.response.UserRegisterResponseDto;
import com.user.service.entity.UserEntity;
import com.user.service.enums.UserRoleEnum;
import com.user.service.exception.BadRequestException;
import com.user.service.exception.UserNotFoundException;
import com.user.service.mapper.UserMapper;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;







    @Override
    public void setRoleAdmin(Long userId, UserRoleEnum role) {

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found with userId : " + userId));

        if (role == UserRoleEnum.ADMIN) {
            if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
                user.setRole(role);
            }
        }else {
            throw new BadRequestException("You are not admin");
        }
    }

}
