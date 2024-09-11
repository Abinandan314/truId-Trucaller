package com.abinandan.spamdetection.app.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class SearchPaginatedResponse {
    List<SearchResultsResp> results;
    private Integer currentPage; // Current page number
    private Integer totalPages; // Total number of pages
    private Integer totalContacts; // Total number of items (user + global contacts)
}
