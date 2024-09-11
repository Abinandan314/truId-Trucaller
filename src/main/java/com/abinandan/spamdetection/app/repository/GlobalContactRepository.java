package com.abinandan.spamdetection.app.repository;

import com.abinandan.spamdetection.app.models.GlobalContact;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalContactRepository extends JpaRepository<GlobalContact, Integer> {
    List<GlobalContact> findByNameContainingIgnoreCase(String name);

    List<GlobalContact> findByPhoneNumber(String phoneNumber);
}
