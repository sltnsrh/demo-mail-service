package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.model.dto.request.UserCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.UserUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.UserMailStats;
import com.salatin.demomailservice.model.dto.response.UserResponseDto;
import com.salatin.demomailservice.service.UserService;
import com.salatin.demomailservice.service.UserStatsService;
import com.salatin.demomailservice.service.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "CRUD operations with users and their mailing statistics")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserStatsService statsService;

    @Operation(summary = "Create user",
            description = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "409", description = "Username or email already exist")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> create(
        @RequestBody @Valid UserCreateRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);

        return new ResponseEntity<>(userMapper.toDto(userService.create(user)),
            HttpStatus.CREATED);
    }

    @Operation(summary = "Update user",
            description = "Updates an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Username or email already exist")
    })
    @PutMapping
    public ResponseEntity<UserResponseDto> update(
        @RequestBody @Valid UserUpdateRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);

        return ResponseEntity.ok(userMapper.toDto(userService.update(user)));
    }

    @Operation(summary = "Delete user",
            description = "Deletes a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable @Positive Integer id
    ) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all users",
            description = "Retrieves all users with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
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

    @Operation(summary = "Find user by username",
            description = "Retrieves a user by their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid username parameter"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(params = "username")
    public ResponseEntity<UserResponseDto> findByUsername(
        @RequestParam @NotBlank String username
    ) {
        return ResponseEntity.ok(userMapper.toDto(userService.findByUsername(username)));
    }

    @Operation(summary = "Find user by email",
            description = "Retrieves a user by their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid email parameter"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(params = "email")
    public ResponseEntity<UserResponseDto> findByEmail(
        @RequestParam @NotBlank String email
    ) {
        return ResponseEntity.ok(userMapper.toDto(userService.findByEmail(email)));
    }

    @Operation(summary = "Show users statistics",
            description = "Retrieves statistics for all users with pagination. "
                    + "Shows how many emails were sent to user, "
                    + "and what type of mailing was used for (REST or CRON). "
                    + "Also, shows the date of the first and the last email sent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users statistics retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping("/stats")
    public ResponseEntity<List<UserMailStats>> showStats(
        @RequestParam(defaultValue = "0") @PositiveOrZero int page,
        @RequestParam(defaultValue = "10") @PositiveOrZero int size
    ) {
        return ResponseEntity.ok(statsService.showForAll(page, size));
    }
}
