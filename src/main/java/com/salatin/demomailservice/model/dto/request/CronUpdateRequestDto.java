package com.salatin.demomailservice.model.dto.request;

import com.salatin.demomailservice.model.dto.validation.ValidCron;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CronUpdateRequestDto {
    private Integer id;
    @ValidCron
    private String expression;
}
