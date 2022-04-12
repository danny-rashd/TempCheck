package com.example.temperaturebackend.service;

import com.example.temperaturebackend.entity.AuthUser;
import com.example.temperaturebackend.entity.AuthUserModel;

public interface AuthUserService {
    AuthUser registerAuthUser(AuthUserModel authUserModel);

    void saveVerificationToken(String token, AuthUser authUser);

    String validateVerificationToken(String token);
}
