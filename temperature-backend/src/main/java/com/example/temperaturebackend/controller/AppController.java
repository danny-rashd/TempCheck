package com.example.temperaturebackend.controller;

import com.example.temperaturebackend.details.AuthUserDetails;
import com.example.temperaturebackend.email.EmailSenderService;
import com.example.temperaturebackend.email.EmailValidator;
import com.example.temperaturebackend.entity.*;
import com.example.temperaturebackend.event.RegistrationCompleteEvent;
import com.example.temperaturebackend.repository.AuthUserRepository;
import com.example.temperaturebackend.repository.VerificationTokenRepository;
import com.example.temperaturebackend.request.AuthenticationRequest;
import com.example.temperaturebackend.service.AuthUserService;
import com.example.temperaturebackend.service.FileService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(path = "api/v1")
@Tag(name = "Temperature web application")
@Api(tags = "Temperature web application")
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

    @ApiOperation(value = "Create new user account")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping("/register")
    public String register(@RequestBody UserModel userModel, final HttpServletRequest request) {
        // Check if valid email
        boolean isValidEmail = emailValidator.test(userModel.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Invalid email!");
        }

        // Check if email exists
        boolean userExists = authUserRepository.findByEmail(userModel.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already taken!");
        }

        UserEntity userEntity = authUserService.registerAuthUser(userModel);

        // create event to sent token to user in email
        eventPublisher.publishEvent(new RegistrationCompleteEvent(
                userEntity,
                applicationUrl(request)));
        return "success";
    }

    @ApiOperation(value = "Sign in user account")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(authUserDetails);
    }

    @ApiOperation(value = "Get token confirmation")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping("/token-verify")
    public String getTokenConfirmation(
            @ApiParam(value = "Confirmation token to enable user authentication", type = "String", required = true) @RequestParam("token") String token) {
        String result = authUserService.validateVerificationToken(token);

        // verify user when the verification link is clicked
        if (result.equalsIgnoreCase("valid")) {
            return "User confirmed!";
        }
        return "Invalid confirmation email!";
    }

    @ApiOperation(value = "Get new confirmation email")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping("/token-resend")
    public String getNewConfirmationEmail(
            @ApiParam(value = "User's email to resend confirmation email to", type = "String", required = true) @RequestParam("email") String email,
            HttpServletRequest request) {
        VerificationTokenEntity verificationTokenEntity = authUserService.generateNewToken(email);

        UserEntity userEntity = verificationTokenEntity.getUserEntity();
        resendTokenEmail(userEntity, applicationUrl(request), verificationTokenEntity);
        return "Sent verification link";
    }

    private void resendTokenEmail(UserEntity userEntity, String applicationUrl,
            VerificationTokenEntity verificationTokenEntity) {
        String url = applicationUrl
                + "/api/v1/token-verify?token="
                + verificationTokenEntity.getToken();

        // send verification email
        log.info(userEntity.getEmail());
        emailSenderService.sendSimpleEmail(userEntity.getEmail(), url);
        log.info("NEW CONFIRMATION EMAIL HAS BEEN SENT");
        log.info("Click the link to verify your account: {}", url);

    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @ApiOperation(value = "Upload file to database")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @ApiParam(value = "File to be uploaded to the database", type = "String", required = true) @RequestParam("file") MultipartFile file)
            throws IOException {
        System.out.println("File Uploaded!");
        return new ResponseEntity<>(fileService.uploadCSVFile(file), HttpStatus.OK);
    }

    @ApiOperation(value = "Get file from MongoDB")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String id) throws IOException {
        FileEntity csvFileEntity = fileService.downloadCSVFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(csvFileEntity.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + csvFileEntity.getFilename() + "\"")
                .body(new ByteArrayResource(csvFileEntity.getFile()));
    }

    @ApiOperation(value = "Get all information of uploaded files")
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping("/files")
    public List<FileDataEntity> getAllFiles() throws IOException {
        return fileService.getAllFiles();
    }

}
