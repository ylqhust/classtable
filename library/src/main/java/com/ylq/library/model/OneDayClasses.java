package com.ylq.library.model;

import com.ylq.library.query.HubBean;
import com.ylq.library.util.Store;

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
    public int mWeek;//星期几1-7
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

    public String getClasses(int month,int day){
        if(month!=mMonth || day!=mDay)
            return null;
        StringBuilder sb = new StringBuilder();
        int i;
        for(i=0;i<mClassUnit.size()-1;i++)
            sb.append(mClassUnit.get(i).mClassName+"|");
        sb.append(mClassUnit.get(i).mClassName);
        return sb.toString();
    }

    public static OneDayClasses getANewDay(String className, List<String> address, String teacher,int weekth, List<int[]> sections, int colorIndex) {
        OneDayClasses oneDayClasses = new OneDayClasses();
        oneDayClasses.mYear = 1996;
        oneDayClasses.mMonth = Store.getMonth(weekth)[sections.get(0)[0]];
        oneDayClasses.mDay = Store.getDay(weekth)[sections.get(0)[0]];
        oneDayClasses.mWeek = sections.get(0)[0]+1;
        for(int i=0;i<sections.size();i++)
            oneDayClasses.add(ClassUnit.getANewClassUnit(className,address.get(i),teacher,oneDayClasses.mMonth,
                    oneDayClasses.mDay,sections.get(i),colorIndex));
        return oneDayClasses;
    }

    public boolean combine(OneDayClasses oneDayClasses) {
        if(mMonth!=oneDayClasses.mMonth || mDay!=oneDayClasses.mDay||mWeek!=oneDayClasses.mWeek)
            return false;
        for(int i=0;i<oneDayClasses.mClassUnit.size();i++)
            add(oneDayClasses.mClassUnit.get(i));
        return true;
    }

    public void deleteByClassNameAndAddress(String className, String address) {
        for(int i=0;i<mClassUnit.size();i++)
            if(mClassUnit.get(i).mClassName.equals(className) && mClassUnit.get(i).mClassAddress.equals(address)){
                mClassUnit.remove(i);
                i--;
            }
    }
}

