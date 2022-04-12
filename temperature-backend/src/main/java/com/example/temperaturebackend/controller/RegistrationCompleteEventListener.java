package com.example.temperaturebackend.controller;

import com.example.temperaturebackend.email.EmailSenderService;
import com.example.temperaturebackend.entity.AuthUser;
import com.example.temperaturebackend.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        //Create the verification token for user
        AuthUser authUser = event.getAuthUser();
        String token = UUID.randomUUID().toString();
        authUserService.saveVerificationToken(token, authUser);

        //Send mail to user
        String url = event.getApplicationUrl()
                + "/verifyRegistration?token="
                + token;

        //send verification email
        log.info(authUser.getEmail());
        emailSenderService.sendSimpleEmail(authUser.getEmail(), url);
        log.info("Click the link to verify your account: {}", url);


    }
}
