package com.salatin.demomailservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CronCreateRequestDto {
    @NotBlank(message = "Cron expression can't be empty")
    private String expression;
}
