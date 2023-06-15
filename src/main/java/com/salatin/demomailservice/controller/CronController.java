package com.salatin.demomailservice.controller;

import com.salatin.demomailservice.model.dto.request.CronCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.CronUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.CronResponseDto;
import com.salatin.demomailservice.service.CronService;
import com.salatin.demomailservice.service.mapper.CronMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Cron", description = "CRUD operations with Cron expressions")
public class CronController {
    private final CronService cronService;
    private final CronMapper cronMapper;

    @Operation(
            summary = "Create cron expression",
            description = "Creates a new cron expression in DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new cron"),
            @ApiResponse(responseCode = "409", description = "Cron expression already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid cron expression format")
    })
    @PostMapping
    public ResponseEntity<CronResponseDto> create(
        @RequestBody @Valid CronCreateRequestDto requestDto
    ) {
        var cron = cronMapper.toModel(requestDto);

        return new ResponseEntity<>(
            cronMapper.toDto(cronService.save(cron)),
            HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Update cron expression",
            description = "Updates an existing cron expression")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the cron expression"),
            @ApiResponse(responseCode = "404", description = "Cron expression not found"),
            @ApiResponse(responseCode = "409", description = "Cron expression already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid cron expression format")
    })
    @PutMapping
    public ResponseEntity<CronResponseDto> update(
        @RequestBody @Valid CronUpdateRequestDto requestDto
    ) {
        var cron = cronMapper.toModel(requestDto);

        return ResponseEntity.ok(
            cronMapper.toDto(cronService.update(cron))
        );
    }

    @Operation(
            summary = "Delete cron expression",
            description = "Deletes a cron expression by its ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successfully deleted the cron expression"
            ),
            @ApiResponse(responseCode = "404", description = "Cron expression not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable @Positive Integer id
    ) {
        cronService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get all cron expressions",
            description = "Retrieves all cron expressions with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all cron expressions")
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
