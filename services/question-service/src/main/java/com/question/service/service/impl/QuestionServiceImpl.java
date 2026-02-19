package com.question.service.service.impl;

import com.question.service.dto.client.request.QuestionSelectionRequestDto;
import com.question.service.dto.client.response.QuestionResponseDto;
import com.question.service.entity.QuestionEntity;
import com.question.service.mapper.QuestionMapper;
import com.question.service.repository.QuestionRepository;
import com.question.service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private static final int DEFAULT_COUNT = 20;

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public List<QuestionResponseDto> selectQuestion(QuestionSelectionRequestDto request) {

        List<Long> ids = questionRepository.findRandomId(request.getTopic(), request.getDifficulty());

        if (ids.size() < DEFAULT_COUNT) {
            throw new IllegalStateException("Not enough questions for your option");
        }
        List<QuestionEntity> questions = questionRepository.findAllByIdInWithOptions(ids);

        return questions.stream().map(questionMapper::toResponse).toList();
    }

}
