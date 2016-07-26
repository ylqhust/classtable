package com.ylq.library.classtable.query;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by apple on 16/7/13.
 */
public class Config {
    public static final int QUERY_START_YEAR = 2016;
    public static final int QUERY_END_YEAR = 2017;
    public static final int QUERY_START_MONTH = Calendar.AUGUST;
    public static final int QUERY_END_MONTH = Calendar.JANUARY;
    public static final int QUERY_START_DAY = 29;
    public static final int QUERY_END_DAY = 31;
    public static final int NOTIFICATION_HOUR = 21;
    public static final int NOTIFICATION_MINUTE = 30;

    public static long getStartTimeInMillis(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(QUERY_START_YEAR,QUERY_START_MONTH,QUERY_START_DAY);
        return calendar.getTimeInMillis();
    }

    public static long getEndTimeInMillis(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(QUERY_END_YEAR,QUERY_END_MONTH,QUERY_END_DAY);
        return calendar.getTimeInMillis();
    }
}
