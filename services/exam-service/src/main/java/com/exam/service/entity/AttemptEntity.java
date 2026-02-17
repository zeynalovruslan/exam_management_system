package com.exam.service.entity;

import com.exam.service.enums.AttemptStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attempts")
public class AttemptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttemptStatusEnum status;

    @Column(nullable = false)
    private Instant startedAt;

    private Instant submittedAt;


}
