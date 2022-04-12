package com.example.temperaturebackend.service;

import com.example.temperaturebackend.entity.AuthUser;
import com.example.temperaturebackend.entity.AuthUserModel;
import com.example.temperaturebackend.entity.VerificationToken;
import com.example.temperaturebackend.repository.AuthUserRepository;
import com.example.temperaturebackend.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;

@Service
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthUser registerAuthUser(AuthUserModel authUserModel) {

        AuthUser authUser = new AuthUser();
        authUser.setEmail(authUserModel.getEmail());
        authUser.setFirstName(authUserModel.getFirstName());
        authUser.setLastName(authUserModel.getLastName());
        authUser.setRole("USER");
        authUser.setPassword(passwordEncoder.encode(authUserModel.getPassword()));

        authUserRepository.save(authUser);
        return authUser;
    }

    @Override
    public void saveVerificationToken(String token, AuthUser authUser) {
        VerificationToken verificationToken = new VerificationToken(authUser, token);

        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken =
                verificationTokenRepository.findByToken(token);

        //if token doesnt not exist in the database
        if (verificationToken == null) {
            return "Invalid Token!";
        }
        AuthUser authUser = verificationToken.getAuthUser();
        Calendar cal = Calendar.getInstance();

        // delete token when it has expired
        if (verificationToken.getExpiredTime().getTime() - cal.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken);
            LocalDateTime now = LocalDateTime.now();
            log.info("Click the link to verify your account: {}", now);
            return "expired";
        }
        //token is valid & yet to expire
        authUser.setEnabled(true);
        authUserRepository.save(authUser);
        return "valid";
    }
}
