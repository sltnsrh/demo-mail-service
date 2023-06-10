package com.salatin.demomailservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.salatin.demomailservice.model.dto.response.UserMailStats;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@ExtendWith(value = MockitoExtension.class)
class UserStatsServiceTest {
    @InjectMocks
    private UserStatsService userStatsService;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void showForAll() {
        var page = 0;
        var size = 5;
        var expectedStats = List.of(new UserMailStats(
                "Username",
                "email",
                new UserMailStats.Count(10, 5),
                "2023-01-01 00:00:00",
                "2023-06-01 00:00:00"));
        when(jdbcTemplate.query(
                anyString(),
                Mockito.<RowMapper<UserMailStats>>any(),
                eq(size),
                eq(page)))
                .thenReturn(expectedStats);

        var actualStats = userStatsService.showForAll(page, size);

        assertEquals(expectedStats, actualStats);
    }
}
