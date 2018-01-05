package com.dongfg.project.api.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

/**
 * @author dongfg
 * @date 17-10-18
 */
@Slf4j
public class DateTimeConverter {

    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final ZoneOffset UTC_8 = ZoneOffset.of("+8");
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * localDateTime to date
     *
     * @param localDateTime java8 date
     * @return date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(UTC_8);
        return Date.from(instant);
    }

    /**
     * date to LocalDateTime
     *
     * @param date date
     * @return localDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, UTC_8);
    }

    /**
     * 日期格式化，使用默认格式
     *
     * @param date 日期
     * @return 格式化日期
     * @see DateTimeConverter#STANDARD_FORMAT
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat(STANDARD_FORMAT).format(date);
    }

    /**
     * 日期格式化，传入格式
     *
     * @param date   日期
     * @param format 日期格式
     * @return 格式化日期
     * @see DateTimeConverter#STANDARD_FORMAT
     */
    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 日期解析，使用默认格式
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, STANDARD_FORMAT);
    }

    /**
     * 日期格式化
     *
     * @param dateStr 日期字符串
     * @param format  日期格式
     * @return 日期
     */
    public static Date parseDate(String dateStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.error("unable to parse date '{}' by format '{}'", dateStr, format);
            return null;
        }
    }

    /**
     * 判断日期是否在本周
     *
     * @param dateTime 日期
     * @return 本周true
     */
    public static boolean currentWeek(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.toLocalDate();
        LocalDate now = LocalDate.now();
        LocalDate monday = now.with(DayOfWeek.MONDAY);

        return localDate.compareTo(monday) >= 0;
    }
}
