package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.service.MailingService;
import com.salatin.demomailservice.service.Scheduler;
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
    private final Scheduler mailingJobScheduler;

    @PostMapping
    @RequestMapping("/users/{userId}/send")
    public ResponseEntity<Void> sendRestEmailToUserById(@PathVariable Integer userId) {
        mailingService.sendRestEmailToUserById(userId);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    @RequestMapping("/crons/{cronId}/schedule")
    public ResponseEntity<Void> scheduleMailJobByCronId(@PathVariable Integer cronId) {
        mailingJobScheduler.schedule(cronId);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    @RequestMapping("/stop-scheduler")
    public ResponseEntity<Void> stopScheduledTask() {
        mailingJobScheduler.stop();

        return ResponseEntity.ok().build();
    }
}
