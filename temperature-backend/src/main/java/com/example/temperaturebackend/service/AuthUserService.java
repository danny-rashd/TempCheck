package com.example.temperaturebackend.service;

import com.example.temperaturebackend.entity.AuthUser;
import com.example.temperaturebackend.entity.AuthUserModel;
import com.example.temperaturebackend.entity.VerificationToken;

public interface AuthUserService {
    AuthUser registerAuthUser(AuthUserModel authUserModel);

    void saveVerificationToken(String token, AuthUser authUser);

    String validateVerificationToken(String token);

    VerificationToken generateNewToken(String token);
}
