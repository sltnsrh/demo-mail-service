package com.salatin.demomailservice.model.dto.response;

import java.time.LocalDateTime;
import lombok.Setter;

@Setter
public class UserResponseDto {
    private int id;
    private String username;
    private String email;
    private LocalDateTime createdOn;
}
