package com.salatin.demomailservice.model.dto.request;

import lombok.Getter;

@Getter
public class CronUpdateRequestDto {
    private Integer id;
    private String expression;
}
