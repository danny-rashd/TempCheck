package com.example.temperaturebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserModel {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
