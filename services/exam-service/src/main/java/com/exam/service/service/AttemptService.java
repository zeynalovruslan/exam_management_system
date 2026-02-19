package com.exam.service.service;

import com.exam.service.dto.api.request.AttemptStartRequestDto;
import com.exam.service.dto.api.response.AttemptResponseDto;

public interface AttemptService {

    AttemptResponseDto start(String authHeader, AttemptStartRequestDto requestDto);

}
