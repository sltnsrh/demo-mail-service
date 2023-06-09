package com.salatin.demomailservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.salatin.demomailservice.model.Log;
import com.salatin.demomailservice.repository.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(value = MockitoExtension.class)
class LogServiceImplTest {
    @InjectMocks
    private LogServiceImpl logService;
    @Mock
    private LogRepository logRepository;

    @Test
    void saveWhenValidDataThenReturnSavedLog() {
        var log = new Log();

        when(logRepository.save(log)).thenReturn(log);
        assertNotNull(logService.save(log));
        verify(logRepository).save(log);
    }
}
