package com.example.temperaturebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan
@EnableWebMvc
@EnableSwagger2
public class TemperatureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemperatureBackendApplication.class, args);
    }
}