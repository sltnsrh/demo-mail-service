package com.salatin.demomailservice.service;

import com.salatin.demomailservice.model.Cron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CronService {

    Cron create(Cron cron);

    Cron findById(Integer id);

    Page<Cron> findAll(PageRequest pageRequest);

    Cron update(Cron cron);

    void delete(Integer id);
}
