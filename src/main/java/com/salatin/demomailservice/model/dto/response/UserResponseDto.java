package com.salatin.demomailservice.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDto {
    private int id;
    private String username;
    private String email;
    private String createdOn;
}
