package com.question.service.service.impl;

import com.question.service.dto.client.request.QuestionSelectionRequestDto;
import com.question.service.dto.client.response.QuestionResponseDto;
import com.question.service.entity.QuestionEntity;
import com.question.service.exception.BadRequestException;
import com.question.service.exception.NotFoundException;
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

        List<Long> ids = questionRepository.findRandomId(request.getTopic(), request.getDifficulty(), DEFAULT_COUNT).
                orElseThrow(() -> new NotFoundException("Question ids not found"));

        if (ids.size() < DEFAULT_COUNT) {
            throw new BadRequestException("Not enough questions for your option");
        }

        List<QuestionEntity> questions = questionRepository.findAllByIdInWithOptions(ids).orElseThrow(()
                -> new NotFoundException("Question not found for this ids : " + ids));
        return questions.stream().map(questionMapper::toResponse).toList();

//        QuestionEntity question = questionRepository.findByIdAndTopicAndDifficultyWithOptions(1L, request.getTopic(), request.getDifficulty());
//        QuestionResponseDto responseDto = new QuestionResponseDto();
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

    }

}
