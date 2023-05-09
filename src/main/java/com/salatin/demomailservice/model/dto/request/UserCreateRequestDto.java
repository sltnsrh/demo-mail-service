package com.salatin.demomailservice.model.dto.request;

import com.salatin.demomailservice.model.dto.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserCreateRequestDto {
    @NotBlank(message = "Username can't be blank")
    private String username;
    @ValidEmail
    private String email;
}
