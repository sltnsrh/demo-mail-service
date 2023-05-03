package com.salatin.demomailservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailSendRequestDto {
    private String subject;
    @NotBlank(message = "Email receiver can't be empty")
    private String to;
    @NotBlank(message = "An email body is required")
    private String body;
}
