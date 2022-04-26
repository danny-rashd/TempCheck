package com.example.temperaturebackend.controller;

import com.example.temperaturebackend.details.AuthUserDetails;
import com.example.temperaturebackend.email.EmailSenderService;
import com.example.temperaturebackend.email.EmailValidator;
import com.example.temperaturebackend.entity.*;
import com.example.temperaturebackend.event.RegistrationCompleteEvent;
import com.example.temperaturebackend.repository.AuthUserRepository;
import com.example.temperaturebackend.repository.VerificationTokenRepository;
import com.example.temperaturebackend.request.AuthenticationRequest;
import com.example.temperaturebackend.response.UserResponse;
import com.example.temperaturebackend.service.AuthUserService;

import com.example.temperaturebackend.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/springboot")
public class AppController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FileService fileService;


    @PostMapping("/register")
    public String register(@RequestBody UserModelEntity userModelEntity, final HttpServletRequest request) {
        //Check if valid email
        boolean isValidEmail = emailValidator.test(userModelEntity.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Invalid email!");
        }

        //Check if email exists
        boolean userExists = authUserRepository.findByEmail(userModelEntity.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already taken!");
        }

        UserEntity userEntity = authUserService.registerAuthUser(userModelEntity);

        //create event to sent token to user in email
        eventPublisher.publishEvent(new RegistrationCompleteEvent(
                userEntity,
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
    public String getTokenConfirmation(@RequestParam("token") String token) {
        String result = authUserService.validateVerificationToken(token);

        // verify user when the verification link is clicked
        if (result.equalsIgnoreCase("valid")) {
            return "User confirmed!".toString();
        }
        return "Invalid confirmation email!".toString();
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUserResponse(Principal user) {
        AuthUserDetails userObj = (AuthUserDetails) userDetailsService.loadUserByUsername(user.getName());

        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(userObj.getFirstName());
        userResponse.setLastName(userObj.getLastName());
        userResponse.setUsername(userObj.getUsername());

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/resendToken")
    public String getNewConfirmationEmail(@RequestParam("email") String email, HttpServletRequest request) {
        VerificationTokenEntity verificationTokenEntity = authUserService.generateNewToken(email);

        UserEntity userEntity = verificationTokenEntity.getUserEntity();
        resendTokenEmail(userEntity, applicationUrl(request), verificationTokenEntity);
        return "Sent verification link";
    }

    private void resendTokenEmail(UserEntity userEntity, String applicationUrl, VerificationTokenEntity verificationTokenEntity) {
        String url = applicationUrl
                + "/api/v1/springboot/verifyRegistration?token="
                + verificationTokenEntity.getToken();

        //send verification email
        log.info(userEntity.getEmail());
        emailSenderService.sendSimpleEmail(userEntity.getEmail(), url);
        log.info("NEW CONFIRMATION EMAIL HAS BEEN SENT");
        log.info("Click the link to verify your account: {}", url);

    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("File Uploaded!");
        return new ResponseEntity<>(fileService.uploadCSVFile(file), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String id) throws IOException {
        FileEntity csvFileEntity = fileService.downloadCSVFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(csvFileEntity.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + csvFileEntity.getFilename() + "\"")
                .body(new ByteArrayResource(csvFileEntity.getFile()));
    }

    @GetMapping("/csv_info")
    public ResponseEntity getFiles(@RequestParam("id") String id) throws IOException {
        ObjectId objectId = new ObjectId(id);
        Map<ObjectId, String> files = fileService.getFileById(objectId);
        return ResponseEntity.ok(files);
    }

    public List<FileDataEntity> getAllFiles() throws IOException {
        return fileService.getAllFiles();
    }

}

