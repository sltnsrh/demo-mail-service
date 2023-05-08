package com.salatin.demomailservice.model.dto.response;

import com.salatin.demomailservice.model.dto.Count;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMailStatistics {
    private String username;
    private String email;
    private Count count;
    private String first;
    private String last;
}
