package com.question.service.service;

import com.question.service.dto.client.request.QuestionResultRequestDto;
import com.question.service.dto.client.request.QuestionSelectionRequestDto;
import com.question.service.dto.client.response.QuestionResultResponseDto;
import com.question.service.dto.client.response.QuestionSelectionResponseDto;

import java.util.List;

public interface QuestionService {

    List<QuestionSelectionResponseDto> selectQuestion(QuestionSelectionRequestDto request);

    QuestionResultResponseDto getResult(QuestionResultRequestDto request);
}
