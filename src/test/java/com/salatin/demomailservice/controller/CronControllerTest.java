package com.salatin.demomailservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.salatin.demomailservice.model.Cron;
import com.salatin.demomailservice.model.dto.request.CronCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.CronUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.CronResponseDto;
import com.salatin.demomailservice.service.CronService;
import com.salatin.demomailservice.service.mapper.CronMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

@ExtendWith(value = MockitoExtension.class)
class CronControllerTest {
    @InjectMocks
    private CronController cronController;
    @Mock
    private CronService cronService;
    @Mock
    private CronMapper cronMapper;

    @Test
    void create_WhenValidRequest_ThenReturnsCreatedCron() {
        var requestDto = new CronCreateRequestDto();
        var cron = new Cron();
        var expectedResponseDto = new CronResponseDto();
        when(cronMapper.toModel(requestDto)).thenReturn(cron);
        when(cronMapper.toDto(cron)).thenReturn(expectedResponseDto);
        when(cronService.save(cron)).thenReturn(cron);

        var response = cronController.create(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponseDto, response.getBody());

        verify(cronMapper).toModel(requestDto);
        verify(cronMapper).toDto(cron);
        verify(cronService).save(cron);
    }

    @Test
    void update_WhenValidRequest_ThenReturnsUpdatedCron() {
        var cronUpdateRequestDto = new CronUpdateRequestDto();
        var cron = new Cron();
        var expectedResponseDto = new CronResponseDto();
        when(cronMapper.toModel(cronUpdateRequestDto)).thenReturn(cron);
        when(cronMapper.toDto(cron)).thenReturn(expectedResponseDto);
        when(cronService.update(cron)).thenReturn(cron);

        var response = cronController.update(cronUpdateRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponseDto, response.getBody());

        verify(cronMapper).toModel(cronUpdateRequestDto);
        verify(cronMapper).toDto(cron);
        verify(cronService).update(cron);
    }

    @Test
    void delete_WhenValidId_ThenReturnsNoContent() {
        var cronId = 1;
        var response = cronController.delete(cronId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(cronService).delete(cronId);
    }

    @Test
    void findAll_WhenValidRequest_ThenReturnsEmptyPage() {
        Page<Cron> emptyCronPage = Page.empty();
        Page<CronResponseDto> expectedResponsePage = Page.empty();
        when(cronService.findAll(any(PageRequest.class))).thenReturn(emptyCronPage);
        when(cronMapper.toPageDto(emptyCronPage)).thenReturn(expectedResponsePage);

        var response = cronController.findAll(0, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponsePage, response.getBody());

        verify(cronService).findAll(any());
        verify(cronMapper).toPageDto(emptyCronPage);
    }
}
