package com.exam.service.service;

import com.exam.service.dto.api.request.AttemptStartRequestDto;
import com.exam.service.dto.api.request.AttemptSubmitRequestDto;
import com.exam.service.dto.api.response.AttemptResultResponseDto;
import com.exam.service.dto.api.response.AttemptStartResponseDto;
import com.exam.service.dto.api.response.AttemptSubmitResponseDto;

public interface AttemptService {

    AttemptStartResponseDto start(AttemptStartRequestDto requestDto);

    AttemptSubmitResponseDto submit(AttemptSubmitRequestDto requestDto);

    AttemptResultResponseDto getResult(Long attemptId);

}
