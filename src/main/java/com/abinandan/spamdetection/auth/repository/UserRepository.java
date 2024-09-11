package com.abinandan.spamdetection.auth.repository;

import com.abinandan.spamdetection.auth.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phone);
}
