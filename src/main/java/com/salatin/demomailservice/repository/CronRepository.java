package com.salatin.demomailservice.repository;

import com.salatin.demomailservice.model.Cron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronRepository extends JpaRepository<Cron, Integer> {
}
