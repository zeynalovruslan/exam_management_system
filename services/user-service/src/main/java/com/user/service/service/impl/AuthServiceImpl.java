package com.user.service.service.impl;

import com.user.service.dto.request.UserLoginRequestDto;
import com.user.service.dto.request.UserRegisterRequestDto;
import com.user.service.dto.response.UserLoginResponseDto;
import com.user.service.dto.response.UserRegisterResponseDto;
import com.user.service.entity.UserEntity;
import com.user.service.enums.UserStatusEnum;
import com.user.service.exception.BadRequestException;
import com.user.service.exception.UserNotFoundException;
import com.user.service.mapper.UserMapper;
import com.user.service.repository.UserRepository;
import com.user.service.service.AuthService;
import com.user.service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Override
    public UserRegisterResponseDto register(UserRegisterRequestDto userRegisterRequestDto) {

        if (userRepository.existsByUsername(userRegisterRequestDto.getUsername())) {
            throw new BadRequestException("Username is already using : " + userRegisterRequestDto.getUsername());
        }

        if (userRepository.existsByEmail(userRegisterRequestDto.getEmail())) {
            throw new BadRequestException("Email is already using : " + userRegisterRequestDto.getEmail());
        }
        UserEntity toEntity = userMapper.ToEntityForRegister(userRegisterRequestDto);
        toEntity.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));
        UserEntity savedUser = userRepository.save(toEntity);

        return userMapper.toRegisterResponse(savedUser);
    }

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {

        UserEntity user = userRepository.findByUsername(userLoginRequestDto.getUsername()).orElseThrow(
                () -> new UserNotFoundException("User not found with username : " + userLoginRequestDto.getUsername())
        );

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(),user.getRole());


        if (!user.getStatus().equals(UserStatusEnum.ACTIVE)){
            throw new BadRequestException("User is not active : " + userLoginRequestDto.getUsername());
        }

        boolean matches = passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword());

        if (!matches){
            throw new BadRequestException("Wrong password : " + userLoginRequestDto.getPassword());
        }

        UserLoginResponseDto response = new UserLoginResponseDto();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().name());
        response.setAccessToken(token);

        return response;
    }
}
