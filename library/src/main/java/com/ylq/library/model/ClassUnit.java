package com.ylq.library.model;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.ylq.library.query.HubBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public String mClassName;
    public String mClassAddress;
    public int mFrom;
    public int mTimes;
    public int mWeek;
    public int mColor;
    public int mMonth;
    public int mDay;

    public ClassUnit(@NonNull String mClassAddress,
                     @NonNull String mClassName,
                     @IntRange(from = 1, to = 12) int mFrom,
                     @IntRange(from = 1, to = 12) int mTimes,
                     @IntRange(from = 1, to = 7) int week,
                     @IntRange(from = 0, to = 9) int colorIndex,
                     @IntRange(from = 1, to = 12) int month,
                     @IntRange(from = 1, to = 31) int day) {
        this.mClassAddress = mClassAddress;
        this.mClassName = mClassName;
        this.mFrom = mFrom;
        this.mTimes = mTimes;
        this.mWeek = week;
        this.mColor = colorIndex;
        this.mMonth = month;
        this.mDay = day;
    }


    public static ClassUnit parser(HubBean.Data data) throws ParseException {
        return new ClassUnit(data.formattedTxt.location,
                data.title,
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
        return object;
    }

    public static ClassUnit parserJSONObject(JSONObject object) throws JSONException {
        return new ClassUnit(object.getString(CA),
                object.getString(CN),
                object.getInt(FROM),
                object.getInt(TIMES),
                object.getInt(WEEK),
                object.getInt(COLOR),
                object.getInt(MONTH),
                object.getInt(DAY));
    }
}
