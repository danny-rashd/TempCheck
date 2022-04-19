package com.example.temperaturebackend.controller;

import com.example.temperaturebackend.details.AuthUserDetails;
import com.example.temperaturebackend.email.EmailSenderService;
import com.example.temperaturebackend.email.EmailValidator;
import com.example.temperaturebackend.entity.AuthUser;
import com.example.temperaturebackend.entity.AuthUserModel;
import com.example.temperaturebackend.entity.VerificationToken;
import com.example.temperaturebackend.repository.AuthUserRepository;
import com.example.temperaturebackend.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
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
    private ApplicationEventPublisher publisher;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    // index page -> http://localhost:8080/
    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    // register page -> http://localhost:8080/register
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("authUser", new AuthUser());

        return "signup_form";
    }

    // process after clicking submit in register
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

    // display page when login is successful -> http://localhost:8080/users
    @GetMapping("/users")
    public String getUsers(AuthUser authUser) {
        String email = authUser.getEmail();
        System.out.println("username: " + email);
        return "Hello User";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "Home page";
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "Admin page";
    }

    //
    @PostMapping("/register")
    public String addUser(@RequestBody AuthUserModel authUserModel, final HttpServletRequest request) {

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

        // verify user when the verification link is clicked
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

