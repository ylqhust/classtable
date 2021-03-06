package com.ylq.library.classtable.model;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.ylq.library.classtable.query.HubBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by apple on 16/7/11.
 */
public class ClassUnit {
    public static final String CN = "class_name";
    public static final String CA = "class_address";
    public static final String FROM = "from";
    public static final String TIMES = "times";
    public static final String WEEK = "week";
    public static final String COLOR = "color";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String TEACHER = "teacher";
    public String mClassName;
    public String mClassAddress;
    public int mFrom;
    public int mTimes;
    public int mWeek;
    public int mColor;
    public int mMonth;
    public int mDay;
    public String mTeacher;

    public ClassUnit(@NonNull String classAddress,
                     @NonNull String className,
                     @NonNull String teacher,
                     @IntRange(from = 1, to = 12) int mFrom,
                     @IntRange(from = 1, to = 12) int mTimes,
                     @IntRange(from = 1, to = 7) int week,
                     @IntRange(from = 0, to = 9) int colorIndex,
                     @IntRange(from = 1, to = 12) int month,
                     @IntRange(from = 1, to = 31) int day) {
        this.mClassAddress = classAddress;
        this.mClassName = className;
        this.mFrom = mFrom;
        this.mTimes = mTimes;
        this.mWeek = week;
        this.mColor = colorIndex;
        this.mMonth = month;
        this.mDay = day;
        this.mTeacher = teacher;
    }


    public static ClassUnit parser(HubBean.Data data) throws ParseException {
        return new ClassUnit(data.formattedTxt.location,
                data.title,
                data.formattedTxt.teacher,
                getFrom(data.start),
                getTimes(data.start, data.end),
                Common.getWeek(data.start),
                Common.getColorIndex(data.title),
                Common.getMonth(data.start),
                Common.getDay(data.start));
    }


    private static int getTimes(String start, String end) {
        String[] s = start.split(" ");
        String[] e = end.split(" ");
        return Common.getTimes(s[1], e[1]);
    }

    private static int getFrom(String start) {
        String[] ss = start.split(" ");
        return Common.getFrom(ss[1]);
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(CN,mClassName);
        object.put(CA,mClassAddress);
        object.put(FROM,mFrom);
        object.put(TIMES,mTimes);
        object.put(WEEK,mWeek);
        object.put(COLOR,mColor);
        object.put(MONTH,mMonth);
        object.put(DAY,mDay);
        object.put(TEACHER,mTeacher);
        return object;
    }

    public static ClassUnit parserJSONObject(JSONObject object) throws JSONException {
        return new ClassUnit(object.getString(CA),
                object.getString(CN),
                object.isNull(TEACHER)?"待定":object.getString(TEACHER),
                object.getInt(FROM),
                object.getInt(TIMES),
                object.getInt(WEEK),
                object.getInt(COLOR),
                object.getInt(MONTH),
                object.getInt(DAY));
    }

    public static ClassUnit getANewClassUnit(String className, String address,String teacher, int mMonth, int mDay, int[] section, int colorIndex) {
        return new ClassUnit(address,className,teacher,section[1]+1,section[2]-section[1]+1,section[0]+1,colorIndex,mMonth,mDay);
    }
}
