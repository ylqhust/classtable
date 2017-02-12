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

    public static final String MIN = "2017-02-13";
    public static final String MAX = "2017-07-30";

    public static final String W1[] = {"2017-02-13","2017-02-14","2017-02-15","2017-02-16","2017-02-17","2017-02-18","2017-02-19"};
    public static final String W2[] = {"2017-02-20","2017-02-21","2017-02-22","2017-02-23","2017-02-24","2017-02-25","2017-02-26"};
    public static final String W3[] = {"2017-02-27","2017-02-28","2017-03-01","2017-03-02","2017-03-03","2017-03-04","2017-03-05"};
    public static final String W4[] = {"2017-03-06","2017-03-07","2017-03-08","2017-03-09","2017-03-10","2017-03-11","2017-03-12"};
    public static final String W5[] = {"2017-03-13","2017-03-14","2017-03-15","2017-03-16","2017-03-17","2017-03-18","2017-03-19"};
    public static final String W6[] = {"2017-03-20","2017-03-21","2017-03-22","2017-03-23","2017-03-24","2017-03-25","2017-03-26"};
    public static final String W7[] = {"2017-03-27","2017-03-28","2017-03-29","2017-03-30","2017-03-31","2017-04-01","2017-04-02"};
    public static final String W8[] = {"2017-04-03","2017-04-04","2017-04-05","2017-04-06","2017-04-07","2017-04-08","2017-04-09"};
    public static final String W9[] = {"2017-04-10","2017-04-11","2017-04-12","2017-04-13","2017-04-14","2017-04-15","2017-04-16"};
    public static final String W10[] = {"2017-04-17","2017-04-18","2017-04-19","2017-04-20","2017-04-21","2017-04-22","2017-04-23"};
    public static final String W11[] = {"2017-04-24","2017-04-25","2017-04-26","2017-04-27","2017-04-28","2017-04-29","2017-04-30"};
    public static final String W12[] = {"2017-05-01","2017-05-02","2017-05-03","2017-05-04","2017-05-05","2017-05-06","2017-05-07"};
    public static final String W13[] = {"2017-05-08","2017-05-09","2017-05-10","2017-05-11","2017-05-12","2017-05-13","2017-05-14"};
    public static final String W14[] = {"2017-05-15","2017-05-16","2017-05-17","2017-05-18","2017-05-19","2017-05-20","2017-05-21"};
    public static final String W15[] = {"2017-05-22","2017-05-23","2017-05-24","2017-05-25","2017-05-26","2017-05-27","2017-05-28"};
    public static final String W16[] = {"2017-05-29","2017-05-30","2017-05-31","2017-06-01","2017-06-02","2017-06-03","2017-06-04"};
    public static final String W17[] = {"2017-06-05","2017-06-06","2017-06-07","2017-06-08","2017-06-09","2017-06-10","2017-06-11"};
    public static final String W18[] = {"2017-06-12","2017-06-13","2017-06-14","2017-06-15","2017-06-16","2017-06-17","2017-06-18"};
    public static final String W19[] = {"2017-06-19","2017-06-20","2017-06-21","2017-06-22","2017-06-23","2017-06-24","2017-06-25"};
    public static final String W20[] = {"2017-06-26","2017-06-27","2017-06-28","2017-06-29","2017-06-30","2017-07-01","2017-07-02"};
    public static final String W21[] = {"2017-07-03","2017-07-04","2017-07-05","2017-07-06","2017-07-07","2017-07-08","2017-07-09"};
    public static final String W22[] = {"2017-07-10","2017-07-11","2017-07-12","2017-07-13","2017-07-14","2017-07-15","2017-07-16"};
    public static final String W23[] = {"2017-07-17","2017-07-18","2017-07-19","2017-07-20","2017-07-21","2017-07-22","2017-07-23"};
    public static final String W24[] = {"2017-07-24","2017-07-25","2017-07-26","2017-07-27","2017-07-28","2017-07-29","2017-07-30"};
    public static final Common.SEASON[] SCHOOL_SEASON = {
            Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER, Common.SEASON.WINTER,
            Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER,
            Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER,
            Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER,
            Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER,
            Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER, Common.SEASON.SUMMER
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
     * 从接口获取的学校课表可能有些周没有课，因此会少一些周，必须调整到和学校周一样
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
