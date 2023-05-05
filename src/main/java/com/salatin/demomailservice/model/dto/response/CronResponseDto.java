package com.salatin.demomailservice.model.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CronResponseDto {
    private Integer id;
    private String expression;
    private LocalDateTime createdOn;
}
