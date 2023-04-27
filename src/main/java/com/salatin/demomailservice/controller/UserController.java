package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.model.dto.request.UserCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.UserUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.UserResponseDto;
import com.salatin.demomailservice.service.UserService;
import com.salatin.demomailservice.service.mapper.UserMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Integer id) {
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> findAll(
        @RequestParam(defaultValue = "0") @PositiveOrZero int page,
        @RequestParam(defaultValue = "10") @PositiveOrZero int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return new ResponseEntity<>(
            userMapper.toPageDto(userService.findAll(pageRequest)),
            HttpStatus.OK);
    }
}
