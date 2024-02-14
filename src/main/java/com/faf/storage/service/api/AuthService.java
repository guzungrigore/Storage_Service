package com.faf.storage.service.api;

import com.faf.storage.dto.ResponseDto;
import com.faf.storage.dto.request.LoginRequest;
import com.faf.storage.dto.request.SignUpRequest;
import com.faf.storage.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    ResponseDto singUp(SignUpRequest signupRequest);
}
