package com.salatin.demomailservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.salatin.demomailservice.model.Cron;
import com.salatin.demomailservice.repository.CronRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(value = MockitoExtension.class)
class CronServiceImplTest {
    private final static Integer CRON_ID = 1;

    @Spy
    @InjectMocks
    private CronServiceImpl cronService;
    @Mock
    private CronRepository cronRepository;

    private Cron cron;

    @BeforeEach
    void init() {
        cron = new Cron();
    }

    @Test
    void save_WhenValidData_ThenReturnsSavedCron() {
        when(cronRepository.save(cron)).thenReturn(cron);

        assertNotNull(cronService.save(cron));
        verify(cronRepository).save(cron);
    }

    @Test
    void save_WhenInvalidData_ThenThrowsEntityExistsException() {
        when(cronRepository.save(cron)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(EntityExistsException.class, () -> cronService.save(cron));
        verify(cronRepository).save(cron);
    }

    @Test
    void findById_WhenValidId_ThenReturnsCron() {
        when(cronRepository.findById(CRON_ID)).thenReturn(Optional.of(cron));

        assertNotNull(cronService.findById(CRON_ID));
        verify(cronRepository).findById(CRON_ID);
    }

    @Test
    void findById_WhenInvalidId_ThenThrowsEntityNotFoundException() {
        when(cronRepository.findById(CRON_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cronService.findById(CRON_ID));
        verify(cronRepository).findById(CRON_ID);
    }

    @Test
    void findAll_WhenValidRequest_ThenReturnsPage() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(cronRepository.findAll(pageRequest)).thenReturn(Page.empty());

        assertNotNull(cronService.findAll(pageRequest));
        verify(cronRepository).findAll(pageRequest);
    }

    @Test
    void update_WhenValidRequest_ThenReturnsCron() {
        cron.setExpression("*****");
        cron.setId(CRON_ID);
        doReturn(cron).when(cronService).findById(CRON_ID);
        doReturn(cron).when(cronService).save(cron);

        assertNotNull(cronService.update(cron));
        verify(cronService).findById(CRON_ID);
        verify(cronService).save(cron);
    }

    @Test
    void delete_WhenValidId_ThenOk() {
        doReturn(cron).when(cronService).findById(CRON_ID);

        cronService.delete(CRON_ID);

        verify(cronRepository, times(1)).delete(cron);
    }
}
