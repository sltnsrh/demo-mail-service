package com.salatin.demomailservice.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CronResponseDto {
    private Integer id;
    private String expression;
    private String createdOn;
}
