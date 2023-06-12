package com.salatin.demomailservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.model.dto.request.UserCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.UserUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.UserResponseDto;
import com.salatin.demomailservice.service.UserService;
import com.salatin.demomailservice.service.UserStatsService;
import com.salatin.demomailservice.service.mapper.UserMapper;
import java.util.Collections;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

@ExtendWith(value = MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserService userService;
    @Mock
    private UserStatsService statsService;

    @Test
    void create_WhenValidRequest_ThenReturnsCreatedUser() {
        var requestDto = new UserCreateRequestDto();
        var user = new User();
        var responseDto = new UserResponseDto();

        when(userMapper.toModel(requestDto)).thenReturn(user);
        when(userService.create(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(responseDto);

        assertNotNull(userController.create(requestDto));
        verify(userMapper).toModel(requestDto);
        verify(userService).create(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void update_WhenValidRequest_ThenReturnsUpdatedUser() {
        var requestDto = new UserUpdateRequestDto();
        var user = new User();
        var responseDto = new UserResponseDto();

        when(userMapper.toModel(requestDto)).thenReturn(user);
        when(userService.update(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(responseDto);

        assertNotNull(userController.update(requestDto));
        verify(userMapper).toModel(requestDto);
        verify(userService).update(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void delete_WhenValidId_ThenHttpStatusNoContent() {
        var userId = 1;
        var actualResponse = userController.delete(userId);

        assertEquals(HttpStatus.NO_CONTENT, actualResponse.getStatusCode());
        verify(userService).delete(userId);
    }

    @Test
    void findAll_WhenValidRequest_ThenReturnsEmptyPage() {
        Page<User> userPage = Page.empty();
        Page<UserResponseDto> userResponsePage = Page.empty();

        when(userService.findAll(any(PageRequest.class))).thenReturn(userPage);
        when(userMapper.toPageDto(userPage)).thenReturn(userResponsePage);

        var actualResponse = userController.findAll(0, 10, "createdOn", "ASC");

        assertFalse(Objects.requireNonNull(actualResponse.getBody()).hasContent());
        verify(userService).findAll(any(PageRequest.class));
        verify(userMapper).toPageDto(userPage);
    }

    @Test
    void findByUserName_WhenValidUsername_ThenReturnsUser() {
        var username = "Test_username";
        var user = new User();
        var responseDto = new UserResponseDto();

        when(userService.findByUsername(username)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(responseDto);

        assertNotNull(userController.findByUsername(username));
        verify(userService).findByUsername(username);
        verify(userMapper).toDto(user);
    }

    @Test
    void findByEmail_WhenValidRequest_ThenReturnsUser() {
        var email = "Test_email@mail.com";
        var user = new User();
        var responseDto = new UserResponseDto();

        when(userService.findByEmail(email)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(responseDto);

        assertNotNull(userController.findByEmail(email));
        verify(userService).findByEmail(email);
        verify(userMapper).toDto(user);
    }

    @Test
    void showStats_WhenValidRequest_ThenReturnsEmptyStatsList() {
        var page = 0;
        var size = 10;

        when(statsService.showForAll(page, size)).thenReturn(Collections.emptyList());

        var actualResponse = userController.showStats(page, size);

        assertTrue(Objects.requireNonNull(actualResponse.getBody()).isEmpty());
    }
}
