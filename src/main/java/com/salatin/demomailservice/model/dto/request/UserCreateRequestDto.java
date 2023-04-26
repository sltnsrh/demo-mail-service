package com.salatin.demomailservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserCreateRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
}
