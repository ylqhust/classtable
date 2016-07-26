package com.ylq.library.classtable;

import android.content.Context;
import android.content.SharedPreferences;

import com.ylq.library.classtable.model.AllClasses;
import com.ylq.library.classtable.model.ClassUnit;
import com.ylq.library.classtable.model.Common;
import com.ylq.library.classtable.model.NewClassDataWrap;
import com.ylq.library.classtable.model.OneWeekClasses;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/7/14.
 */
public class Store {
    public static final String SH_NAME = "hust_pass_classtable";
    public static final String NAME_SAVE_SUCCESS = "save_success";
    private static final String SAVED_CLASS_TABLE = "saved_class_table";
    private static final String SHOW_ALL_CLASSES = "show_all_classes";
    private static final String NOTIFICATION_TOMORROW = "notification_tomorrow";
    private static final String ADD_BY_MYSELF_DATA = "add_by_myself_data";
    public static AllClasses mMainData;

    /**
     * 保存从hub上获得的课程表数据
     *
     * @return
     */
    public static boolean saveClassData(Context context, AllClasses allClasses) {
        return saveToLocal(context, allClasses.getAllDataJSONString());
    }

    private static boolean saveToLocal(Context context, String string) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVED_CLASS_TABLE, string);
        editor.putBoolean(NAME_SAVE_SUCCESS, true);
        editor.commit();
        return true;
    }

    public static boolean isLocalHaveData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(NAME_SAVE_SUCCESS, false);
    }

    public static AllClasses getLocalData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString(SAVED_CLASS_TABLE, null);
        String addData = sharedPreferences.getString(ADD_BY_MYSELF_DATA, null);
        if (data == null && addData == null)
            return null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            AllClasses main = AllClasses.parserJSONArray(jsonArray);
            mMainData = main;
            if (addData != null) {
                JSONArray jsonArrays = new JSONArray(addData);
                AllClasses x = AllClasses.parserJSONArray(jsonArrays);
                main = main.combine(x);
            }
            return main;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取明天的课程
     *
     * @param context
     * @return
     */
    public static String getTomorrowClasses(Context context) {
        if (!isLocalHaveData(context))
            return null;
        AllClasses allClasses = getLocalData(context);
        if (allClasses == null)
            return null;
        List<OneWeekClasses> data = allClasses.getData();
        if (data == null)
            return null;
        for (OneWeekClasses oneWeekClasses : data) {
            String s = oneWeekClasses.getTomorrowClasses();
            if (s != null)
                return s;
        }
        return null;
    }

    private static boolean getBoolean(String key, Context context, boolean defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static boolean isShowAll(Context context) {
        return getBoolean(SHOW_ALL_CLASSES, context, false);
    }

    public static boolean isNotification(Context context) {
        return getBoolean(NOTIFICATION_TOMORROW, context, true);
    }

    private static void saveSetting(String key, Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void saveShowAllSettig(Context context, boolean value) {
        saveSetting(SHOW_ALL_CLASSES, context, value);
    }

    public static void saveNotificationSetting(Context context, boolean value) {
        saveSetting(NOTIFICATION_TOMORROW, context, value);
    }

    public static boolean addNewClassData(Context context, NewClassDataWrap usefulData) throws JSONException {
        SharedPreferences share = context.getSharedPreferences(SH_NAME, Context.MODE_PRIVATE);
        String existData = share.getString(ADD_BY_MYSELF_DATA, null);
        AllClasses existClass = null;
        if (existData != null)
            existClass = AllClasses.parserJSONArray(new JSONArray(existData));
        AllClasses newClass = AllClasses.parserNewClassDataWrap(usefulData);
        if (existClass != null) {
            existClass.combine(newClass);
            SharedPreferences.Editor edit = share.edit();
            edit.putString(ADD_BY_MYSELF_DATA, existClass.getAllDataJSONString());
            edit.commit();
            return true;
        } else {
            SharedPreferences.Editor edit = share.edit();
            edit.putString(ADD_BY_MYSELF_DATA, newClass.getAllDataJSONString());
            edit.commit();
            return true;
        }
    }

    public static int[] getMonth(int weekth) {
        return mMainData.getOneWeek(weekth).mMonth;
    }

    public static int[] getDay(int weekth) {
        return mMainData.getOneWeek(weekth).mDay;
    }

    public static Common.SEASON getSeason(int weekth) {
        return mMainData.getOneWeek(weekth).getSEASON();
    }

    /**
     * 根据课程名和课程地址找到老师名
     *
     * @param className
     * @param address
     * @return
     */
    public static String findTeacher(String className, String address) {
        String result = "";
        List<OneWeekClasses> allWeek = mMainData.getData();
        for (int i = 0; i < allWeek.size(); i++) {
            List<ClassUnit> allUnit = allWeek.get(i).getAllUnit();
            for (int j = 0; j < allUnit.size(); j++) {
                if (allUnit.get(j).mClassName.equals(className) && allUnit.get(j).mClassAddress.equals(address)) {
                    String teacher = allUnit.get(j).mTeacher;
                    if (!result.contains(teacher)) {
                        result += teacher;
                        result += ",";
                    }
                }
            }
        }
        if (result.length() == 0)
            result = "待定";
        else
            result = result.substring(0, result.length() - 1);
        return result;
    }

    /**
     * 根据课程名和课程地址获取课程的周次和节次信息，每一组使用一个@符号结合
     *
     * @param className
     * @param address
     * @return
     */
    public static List<String> findWeekAndSection(String className, String address) {
        List<int[]> sections = new ArrayList<>();
        List<byte[]> weeks = new ArrayList<>();
        List<OneWeekClasses> allWeek = mMainData.getData();
        for (int i = 0; i < allWeek.size(); i++) {
            List<ClassUnit> allUnit = allWeek.get(i).getAllUnit();
            for (int j = 0; j < allUnit.size(); j++) {
                if (allUnit.get(j).mClassName.equals(className) && allUnit.get(j).mClassAddress.equals(address)) {
                    int week = allUnit.get(j).mWeek;
                    int from = allUnit.get(j).mFrom;
                    int times = allUnit.get(j).mTimes;
                    int index = getExistIndex(sections, week, from, times);
                    if (index == -1) {
                        int[] section = new int[3];
                        section[0] = week;
                        section[1] = from;
                        section[2] = times;
                        sections.add(section);
                        byte[] wek = getEmptyWeek();
                        wek[i] = 1;
                        weeks.add(wek);
                    } else {
                        weeks.get(index)[i] = 1;
                    }
                }
            }
        }

        List<String> results = new ArrayList<>();
        for(int i=0;i<weeks.size();i++)
            results.add(getWeekString(weeks.get(i))+"@"+getSectionString(sections.get(i)));
        return results;
    }

    private static String getSectionString(int[] ints) {
        return "星期"+ints[0]+" 第"+ints[1]+"节 到 第"+(ints[1]+ints[2]-1)+"节";
    }

    private static String getWeekString(byte[] checked) {
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < 24; ) {
            if (checked[i] != 1) {
                i++;
                continue;
            }
            int j;
            for (j = i + 1; j < 24; j++) {
                if (checked[j] != 1) {
                    if (j == i + 1)
                        sb.append((i + 1) + ",");
                    else
                        sb.append((i + 1) + "-" + j + ",");
                    i = j + 1;
                    break;
                }
            }
            if (j == 23)
                break;
        }
        if (i<24 && checked[i] != 0) {
            if (i == 23)
                sb.append("24,");
            else
                sb.append((i + 1) + "-24,");
        }
        String s = sb.toString();
        s = s.substring(0, s.length() - 1);
        s += "周";
        return s;
    }


    private static int getExistIndex(List<int[]> sections, int week, int from, int times) {
        for (int i = 0; i < sections.size(); i++) {
            int[] t = sections.get(i);
            if (t[0] == week && t[1] == from && t[2] == times)
                return i;
        }
        return -1;
    }


    private static byte[] getEmptyWeek() {
        byte[] week = new byte[24];
        for (int i = 0; i < week.length; i++)
            week[i] = 0;
        return week;
    }


    /**
     * 根据课程名和课程地址删除课程
     * @param mClassName
     * @param mAddress
     * @return
     */
    public static boolean deleteByClassNameAndAddress(Context context,String className, String address) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String data = sharedPreferences.getString(SAVED_CLASS_TABLE, null);
        String addData = sharedPreferences.getString(ADD_BY_MYSELF_DATA, null);
        if (data == null && addData == null)
            return false;
        try {
            JSONArray jsonArray = new JSONArray(data);
            AllClasses main = AllClasses.parserJSONArray(jsonArray);
            main.deleteByClassNameAndAddress(className,address);
            editor.putString(SAVED_CLASS_TABLE,main.getAllDataJSONString());
            editor.commit();
            if (addData != null) {
                JSONArray jsonArrays = new JSONArray(addData);
                AllClasses x = AllClasses.parserJSONArray(jsonArrays);
                x.deleteByClassNameAndAddress(className,address);
                editor.putString(ADD_BY_MYSELF_DATA,x.getAllDataJSONString());
                editor.commit();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 按照主客户端的需求，提供一个查询当前周所有课程方法
     * 返回一个String[]数组，此数组的长度一定为7，代表从星期一到星期7
     * 数组中的值要么是null，要么是一个字符串，每个字符串
     * @param context
     * @return
     */
    public static String[] queryCurrentWeek(Context context){
        return null;
    }
}
