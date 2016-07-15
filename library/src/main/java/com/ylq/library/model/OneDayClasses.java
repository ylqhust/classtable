package com.ylq.library.model;

import com.ylq.library.query.HubBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/7/13.
 */
public class OneDayClasses {
    public int mYear;
    public int mMonth;
    public int mDay;
    public int mWeek;
    private List<ClassUnit> mClassUnit = new ArrayList<>();
    private OneDayClasses(){}
    public OneDayClasses(HubBean.Data data) throws ParseException {
        mMonth = Common.getMonth(data.start);
        mDay = Common.getDay(data.start);
        mWeek = Common.getWeek(data.start);
        mYear = Common.getYear(data.start);
    }

    public void add(ClassUnit classUnit){
        this.mClassUnit.add(classUnit);
    }

    public boolean shouldAddTheClass(ClassUnit classUnit) {
        if((mDay==classUnit.mDay)&&(mMonth==classUnit.mMonth))
            return true;
        return false;
    }

    public void putTo(List<ClassUnit> classUnits) {
        for(ClassUnit c:mClassUnit)
            classUnits.add(c);
    }

    public JSONObject getOneDayClassJSONObject() throws JSONException {
        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(ClassUnit classUnit:mClassUnit)
            jsonArray.put(classUnit.getJSONObject());
        object.put("year",mYear);
        object.put("month",mMonth);
        object.put("day",mDay);
        object.put("week",mWeek);
        object.put("array",jsonArray);
        return object;
    }



    public static OneDayClasses parserJSONObject(JSONObject jsonObject) throws JSONException {
        OneDayClasses oneDayClasses = new OneDayClasses();
        oneDayClasses.mYear = jsonObject.getInt("year");
        oneDayClasses.mMonth = jsonObject.getInt("month");
        oneDayClasses.mDay = jsonObject.getInt("day");
        oneDayClasses.mWeek = jsonObject.getInt("week");
        JSONArray array = jsonObject.getJSONArray("array");
        for(int i=0;i<array.length();i++)
            oneDayClasses.add(ClassUnit.parserJSONObject(array.getJSONObject(i)));
        return oneDayClasses;
    }
}

