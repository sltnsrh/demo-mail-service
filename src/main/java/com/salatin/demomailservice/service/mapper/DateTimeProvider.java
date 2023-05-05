package com.salatin.demomailservice.service.mapper;

import java.time.LocalDateTime;

public interface DateTimeProvider {

    default LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
