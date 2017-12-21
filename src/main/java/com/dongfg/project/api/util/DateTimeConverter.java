package com.dongfg.project.api.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongfg
 * @date 17-10-18
 */
@Slf4j
public class DateTimeConverter {

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
        SimpleDateFormat dateFormat = new SimpleDateFormat(STANDARD_FORMAT);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.error("unable to parse date '{}' by format '{}'", dateStr, dateFormat);
            return null;
        }
    }
}
