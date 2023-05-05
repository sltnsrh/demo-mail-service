package com.salatin.demomailservice.service;

import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CronJobScheduler {
    private final CronService cronService;
    private final TaskScheduler taskScheduler;
    private final UserService userService;
    private final MailingService mailingService;

    public void scheduleMailJob(Integer cronId) {
        var cron = cronService.findById(cronId);

        taskScheduler.schedule(
            createMailJobTask(),
            new CronTrigger(cron.getExpression(), TimeZone.getDefault())
        );
    }

    private Runnable createMailJobTask() {
        return () -> {
            var users = userService.findAll();

            users.forEach(mailingService::sendCronEmailToUser);
        };
    }
}
