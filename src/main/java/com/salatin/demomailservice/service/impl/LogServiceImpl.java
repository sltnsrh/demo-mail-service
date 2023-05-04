package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.model.Log;
import com.salatin.demomailservice.repository.LogRepository;
import com.salatin.demomailservice.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }
}
