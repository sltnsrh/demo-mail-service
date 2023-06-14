package com.salatin.demomailservice.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

public class MailIntegrationTest extends IntegrationTest {

    @Test
    @Sql(value = "/create_first_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete_all_logs.sql", "/delete_all_users.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void sendRestEmailToUserById_WhenValidUserId_ThenReturnsStatusOk() throws Exception {
        mockMvc.perform(post("/mail/users/{userId}/send", 1))
                .andExpect(status().isOk());
    }

    @Test
    void sendRestEmailToUserById_WhenInvalidUserId_ThenReturnsStatusNotFound() throws Exception {
        mockMvc.perform(post("/mail/users/{userId}/send", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(value = "/create_first_cron.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete_all_crons.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void scheduleMailJobByCronId_WhenValidCronId_ThenReturnsStatusOk() throws Exception {
        mockMvc.perform(post("/mail/crons/{cronId}/schedule", 1))
                .andExpect(status().isOk());
    }

    @Test
    void scheduleMailJobByCronId_WhenInvalidCronId_ThenReturnsStatusNotFound() throws Exception {
        mockMvc.perform(post("/mail/crons/{cronId}/schedule", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void stopScheduledTask_WhenNotScheduledTaskBefore_ThenReturnsStatusAccepted()
            throws Exception {
        mockMvc.perform(post("/mail/stop-scheduler"))
                .andExpect(status().isAccepted());
    }

    @Test
    @Sql(value = "/create_first_cron.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete_all_crons.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void stopScheduledTask_WhenScheduledTaskBefore_ThenReturnsStatusOk() throws Exception {
        mockMvc.perform(post("/mail/crons/{cronId}/schedule", 1))
                .andExpect(status().isOk());

        mockMvc.perform(post("/mail/stop-scheduler"))
                .andExpect(status().isOk());
    }
}
