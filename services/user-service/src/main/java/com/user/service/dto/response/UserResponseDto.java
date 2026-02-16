package com.user.service.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
}
