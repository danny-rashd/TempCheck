package com.example.temperaturebackend.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSenderService implements EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleEmail(String email, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tempcheck.spring@gmail.com");
        message.setTo(email);
        message.setText(body);
        message.setSubject("TempCheck : Confirm your email");

        javaMailSender.send(message);
        log.info("Mail Send....");
    }
}
