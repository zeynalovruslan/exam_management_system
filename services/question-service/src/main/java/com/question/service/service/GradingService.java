package com.question.service.service;

import com.question.service.dto.api.request.AnswerTotalRequestDto;
import com.question.service.dto.api.response.AnswerResponseDto;

public interface GradingService {

    AnswerResponseDto gradeAnswer(AnswerTotalRequestDto request);
}
