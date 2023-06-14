package com.salatin.demomailservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.salatin.demomailservice.model.dto.request.UserCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.UserUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

public class UserIntegrationTest extends IntegrationTest {

    @Test
    @Sql(value = "/delete_all_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_WhenValidRequest_ThenReturnsCreatedUser() throws Exception {
        var requestDto = new UserCreateRequestDto("username", "test@mail.com");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @Sql(value = "/create_first_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete_all_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_WhenEmailExists_ThenThrowsConflict() throws Exception{
        var requestDto = new UserCreateRequestDto("username", "test@mail.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isConflict());
    }

    @Test
    @Sql(value = "/create_first_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete_all_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update_WhenValidRequest_ThenReturnsOk() throws Exception {
        var requestDto = new UserUpdateRequestDto(1, "new_username", "test@mail.com");

        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void update_WhenUserDoesNotExist_ThenReturnsNotFound() throws Exception {
        var requestDto = new UserUpdateRequestDto(Integer.MAX_VALUE, "username", "test1@mail.com");

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(value = {"/create_first_user.sql", "/create_second_user.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete_all_users.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update_WhenUsernameExists_ThenReturnsConflict() throws Exception {
        var requestDto = new UserUpdateRequestDto(1, "username2", "test@mail.com");

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isConflict());
    }

    @Test
    @Sql(value = "/create_first_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete_WhenValidId_ThenReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_WhenInvalidId_ThenReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_WhenValidRequest_ThenReturnsStatusOk() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = "/create_first_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete_all_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByUsername_WhenValidUsername_ThenReturnsUserWithRequestedUsername() throws Exception {
        var expectedUsername = "username";
        var result = mockMvc.perform(get("/users")
                .param("username", expectedUsername))
                .andExpect(status().isOk())
                .andReturn();
        var userResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(),
                UserResponseDto.class);

        assertEquals(expectedUsername, userResponseDto.getUsername());
    }

    @Test
    void findByUsername_WhenInvalidUsername_ThenReturnsNotFoundStatus() throws Exception {
        mockMvc.perform(get("/users")
                        .param("username", "not-existing-username"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @Sql(value = "/create_first_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete_all_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByEmail_WhenValidEmail_ThenReturnsUserWithRequestedEmail() throws Exception {
        var expectedEmail = "test@mail.com";
        var result = mockMvc.perform(get("/users")
                        .param("email", expectedEmail))
                .andExpect(status().isOk())
                .andReturn();
        var userResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(),
                UserResponseDto.class);

        assertEquals(expectedEmail, userResponseDto.getEmail());
    }

    @Test
    void findByEmail_WhenInvalidEmail_ThenReturnsNotFoundStatus() throws Exception {
        mockMvc.perform(get("/users")
                        .param("email", "invalid@mail.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void showStats_WhenValidRequest_ThenReturnsStatusOk() throws Exception {
        mockMvc.perform(get("/users/stats"))
                .andExpect(status().isOk());
    }
}
