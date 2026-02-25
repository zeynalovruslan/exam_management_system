package com.question.service.service.impl;

import com.question.service.dto.client.request.QuestionRequest;
import com.question.service.dto.client.request.QuestionResultRequestDto;
import com.question.service.dto.client.request.QuestionSelectionRequestDto;
import com.question.service.dto.client.response.QuestionResultResponseDto;
import com.question.service.dto.client.response.QuestionSelectionResponseDto;
import com.question.service.dto.client.response.QuestionsResponse;
import com.question.service.entity.OptionEntity;
import com.question.service.entity.QuestionEntity;
import com.question.service.exception.BadRequestException;
import com.question.service.exception.NotFoundException;
import com.question.service.mapper.QuestionMapper;
import com.question.service.repository.OptionRepository;
import com.question.service.repository.QuestionRepository;
import com.question.service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private static final int DEFAULT_COUNT = 20;

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final OptionRepository optionRepository;

    public List<QuestionSelectionResponseDto> selectQuestion(QuestionSelectionRequestDto request) {

        List<Long> ids = questionRepository.findRandomId(
                request.getTopic(),
                request.getDifficulty().name(),
                DEFAULT_COUNT
        );

        if (ids.size() < DEFAULT_COUNT) {
            throw new BadRequestException("Not enough questions for your option");
        }

        List<QuestionEntity> questions = questionRepository.findAllByIdInWithOptions(ids)
                .orElseThrow(() -> new NotFoundException("Question not found for ids: " + ids));

        return questions.stream().map(questionMapper::toResponse).toList();
    }

//        QuestionEntity question = questionRepository.findByIdAndTopicAndDifficultyWithOptions(1L, request.getTopic(), request.getDifficulty());
//        QuestionSelectionResponseDto responseDto = new QuestionSelectionResponseDto();
//        responseDto.setId(question.getId());
//        responseDto.setText(question.getText());
//
//        List<OptionDto> option = question.getOptions().stream().map(optionEntity -> {
//            OptionDto optionDto = new OptionDto();
//            optionDto.setId(optionEntity.getId());
//            optionDto.setText(optionEntity.getText());
//            optionDto.setOrderIndex(optionEntity.getOrderIndex());
//            return optionDto;
//        }).toList();
//
//        responseDto.setOptions(option);
//
//        return responseDto;


    @Override
    public QuestionResultResponseDto getResult(QuestionResultRequestDto request) {

        QuestionResultResponseDto result = new QuestionResultResponseDto();
        List<QuestionsResponse> responseList = new ArrayList<>();
        Integer correctAnswers = 0;

        for (QuestionRequest questionRequest : request.getRequests()) {
            QuestionEntity question = questionRepository.findById(questionRequest.getQuestionId()).orElseThrow(
                    () -> new NotFoundException("Question not found for id: " + questionRequest.getQuestionId()));

            OptionEntity correctOption = optionRepository.findByQuestionIdAndIsCorrectTrue(question.getId()).orElseThrow(
                    () -> new NotFoundException("Correct option not found for id: " + question.getId()));

            OptionEntity selectedOption = optionRepository.findById(questionRequest.getSelectedOptionId()).orElseThrow(
                    () -> new NotFoundException("Option not found for id: " + questionRequest.getSelectedOptionId()));

            if (!selectedOption.getQuestion().getId().equals(question.getId())) {
                throw new BadRequestException("Selected option does not belong to questionId: "
                        + question.getId());

            }

            QuestionsResponse response = new QuestionsResponse();
            response.setQuestionId(question.getId());
            response.setQuestionText(question.getText());
            response.setSelectedOptionId(selectedOption.getId());
            response.setSelectedOptionText(selectedOption.getText());
            response.setCorrectAnswerId(correctOption.getId());
            response.setCorrectAnswerText(correctOption.getText());

            if (selectedOption.getId().equals(correctOption.getId())) {
                correctAnswers++;
            }
            responseList.add(response);
        }

        Integer totalQuestions = responseList.size();
        Integer wrongAnswers = totalQuestions - correctAnswers;

        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswer(correctAnswers);
        result.setWrongAnswer(wrongAnswers);
        result.setScore(correctAnswers);
        result.setQuestions(responseList);
        return result;
    }


}

