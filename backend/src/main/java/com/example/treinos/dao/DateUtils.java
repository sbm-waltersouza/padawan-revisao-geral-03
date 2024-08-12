package com.example.treinos.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");

    public static Timestamp convertToUtc(String localDateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeString, formatter);
        ZonedDateTime utcZonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(UTC_ZONE_ID);
        return Timestamp.from(utcZonedDateTime.toInstant());
    }
}
