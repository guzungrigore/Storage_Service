package com.faf.storage.service;

import com.faf.storage.dto.ResponseDto;
import com.faf.storage.dto.request.LoginRequest;
import com.faf.storage.dto.request.SignUpRequest;
import com.faf.storage.dto.response.LoginResponse;
import com.faf.storage.service.api.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public ResponseDto singUp(SignUpRequest signupRequest) {
        return null;
    }
}
