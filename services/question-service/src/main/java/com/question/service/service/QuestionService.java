package com.question.service.service;

import com.question.service.dto.client.request.QuestionSelectionRequestDto;
import com.question.service.dto.client.response.QuestionResponseDto;

import java.util.List;

public interface QuestionService {

    List<QuestionResponseDto> selectQuestion(QuestionSelectionRequestDto request);
}
