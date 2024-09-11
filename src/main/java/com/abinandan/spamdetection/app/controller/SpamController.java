package com.abinandan.spamdetection.app.controller;

import com.abinandan.spamdetection.app.dto.SpamRequest;
import com.abinandan.spamdetection.app.service.SpamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spam")
public class SpamController {

    private final SpamService spamService;

    @PostMapping
    public void updateSpamEntry(@Valid @RequestBody SpamRequest spamRequest) {
        spamService.updateSpamEntry(spamRequest.getPhoneNumber());
    }
}
