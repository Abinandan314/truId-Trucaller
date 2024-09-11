package com.abinandan.spamdetection.app.service;

import com.abinandan.spamdetection.app.dto.SearchPaginatedResponse;
import com.abinandan.spamdetection.app.dto.SearchResultsResp;
import com.abinandan.spamdetection.app.models.GlobalContact;
import com.abinandan.spamdetection.app.models.Spam;
import com.abinandan.spamdetection.app.repository.GlobalContactRepository;
import com.abinandan.spamdetection.app.repository.SpamRepository;
import com.abinandan.spamdetection.auth.models.User;
import com.abinandan.spamdetection.auth.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final GlobalContactRepository globalContactRepository;
    private final UserRepository userRepository;
    private final SpamRepository spamRepository;
    private final GlobalContactService globalContactService;

    public SearchPaginatedResponse getSearchResultResponse(
            Integer userId, String name, String phoneNumber, Integer pageNumber, Integer pageSize) {

        List<SearchResultsResp> results = new ArrayList<>();

        if (phoneNumber != null) {
            // Search by phone number
            results.addAll(getUsersByPhoneNumber(phoneNumber));
        } else {
            // Search by name
            results.addAll(getUsersByName(name));
        }

        List<SearchResultsResp> paginatedList =
                results.stream().skip((long) pageNumber * pageSize).limit(pageSize).toList();

        int totalContacts = results.size();
        int totalPages = (int) Math.ceil((double) totalContacts / pageSize);
        return SearchPaginatedResponse.builder()
                .currentPage(pageNumber)
                .totalContacts(totalContacts)
                .totalPages(totalPages)
                .results(paginatedList)
                .build();
    }

    public List<SearchResultsResp> getUsersByName(String name) {

        List<GlobalContact> allResults = new ArrayList<>();
        List<GlobalContact> nameMatches =
                globalContactRepository.findByNameContainingIgnoreCase(name);

        // Separate results starting with the query and those containing but not starting
        List<GlobalContact> startsWithMatches =
                nameMatches.stream()
                        .filter(
                                contact ->
                                        contact.getName()
                                                .toLowerCase()
                                                .startsWith(name.toLowerCase()))
                        .toList();
        List<GlobalContact> containsMatches =
                nameMatches.stream()
                        .filter(
                                contact ->
                                        !contact.getName()
                                                .toLowerCase()
                                                .startsWith(name.toLowerCase()))
                        .toList();
        allResults.addAll(startsWithMatches);
        allResults.addAll(containsMatches);

        return allResults.stream()
                .map(
                        globalContact -> {
                            return SearchResultsResp.builder()
                                    .name(globalContact.getName())
                                    .number(globalContact.getPhoneNumber())
                                    .spamStatus(globalContact.getSpamStatus())
                                    .registered(false)
                                    .build();
                        })
                .toList();
    }

    public List<SearchResultsResp> getUsersByPhoneNumber(String phoneNumber) {

        Optional<Spam> spam = spamRepository.findByNumber(phoneNumber);

        String spamStatus = (spam.isPresent()) ? spam.get().getSpamStatus() : "N";

        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isPresent()) {
            globalContactService.syncGlobalContactRepository(
                    phoneNumber, user.get().getEmail(), user.get().getFirstName());
            return List.of(
                    SearchResultsResp.builder()
                            .name(user.get().getFirstName())
                            .number(phoneNumber)
                            .spamStatus(spamStatus)
                            .registered(true)
                            .email(user.get().getEmail())
                            .build());
        }

        List<GlobalContact> globalContacts = globalContactRepository.findByPhoneNumber(phoneNumber);

        return globalContacts.stream()
                .map(
                        globalContact -> {
                            return SearchResultsResp.builder()
                                    .name(globalContact.getName())
                                    .number(globalContact.getPhoneNumber())
                                    .email(globalContact.getEmail())
                                    .spamStatus(spamStatus)
                                    .registered(globalContact.isRegistered())
                                    .build();
                        })
                .toList();
    }
}
