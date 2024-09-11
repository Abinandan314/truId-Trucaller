package com.abinandan.spamdetection.app.repository;

import com.abinandan.spamdetection.app.models.Spam;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpamRepository extends JpaRepository<Spam, Integer> {
    Optional<Spam> findByNumber(String number);
}
