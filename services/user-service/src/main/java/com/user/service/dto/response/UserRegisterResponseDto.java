package com.user.service.dto.response;

import lombok.Data;

@Data
public class UserRegisterResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
}
