package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.model.dto.request.UserCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.UserUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.UserResponseDto;
import com.salatin.demomailservice.service.UserService;
import com.salatin.demomailservice.service.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(
        @RequestBody @Valid UserCreateRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);

        return new ResponseEntity<>(userMapper.toDto(userService.create(user)), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> update(
        @RequestBody @Valid UserUpdateRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);

        return new ResponseEntity<>(userMapper.toDto(userService.update(user)), HttpStatus.OK);
    }
}
