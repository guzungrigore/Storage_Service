package com.faf.storage.controller;

import com.faf.storage.domain.User;
import com.faf.storage.dto.request.SignUpRequest;
import com.faf.storage.repository.UserRepository;
import com.faf.storage.service.MailService;
import com.faf.storage.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/user")
public class UserController {

    private static class UserControllerException extends RuntimeException {
        private UserControllerException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    public UserController(UserRepository userRepository, UserService userService, MailService mailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(CREATED)
    public void registerAccount(@RequestBody @Valid SignUpRequest request) {
        User user = userService.signUp(request);
        mailService.sendActivationEmail(user);
    }
}
