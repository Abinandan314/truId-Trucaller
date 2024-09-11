package com.abinandan.spamdetection.app.datasetup;

import com.abinandan.spamdetection.app.models.GlobalContact;
import com.abinandan.spamdetection.app.models.Spam;
import com.abinandan.spamdetection.app.repository.GlobalContactRepository;
import com.abinandan.spamdetection.app.repository.SpamRepository;
import com.abinandan.spamdetection.auth.models.Role;
import com.abinandan.spamdetection.auth.models.User;
import com.abinandan.spamdetection.auth.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemoDataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final GlobalContactRepository globalContactRepository;
    private final PasswordEncoder passwordEncoder;
    private final SpamRepository spamRepository;

    @Override
    public void run(String... args) throws Exception {
        loadGlobalContacts();
        loadUserData();
        loadSpamData();
    }

    private void loadGlobalContacts() {
        globalContactRepository.save(
                new GlobalContact(
                        1,
                        "1234567890",
                        "John Doe",
                        "N",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));
        globalContactRepository.save(
                new GlobalContact(
                        2,
                        "0987654321",
                        "Jane Smith",
                        "N",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));
        globalContactRepository.save(
                new GlobalContact(
                        3,
                        "5555555555",
                        "Alice Johnson",
                        "N",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));
        globalContactRepository.save(
                new GlobalContact(
                        4,
                        "1234567890",
                        "John Dhoe",
                        "N",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));
        globalContactRepository.save(
                new GlobalContact(
                        5,
                        "1234567890",
                        "Johnathan Doe",
                        "N",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));
        globalContactRepository.save(
                new GlobalContact(
                        6,
                        "2345678901",
                        "Jane Doe",
                        "N",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));
        globalContactRepository.save(
                new GlobalContact(
                        7,
                        "3456789012",
                        "Michael Smith",
                        "N",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));
        globalContactRepository.save(
                new GlobalContact(
                        8,
                        "4567890123",
                        "Emily Davis",
                        "N",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));
        globalContactRepository.save(
                new GlobalContact(
                        9,
                        "1234567890",
                        "Johnny Doe",
                        "N",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));
        globalContactRepository.save(
                new GlobalContact(
                        10,
                        "5678901234",
                        "David Wilson",
                        "L",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));

        globalContactRepository.save(
                new GlobalContact(
                        11,
                        "7776663331",
                        "George RR Martin",
                        "N",
                        null,
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now()));

        globalContactRepository.save(
                new GlobalContact(
                        12,
                        "3456789012",
                        "Mike Smith",
                        "N",
                        null,
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now()));
    }

    private void loadUserData() {
        userRepository.save(
                new User(
                        1,
                        "admin",
                        "last",
                        "sample@gmail.com",
                        passwordEncoder.encode("password"),
                        "0123456789",
                        Role.ADMIN));

        userRepository.save(
                new User(
                        2,
                        "abinandan",
                        "v",
                        "abi@gmail.com",
                        passwordEncoder.encode("password"),
                        "8508000551",
                        Role.USER));

        userRepository.save(
                new User(
                        3,
                        "George",
                        "Martin",
                        "georgemartin@gmail.com",
                        passwordEncoder.encode("password"),
                        "7776663331",
                        Role.USER));
    }

    private void loadSpamData() {
        spamRepository.save(
                new Spam(1, "5678901234", "L", 24, LocalDateTime.now(), LocalDateTime.now()));
    }
}
