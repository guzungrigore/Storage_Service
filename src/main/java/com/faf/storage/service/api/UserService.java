package com.faf.storage.service.api;

import com.faf.storage.dto.ResponseDto;
import com.faf.storage.dto.UserDto;

import java.util.List;

public interface UserService {
    
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    ResponseDto saveUser(UserDto userDto);

    ResponseDto updateUser(Long id, UserDto userDto);

    ResponseDto deleteUser(Long id);
}
