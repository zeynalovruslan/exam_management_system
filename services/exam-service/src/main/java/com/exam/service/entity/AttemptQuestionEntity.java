package com.exam.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attempt_questions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"attempt_id", "question_id"}))
public class AttemptQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attempt_id", nullable = false)
    private Long attemptId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "selected_option_id", nullable = false)
    private Long selectedOptionId;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }
}
