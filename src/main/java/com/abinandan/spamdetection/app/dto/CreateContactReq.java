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
public class CreateContactReq {
    @NotBlank(message = "Name Cannot be blank")
    private String name;

    @NotBlank(message = "phoneNumber Cannot be blank")
    private String phoneNumber;

    private String email;
}
