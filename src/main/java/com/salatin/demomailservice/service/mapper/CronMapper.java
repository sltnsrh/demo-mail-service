package com.salatin.demomailservice.service.mapper;

import com.salatin.demomailservice.model.Cron;
import com.salatin.demomailservice.model.dto.request.CronCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.CronUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.CronResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(componentModel = "spring")
public interface CronMapper extends DateTimeProvider {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", expression = "java(getCurrentDateTime())")
    Cron toModel(CronCreateRequestDto dto);

    @Mapping(target = "createdOn", ignore = true)
    Cron toModel(CronUpdateRequestDto dto);

    CronResponseDto toDto(Cron cron);

    default Page<CronResponseDto> toPageDto(Page<Cron> cronPage) {
        List<CronResponseDto> cronDtoList = cronPage.getContent().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
        return new PageImpl<>(cronDtoList, cronPage.getPageable(), cronPage.getTotalElements());
    }
}
