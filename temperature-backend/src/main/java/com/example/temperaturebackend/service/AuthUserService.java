package com.example.temperaturebackend.service;

import com.example.temperaturebackend.entity.UserEntity;
import com.example.temperaturebackend.entity.UserModelEntity;
import com.example.temperaturebackend.entity.VerificationTokenEntity;

public interface AuthUserService {
    UserEntity registerAuthUser(UserModelEntity userModelEntity);

    void saveVerificationToken(String token, UserEntity userEntity);

    String validateVerificationToken(String token);

    VerificationTokenEntity generateNewToken(String token);

}
