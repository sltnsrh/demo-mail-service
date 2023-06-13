package com.salatin.demomailservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.salatin.demomailservice.model.dto.request.CronCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.CronUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.CronResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

public class CronIntegrationTest extends IntegrationTest {

    @Test
    @Sql(value = "/delete_cron.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_WhenValidRequest_ThenReturnsIsCreated () throws Exception {
        var requestDto = new CronCreateRequestDto("* * * * * *");

        mockMvc.perform(post("/crons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void create_WhenInvalidRequest_ThenReturnsBadRequest () throws Exception {
        var requestDto = new CronCreateRequestDto("bad-expression");

        mockMvc.perform(post("/crons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(value = "/create_cron.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete_cron.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_WhenExpressionExists_ThenReturnsConflict () throws Exception {
        var requestDto = new CronCreateRequestDto("* * * * * *");

        mockMvc.perform(post("/crons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isConflict());
    }

    @Test
    @Sql(value = "/create_cron.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete_updated_cron.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update_WhenValidRequest_ThenExpressionUpdated() throws Exception {
        var requestDto = new CronUpdateRequestDto(1, "*/1 * * * * *");
        var mvcResult = mockMvc.perform(put("/crons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();
        var actualResponseDto = objectMapper
                .readValue(
                        mvcResult.getResponse().getContentAsString(),
                        CronResponseDto.class
                );

        assertEquals(requestDto.getExpression(), actualResponseDto.getExpression());
    }

    @Test
    void update_WhenInvalidId_ThenReturnsNotFound() throws Exception {
        var requestDto = new CronUpdateRequestDto(Integer.MAX_VALUE, "*/1 * * * * *");
        mockMvc.perform(put("/crons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_WhenInvalidExpression_ThenReturnsBadRequest() throws Exception {
        var requestDto = new CronUpdateRequestDto(1, "bad-expression");
        mockMvc.perform(put("/crons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(value = "/create_cron.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete_WhenValidId_ThenReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/crons/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_WhenInvalidId_ThenReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/crons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_WhenValidRequest_ThenReturnsOk() throws Exception {
        mockMvc.perform(get("/crons"))
                .andExpect(status().isOk());
    }
}
