package com.user.service.mapper;

import com.user.service.dto.request.UserRegisterRequestDto;
import com.user.service.dto.response.UserResponseDto;
import com.user.service.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target="status", expression = "java(com.user.service.enums.UserStatusEnum.ACTIVE)")
    @Mapping(target = "role", expression = "java(com.user.service.enums.UserRoleEnum.STUDENT)")
    UserEntity ToEntityForRegister(UserRegisterRequestDto request);

    UserResponseDto toResponse(UserEntity user);
}
