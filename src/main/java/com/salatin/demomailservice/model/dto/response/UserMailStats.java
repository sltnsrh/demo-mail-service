package com.salatin.demomailservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMailStats {
    private String username;
    private String email;
    private Count count;
    private String first;
    private String last;

    public record Count(Integer rest, Integer cron) {}
}
