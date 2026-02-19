package com.exam.service.service.impl;

import com.exam.service.dto.api.request.AttemptStartRequestDto;
import com.exam.service.dto.api.response.AttemptResponseDto;
import com.exam.service.dto.client.request.QuestionSelectionRequestDto;
import com.exam.service.dto.client.response.QuestionResponseDto;
import com.exam.service.entity.AttemptEntity;
import com.exam.service.enums.AttemptStatusEnum;
import com.exam.service.integration.QuestionClient;
import com.exam.service.repository.AttemptRepository;
import com.exam.service.service.AttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttemptServiceImpl implements AttemptService {

    private final AttemptRepository attemptRepository;
    private final QuestionClient questionClient;


    @Override
    public AttemptResponseDto start(String authHeader, AttemptStartRequestDto requestBody) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        String userId = auth.getPrincipal().toString();

        if (userId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        if (attemptRepository.existsByUserIdAndStatus(userId, AttemptStatusEnum.STARTED)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Active attempt already exists");
        }

        AttemptEntity entity = new AttemptEntity();
        entity.setUserId(userId);
        entity.setStatus(AttemptStatusEnum.STARTED);
        entity.setStartedAt(Instant.now());
        attemptRepository.save(entity);

        QuestionSelectionRequestDto requestDto = new QuestionSelectionRequestDto();
        requestDto.setCount(20);
        requestDto.setTopic(requestBody.getTopic());

        List<QuestionResponseDto> questionResponseDto = questionClient.selectQuestions(requestDto, authHeader);

        AttemptResponseDto response = new AttemptResponseDto();
        response.setId(entity.getId());
        response.setStatus(entity.getStatus().name());
        response.setStartedAt(entity.getStartedAt());
        response.setQuestions(questionResponseDto);

        return response;
    }
}
