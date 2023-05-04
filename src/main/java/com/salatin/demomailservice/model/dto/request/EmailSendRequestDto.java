package com.salatin.demomailservice.model.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class EmailSendRequestDto {
    @Positive
    private Integer userId;
    private String subject;
    private String body;
}
