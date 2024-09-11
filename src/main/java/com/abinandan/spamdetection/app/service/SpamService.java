package com.abinandan.spamdetection.app.service;

import com.abinandan.spamdetection.app.models.Spam;
import com.abinandan.spamdetection.app.repository.SpamRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpamService {
    private final SpamRepository spamRepository;
    private final GlobalContactService globalContactService;

    private final Integer SPAM_LOW = 10;
    private final Integer SPAM_MED = 25;
    private final Integer SPAM_HIGH = 50;

    public void updateSpamEntry(String phoneNumber) {
        globalContactService.validatePhoneNumber(phoneNumber);
        Optional<Spam> spamOptional = spamRepository.findByNumber(phoneNumber);

        if (spamOptional.isPresent()) {
            Spam spam = spamOptional.get();

            Integer newReportedCount = spam.getReportedCount() + 1;
            spam.setReportedCount(newReportedCount);

            String newSpamStatus;
            if (newReportedCount >= SPAM_HIGH) {
                newSpamStatus = "H";
            } else if (newReportedCount >= SPAM_MED) {
                newSpamStatus = "M";
            } else if (newReportedCount >= SPAM_LOW) {
                newSpamStatus = "L";
            } else {
                newSpamStatus = "N";
            }

            if (!newSpamStatus.equals(spam.getSpamStatus())) {
                globalContactService.updateSpamStatus(phoneNumber, newSpamStatus);
            }
            spam.setSpamStatus(newSpamStatus);
            spam.setUpdatedAt(LocalDateTime.now());
            spamRepository.save(spam);
        } else {
            Spam newSpam =
                    Spam.builder()
                            .spamStatus("N")
                            .number(phoneNumber)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .reportedCount(1)
                            .build();
            spamRepository.save(newSpam);
        }
    }
}
