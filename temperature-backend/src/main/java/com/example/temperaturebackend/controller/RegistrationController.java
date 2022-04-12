package com.example.temperaturebackend.controller;

import com.example.temperaturebackend.entity.AuthUser;
import com.example.temperaturebackend.entity.AuthUserModel;
import com.example.temperaturebackend.repository.AuthUserRepository;
import com.example.temperaturebackend.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AuthUserRepository authUserRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody AuthUserModel authUserModel, final HttpServletRequest request) {

        //Check if valid email
        boolean isValidEmail = emailValidator.test(authUserModel.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Invalid email!");
        }

        //Check if email exists
        boolean userExists = authUserRepository.findByEmail(authUserModel.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already taken!");
        }

        AuthUser authUser = authUserService.registerAuthUser(authUserModel);

        //create event to sent token to user in email
        publisher.publishEvent(new RegistrationCompleteEvent(
                authUser,
                applicationUrl(request)
        ));
        return "success";
    }

    @PostMapping("/login")
    public String LoginUser(@RequestBody AuthUserModel authUserModel, final HttpServletRequest request) {
        AuthUser authUser = authUserService.registerAuthUser(authUserModel);

        //create event to sent token to user in email
        return "success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = authUserService.validateVerificationToken(token);

        if (result.equalsIgnoreCase("valid")) {
            return "User verified";
        }
        return "Bad User";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @GetMapping("/home")
    public String home() {
        return "This is home page";
    }
}
