package com.exam.service.service.impl;

import com.exam.service.dto.api.request.AnswerDto;
import com.exam.service.dto.api.request.AttemptStartRequestDto;
import com.exam.service.dto.api.request.AttemptSubmitRequestDto;
import com.exam.service.dto.api.response.AttemptResultResponseDto;
import com.exam.service.dto.api.response.AttemptStartResponseDto;
import com.exam.service.dto.api.response.AttemptSubmitResponseDto;
import com.exam.service.dto.api.response.ResultResponse;
import com.exam.service.dto.client.request.QuestionGradeRequestDto;
import com.exam.service.dto.client.request.QuestionRequest;
import com.exam.service.dto.client.request.QuestionResultRequestDto;
import com.exam.service.dto.client.request.QuestionSelectionRequestDto;
import com.exam.service.dto.client.response.QuestionGradeResponseDto;
import com.exam.service.dto.client.response.QuestionResultResponseDto;
import com.exam.service.dto.client.response.QuestionSelectionResponseDto;
import com.exam.service.dto.client.response.ResultDto;
import com.exam.service.entity.AttemptEntity;
import com.exam.service.entity.AttemptQuestionEntity;
import com.exam.service.enums.AttemptStatusEnum;
import com.exam.service.exception.ConflictException;
import com.exam.service.exception.NotFoundException;
import com.exam.service.exception.UnauthorizedException;
import com.exam.service.integration.QuestionClient;
import com.exam.service.repository.AttemptQuestionRepository;
import com.exam.service.repository.AttemptRepository;
import com.exam.service.service.AttemptService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttemptServiceImpl implements AttemptService {

    private static final int DEFAULT_COUNT = 20;

    private final AttemptRepository attemptRepository;
    private final QuestionClient questionClient;
    private final AttemptQuestionRepository attemptQuestionRepository;

    @Transactional
    @Override
    public AttemptStartResponseDto start(AttemptStartRequestDto requestBody) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            log.warn("Attempt start denied: missing authentication");
            throw new UnauthorizedException("Unauthorized - authority is cannot be null");
        }

        String userId = auth.getPrincipal().toString();

        if (userId.isBlank()) {
            log.warn("Attempt start denied: blank userId in principal");
            throw new UnauthorizedException("Unauthorized - user id can not be blank");
        }

        log.info("Attempt start requested. topic={}, difficulty={}", requestBody.getTopic(), requestBody.getDifficulty());


        if (attemptRepository.existsByUserIdAndStatus(userId, AttemptStatusEnum.STARTED)) {
            log.warn("Attempt start conflict: active STARTED attempt already exists");
            throw new ConflictException("Active attempt already exists");
        }

        AttemptEntity attemptEntity = new AttemptEntity();
        attemptEntity.setUserId(userId);
        attemptEntity.setStatus(AttemptStatusEnum.STARTED);
        attemptEntity.setStartedAt(Instant.now());
        attemptRepository.save(attemptEntity);

        log.info("Attempt created. attemptId={}, status={}", attemptEntity.getId(), attemptEntity.getStatus());

        QuestionSelectionRequestDto requestDto = new QuestionSelectionRequestDto();
        requestDto.setCount(DEFAULT_COUNT);
        requestDto.setTopic(requestBody.getTopic());
        requestDto.setDifficulty(requestBody.getDifficulty());

        List<QuestionSelectionResponseDto> questions = questionClient.selectQuestions(requestDto);

        log.info("Questions selected. attemptId={}, count={}", attemptEntity.getId(), questions.size());

        List<AttemptQuestionEntity> attemptQuestions = questions.stream().map(questionResponseDto -> {
            AttemptQuestionEntity attemptQuestionEntity = new AttemptQuestionEntity();
            attemptQuestionEntity.setQuestionId(questionResponseDto.getId());
            attemptQuestionEntity.setAttemptId(attemptEntity.getId());
            return attemptQuestionEntity;
        }).toList();

        attemptQuestionRepository.saveAll(attemptQuestions);

        log.info("Attempt questions saved. attemptId={}, rows={}", attemptEntity.getId(), attemptQuestions.size());

        AttemptStartResponseDto response = new AttemptStartResponseDto();
        response.setId(attemptEntity.getId());
        response.setStatus(attemptEntity.getStatus().name());
        response.setStartedAt(attemptEntity.getStartedAt());
        response.setQuestions(questions);

        log.info("Attempt start completed. attemptId={}", attemptEntity.getId());

        return response;
    }

    @Override
    @Transactional
    public AttemptSubmitResponseDto submit(AttemptSubmitRequestDto requestDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            log.warn("Attempt submit denied: missing authentication");
            throw new UnauthorizedException("Unauthorized - authority is cannot be null");
        }

        String userId = auth.getPrincipal().toString();

        if (userId.isBlank()) {
            log.warn("Attempt submit denied: blank userId in principal");
            throw new UnauthorizedException("Unauthorized - user id can not be blank");
        }

        AttemptEntity attemptEntity = attemptRepository.findById(requestDto.getAttemptId()).orElseThrow(() ->
                new NotFoundException("Attempt with id " + requestDto.getAttemptId() + " not found"));


        if (!userId.equals(attemptEntity.getUserId())) {
            throw new UnauthorizedException("Attempt does not belong to current user");
        }

        if (attemptEntity.getStatus() != AttemptStatusEnum.STARTED) {
            throw new ConflictException("Attempt must be STARTED. Current : " + attemptEntity.getStatus());
        }

        List<AttemptQuestionEntity> attemptQuestionList = attemptQuestionRepository.
                findByAttemptId(attemptEntity.getId());

        if (attemptQuestionList.isEmpty()) {
            throw new NotFoundException("Attempt has no questions: " + attemptEntity.getId());
        }

        Map<Long, AttemptQuestionEntity> attemptQuestionEntityMap = attemptQuestionList.stream().
                collect(Collectors.toMap(AttemptQuestionEntity::getQuestionId,
                        attemptQuestionEntity -> attemptQuestionEntity));

        Set<Long> set = new HashSet<>();
        for (AnswerDto answerDto : requestDto.getAnswers()) {
            if (!set.add(answerDto.getQuestionId())) {
                log.warn("Attempt submit conflict: duplicated answer questionId. questionId={}",
                        answerDto.getQuestionId());
                throw new ConflictException("Answer id " + answerDto.getQuestionId() + " is duplicated");
            }
        }

        for (AnswerDto answerDto : requestDto.getAnswers()) {
            AttemptQuestionEntity attemptQuestionEntity = attemptQuestionEntityMap.get(answerDto.getQuestionId());
            if (attemptQuestionEntity == null) {
                throw new NotFoundException("Question id " + answerDto.getQuestionId() + " is not part of attempt " + attemptEntity.getId());
            }
            attemptQuestionEntity.setSelectedOptionId(answerDto.getSelectedOptionId());
        }
        attemptQuestionRepository.saveAll(attemptQuestionList);
        log.info("Attempt answers saved. answers={}", requestDto.getAnswers().size());


        QuestionGradeRequestDto gradeRequestDto = new QuestionGradeRequestDto();

        List<com.exam.service.dto.client.request.AnswerDto> gradeAnswers = attemptQuestionList.stream().map(
                attemptQuestionEntity -> {

                    com.exam.service.dto.client.request.AnswerDto answerDto = new com.exam.service.dto.client.request.AnswerDto();
                    answerDto.setQuestionId(attemptQuestionEntity.getQuestionId());
                    answerDto.setSelectedOptionId(attemptQuestionEntity.getSelectedOptionId());
                    return answerDto;
                }
        ).toList();

        gradeRequestDto.setAnswers(gradeAnswers);

        QuestionGradeResponseDto response = questionClient.grade(gradeRequestDto);

        Map<Long, ResultDto> resultMap = response.getResults().stream()
                .collect(Collectors.toMap(ResultDto::getQuestionId, r -> r));

        for (AttemptQuestionEntity aq : attemptQuestionList) {
            ResultDto result = resultMap.get(aq.getQuestionId());

            if (result == null) {
                throw new IllegalStateException("Missing grade result for question id = " + aq.getQuestionId());
            }
            aq.setIsCorrect(result.getIsCorrect());
            if (Boolean.TRUE.equals(result.getIsCorrect())) {
                aq.setScore(1);
            } else {
                aq.setScore(0);
            }
        }
        attemptQuestionRepository.saveAll(attemptQuestionList);

        attemptEntity.setStatus(AttemptStatusEnum.SUBMITTED);
        attemptEntity.setSubmittedAt(Instant.now());
        attemptEntity.setScore(response.getCorrectAnswerCount());
        attemptRepository.save(attemptEntity);

        Integer totalAnswers = attemptQuestionList.size();

        Integer wrongAnswers = totalAnswers - response.getCorrectAnswerCount();

        AttemptSubmitResponseDto responseDto = new AttemptSubmitResponseDto();
        responseDto.setAttemptId(attemptEntity.getId());
        responseDto.setTotalQuestions(totalAnswers);
        responseDto.setWrongAnswerCount(wrongAnswers);
        responseDto.setCorrectAnswerCount(response.getCorrectAnswerCount());

        responseDto.setResults(response.getResults());

        return responseDto;
    }

    @Override
    public AttemptResultResponseDto getResult(Long attemptId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            log.warn("Attempt result denied: missing authentication. attemptId={}", attemptId);
            throw new UnauthorizedException("Unauthorized - authority is cannot be null");
        }

        String userId = auth.getPrincipal().toString();

        if (userId.isBlank()) {
            log.warn("Attempt result denied: blank userId in principal. attemptId={}", attemptId);
            throw new UnauthorizedException("Unauthorized - user id can not be blank");
        }

        log.info("Attempt result requested. attemptId={}", attemptId);

        AttemptEntity attemptEntity = attemptRepository.findById(attemptId).orElseThrow(() ->
                new NotFoundException("Attempt with id " + attemptId + " not found"));

        if (!userId.equals(attemptEntity.getUserId())) {
            log.warn("Attempt result denied: attempt does not belong to user. attemptId={}, ownerUserId={}",
                    attemptId, attemptEntity.getUserId());
            throw new UnauthorizedException("Attempt does not belong to current user");
        }

        if (!attemptEntity.getStatus().equals(AttemptStatusEnum.SUBMITTED)) {
            log.warn("Attempt result conflict: attempt not SUBMITTED. attemptId={}, status={}",
                    attemptId, attemptEntity.getStatus());
            throw new ConflictException("Attempt must be SUBMITTED");
        }

        List<AttemptQuestionEntity> attemptQuestion = attemptQuestionRepository.findByAttemptId(attemptId);
        if (attemptQuestion.isEmpty()) {
            log.error("Attempt result failed: attempt has no questions. attemptId={}", attemptId);
            throw new NotFoundException("Attempt question has no questions: " + attemptId);
        }

        List<QuestionRequest> questionRequestList = attemptQuestion.stream()
                .map(attemptQuestionEntity -> {
                    QuestionRequest questionRequest = new QuestionRequest();
                    questionRequest.setQuestionId(attemptQuestionEntity.getQuestionId());
                    questionRequest.setSelectedOptionId(attemptQuestionEntity.getSelectedOptionId());
                    return questionRequest;
                }).toList();

        QuestionResultRequestDto requestDto = new QuestionResultRequestDto();
        requestDto.setRequests(questionRequestList);

        QuestionResultResponseDto questionResult = questionClient.getResult(requestDto);

        log.info("Attempt result fetched from question-service. attemptId={}, total={}, correct={}, wrong={}, score={}",
                attemptId,
                questionResult.getTotalQuestions(),
                questionResult.getCorrectAnswer(),
                questionResult.getWrongAnswer(),
                questionResult.getScore()
        );

        List<ResultResponse> questionResponseList = questionResult.getQuestions().stream().map(questionResponse -> {
            ResultResponse resultResponse = new ResultResponse();
            resultResponse.setQuestionId(questionResponse.getQuestionId());
            resultResponse.setQuestionText(questionResponse.getQuestionText());
            resultResponse.setSelectedOptionId(questionResponse.getSelectedOptionId());
            resultResponse.setSelectedOptionText(questionResponse.getSelectedOptionText());
            resultResponse.setCorrectAnswerId(questionResponse.getCorrectAnswerId());
            resultResponse.setCorrectAnswerText(questionResponse.getCorrectAnswerText());
            return resultResponse;
        }).toList();

        AttemptResultResponseDto attemptResultResponseDto = new AttemptResultResponseDto();
        attemptResultResponseDto.setQuestions(questionResponseList);
        attemptResultResponseDto.setScore(questionResult.getScore());
        attemptResultResponseDto.setTotalQuestions(questionResult.getTotalQuestions());
        attemptResultResponseDto.setWrongAnswerCount(questionResult.getWrongAnswer());
        attemptResultResponseDto.setCorrectAnswerCount(questionResult.getCorrectAnswer());

        log.info("Attempt result completed. attemptId={}", attemptId);
        return attemptResultResponseDto;
    }
}
