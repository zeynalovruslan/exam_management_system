package com.question.service.service.impl;

import com.question.service.dto.api.request.AnswerItemRequestDto;
import com.question.service.dto.api.request.AnswerTotalRequestDto;
import com.question.service.dto.api.response.AnswerResponseDto;
import com.question.service.dto.client.request.QuestionGradeRequestDto;
import com.question.service.dto.client.response.QuestionGradeResponseDto;
import com.question.service.dto.client.response.ResultDto;
import com.question.service.exception.BadRequestException;
import com.question.service.exception.NotFoundException;
import com.question.service.repository.OptionRepository;
import com.question.service.service.GradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GradingServiceImpl implements GradingService {

    private final OptionRepository optionRepository;


    @Override
    public AnswerResponseDto gradeTotalQuestions(AnswerTotalRequestDto request) {

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


    @Override
    public QuestionGradeResponseDto gradePerQuestions(QuestionGradeRequestDto request) {

        if (request == null || request.getAnswers() == null || request.getAnswers().isEmpty()) {
            throw new BadRequestException("Answers cannot be empty");
        }
        Integer correctCount = 0;

        List<ResultDto> results = request.getAnswers().stream().map(answer -> {
            if (answer.getQuestionId() == null || answer.getSelectedOptionId() == null) {
                throw new BadRequestException("Question id and selected option id cannot be null");
            }

            boolean correct = optionRepository
                    .existsByIdAndQuestionIdAndIsCorrectTrue(answer.getSelectedOptionId(), answer.getQuestionId())
                    .orElseThrow(() -> new NotFoundException("Option is not true"));

            ResultDto result = new ResultDto();
            result.setQuestionId(answer.getQuestionId());
            result.setIsCorrect(correct);
            return result;
        }).toList();

        for (ResultDto r : results) {
            if (Boolean.TRUE.equals(r.getIsCorrect())) correctCount++;
        }

        QuestionGradeResponseDto response = new QuestionGradeResponseDto();
        response.setResults(results);
        response.setCorrectAnswerCount(correctCount);
        return response;
    }
}