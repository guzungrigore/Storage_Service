package com.faf.storage.controller;

import com.faf.storage.dto.ResponseDto;
import com.faf.storage.dto.request.LoginRequest;
import com.faf.storage.dto.request.SignUpRequest;
import com.faf.storage.dto.response.LoginResponse;
import com.faf.storage.service.api.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.status(OK).body(authService.login(loginRequest));
    }

    @PostMapping("/singUp")
    public ResponseEntity<ResponseDto> singUp(@RequestBody @Valid SignUpRequest signupRequest) {
        return ResponseEntity.status(OK).body(authService.singUp(signupRequest));
    }
}
