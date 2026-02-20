package com.question.service.service.impl;

import com.question.service.dto.api.request.AnswerItemRequestDto;
import com.question.service.dto.api.request.AnswerTotalRequestDto;
import com.question.service.dto.api.response.AnswerResponseDto;
import com.question.service.exception.BadRequestException;
import com.question.service.exception.NotFoundException;
import com.question.service.repository.OptionRepository;
import com.question.service.service.GradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradingServiceImpl implements GradingService {

    private final OptionRepository optionRepository;


    @Override
    public AnswerResponseDto gradeAnswer(AnswerTotalRequestDto request) {

        int score = 0;
        int totalQuestions = request.getAnswers().size();

        for (AnswerItemRequestDto answer : request.getAnswers()) {
            if (answer.getOptionId() == null || answer.getOptionId() == 0) {
                throw new BadRequestException("Option id or request id cannot be null");
            }
            boolean exist = optionRepository.existsByIdAndQuestionIdAndIsCorrectTrue(answer.getOptionId(),
                    answer.getQuestionId()).orElseThrow(() -> new NotFoundException("Option not found"));
            if (exist) score++;
        }
        AnswerResponseDto response = new AnswerResponseDto();
        response.setScore(score);
        response.setTotalQuestions(totalQuestions);
        response.setCorrectAnswers(score);

        return response;
    }
}
