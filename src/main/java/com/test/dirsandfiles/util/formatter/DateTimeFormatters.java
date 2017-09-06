package com.test.dirsandfiles.util.formatter;

import com.test.dirsandfiles.util.DateTimeUtil;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.test.dirsandfiles.util.DateTimeUtil.parseLocalDate;
import static com.test.dirsandfiles.util.DateTimeUtil.parseLocalDateTime;
import static com.test.dirsandfiles.util.DateTimeUtil.parseLocalTime;

public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate> {
        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return parseLocalDate(text);
        }

        @Override
        public String print(LocalDate lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {
        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
            return parseLocalTime(text);
        }

        @Override
        public String print(LocalTime lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }

    public static class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
        @Override
        public LocalDateTime parse(String text, Locale locale) throws ParseException {
            return parseLocalDateTime(text);
        }

        @Override
        public String print(LocalDateTime lt, Locale locale) {
            return lt.format(DateTimeUtil.DATE_TIME_FORMATTER);
        }
    }
}
