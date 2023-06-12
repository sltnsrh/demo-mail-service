package com.salatin.demomailservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.salatin.demomailservice.service.MailingService;
import com.salatin.demomailservice.service.Scheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(value = MockitoExtension.class)
class MailControllerTest {
    @InjectMocks
    private MailController mailController;
    @Mock
    private MailingService mailingService;
    @Mock
    private Scheduler mailingJobScheduler;

    @Test
    void sendRestEmailToUserById_WhenValidUserId_ThenHttpStatusOk() {
        var userId = 1;
        var actualResponse = mailController.sendRestEmailToUserById(userId);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        verify(mailingService).sendRestEmailToUserById(userId);
    }

    @Test
    void scheduleMailJobByCronId_WhenValidCronId_ThenHttpStatusOk() {
        var cronId = 1;
        var actualResponse = mailController.scheduleMailJobByCronId(cronId);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        verify(mailingJobScheduler).schedule(cronId);
    }

    @Test
    void stopScheduledTask_WhenStoppedSuccessfully_ThenHttpStatusOk() {
        var actualResponse = mailController.stopScheduledTask();

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        verify(mailingJobScheduler).stop();
    }
}