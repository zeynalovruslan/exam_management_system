package com.exam.service.service.impl;

import com.exam.service.dto.api.request.AttemptStartRequestDto;
import com.exam.service.dto.api.response.AttemptStartResponseDto;
import com.exam.service.dto.client.request.QuestionSelectionRequestDto;
import com.exam.service.dto.client.response.QuestionSelectionResponseDto;
import com.exam.service.entity.AttemptEntity;
import com.exam.service.entity.AttemptQuestionEntity;
import com.exam.service.enums.AttemptStatusEnum;
import com.exam.service.exception.ConflictException;
import com.exam.service.exception.UnauthorizedException;
import com.exam.service.integration.QuestionClient;
import com.exam.service.repository.AttemptQuestionRepository;
import com.exam.service.repository.AttemptRepository;
import com.exam.service.service.AttemptService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttemptServiceImpl implements AttemptService {

    private static final int DEFAULT_COUNT = 20;

    private final AttemptRepository attemptRepository;
    private final QuestionClient questionClient;
    private final AttemptQuestionRepository attemptQuestionRepository;

    @Transactional
    @Override
    public AttemptStartResponseDto start(String authHeader, AttemptStartRequestDto requestBody) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            throw new UnauthorizedException("Unauthorized - authority is cannot be null");
        }

        String userId = auth.getPrincipal().toString();

        if (userId.isBlank()) {
            throw new UnauthorizedException("Unauthorized - user id can not be blank");
        }

        if (attemptRepository.existsByUserIdAndStatus(userId, AttemptStatusEnum.STARTED)) {
            throw new ConflictException("Active attempt already exists");
        }

        AttemptEntity attemptEntity = new AttemptEntity();
        attemptEntity.setUserId(userId);
        attemptEntity.setStatus(AttemptStatusEnum.STARTED);
        attemptEntity.setStartedAt(Instant.now());
        attemptRepository.save(attemptEntity);

        QuestionSelectionRequestDto requestDto = new QuestionSelectionRequestDto();
        requestDto.setCount(DEFAULT_COUNT);
        requestDto.setTopic(requestBody.getTopic());
        requestDto.setDifficulty(requestBody.getDifficulty());

        List<QuestionSelectionResponseDto> questions = questionClient.selectQuestions(requestDto, authHeader);

        List<AttemptQuestionEntity> attemptQuestions = questions.stream().map(questionResponseDto -> {
            AttemptQuestionEntity attemptQuestionEntity = new AttemptQuestionEntity();
            attemptQuestionEntity.setQuestionId(questionResponseDto.getId());
            attemptQuestionEntity.setAttemptId(attemptEntity.getId());
            return attemptQuestionEntity;
        }).toList();

        attemptQuestionRepository.saveAll(attemptQuestions);

        AttemptStartResponseDto response = new AttemptStartResponseDto();
        response.setId(attemptEntity.getId());
        response.setStatus(attemptEntity.getStatus().name());
        response.setStartedAt(attemptEntity.getStartedAt());
        response.setQuestions(questions);

        return response;
    }
}
