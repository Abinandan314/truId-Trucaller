package com.abinandan.spamdetection.app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String number;
    private String spamStatus;
    private Integer reportedCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
