package com.example.temperaturebackend.service;

import com.example.temperaturebackend.entity.UserEntity;
import com.example.temperaturebackend.entity.UserModelEntity;
import com.example.temperaturebackend.entity.VerificationTokenEntity;
import com.example.temperaturebackend.repository.AuthUserRepository;
import com.example.temperaturebackend.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

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
    public UserEntity registerAuthUser(UserModelEntity userModelEntity) {

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userModelEntity.getEmail());
        userEntity.setUsername(userModelEntity.getEmail());
        userEntity.setFirstName(userModelEntity.getFirstName());
        userEntity.setLastName(userModelEntity.getLastName());
        userEntity.setRole("USER");
        userEntity.setPassword(passwordEncoder.encode(userModelEntity.getPassword()));

        authUserRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public void saveVerificationToken(String token, UserEntity userEntity) {
        VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity(userEntity, token);

        verificationTokenRepository.save(verificationTokenEntity);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationTokenEntity verificationTokenEntity =
                verificationTokenRepository.findByToken(token);

        //if token doesnt not exist in the database
        if (verificationTokenEntity == null) {
            return "Invalid Token!";
        }

        UserEntity userEntity = verificationTokenEntity.getUserEntity();
        Calendar cal = Calendar.getInstance();

        // delete token when it has expired
        if (verificationTokenEntity.getExpiredTime().getTime() - cal.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationTokenEntity);
            LocalDateTime now = LocalDateTime.now();
            log.info(String.valueOf(now));
            return "expired";
        }
        //token is valid & yet to expire
        userEntity.setEnabled(true);
        authUserRepository.save(userEntity);
        return "valid";
    }

    @Override
    public VerificationTokenEntity generateNewToken(String email) {

        //Search existing token by email
        VerificationTokenEntity verificationTokenEntity =
                verificationTokenRepository.findTokenByEmail(email);

        verificationTokenEntity.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationTokenEntity);
        return verificationTokenEntity;
    }

}
