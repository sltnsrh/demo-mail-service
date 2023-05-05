package com.salatin.demomailservice.service.mapper;

import com.salatin.demomailservice.model.Cron;
import com.salatin.demomailservice.model.dto.request.CronCreateRequestDto;
import com.salatin.demomailservice.model.dto.response.CronResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CronMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", expression = "java(LocalDateTime.now())")
    Cron toModel(CronCreateRequestDto dto);

    CronResponseDto toDto(Cron cron);
}
