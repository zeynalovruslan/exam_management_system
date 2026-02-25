package com.question.service.service;

import com.question.service.dto.api.request.AnswerTotalRequestDto;
import com.question.service.dto.api.response.AnswerResponseDto;
import com.question.service.dto.client.request.QuestionGradeRequestDto;
import com.question.service.dto.client.response.QuestionGradeResponseDto;

public interface GradingService {

    AnswerResponseDto gradeTotalQuestions(AnswerTotalRequestDto request);

    QuestionGradeResponseDto gradePerQuestions(QuestionGradeRequestDto request);
}
