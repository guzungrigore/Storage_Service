package com.faf.storage.service;

import com.faf.storage.dto.ResponseDto;
import com.faf.storage.dto.UserDto;
import com.faf.storage.service.api.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }

    @Override
    public ResponseDto saveUser(UserDto userDto) {
        return null;
    }

    @Override
    public ResponseDto updateUser(Long id, UserDto userDto) {
        return null;
    }

    @Override
    public ResponseDto deleteUser(Long id) {
        return null;
    }
}
