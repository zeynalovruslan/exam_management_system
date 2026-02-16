package com.user.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
