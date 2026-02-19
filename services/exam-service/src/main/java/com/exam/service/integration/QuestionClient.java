package com.exam.service.integration;


import com.exam.service.dto.client.request.QuestionSelectionRequestDto;
import com.exam.service.dto.client.response.QuestionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "question-service", url = "${services.question-service.base-url}")
public interface QuestionClient {

    @PostMapping("/question/select")
    List<QuestionResponseDto> selectQuestions(@RequestBody QuestionSelectionRequestDto requestDto,
                                              @RequestHeader("Authorization") String authorization
    );
}
