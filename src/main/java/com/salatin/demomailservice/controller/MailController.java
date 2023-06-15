package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.service.MailingService;
import com.salatin.demomailservice.service.Scheduler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
@Tag(name = "Mailing", description = "Operations for sending and scheduling emails")
public class MailController {
    private final MailingService mailingService;
    private final Scheduler mailingJobScheduler;

    @Operation(summary = "Send email to user by ID",
            description = "Sends single email to the user specified by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/users/{userId}/send")
    public ResponseEntity<Void> sendRestEmailToUserById(@PathVariable Integer userId) {
        mailingService.sendRestEmailToUserById(userId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Schedule mail job by cron ID",
            description = "Schedules a mail job based on the specified cron ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mail job scheduled successfully"),
            @ApiResponse(responseCode = "202",
                    description = "Cron job already scheduled, needs to stop previous"),
            @ApiResponse(responseCode = "404", description = "Cron not found")

    })
    @PostMapping("/crons/{cronId}/schedule")
    public ResponseEntity<Void> scheduleMailJobByCronId(@PathVariable Integer cronId) {
        mailingJobScheduler.schedule(cronId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Stop the mailing scheduler",
            description = "Stops the actually scheduled mailing task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mailing scheduler stopped successfully"),
            @ApiResponse(responseCode = "202",
                    description = "Cron job wasn't created yet or already stopped")
    })
    @PostMapping("/stop-scheduler")
    public ResponseEntity<Void> stopScheduledTask() {
        mailingJobScheduler.stop();

        return ResponseEntity.ok().build();
    }
}
