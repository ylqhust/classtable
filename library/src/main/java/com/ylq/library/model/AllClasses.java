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
public class AllClasses implements Cloneable{
    private List<OneWeekClasses> mAllClasses = new ArrayList<>();

    private AllClasses() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public String getAllDataJSONString(){
        JSONArray all = new JSONArray();
        int i=1;
        while(true){
            JSONObject object = null;
            try {
                object = getOneWeekJSONObject(i++);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            if(object==null)
                break;
            all.put(object);
        }
        return all.toString();
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

    public static AllClasses parserNewClassDataWrap(NewClassDataWrap usefulData) {
        AllClasses allClasses = new AllClasses();
        int colorIndex = (int) (System.currentTimeMillis()%10);
        if(colorIndex>9)
            colorIndex=9;
        for(int i=0;i<usefulData.weeks.get(0).length;i++){
            List<int[]> sections = new ArrayList<>();
            List<String> addresses = new ArrayList<>();
            for(int j=0;j<usefulData.weeks.size();j++){
                if(usefulData.weeks.get(j)[i]==1){
                    sections.add(usefulData.sections.get(j));
                    addresses.add(usefulData.addresses.get(j));
                }
            }
            if(sections.size()==0)
                continue;
            allClasses.add(OneWeekClasses.getANewWeek(usefulData.className,addresses,usefulData.teacherName,i+1,sections,colorIndex));
        }
        return allClasses;
    }

    public AllClasses combine(AllClasses newClass) {
        List<OneWeekClasses> news = newClass.mAllClasses;
        for(int i=0;i<mAllClasses.size();i++){
            if(news.size()==0)
                break;
            for(int j=0;j<news.size();j++)
                if(mAllClasses.get(i).combine(news.get(j))){
                    news.remove(j);
                    break;
                }
        }
        while(news.size()!=0){//说明周次是错开的，那么将news中的周次按照时间顺序插入到mAllClasses中
            OneWeekClasses ow = news.get(0);
            insertByTime(mAllClasses,ow);
            news.remove(0);
        }
        return this;
    }

    private void insertByTime(List<OneWeekClasses> mother, OneWeekClasses ow) {
        if(mother.size()==0){
            mother.add(ow);
            return;
        }
        for(int i=0;i<mother.size();i++)
            if(ow.before(mother.get(i))){
                mother.add(i,ow);
                return;
            }
        mother.add(ow);
    }


    public void deleteByClassNameAndAddress(String className, String address) {
        for(int i=0;i<mAllClasses.size();i++)
            mAllClasses.get(i).deleteByClassNameAndAddress(className,address);
    }
}
