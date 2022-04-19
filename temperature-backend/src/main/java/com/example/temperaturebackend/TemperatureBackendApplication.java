package com.example.temperaturebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class TemperatureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemperatureBackendApplication.class, args);
    }
}