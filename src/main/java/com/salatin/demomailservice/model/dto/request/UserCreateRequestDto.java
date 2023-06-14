package com.salatin.demomailservice.model.dto.request;

import com.salatin.demomailservice.model.dto.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDto {
    @NotBlank(message = "Username can't be blank")
    private String username;
    @ValidEmail
    private String email;
}
