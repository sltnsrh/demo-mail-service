package com.salatin.demomailservice.model.dto.request;

import lombok.Getter;

@Getter
public class CreateUserRequestDto {
    private String username;
    private String email;
}
