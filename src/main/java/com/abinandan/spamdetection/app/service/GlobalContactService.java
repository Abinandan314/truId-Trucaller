package com.abinandan.spamdetection.app.service;

import com.abinandan.spamdetection.app.dto.CreateContactReq;
import com.abinandan.spamdetection.app.models.GlobalContact;
import com.abinandan.spamdetection.app.models.Spam;
import com.abinandan.spamdetection.app.repository.GlobalContactRepository;
import com.abinandan.spamdetection.app.repository.SpamRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlobalContactService {
    private final GlobalContactRepository globalContactRepository;
    private final SpamRepository spamRepository;

    public void createGlobalContact(CreateContactReq createContactReq) {

        Optional<Spam> spamOptional =
                spamRepository.findByNumber(createContactReq.getPhoneNumber());

        globalContactRepository.save(
                GlobalContact.builder()
                        .name(createContactReq.getName())
                        .phoneNumber(createContactReq.getPhoneNumber())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .email(createContactReq.getEmail())
                        .spamStatus(
                                (spamOptional.isPresent())
                                        ? spamOptional.get().getSpamStatus()
                                        : "N")
                        .registered(true)
                        .build());
    }

    public void updateSpamStatus(String phoneNumber, String spamStatus) {

        List<GlobalContact> globalContacts = globalContactRepository.findByPhoneNumber(phoneNumber);

        // Update the isSpam status for each contact
        globalContacts.forEach(globalContact -> globalContact.setSpamStatus(spamStatus));

        // Save the updated contacts back to the database
        globalContactRepository.saveAll(globalContacts);
    }

    public void validatePhoneNumber(String phoneNumber) {
        List<GlobalContact> globalContacts = globalContactRepository.findByPhoneNumber(phoneNumber);

        if (globalContacts.isEmpty()) {
            throw new RuntimeException("Invalid Phone Number");
        }
    }

    // Upon new Signup Existing UserDetails will be deleted and recreated
    public void syncGlobalContactRepository(String phoneNumber, String email, String name) {
        List<GlobalContact> globalContacts = globalContactRepository.findByPhoneNumber(phoneNumber);

        if (globalContacts.isEmpty()) return;

        globalContacts.forEach(
                globalContact -> {
                    globalContactRepository.deleteById(globalContact.getId());
                });
        createGlobalContact(
                CreateContactReq.builder()
                        .name(name)
                        .phoneNumber(phoneNumber)
                        .email(email)
                        .build());
    }
}
