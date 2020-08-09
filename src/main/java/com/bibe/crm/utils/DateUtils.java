package com.bibe.crm.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 获取前一天日期
     *
     * @return
     */
    public static Date getBeforeDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    /**
     * 获取指定日期前一天
     *
     * @return
     */
    public static Date getAssignBeforeDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }


    /**
     * 获取指定日期前一天 字符串
     *
     * @param date
     * @return
     */
    public static String getAssignBeforeDayString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        return format.format(c.getTime());
    }

    /**
     * 获取日期一号数据
     *
     * @param date
     * @return
     */
    public static Date getMonthOne(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        return c.getTime();
    }


    /**
     * 获取前一月日期
     *
     * @return
     */
    public static Date getBeforeMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }

    /**
     * 获取指定日期前一个月
     *
     * @param date
     * @return
     */
    public static Date getAssignBeforeMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }


    /**
     * 获取前一月日期 字符串
     *
     * @return
     */
    public static String getAssignBeforeMonthString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        return format.format(c.getTime());
    }

    /**
     * 获取后一个月日期 字符串
     *
     * @param date
     * @return
     */
    public static String getAssignBehindMonthString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        return format.format(c.getTime());
    }

    /**
     * 获取当前时间年月日字符串
     *
     * @return
     */
    public static String getNowDayDateString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date().getTime());
    }

    /**
     * 获取当前时间年月字符串
     *
     * @return
     */
    public static String getNowMonthDateString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(new Date().getTime());
    }

    /**
     * 字符串日期转时间日期
     * @param date
     * @return
     */
    public static Date getDateStringToDate(String date) throws Exception {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = s.parse(date);
        return parse;
    }



    public static void zz(String[] args) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, 1);
        Date end = c.getTime();
        String dqrq = format.format(end);//当前日期

        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        c.setTime(start);
        String qyt = format.format(c.getTime());//前一天

        c.add(Calendar.MONTH, -1);
        Date start1 = c.getTime();
        String startDay = format.format(start1);//前一月

        c.add(Calendar.YEAR, -1);
        Date start2 = c.getTime();
        String startDaya1 = format.format(start2);//前一年

        String d = format.format(new Date());
        System.out.println(d.equals(dqrq));
        System.out.println(dqrq);
        System.out.println(qyt);
        System.out.println(startDay);
        System.out.println(startDaya1);
        System.out.println("---------------------");
        System.out.println(start);
        System.out.println(start1);
        System.out.println(start2);
    }
}
