package com.faf.storage.controller;

import com.faf.storage.dto.ResponseDto;
import com.faf.storage.dto.UserDto;
import com.faf.storage.service.api.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.status(OK).body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> saveUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.status(CREATED).body(userService.saveUser(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable Long id,
                                                  @RequestBody @Valid UserDto userDto) {
        return ResponseEntity.status(OK).body(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(userService.deleteUser(id));
    }
}
