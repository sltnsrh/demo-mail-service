package com.salatin.demomailservice.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(value = MockitoExtension.class)
class GmailSenderServiceImplTest {
    @InjectMocks
    private GmailSenderServiceImpl gmailSenderService;
    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void send_WhenValidData_ThenSendsMethodInvoke() {
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        gmailSenderService.send("to", "subject", "body");

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }
}
