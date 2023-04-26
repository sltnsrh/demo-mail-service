package com.salatin.demomailservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserCreateRequestDto {
    @NotBlank(message = "Username can't be blank")
    private String username;
    @NotBlank(message = "Email can't be blank")
    private String email;
}
