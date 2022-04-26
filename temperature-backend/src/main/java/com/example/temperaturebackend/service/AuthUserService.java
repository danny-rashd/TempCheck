package com.example.temperaturebackend.service;

import com.example.temperaturebackend.entity.UserEntity;
import com.example.temperaturebackend.entity.UserModel;
import com.example.temperaturebackend.entity.VerificationTokenEntity;

public interface AuthUserService {
    UserEntity registerAuthUser(UserModel userModel);

    void saveVerificationToken(String token, UserEntity userEntity);

    String validateVerificationToken(String token);

    VerificationTokenEntity generateNewToken(String token);

}
