package com.salatin.demomailservice.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    @Min(value = 1, message = "The id value should be minimum 1")
    @Max(value = Integer.MAX_VALUE, message = "The id value is too big")
    private int id;
    @NotBlank(message = "Username can't be blank")
    private String username;
    @NotBlank(message = "Email can't be blank")
    private String email;
}
