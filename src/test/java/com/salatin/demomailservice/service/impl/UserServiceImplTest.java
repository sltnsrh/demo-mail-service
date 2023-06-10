package com.salatin.demomailservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(value = MockitoExtension.class)
class UserServiceImplTest {
    private final static Integer ID = 1;
    private final static String EMAIL = "user@email.com";
    private final static String USERNAME = "test-username";

    @Spy
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void init() {
        user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
        user.setUsername(USERNAME);
    }

    @Test
    void create_WhenUserIsValidAndUnique_ThenReturnsCreatedUser() {
        when(userRepository.findByUsernameIgnoreCase(user.getUsername()))
                .thenReturn(Optional.empty());
        when(userRepository.findByEmailIgnoreCase(user.getEmail()))
                .thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        assertNotNull(userService.create(user));
        verify(userRepository).findByUsernameIgnoreCase(user.getUsername());
        verify(userRepository).findByEmailIgnoreCase(user.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void create_WhenUsernameExists_ThenThrowsEntityExistsException() {
        when(userRepository.findByUsernameIgnoreCase(user.getUsername()))
                .thenReturn(Optional.of(new User()));

        assertThrows(EntityExistsException.class, () -> userService.create(user));
        verify(userRepository).findByUsernameIgnoreCase(user.getUsername());
    }

    @Test
    void create_WhenEmailExists_ThenThrowsEntityExistsException() {
        when(userRepository.findByEmailIgnoreCase(user.getEmail()))
                .thenReturn(Optional.of(new User()));

        assertThrows(EntityExistsException.class, () -> userService.create(user));
        verify(userRepository).findByEmailIgnoreCase(user.getEmail());
    }

    @Test
    void update_WhenUserDoesNotExist_ThenThrowsEntityNotFoundException() {
        doThrow(EntityNotFoundException.class).when(userService).findById(user.getId());

        assertThrows(EntityNotFoundException.class, () -> userService.update(user));
    }

    @Test
    void update_WhenValidRequest_ThenReturnsUpdatedUser() {
        doReturn(user).when(userService).findById(user.getId());
        when(userRepository.findByUsernameIgnoreCase(user.getUsername()))
                .thenReturn(Optional.of(user));
        when(userRepository.findByEmailIgnoreCase(user.getEmail()))
                .thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        assertNotNull(userService.update(user));
        verify(userRepository).findByUsernameIgnoreCase(user.getUsername());
        verify(userRepository).findByEmailIgnoreCase(user.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void findById_WhenInvalidId_ThenThrowsEntityNotFoundException() {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.findById(ID));
    }

    @Test
    void findById_WhenValidId_ThenReturnsUser() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));

        assertNotNull(userService.findById(ID));
    }

    @Test
    void delete_WhenValidId_ThenInvokesDelete() {
        doReturn(user).when(userService).findById(ID);
        doNothing().when(userRepository).delete(user);
        userService.delete(ID);

        verify(userRepository).delete(user);
    }

    @Test
    void findAll_WhenArgumentPageRequest_ThenReturnsEmptyPage() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(userRepository.findAll(pageRequest)).thenReturn(Page.empty());

        assertEquals(0, userService.findAll(pageRequest).getTotalElements());
    }

    @Test
    void findAll_WhenInvoke_ThenReturnsEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());
        var actual = userService.findAll();

        assertEquals(0, actual.size());
    }

    @Test
    void findByUserName_WhenUsernameIsValid_ThenReturnsUser() {
        when(userRepository.findByUsernameIgnoreCase(USERNAME))
                .thenReturn(Optional.of(user));

        assertNotNull(userService.findByUsername(USERNAME));
    }

    @Test
    void findByUserName_WhenUsernameIsInvalid_ThenThrowsEntityNotFoundException() {
        when(userRepository.findByUsernameIgnoreCase(USERNAME))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.findByUsername(USERNAME));
    }

    @Test
    void findByEmail_WhenEmailIsValid_ThenReturnsUser() {
        when(userRepository.findByEmailIgnoreCase(EMAIL)).thenReturn(Optional.of(user));

        assertNotNull(userService.findByEmail(EMAIL));
    }

    @Test
    void findByEmail_WhenEmailIsInvalid_ThenThrowsEntityNotFoundException() {
        when(userRepository.findByEmailIgnoreCase(EMAIL)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.findByEmail(EMAIL));
    }
}
