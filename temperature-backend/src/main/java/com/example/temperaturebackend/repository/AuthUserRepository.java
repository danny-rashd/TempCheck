package com.example.temperaturebackend.repository;

import com.example.temperaturebackend.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);

    @Query("SELECT u FROM AuthUser u WHERE u.email = ?1")
    AuthUser findByUsername(String email);

}
