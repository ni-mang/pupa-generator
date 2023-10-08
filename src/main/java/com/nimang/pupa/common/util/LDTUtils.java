package com.nimang.pupa.common.util;

import cn.hutool.core.date.DatePattern;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * LocalDateTime 处理工具类
 * @ClassName LDTUtils
 * @Author linlc
 * @Date 2021/5/25 14:13
 */
public class LDTUtils {

    //比较日期先后
    //LocalDateTime.now().isBefore(),
    //LocalDateTime.now().isAfter(),

    //Date转换为LocalDateTime
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    //LocalDateTime转换为Date
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    //获取指定日期的毫秒
    public static Long getMilliByTime(LocalDate day) {
        return getDayStart(day).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    //获取指定时间的毫秒
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    //获取指定时间的秒
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    //获取指定时间的指定格式
    public static String formatTime(LocalDateTime time,String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    //获取当前时间的指定格式
    public static String formatNow(String pattern) {
        return  formatTime(LocalDateTime.now(), pattern);
    }

    //日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    //日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field){
        return time.minus(number,field);
    }

    /**
     * 获取两个时间的差  field参数为ChronoUnit.*
     * @param startTime
     * @param endTime
     * @param field  单位(年月日时分秒)
     * @return
     */
    public static long timeDifference(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) return period.getYears();
        if (field == ChronoUnit.MONTHS) return period.getYears() * 12L + period.getMonths();
        return field.between(startTime, endTime);
    }

    public static long timeDifference(LocalDate startDay, LocalDate endDay, ChronoUnit field) {
        Period period = Period.between(startDay, endDay);
        if (field == ChronoUnit.YEARS) return period.getYears();
        if (field == ChronoUnit.MONTHS) return period.getYears() * 12L + period.getMonths();
        return field.between(startDay, endDay);
    }


    //获取一天的开始时间，2017,7,22 00:00
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.with(LocalTime.MIN);
    }

    public static LocalDateTime getDayStart(LocalDate day) {
        return day.atStartOfDay();
    }

    //获取一天的结束时间，2017,7,22 23:59:59.999999999
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.with(LocalTime.MAX);
    }

    public static LocalDateTime getDayEnd(LocalDate day) {
        return day.atStartOfDay().with(LocalTime.MAX);
    }

    /**
     * 获取本周开始时间
     * @param time
     * @return
     */
    public static LocalDateTime getWeekStart(LocalDateTime time) {
        int dayOfWeek = time.getDayOfWeek().getValue();
        return time.minusDays(dayOfWeek - 1).with(LocalTime.MIN);
    }
    /**
     * 获取本周结束时间
     * @param time
     * @return
     */
    public static LocalDateTime getWeekEnd(LocalDateTime time) {
        int dayOfWeek = time.getDayOfWeek().getValue();
        return time.plusDays(7 - dayOfWeek).with(LocalTime.MAX);
    }

    /**
     * 获取月份开始时间
     * @param time
     * @return
     */
    public static LocalDateTime getMonthStart(LocalDateTime time) {
        return time.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * 获取月份结束时间
     * @param time
     * @return
     */
    public static LocalDateTime getMonthEnd(LocalDateTime time) {
        return time.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    public static LocalDateTime getYearStart(LocalDateTime time) {
        return time.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
    }

    public static LocalDateTime getYearEnd(LocalDateTime time) {
        return time.with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
    }


    /**
     * 判断两段时间是否有交集
     * @param startDateOne
     * @param endDateOne
     * @param startDateTwo
     * @param endDateTwo
     * @return
     */
    public static Boolean isInterSection(LocalDate startDateOne,LocalDate endDateOne,LocalDate startDateTwo,LocalDate endDateTwo) {
        return isInterSection(
                getDayStart(startDateOne.atStartOfDay()),
                getDayEnd(endDateOne.atStartOfDay()),
                getDayStart(startDateTwo.atStartOfDay()),
                getDayEnd(endDateTwo.atStartOfDay()));
    }

    /**
     * 判断两段时间是否有交集
     * @param startDateOne
     * @param endDateOne
     * @param startDateTwo
     * @param endDateTwo
     * @return
     */
    public static Boolean isInterSection(LocalDateTime startDateOne,LocalDateTime endDateOne,LocalDateTime startDateTwo,LocalDateTime endDateTwo) {
        LocalDateTime maxStartDate = startDateOne;
        if(maxStartDate.isBefore(startDateTwo))
        {
            maxStartDate = startDateTwo;
        }

        LocalDateTime minEndDate = endDateOne;
        if(endDateTwo.isBefore(minEndDate))
        {
            minEndDate = endDateTwo;
        }
        if(maxStartDate.isBefore(minEndDate) || (maxStartDate.equals(minEndDate)))
        {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 字符串转LocalDateTime
     * @param timeStr
     * @return LocalDateTime
     * @date 2022/8/11 19:21
     */
    public static LocalDateTime parseLocalDateTime(String timeStr){
        return LocalDateTime.parse(timeStr,DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
    }

    /**
     * 字符串转LocalDate
     * @param timeStr
     * @return LocalDate
     * @date 2022/8/11 19:23
     */
    public static LocalDate parseLocalDate(String timeStr){
        return LocalDate.parse(timeStr,DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
    }


    public static void main(String[] args) {
        String timeStr1 = "2022-08-17 10:23:32";
        String timeStr2 = "2022-09-10 10:23:32";

        LocalDateTime ldt1 = parseLocalDateTime(timeStr1);
        LocalDateTime ldt2 = parseLocalDateTime(timeStr2);

        LocalDateTime ws1 = getWeekStart(ldt1);
        LocalDateTime we1 = getWeekEnd(ldt1);
        LocalDateTime ws2 = getWeekStart(ldt2);
        LocalDateTime we2 = getWeekEnd(ldt2);

        LocalDateTime ms = getMonthStart(ldt1);
        LocalDateTime me = getMonthEnd(ldt1);

        LocalDateTime ys = getYearStart(ldt1);
        LocalDateTime ye = getYearEnd(ldt1);

        System.out.println(we1);
    }

}
