package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.model.Cron;
import com.salatin.demomailservice.repository.CronRepository;
import com.salatin.demomailservice.service.CronService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CronServiceImpl implements CronService {
    private final CronRepository cronRepository;

    @Override
    public Cron create(Cron cron) {
        Cron savedCron;

        try {
            savedCron = cronRepository.save(cron);
        } catch (DataIntegrityViolationException e) {
            throw new EntityExistsException(
                String.format("Cron with the same expression: %s already exists",
                    cron.getExpression()));
        }

        log.info("Cron was successfully created: {}", cron);

        return savedCron;
    }

    @Override
    public Cron findById(Integer id) {

        return cronRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Can't find cron expression with id: " + id));
    }

    @Override
    public Page<Cron> findAll(PageRequest pageRequest) {

        return cronRepository.findAll(pageRequest);
    }

    @Override
    public Cron update(Cron cron) {
        var cronToUpdate = this.findById(cron.getId());

        cronToUpdate.setExpression(cron.getExpression());

        return cronRepository.save(cronToUpdate);
    }

    @Override
    public void delete(Integer id) {
        var cron = this.findById(id);

        cronRepository.delete(cron);
    }
}
