package com.salatin.demomailservice.service.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Named;

public interface DateTimeProvider {
    String DATE_TIME_OUT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    default LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    @Named("formatDateTimeToString")
    default String formatDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DATE_TIME_OUT_PATTERN);
        LocalDateTime parsedDateTime =
            LocalDateTime.parse(localDateTime.toString(), inputFormatter);

        return outputFormatter.format(parsedDateTime);
    }
}
