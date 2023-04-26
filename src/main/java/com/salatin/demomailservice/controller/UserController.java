package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.service.UserService;
import com.salatin.demomailservice.model.dto.request.UserCreateRequestDto;
import com.salatin.demomailservice.model.dto.response.UserResponseDto;
import com.salatin.demomailservice.service.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(
        @RequestBody @Valid UserCreateRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);

        return new ResponseEntity<>(userMapper.toDto(userService.save(user)), HttpStatus.CREATED);
    }
}
