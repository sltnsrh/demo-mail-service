package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.model.dto.request.CronCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.CronUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.CronResponseDto;
import com.salatin.demomailservice.service.CronService;
import com.salatin.demomailservice.service.mapper.CronMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crons")
@RequiredArgsConstructor
@Validated
public class CronController {
    private final CronService cronService;
    private final CronMapper cronMapper;

    @PostMapping
    public ResponseEntity<CronResponseDto> create(
        @RequestBody @Valid CronCreateRequestDto requestDto
    ) {
        var cron = cronMapper.toModel(requestDto);

        return new ResponseEntity<>(
            cronMapper.toDto(cronService.create(cron)),
            HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<CronResponseDto> update(
        @RequestBody @Valid CronUpdateRequestDto requestDto
    ) {
        var cron = cronMapper.toModel(requestDto);

        return ResponseEntity.ok(
            cronMapper.toDto(cronService.update(cron))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Integer id) {
        cronService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CronResponseDto>> findAll(
        @RequestParam(defaultValue = "0") @PositiveOrZero int page,
        @RequestParam(defaultValue = "10") @PositiveOrZero int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(
            cronMapper.toPageDto(cronService.findAll(pageRequest))
        );
    }
}
