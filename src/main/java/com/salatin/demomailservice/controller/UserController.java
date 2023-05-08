package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.model.dto.request.UserCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.UserUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.UserMailStats;
import com.salatin.demomailservice.model.dto.response.UserResponseDto;
import com.salatin.demomailservice.service.UserService;
import com.salatin.demomailservice.service.UserStatsService;
import com.salatin.demomailservice.service.mapper.UserMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserStatsService statsService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(
        @RequestBody @Valid UserCreateRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);

        return new ResponseEntity<>(userMapper.toDto(userService.create(user)),
            HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> update(
        @RequestBody @Valid UserUpdateRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);

        return ResponseEntity.ok(userMapper.toDto(userService.update(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable @Positive @NotBlank Integer id
    ) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> findAll(
        @RequestParam(defaultValue = "0") @PositiveOrZero int page,
        @RequestParam(defaultValue = "10") @PositiveOrZero int size,
        @RequestParam(defaultValue = "createdOn") String sortByField,
        @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        PageRequest pageRequest = PageRequest.of(
            page,
            size,
            Sort.by(Sort.Direction.valueOf(sortDirection.toUpperCase()), sortByField)
        );

        return ResponseEntity.ok(userMapper.toPageDto(userService.findAll(pageRequest)));
    }

    @GetMapping(params = "username")
    public ResponseEntity<UserResponseDto> findByUsername(
        @RequestParam @NotBlank String username
    ) {

        return ResponseEntity.ok(userMapper.toDto(userService.findByUsername(username)));
    }

    @GetMapping(params = "email")
    public ResponseEntity<UserResponseDto> findByEmail(
        @RequestParam @NotBlank String email
    ) {

        return ResponseEntity.ok(userMapper.toDto(userService.findByEmail(email)));
    }

    @GetMapping("/stats")
    public ResponseEntity<List<UserMailStats>> showStats(
        @RequestParam(defaultValue = "0") @PositiveOrZero int page,
        @RequestParam(defaultValue = "10") @PositiveOrZero int size
    ) {

        return ResponseEntity.ok(statsService.showForAll(page, size));
    }
}
