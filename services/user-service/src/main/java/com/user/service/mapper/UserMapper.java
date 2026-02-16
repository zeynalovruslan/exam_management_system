package com.user.service.mapper;

import com.user.service.dto.request.UserRegisterRequestDto;
import com.user.service.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UserMapper {

UserEntity ToEntityForRegister(UserRegisterRequestDto request);
}
