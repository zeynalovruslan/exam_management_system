package com.exam.service.service;

import com.exam.service.dto.api.request.AttemptStartRequestDto;
import com.exam.service.dto.api.response.AttemptStartResponseDto;

public interface AttemptService {

    AttemptStartResponseDto start(String authHeader, AttemptStartRequestDto requestDto);

}
