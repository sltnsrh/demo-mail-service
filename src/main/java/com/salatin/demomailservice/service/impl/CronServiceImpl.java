package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.model.Cron;
import com.salatin.demomailservice.repository.CronRepository;
import com.salatin.demomailservice.service.CronService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CronServiceImpl implements CronService {
    private final CronRepository cronRepository;

    @Override
    public Cron create(Cron cron) {

        return cronRepository.save(cron);
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
        var cronToUpdate = findById(cron.getId());

        cronToUpdate.setExpression(cron.getExpression());

        return cronRepository.save(cronToUpdate);
    }

    @Override
    public void delete(Integer id) {
        var cron = findById(id);

        cronRepository.delete(cron);
    }
}
