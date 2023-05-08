package com.salatin.demomailservice.service;

import com.salatin.demomailservice.model.dto.response.UserMailStatistics;
import org.springframework.stereotype.Component;

@Component
public class MailingStatistics {

    public UserMailStatistics showByAllUsers() {

        UserMailStatistics.Count count = new UserMailStatistics.Count(1, 1);

        UserMailStatistics userMailStatistics = new UserMailStatistics(
            "username", "email", count, "first", "last");

        return userMailStatistics;
    }
}
