package com.salatin.demomailservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.salatin.demomailservice.model.Log;
import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.service.LogService;
import com.salatin.demomailservice.service.MailSenderService;
import com.salatin.demomailservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(value = MockitoExtension.class)
class MailingServiceImplTest {
    private static final Integer USER_ID = 1;
    private static final String SUBJECT_GREETING = "Greetings!";
    private static final String USERNAME_TITLE = "Username: ";

    @InjectMocks
    private MailingServiceImpl mailingService;
    @Mock
    private MailSenderService mailSenderService;
    @Mock
    private UserService userService;
    @Mock
    private LogService logService;
    private User user;
    private ArgumentCaptor<String> userEmailCaptor;
    private ArgumentCaptor<String> subjectCaptor;
    private ArgumentCaptor<String> bodyCaptor;


    @BeforeEach
    void init() {
        user = new User();
        user.setEmail("email");
        user.setUsername("username");

        userEmailCaptor = ArgumentCaptor.forClass(String.class);
        subjectCaptor = ArgumentCaptor.forClass(String.class);
        bodyCaptor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    void sendRestEmailToUserById_WhenUserIdValid_ThenInvokesSaveLog() {
        when(userService.findById(USER_ID)).thenReturn(user);
        when(logService.save(any(Log.class))).thenReturn(new Log());

        mailingService.sendRestEmailToUserById(USER_ID);

        verify(userService).findById(USER_ID);
        verify(mailSenderService).send(
                userEmailCaptor.capture(),
                subjectCaptor.capture(),
                bodyCaptor.capture());
        verify(logService).save(any(Log.class));

        assertEquals(user.getEmail(), userEmailCaptor.getValue());
        assertEquals(SUBJECT_GREETING, subjectCaptor.getValue());
        assertTrue(bodyCaptor.getValue().startsWith(USERNAME_TITLE));
    }

    @Test
    void sendCronEmailToUser_WhenUserIsValid_ThenInvokesSaveLog() {
        when(logService.save(any(Log.class))).thenReturn(new Log());

        mailingService.sendCronEmailToUser(user);

        verify(mailSenderService)
                .send(userEmailCaptor.capture(), subjectCaptor.capture(), bodyCaptor.capture());
        verify(logService).save(any(Log.class));

        assertEquals(user.getEmail(), userEmailCaptor.getValue());
        assertEquals(SUBJECT_GREETING, subjectCaptor.getValue());
        assertTrue(bodyCaptor.getValue().startsWith(USERNAME_TITLE));
    }
}
