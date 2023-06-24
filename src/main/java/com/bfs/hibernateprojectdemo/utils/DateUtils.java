package com.bfs.hibernateprojectdemo.utils;

import java.time.LocalDateTime;

public class DateUtils {
    public static LocalDateTime roundToMicroseconds(LocalDateTime dateTime) {
        // Round the nanosecond value to microseconds
        int microsecond = (dateTime.getNano() + 500) / 1000 * 1000;
        LocalDateTime roundedTime = dateTime.withNano(microsecond);

        return roundedTime;
    }
}
