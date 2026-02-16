package com.user.service.controller;

import com.user.service.dto.request.UserRegisterRequestDto;
import com.user.service.dto.response.UserRegisterResponseDto;
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


    @PostMapping
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody UserRegisterRequestDto request) {
        UserRegisterResponseDto response = authService.register(request);
        return ResponseEntity.ok(response);
    }
}
