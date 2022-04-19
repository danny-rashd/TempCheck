package com.example.temperaturebackend.email;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class EmailValidator implements Predicate<String> {

    private static final String REGEX = "^(.+)@(.+)$";

    @Override
    public boolean test(String s) {
        return s.matches(REGEX);
    }
}
