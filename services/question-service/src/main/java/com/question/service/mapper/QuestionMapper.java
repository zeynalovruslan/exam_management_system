package com.question.service.mapper;

import com.question.service.dto.client.response.QuestionSelectionResponseDto;
import com.question.service.entity.QuestionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionSelectionResponseDto toResponse(QuestionEntity entity);

}
