package com.abinandan.spamdetection.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpamRequest {
    @NotBlank(message = "Phone Number cannot be empty or null")
    private String phoneNumber;
}
