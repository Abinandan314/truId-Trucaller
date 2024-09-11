package com.abinandan.spamdetection.app.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SearchResultsResp {
    private String name;
    private String number;
    private String spamStatus;
    private String email;
    private boolean registered;
}
