package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.service.CronJobScheduler;
import com.salatin.demomailservice.service.MailingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailingService mailingService;
    private final CronJobScheduler cronJobScheduler;

    @PostMapping
    @RequestMapping("/send/user/{userId}")
    public ResponseEntity<Void> sendByUserId(@PathVariable Integer userId) {
        mailingService.sendRestEmailToUserById(userId);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    @RequestMapping("/setup/cron/{cronId}")
    public ResponseEntity<Void> setCronJob(@PathVariable Integer cronId) {
        cronJobScheduler.scheduleMailJob(cronId);

        return ResponseEntity.ok().build();
    }
}
