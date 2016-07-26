package com.ylq.library.classtable.model;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by apple on 16/7/13.
 */
public class Common {


    public enum SEASON {
        SUMMER, WINTER, UNKNOW
    }

    private static SEASON mCurrentSeason = SEASON.SUMMER;
    private static HashMap<String, Integer> classColorIndex = new HashMap<>();
    private static int colorIndex = 0;
    private static int COLOR_INDEX_MAX = 10;

    public static int getColorIndex(@NonNull String className) {
        if (classColorIndex.containsKey(className))
            return classColorIndex.get(className);
        else {
            classColorIndex.put(className, colorIndex % 10);
            colorIndex += 1;
            return colorIndex - 1;
        }
    }


    private static String[] SUMMER_START_TIME = {
            "08:00", "08:55", "10:10", "11:05", "14:30", "15:20", "16:25", "17:15", "19:00", "19:50", "20:45", "21:35"
    };

    private static String[] WINTER_START_TIME = {
            "08:00", "08:55", "10:10", "11:05", "14:00", "14:50", "15:55", "16:45", "18:30", "19:20", "20:15", "21:05"
    };

    private static String[] SUMMER_END_TIME = {
            "08:45", "09:40", "10:55", "11:50", "15:15", "16:05", "17:10", "18:00", "19:45", "20:35", "21:30", "22:20"
    };

    private static String[] WINTER_END_TIME = {
            "08:45", "09:40", "10:55", "11:50", "14:45", "15:35", "16:40", "17:30", "19:15", "20:05", "21:00", "21:50"
    };

    public static int getFrom(@NonNull String hh_mm) {
        for (int i = 0; i < SUMMER_START_TIME.length; i++)
            if (SUMMER_START_TIME[i].equals(hh_mm)) {
                if (i > 3)
                    setSeason(SEASON.SUMMER);
                return i + 1;
            }
        for (int i = 0; i < WINTER_START_TIME.length; i++)
            if (WINTER_START_TIME[i].equals(hh_mm)) {
                if (i > 3)
                    setSeason(SEASON.WINTER);
                return i + 1;
            }
        throw new IllegalArgumentException(hh_mm + " can not be known");
    }

    private static int getEnd(@NonNull String hh_mm) {
        for (int i = 0; i < SUMMER_START_TIME.length; i++)
            if (SUMMER_END_TIME[i].equals(hh_mm)) {
                if (i > 3)
                    setSeason(SEASON.SUMMER);
                return i + 1;
            }
        for (int i = 0; i < WINTER_START_TIME.length; i++)
            if (WINTER_END_TIME[i].equals(hh_mm)) {
                if (i > 3)
                    setSeason(SEASON.WINTER);
                return i + 1;
            }
        throw new IllegalArgumentException(hh_mm + " can not be known");
    }


    /**
     * 获取当前时间是星期几
     *
     * @param yyyy_mm_dd_hh_mm
     * @return
     * @throws ParseException
     */
    public static int getWeek(String yyyy_mm_dd_hh_mm) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.set(parserYear(yyyy_mm_dd_hh_mm), parserMonth(yyyy_mm_dd_hh_mm), parserDay(yyyy_mm_dd_hh_mm));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    public static int getYear(String yyyy_mm_dd_hh_mm) {
        Calendar c = Calendar.getInstance();
        c.set(parserYear(yyyy_mm_dd_hh_mm), parserMonth(yyyy_mm_dd_hh_mm), parserDay(yyyy_mm_dd_hh_mm));
        return c.get(Calendar.YEAR);
    }


    public static int getMonth(String yyyy_mm_dd_hh_mm) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.set(parserYear(yyyy_mm_dd_hh_mm), parserMonth(yyyy_mm_dd_hh_mm), parserDay(yyyy_mm_dd_hh_mm));
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDay(String yyyy_mm_dd_hh_mm) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.set(parserYear(yyyy_mm_dd_hh_mm), parserMonth(yyyy_mm_dd_hh_mm), parserDay(yyyy_mm_dd_hh_mm));
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getTimes(@NonNull String hh_mm_start, @NonNull String hh_mm_end) {
        int start = getFrom(hh_mm_start);
        int end = getEnd(hh_mm_end);
        return end - start + 1;
    }

    private static void setSeason(SEASON season) {
        mCurrentSeason = season;
    }

    public static boolean isSummer() {
        return mCurrentSeason == SEASON.SUMMER;
    }

    private static int parserYear(String yyyy_mm_dd_hh_mm) {
        return Integer.parseInt(yyyy_mm_dd_hh_mm.substring(0, 4));
    }

    private static int parserMonth(String yyyy_mm_dd_hh_mm) {
        return Integer.parseInt(yyyy_mm_dd_hh_mm.substring(5, 7)) - 1;
    }

    private static int parserDay(String yyyy_mm_dd_hh_mm) {
        return Integer.parseInt(yyyy_mm_dd_hh_mm.substring(8, 10));
    }

}
