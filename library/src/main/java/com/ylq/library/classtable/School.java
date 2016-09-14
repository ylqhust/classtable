package com.ylq.library.classtable;

import android.support.annotation.IntRange;

import com.ylq.library.classtable.model.Common;
import com.ylq.library.classtable.model.OneWeekClasses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * Created by ylq on 16/8/17.
 */
public class School {

    public static final String MIN = "2016-08-29";
    public static final String MAX = "2017-02-12";

    public static final String W1[] = {"2016-08-29", "2016-08-30", "2016-08-31", "2016-09-01", "2016-09-02", "2016-09-03", "2016-09-04"};//第一周的日期
    public static final String W2[] = {"2016-09-05", "2016-09-06", "2016-09-07", "2016-09-08", "2016-09-09", "2016-09-10", "2016-09-11"};
    public static final String W3[] = {"2016-09-12", "2016-09-13", "2016-09-14", "2016-09-15", "2016-09-16", "2016-09-17", "2016-09-18"};
    public static final String W4[] = {"2016-09-19", "2016-09-20", "2016-09-21", "2016-09-22", "2016-09-23", "2016-09-24", "2016-09-25"};
    public static final String W5[] = {"2016-09-26", "2016-09-27", "2016-09-28", "2016-09-29", "2016-09-30", "2016-10-01", "2016-10-02"};
    public static final String W6[] = {"2016-10-03", "2016-10-04", "2016-10-05", "2016-10-06", "2016-10-07", "2016-10-08", "2016-10-09"};
    public static final String W7[] = {"2016-10-10", "2016-10-11", "2016-10-12", "2016-10-13", "2016-10-14", "2016-10-15", "2016-10-16"};
    public static final String W8[] = {"2016-10-17", "2016-10-18", "2016-10-19", "2016-10-20", "2016-10-21", "2016-10-22", "2016-10-23"};
    public static final String W9[] = {"2016-10-24", "2016-10-25", "2016-10-26", "2016-10-27", "2016-10-28", "2016-10-29", "2016-10-30"};
    public static final String W10[] = {"2016-10-31", "2016-11-01", "2016-11-02", "2016-11-03", "2016-11-04", "2016-11-05", "2016-11-06"};
    public static final String W11[] = {"2016-11-07", "2016-11-08", "2016-11-09", "2016-11-10", "2016-11-11", "2016-11-12", "2016-11-13"};
    public static final String W12[] = {"2016-11-14", "2016-11-15", "2016-11-16", "2016-11-17", "2016-11-18", "2016-11-19", "2016-11-20"};
    public static final String W13[] = {"2016-11-21", "2016-11-22", "2016-11-23", "2016-11-24", "2016-11-25", "2016-11-26", "2016-11-27"};
    public static final String W14[] = {"2016-11-28", "2016-11-29", "2016-11-30", "2016-12-01", "2016-12-02", "2016-12-03", "2016-12-04"};
    public static final String W15[] = {"2016-12-05", "2016-12-06", "2016-12-07", "2016-12-08", "2016-12-09", "2016-12-10", "2016-12-11"};
    public static final String W16[] = {"2016-12-12", "2016-12-13", "2016-12-14", "2016-12-15", "2016-12-16", "2016-12-17", "2016-12-18"};
    public static final String W17[] = {"2016-12-19", "2016-12-20", "2016-12-21", "2016-12-22", "2016-12-23", "2016-12-24", "2016-12-25"};
    public static final String W18[] = {"2016-12-26", "2016-12-27", "2016-12-28", "2016-12-29", "2016-12-30", "2016-12-31", "2017-01-01"};
    public static final String W19[] = {"2017-01-02", "2017-01-03", "2017-01-04", "2017-01-05", "2017-01-06", "2017-01-07", "2017-01-08"};
    public static final String W20[] = {"2017-01-09", "2017-01-10", "2017-01-11", "2017-01-12", "2017-01-13", "2017-01-14", "2017-01-15"};
    public static final String W21[] = {"2017-01-16", "2017-01-17", "2017-01-18", "2017-01-19", "2017-01-20", "2017-01-21", "2017-01-22"};
    public static final String W22[] = {"2017-01-23", "2017-01-24", "2017-01-25", "2017-01-26", "2017-01-27", "2017-01-28", "2017-01-29"};
    public static final String W23[] = {"2017-01-30", "2017-01-31", "2017-02-01", "2017-02-02", "2017-02-03", "2017-02-04", "2017-02-05"};
    public static final String W24[] = {"2017-02-06", "2017-02-07", "2017-02-08", "2017-02-09", "2017-02-10", "2017-02-11", "2017-02-12"};
    public static final Common.SEASON[] SCHOOL_SEASON = {
            Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER,
            Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER,
            Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER,
            Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER,
            Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER
    };

    public static final String[][] ALLW = {W1, W2, W3, W4, W5, W6, W7, W8, W9, W10, W11, W12, W13, W14, W15, W16, W17, W18, W19, W20, W21, W22, W23, W24};

    /**
     * 获取今天是第几周
     *
     * @return 1<=return<=ALLW.length
     */
    public static int getCurrentWeekth() {
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = "" + (calendar.get(Calendar.MONTH) + 1);
        String day = calendar.get(Calendar.DATE) + "";
        if (month.length() == 1)
            month = "0" + month;
        if (day.length() == 1)
            day = "0" + day;
        String q = year + "-" + month + "-" + day;
        return queryWeekth(q);
    }

    private static boolean biggerThanMax(String y_m_d) {
        int[] i1 = split(y_m_d);
        int[] max = split(MAX);
        if (i1[0] > max[0])
            return true;
        if (i1[0] < max[0])
            return false;
        if (i1[1] > max[1])
            return true;
        if (i1[1] < max[1])
            return false;
        if (i1[2] > max[2])
            return true;
        if (i1[2] < max[2])
            return false;
        return true;
    }

    private static boolean smallerThanMin(String y_m_d) {
        int[] i1 = split(y_m_d);
        int[] min = split(MIN);
        if (i1[0] < min[0])
            return true;
        if (i1[0] > min[0])
            return false;
        if (i1[1] < min[1])
            return true;
        if (i1[1] > min[1])
            return false;
        if (i1[2] < min[2])
            return true;
        if (i1[2] > min[2])
            return false;
        return true;
    }

    public static int queryWeekth(String y_m_d) {
        if (smallerThanMin(y_m_d))
            return 1;
        if (biggerThanMax(y_m_d))
            return ALLW.length;
        for (int i = 0; i < ALLW.length; i++)
            for (int j = 0; j < ALLW[i].length; j++)
                if (ALLW[i][j].equals(y_m_d))
                    return i + 1;
        throw new IllegalArgumentException("School->queryWeekth 非法参数：" + y_m_d);
    }


    private static int[] split(String y_m_d) {
        String[] s = y_m_d.split("-");
        int[] is = new int[3];
        is[0] = Integer.valueOf(s[0]);
        is[1] = Integer.valueOf(s[1]);
        is[2] = Integer.valueOf(s[2]);
        return is;
    }

    /**
     * @param weekth 第几周
     * @param week   星期几
     * @return
     */
    public static int queryYear(@IntRange(from = 1, to = 24) int weekth,
                                @IntRange(from = 1, to = 7) int week) {
        String y_m_d = ALLW[weekth - 1][week - 1];
        int[] d = split(y_m_d);
        return d[0];
    }

    public static int[] queryMonth(@IntRange(from = 1, to = 24) int weekth) {
        String[] date = ALLW[weekth - 1];
        int[] month = new int[7];
        for (int i = 0; i < date.length; i++) {
            int[] d = split(date[i]);
            month[i] = d[1];
        }
        return month;
    }

    public static int[] queryDay(@IntRange(from = 1, to = 24) int weekth) {
        String[] date = ALLW[weekth-1];
        int[] day = new int[7];
        for(int i=0;i<date.length;i++){
            int[] d = split(date[i]);
            day[i] = d[2];
        }
        return day;
    }

    /**
     * 从第五周开始使用冬季作息
     * @param weekth
     * @return
     */
    public static Common.SEASON querySeason(@IntRange(from = 1,to = 24) int weekth){
        return SCHOOL_SEASON[weekth-1];
    }

    /**
     * 从接口获取的学校课表可能有些周没有课，因此会少一些周，必须调整到和学校周一杨
     */
    public static  List<OneWeekClasses> getSchoolWeeks() {
        List<OneWeekClasses> oneES = new ArrayList<>();
        for(int i=0;i<ALLW.length;i++){
            OneWeekClasses oneWeek = new OneWeekClasses();
            oneWeek.setSeason(SCHOOL_SEASON[i]);
            for(int j=0;j<ALLW[i].length;j++){
                int[] y_m_d = split(ALLW[i][j]);
                oneWeek.mMonth[j] = y_m_d[1];
                oneWeek.mDay[j] = y_m_d[2];
            }
            oneES.add(oneWeek);
        }
        return oneES;
    }


}
