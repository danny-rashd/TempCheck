package com.example.temperaturebackend.event;

import com.example.temperaturebackend.email.EmailSenderService;
import com.example.temperaturebackend.entity.UserEntity;
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
        UserEntity userEntity = event.getUserEntity();
        String token = UUID.randomUUID().toString();
        authUserService.saveVerificationToken(token, userEntity);

        //Send mail to user
        String url = event.getApplicationUrl()
                + "/api/v1/springboot/verifyRegistration?token="
                + token;

        //send verification email
        log.info(userEntity.getEmail());
        emailSenderService.sendSimpleEmail(userEntity.getEmail(), url);
        log.info("Click the link to verify your account: {}", url);


    }
}
