package com.example.temperaturebackend.repository;

import com.example.temperaturebackend.entity.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Long> {
    VerificationTokenEntity findByToken(String token);

    @Query("SELECT v FROM VerificationToken v WHERE v.email = ?1")
    VerificationTokenEntity findTokenByEmail(String email);
}
