package com.ylq.library.common;

import android.support.annotation.IntRange;

import java.util.Calendar;

/**
 * Created by apple on 16/7/11.
 */
public class DateUtils {
    /**
     * 获取今天是星期几
     *
     * @return
     */
    public static int getWeekday() {
        Calendar calendar = Calendar.getInstance();
        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        return day;
    }

    public static int[] MONTH_DAY = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * 获取这一天的前几天是几月
     *
     * @param year
     * @param month
     * @param day
     * @param offset
     * @return
     */
    public static
    @IntRange(from = 1, to = 12)
    int getBeforeDateMonth(int year, @IntRange(from = 1, to = 12) int month, @IntRange(from = 1, to = 31) int day, @IntRange(from = 0, to = 7) int offset) {
        fixFebMonth(year);
        if (offset == 0)
            return month;
        if (offset < day)
            return month;
        if (month == 1)
            return 12;
        return month - 1;
    }

    /**
     * 获取这一天的后几天是几月
     *
     * @param year
     * @param month
     * @param day
     * @param offset
     * @return
     */
    public static
    @IntRange(from = 1, to = 12)
    int getAfterDateMonth(int year, @IntRange(from = 1, to = 12) int month, @IntRange(from = 1, to = 31) int day, @IntRange(from = 0, to = 7) int offset) {
        fixFebMonth(year);
        if (offset == 0)
            return month;
        if ((offset + day) <= MONTH_DAY[month - 1])
            return month;
        if (month == 12)
            return 1;
        return month + 1;
    }

    /**
     * 获取这一天的前几天是几号
     *
     * @param year
     * @param month
     * @param day
     * @param offset
     * @return
     */
    public static int getBeforeDateDay(int year, @IntRange(from = 1, to = 12) int month, @IntRange(from = 1, to = 31) int day, @IntRange(from = 0, to = 7) int offset) {
        fixFebMonth(year);
        if(offset==0)
            return day;
        if(offset<day)
            return day-offset;
        int beforeMonth = getBeforeDateMonth(year,month,day,offset);
        return MONTH_DAY[beforeMonth-1]-(offset-day);
    }


    /**
     * 获取这一天的后几天是几号
     *
     * @param year
     * @param month
     * @param day
     * @param offset
     * @return
     */
    public static int getAfterDateDay(int year, @IntRange(from = 1, to = 12) int month, @IntRange(from = 1, to = 31) int day, @IntRange(from = 0, to = 7) int offset) {
        fixFebMonth(year);
        if(offset==0)
            return day;
        if ((offset + day) <= MONTH_DAY[month - 1])
            return day+offset;
        return day+offset-MONTH_DAY[month-1];
    }

    public static boolean isRain(int year) {
        if (year % 400 == 0)
            return true;
        if (year % 100 == 0)
            return false;
        if (year % 4 == 0)
            return true;
        return false;
    }

    private static void fixFebMonth(int year) {
        if (isRain(year))
            MONTH_DAY[1] = 29;
        else
            MONTH_DAY[1] = 28;

    }

}
