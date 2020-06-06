package com.ubb.cloud.security.controller;

import com.ubb.cloud.security.exception.BadRequestException;
import com.ubb.cloud.security.messaging.UserMessageSender;
import com.ubb.cloud.security.model.User;
import com.ubb.cloud.security.payload.ApiResponse;
import com.ubb.cloud.security.payload.SignUpRequest;
import com.ubb.cloud.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    private final UserMessageSender userMessageSender;

    @Autowired
    public AuthController(UserService userService, UserMessageSender userMessageSender) {
        this.userService = userService;
        this.userMessageSender = userMessageSender;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            log.error("Email in use.");
            throw new BadRequestException("Email address already in use.");
        }
        User result = userService.saveUserSignUp(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        userMessageSender.sendUser(userService.toUserDetails(result));

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully."));
    }

}