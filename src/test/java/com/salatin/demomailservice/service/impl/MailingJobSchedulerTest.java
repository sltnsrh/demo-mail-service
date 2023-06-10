package com.salatin.demomailservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.salatin.demomailservice.model.Cron;
import com.salatin.demomailservice.service.CronService;
import java.util.concurrent.ScheduledFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@ExtendWith(MockitoExtension.class)
class MailingJobSchedulerTest {
    private static final Integer CRON_ID = 1;

    @InjectMocks
    private MailingJobScheduler mailingJobScheduler;
    @Mock
    private CronService cronService;
    @Mock
    private TaskScheduler taskScheduler;
    @Mock
    private ScheduledFuture<?> scheduledTask;

    @BeforeEach
    void setUp() {
        mailingJobScheduler.setScheduledTask(scheduledTask);
    }

    @Test
    void schedule_WhenScheduledTaskIsNull_InvokesScheduleMethod() {
        mailingJobScheduler.setScheduledTask(null);
        Cron cron = createCronWithExpression();
        when(cronService.findById(CRON_ID)).thenReturn(cron);

        mailingJobScheduler.schedule(CRON_ID);

        verify(cronService).findById(CRON_ID);
        verify(taskScheduler).schedule(any(Runnable.class), any(CronTrigger.class));
    }

    @Test
    void schedule_WhenScheduledTaskIsNotCanceled_ThrowsSchedulingException() {
        when(scheduledTask.isCancelled()).thenReturn(false);

        assertThrows(SchedulingException.class, () -> mailingJobScheduler.schedule(CRON_ID));
    }

    @Test
    void schedule_WhenScheduledTaskIsCanceled_InvokesScheduleMethod() {
        Cron cron = createCronWithExpression();
        when(scheduledTask.isCancelled()).thenReturn(true);
        when(cronService.findById(CRON_ID)).thenReturn(cron);

        mailingJobScheduler.schedule(CRON_ID);

        verify(cronService).findById(CRON_ID);
        verify(taskScheduler).schedule(any(Runnable.class), any(CronTrigger.class));
    }

    @Test
    void stop_WhenScheduledTaskIsNull_ThrowsSchedulingException() {
        mailingJobScheduler.setScheduledTask(null);

        assertThrows(SchedulingException.class, mailingJobScheduler::stop);
    }

    @Test
    void stop_WhenScheduledTaskAlreadyCanceled_ThrowsSchedulingException() {
        when(scheduledTask.cancel(true)).thenReturn(false);

        assertThrows(SchedulingException.class, mailingJobScheduler::stop);
    }

    @Test
    void stop_WhenScheduledTaskNotCanceled_DoesNotThrow() {
        when(scheduledTask.cancel(true)).thenReturn(true);

        assertDoesNotThrow(mailingJobScheduler::stop);
    }

    Cron createCronWithExpression() {
        Cron cron = new Cron();
        cron.setExpression("* * * * * *");
        return cron;
    }
}
