package com.abinandan.spamdetection.app.controller;

import com.abinandan.spamdetection.app.dto.CreateContactReq;
import com.abinandan.spamdetection.app.dto.SearchPaginatedResponse;
import com.abinandan.spamdetection.app.service.GlobalContactService;
import com.abinandan.spamdetection.app.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contact")
public class ContactController {
    private final SearchService searchService;
    private final GlobalContactService contactService;

    @GetMapping("/search")
    public ResponseEntity<SearchPaginatedResponse> search(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return ResponseEntity.ok(
                searchService.getSearchResultResponse(userId, name, phoneNumber, pageNo, pageSize));
    }

    @PostMapping
    public void createGlobalContact(@Valid @RequestBody CreateContactReq createContactReq) {
        contactService.createGlobalContact(createContactReq);
    }
}
