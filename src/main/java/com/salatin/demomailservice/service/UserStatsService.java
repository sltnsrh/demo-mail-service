package com.salatin.demomailservice.service;

import com.salatin.demomailservice.model.dto.response.UserMailStats;
import java.text.SimpleDateFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStatsService {
    private static final String STATS_QUERY = "SELECT u.username, u.email, "
            + "(SELECT COUNT(*) FROM Logs l WHERE l.user_id = u.id AND l.type = 'REST') AS rest_count, "
            + "(SELECT COUNT(*) FROM Logs l WHERE l.user_id = u.id AND l.type ='CRON') AS cron_count, "
            + "MIN(l.created_on) AS first, "
            + "MAX(l.created_on) AS last, "
            + "(SELECT COUNT(*) FROM Logs l WHERE l.user_id = u.id) AS mails_sum "
            + "FROM Users u "
            + "LEFT JOIN Logs l ON u.id = l.user_id "
            + "GROUP BY u.id "
            + "ORDER BY mails_sum DESC "
            + "LIMIT ? OFFSET ?";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final JdbcTemplate jdbcTemplate;

    public List<UserMailStats> showForAll(int page, int size) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
        int offset = page * size;

        return jdbcTemplate.query(
            STATS_QUERY,
            (rs, rowNum) -> {
                var username = rs.getString("username");
                var email = rs.getString("email");
                var restCount = rs.getInt("rest_count");
                var cronCount = rs.getInt("cron_count");
                var first = rs.getTimestamp("first") == null ? null
                    : dateFormat.format(rs.getTimestamp("first"));
                var last = rs.getTimestamp("last") == null ? null
                    : dateFormat.format(rs.getTimestamp("last"));
                var count = new UserMailStats.Count(restCount, cronCount);

                return new UserMailStats(username, email, count, first, last);
            },
            size,
            offset);
    }
}
