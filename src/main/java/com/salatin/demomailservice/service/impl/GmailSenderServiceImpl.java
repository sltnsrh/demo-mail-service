package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.service.MailSenderService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class GmailSenderServiceImpl implements MailSenderService {
    private static final String NO_SUBJECT = "No subject";

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(String to,
                     String subject,
                     String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setText(body);
        message.setSubject(Objects.requireNonNullElse(subject, NO_SUBJECT));

        mailSender.send(message);

        log.info(() -> "Email was successfully sent to: " + to);
    }
}
