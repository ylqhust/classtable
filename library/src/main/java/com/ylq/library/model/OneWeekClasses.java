package com.ylq.library.model;

import android.support.annotation.IntRange;

import com.ylq.library.query.Config;
import com.ylq.library.util.DateUtils;
import com.ylq.library.util.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * Created by apple on 16/7/12.
 * 一周的课表
 */
public class OneWeekClasses {

    private Common.SEASON mSeason = Common.SEASON.SUMMER;
    public int[] mMonth = new int[7];
    public int[] mDay = new int[7];

    private List<OneDayClasses> mOneDayClasses = new ArrayList<>();

    public void add(OneDayClasses oneDayClasses) {
        this.mOneDayClasses.add(oneDayClasses);
    }


    public boolean shouldAddTheDay(OneDayClasses oneDayClasses) {
        if (mOneDayClasses.size() == 0) {
            initIntArray(oneDayClasses);
            return true;
        }
        OneDayClasses currentLastDay = mOneDayClasses.get(mOneDayClasses.size() - 1);
        if (oneDayClasses.mWeek <= currentLastDay.mWeek)
            return false;
        if ((oneDayClasses.mMonth == currentLastDay.mMonth) &&
                (oneDayClasses.mDay - currentLastDay.mDay + 1) > 7)
            return false;
        if ((oneDayClasses.mMonth != currentLastDay.mMonth) && ((getFullDay(currentLastDay) + 1 - currentLastDay.mDay + oneDayClasses.mDay) > 7))
            return false;
        return true;
    }

    private void initIntArray(OneDayClasses oneDayClasses) {
        int i=1;
        while(i<=7){
            if(i<=oneDayClasses.mWeek){
                mMonth[i-1] = DateUtils.getBeforeDateMonth(oneDayClasses.mYear,oneDayClasses.mMonth,oneDayClasses.mDay,oneDayClasses.mWeek-i);
                mDay[i-1] = DateUtils.getBeforeDateDay(oneDayClasses.mYear,oneDayClasses.mMonth,oneDayClasses.mDay,oneDayClasses.mWeek-i);
            }else {
                mMonth[i-1] = DateUtils.getAfterDateMonth(oneDayClasses.mYear,oneDayClasses.mMonth,oneDayClasses.mDay,i-oneDayClasses.mWeek);
                mDay[i-1] = DateUtils.getAfterDateDay(oneDayClasses.mYear,oneDayClasses.mMonth,oneDayClasses.mDay,i-oneDayClasses.mWeek);
            }
            i++;
        }
    }

    private int getFullDay(OneDayClasses currentLastDay) {
        int month = currentLastDay.mMonth;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
            return 31;
        if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        int year = currentLastDay.mYear;
        if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
            return 29;
        return 28;
    }

    public List<ClassUnit> getAllUnit() {
        List<ClassUnit> classUnits = new ArrayList<>();
        for (OneDayClasses oneDayClasses : mOneDayClasses)
            oneDayClasses.putTo(classUnits);
        return classUnits;
    }


    public void setSeason(Common.SEASON season) {
        this.mSeason = season;
    }

    public Common.SEASON getSEASON(){
        return mSeason;
    }

    private String getSeasonString() {
        return mSeason == Common.SEASON.SUMMER ? "summer" : "winter";
    }

    public static OneWeekClasses parserJSONObject(JSONObject object) throws JSONException {
        OneWeekClasses oneWeekClasses = new OneWeekClasses();
        String season = object.getString("season");
        returnIntArray(oneWeekClasses, 0, object.getString("date1"));
        returnIntArray(oneWeekClasses, 1, object.getString("date2"));
        returnIntArray(oneWeekClasses, 2, object.getString("date3"));
        returnIntArray(oneWeekClasses, 3, object.getString("date4"));
        returnIntArray(oneWeekClasses, 4, object.getString("date5"));
        returnIntArray(oneWeekClasses, 5, object.getString("date6"));
        returnIntArray(oneWeekClasses, 6, object.getString("date7"));
        JSONArray oneWeek = object.getJSONArray("array");
        oneWeekClasses.mSeason = season.equals("summer") ? Common.SEASON.SUMMER : Common.SEASON.WINTER;
        for (int i = 0; i < oneWeek.length(); i++)
            oneWeekClasses.add(OneDayClasses.parserJSONObject(oneWeek.getJSONObject(i)));
        return oneWeekClasses;
    }

    private static void returnIntArray(OneWeekClasses oneWeekClasses, int i, String data) {
        String[] ss = data.split("\\.");
        oneWeekClasses.mMonth[i] = Integer.parseInt(ss[0]);
        oneWeekClasses.mDay[i] = Integer.parseInt(ss[1]);
    }

    public JSONObject getOneWeekJSONObject() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("season", getSeasonString());
        object.put("date1", mMonth[0] + "." + mDay[0]);
        object.put("date2", mMonth[1] + "." + mDay[1]);
        object.put("date3", mMonth[2] + "." + mDay[2]);
        object.put("date4", mMonth[3] + "." + mDay[3]);
        object.put("date5", mMonth[4] + "." + mDay[4]);
        object.put("date6", mMonth[5] + "." + mDay[5]);
        object.put("date7", mMonth[6] + "." + mDay[6]);
        JSONArray array = new JSONArray();
        for (OneDayClasses oneDayClasses : mOneDayClasses)
            array.put(oneDayClasses.getOneDayClassJSONObject());
        object.put("array", array);
        return object;
    }

    public boolean isContainToday(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        for(int i=0;i<7;i++)
            if(mMonth[i]==month&&mDay[i]==day)
                return true;
        return false;
    }

    public String getTomorrowClasses(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        for(int i=0;i<7;i++)
            if(mMonth[i]==month&&mDay[i]==day){
                for(int j=0;j<mOneDayClasses.size();j++){
                    String s = mOneDayClasses.get(i).getClasses(month,day);
                    if(s!=null)
                        return s;
                }
            }
        return null;
    }

    public OneWeekClasses getEmptyWeek(){
        OneWeekClasses oneWeekClasses = new OneWeekClasses();
        oneWeekClasses.mSeason = mSeason;
        for(int i=0;i<7;i++){
            oneWeekClasses.mMonth[i] = DateUtils.getAfterDateMonth(Config.QUERY_END_YEAR,mMonth[6],mDay[6],i+1);
            oneWeekClasses.mDay[i] = DateUtils.getAfterDateDay(Config.QUERY_END_YEAR,mMonth[6],mDay[6],i+1);
        }
        return oneWeekClasses;
    }

    public String getMonthAndDateText(@IntRange(from = 0,to = 6) int i) {
        return mMonth[i]+"."+mDay[i];
    }

    /**
     * 返回这一周的课程
     * @param className
     * @param addresses
     * @param weekth 第几周，从1开始，到24结束
     * @param sections
     * @return
     */
    public static OneWeekClasses getANewWeek(String className, List<String> addresses, @IntRange(from = 1, to = 24) int weekth, List<int[]> sections, int colorIndex) {
        OneWeekClasses oneWeek = new OneWeekClasses();
        oneWeek.mSeason = Store.getSeason(weekth);
        oneWeek.mMonth = Store.getMonth(weekth);
        oneWeek.mDay = Store.getDay(weekth);
        List<int[]> s = sections;
        List<String> ad = addresses;
        while(s.size()>0){
            List<int[]> ss = new ArrayList<>();
            List<String> sa = new ArrayList<>();
            ss.add(s.get(0));
            sa.add(ad.get(0));
            s.remove(0);
            ad.remove(0);
            for(int i=0;i<s.size();i++)
                if(s.get(i)[0]==ss.get(0)[0]){
                    ss.add(s.get(i));
                    sa.add(ad.get(i));
                    s.remove(i);
                    ad.remove(i);
                    i--;
                }
            oneWeek.add(OneDayClasses.getANewDay(className,sa,weekth,ss,colorIndex));
        }
        return oneWeek;
    }

    public boolean combine(OneWeekClasses oneWeekClasses) {
        if(mMonth[0]!=oneWeekClasses.mMonth[0] || mDay[0]!=oneWeekClasses.mDay[0] || mSeason!=oneWeekClasses.mSeason)
            return false;
        List<OneDayClasses> oneDayClasses = oneWeekClasses.mOneDayClasses;
        for(int i=0;i<mOneDayClasses.size();i++)
            for(int j=0;j<oneDayClasses.size();j++)
                if(mOneDayClasses.get(i).combine(oneDayClasses.get(j))){
                    oneDayClasses.remove(j);
                    j--;
                }
        while(oneDayClasses.size()!=0){
            OneDayClasses od = oneDayClasses.get(0);
            insertByTime(mOneDayClasses,od);
            oneDayClasses.remove(0);
        }
        return true;
    }

    private void insertByTime(List<OneDayClasses> mother, OneDayClasses od) {
        if(mother.size()==0){
            mother.add(od);
            return;
        }
        for(int i=0;i<mother.size();i++)
            if(od.mWeek<mother.get(i).mWeek){
                mother.add(i,od);
                return;
            }
        mother.add(od);
    }

    /**
     * 判断this周是否在oneWeekClasses周之前
     * @param oneWeekClasses
     * @return
     */
    public boolean before(OneWeekClasses oneWeekClasses) {
        if(mMonth[6]==oneWeekClasses.mMonth[0])
            return mDay[6]<oneWeekClasses.mDay[0];
        int thisMonth = mMonth[6];
        int thatMonth = oneWeekClasses.mMonth[0];
        if(thisMonth>=8 && thisMonth<=12 && thatMonth>=8 && thatMonth<=12)
            return thisMonth<thatMonth;
        if(thisMonth>=8 && thisMonth<=12 && thatMonth>=1 && thatMonth<=2)
            return true;//认为thatMonth是下一年的
        if(thisMonth>=1 && thisMonth<=2 && thatMonth>=1 && thatMonth<=2)
            return thisMonth<thatMonth;
        if(thisMonth>=1 && thisMonth<=2 && thatMonth>=8 && thatMonth<=12)
            return false;
        return thisMonth<thatMonth;
    }
}

