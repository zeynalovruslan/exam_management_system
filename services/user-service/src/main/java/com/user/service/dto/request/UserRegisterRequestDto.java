package com.user.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Size;


@Data
public class UserRegisterRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @Size(min = 6, max = 100)
    private String password;

}
