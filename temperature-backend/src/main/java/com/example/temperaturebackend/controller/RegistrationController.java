package com.example.temperaturebackend.controller;

import com.example.temperaturebackend.details.AuthUserDetails;
import com.example.temperaturebackend.email.EmailSenderService;
import com.example.temperaturebackend.email.EmailValidator;
import com.example.temperaturebackend.entity.AuthUser;
import com.example.temperaturebackend.entity.AuthUserModel;
import com.example.temperaturebackend.entity.VerificationToken;
import com.example.temperaturebackend.repository.AuthUserRepository;
import com.example.temperaturebackend.response.UserInfo;
import com.example.temperaturebackend.service.AuthUserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/springboot")
public class RegistrationController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/home")
    public String getHomePage() {
        return "Home page";
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "Admin page";
    }


    @PostMapping("/register")
    public String register(@RequestBody AuthUserModel authUserModel, final HttpServletRequest request) {
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
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(authUserDetails);
    }

    @GetMapping("/verifyRegistration")
    public String verifyToken(@RequestParam("token") String token) {
        String result = authUserService.validateVerificationToken(token);

        // verify user when the verification link is clicked
        if (result.equalsIgnoreCase("valid")) {
            return "User confirmed!".toString();
        }
        return "Invalid confirmation email!".toString();
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUserInfo(Principal user) {
        AuthUserDetails userObj = (AuthUserDetails) userDetailsService.loadUserByUsername(user.getName());

        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(userObj.getFirstName());
        userInfo.setLastName(userObj.getLastName());
        userInfo.setUsername(userObj.getUsername());

        return ResponseEntity.ok(userInfo);

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
                + "api/v1/springboot/verifyRegistration?token="
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

