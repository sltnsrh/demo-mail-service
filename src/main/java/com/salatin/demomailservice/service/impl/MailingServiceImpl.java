package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.model.Log;
import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.model.enums.EmailType;
import com.salatin.demomailservice.service.LogService;
import com.salatin.demomailservice.service.MailSenderService;
import com.salatin.demomailservice.service.MailingService;
import com.salatin.demomailservice.service.UserService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MailingServiceImpl implements MailingService {
    private static final String SUBJECT_GREETING = "Greetings!";
    private static final String USERNAME_TITLE = "Username: ";
    private static final String DATE_TIME_CREATION_TITLE = "Creation date and time: ";

    private final MailSenderService mailSenderService;
    private final UserService userService;
    private final LogService logService;

    @Override
    public void sendRestEmailToUserById(Integer userId) {
        var user = userService.findById(userId);
        var createdOn = LocalDateTime.now();
        var body = createBody(user, createdOn.toString());

        mailSenderService.send(user.getEmail(), SUBJECT_GREETING, body);
        createLog(user, createdOn, EmailType.REST);
    }

    @Override
    public void sendCronEmailToUser(User user) {
        var createdOn = LocalDateTime.now();
        var body = createBody(user, createdOn.toString());

        mailSenderService.send(user.getEmail(), SUBJECT_GREETING, body);
        createLog(user, createdOn, EmailType.CRON);
    }

    private String createBody(User user, String createdOn) {

        return USERNAME_TITLE + user.getUsername()
            + System.lineSeparator()
            + DATE_TIME_CREATION_TITLE + createdOn;
    }

    private void createLog(User user, LocalDateTime createdOn, EmailType emailType) {
        Log logToSave = new Log();
        logToSave.setUser(user);
        logToSave.setType(emailType);
        logToSave.setCreatedOn(createdOn);

        var savedLog = logService.save(logToSave);
        log.info("Log was successfully created: {}", savedLog);
    }
}
