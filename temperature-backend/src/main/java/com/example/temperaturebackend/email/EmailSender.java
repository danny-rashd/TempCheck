package com.example.temperaturebackend.email;

public interface EmailSender {
    void sendSimpleEmail(String email, String body);
}
