package com.example.temperaturebackend.controller;

import com.example.temperaturebackend.email.EmailSenderService;
import com.example.temperaturebackend.entity.AuthUser;
import com.example.temperaturebackend.entity.AuthUserModel;
import com.example.temperaturebackend.entity.VerificationToken;
import com.example.temperaturebackend.repository.AuthUserRepository;
import com.example.temperaturebackend.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
public class RegistrationController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private EmailSenderService emailSenderService;


    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("authUser", new AuthUser());

        return "signup_form";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("authUser", new AuthUser());

        return "login";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<AuthUser> listUsers = authUserRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @RequestMapping(value = "/process_register", method = RequestMethod.POST)
    public String processRegister(AuthUserModel authUserModel, final HttpServletRequest request) {

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
        return "register_success";
    }

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


    @GetMapping("/verifyRegistration")
    public String verifyToken(@RequestParam("token") String token) {
        String result = authUserService.validateVerificationToken(token);

        if (result.equalsIgnoreCase("valid")) {
            return "User verified".toString();
        }
        return "Bad User".toString();
    }

    @GetMapping("/resendToken")
    public String resendToken(@RequestParam("token") String token, HttpServletRequest request) {
        VerificationToken verificationToken = authUserService.generateNewToken(token);

        AuthUser authUser = verificationToken.getAuthUser();
        resendTokenEmail(authUser, applicationUrl(request), verificationToken);
        return "Sent verification link";
    }

    private void resendTokenEmail(AuthUser authUser, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl
                + "/verifyRegistration?token="
                + verificationToken.getToken();

        //send verification email
        log.info(authUser.getEmail());
        emailSenderService.sendSimpleEmail(authUser.getEmail(), url);
        log.info("Click the link to verify your account: {}", url);

    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


}

