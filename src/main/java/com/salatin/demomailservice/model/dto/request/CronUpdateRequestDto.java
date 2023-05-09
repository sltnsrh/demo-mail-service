package com.salatin.demomailservice.model.dto.request;

import com.salatin.demomailservice.model.dto.validation.ValidCron;
import lombok.Getter;

@Getter
public class CronUpdateRequestDto {
    private Integer id;
    @ValidCron
    private String expression;
}
