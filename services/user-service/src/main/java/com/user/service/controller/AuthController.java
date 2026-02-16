package com.user.service.controller;

import com.user.service.dto.request.UserLoginRequestDto;
import com.user.service.dto.request.UserRegisterRequestDto;
import com.user.service.dto.response.UserLoginResponseDto;
import com.user.service.dto.response.UserResponseDto;
import com.user.service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/auth")
@Validated
public class AuthController {
    private final AuthService authService;


    @PostMapping("/registration")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegisterRequestDto request) {
        UserResponseDto response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto request) {
        UserLoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
