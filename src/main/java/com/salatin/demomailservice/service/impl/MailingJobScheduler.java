package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.service.CronService;
import com.salatin.demomailservice.service.MailingService;
import com.salatin.demomailservice.service.Scheduler;
import com.salatin.demomailservice.service.UserService;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class MailingJobScheduler implements Scheduler {
    private final CronService cronService;
    private final TaskScheduler taskScheduler;
    private final UserService userService;
    private final MailingService mailingService;
    @Setter
    private ScheduledFuture<?> scheduledTask;

    @Override
    public void schedule(Integer cronId) {
        if (scheduledTask != null && !scheduledTask.isCancelled())
            throw new SchedulingException("Scheduler is already started, try to stop it first");

        var cron = cronService.findById(cronId);

        scheduledTask = taskScheduler.schedule(
            createMailJobTask(),
            new CronTrigger(cron.getExpression(), TimeZone.getDefault())
        );
    }

    @Override
    public void stop() {
        if (scheduledTask != null) {
            boolean canceled = scheduledTask.cancel(true);
            if (!canceled) {
                throw new SchedulingException("Scheduler was already stopped");
            } else {
                log.info("Mailing job was successfully stopped");
            }
        } else {
            throw new SchedulingException("Can't stop scheduler, because it wasn't started yet");
        }
    }

    private Runnable createMailJobTask() {
        return () -> {
            var users = userService.findAll();

            users.forEach(mailingService::sendCronEmailToUser);
        };
    }
}
