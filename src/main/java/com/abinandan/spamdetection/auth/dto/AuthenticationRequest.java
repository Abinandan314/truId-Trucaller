package com.abinandan.spamdetection.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
    private String firstName;
    private String lastName;
    @NotBlank private String phoneNumber;
    @NotBlank private String password;
}
