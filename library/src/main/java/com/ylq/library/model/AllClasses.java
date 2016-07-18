package com.ylq.library.model;

import com.ylq.library.query.Config;
import com.ylq.library.query.HubBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by apple on 16/7/12.
 */
public class AllClasses {
    private List<OneWeekClasses> mAllClasses = new ArrayList<>();


    private AllClasses() {
    }

    public static AllClasses parserHubBean(HubBean hubBean) throws ParseException {
        AllClasses allClasses = new AllClasses();
        int index = 0;
        while (index < hubBean.datas.size()) {
            OneWeekClasses oneWeekClasses = new OneWeekClasses();
            while (index < hubBean.datas.size()) {
                OneDayClasses oneDayClasses = new OneDayClasses(hubBean.datas.get(index));
                if (oneWeekClasses.shouldAddTheDay(oneDayClasses)) {
                    oneWeekClasses.add(oneDayClasses);
                    while (index < hubBean.datas.size()) {
                        ClassUnit classUnit = ClassUnit.parser(hubBean.datas.get(index));
                        if (oneDayClasses.shouldAddTheClass(classUnit)) {
                            oneDayClasses.add(classUnit);
                            index++;
                        } else
                            break;
                    }
                } else
                    break;
            }
            oneWeekClasses.setSeason(Common.isSummer()? Common.SEASON.SUMMER: Common.SEASON.WINTER);
            allClasses.add(oneWeekClasses);
        }
        return allClasses;
    }

    private void add(OneWeekClasses oneWeekClasses) {
        this.mAllClasses.add(oneWeekClasses);
    }


    public OneWeekClasses getOneWeek(int i) {
        if(i-1>=mAllClasses.size())
            return null;
        return mAllClasses.get(i-1);
    }

    public JSONObject getOneWeekJSONObject(int index) throws JSONException {
        if((index-1)>=mAllClasses.size())
            return null;
        return mAllClasses.get(index-1).getOneWeekJSONObject();
    }


    public static AllClasses parserJSONArray(JSONArray jsonArray) throws JSONException {
        AllClasses allClasses = new AllClasses();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject object = (JSONObject) jsonArray.get(i);
            OneWeekClasses oneWeekClasses = OneWeekClasses.parserJSONObject(object);
            allClasses.add(oneWeekClasses);
        }
        return allClasses;
    }

    /**
     * 返回值必须大于等于1
     */
    public int getCurrentWeek() {
        for(int i=0;i<mAllClasses.size();i++)
            if(mAllClasses.get(i).isContainToday())
                return i+1;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        if (year == Config.QUERY_START_YEAR && month<=Config.QUERY_START_MONTH)
            return 1;
        return mAllClasses.size();
    }

    public void addEmptyWeekUntil24() {
        if(mAllClasses.size()>=24)
            return;
        while(mAllClasses.size()!=24){
            mAllClasses.add(mAllClasses.get(mAllClasses.size()-1).getEmptyWeek());
        }
    }
    public int getAllWeekCount() {
        return mAllClasses.size();
    }

    public List<OneWeekClasses> getData(){
        return mAllClasses;
    }
}
