package com.user.service.dto.response;

import lombok.Data;

@Data
public class UserLoginResponseDto {
    private Long id;
    private String username;
    private String role;
    private String accessToken;

}
