package com.question.service.repository;

import com.question.service.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    Optional<Boolean> existsByIdAndQuestionIdAndIsCorrectTrue(Long optionId, Long questionId);
}
