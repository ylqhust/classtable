package com.ylq.library.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ylq.library.model.AllClasses;
import com.ylq.library.model.OneWeekClasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    /**
     *
     * 保存从hub上获得的课程表数据
     * @return
     */
    public static boolean saveClassData(Context context,AllClasses allClasses) {
        JSONArray all = new JSONArray();
        int i=1;
        while(true){
            JSONObject object = null;
            try {
                object = allClasses.getOneWeekJSONObject(i++);
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
            if(object==null)
                break;
            all.put(object);
        }
        return saveToLocal(context,all.toString());
    }

    private static boolean saveToLocal(Context context,String string){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVED_CLASS_TABLE,string);
        editor.putBoolean(NAME_SAVE_SUCCESS,true);
        editor.commit();
        return true;
    }

    public static boolean isLocalHaveData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(NAME_SAVE_SUCCESS,false);
    }

    public static AllClasses getLocalData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME,Context.MODE_PRIVATE);
        String data = sharedPreferences.getString(SAVED_CLASS_TABLE,null);
        if(data==null)
            return null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            return AllClasses.parserJSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取明天的课程
     * @param context
     * @return
     */
    public static String getTomorrowClasses(Context context){
        if(!isLocalHaveData(context))
            return null;
        AllClasses allClasses = getLocalData(context);
        if(allClasses==null)
            return null;
        List<OneWeekClasses> data = allClasses.getData();
        if(data==null)
            return null;
        for(OneWeekClasses oneWeekClasses:data){
            String s = oneWeekClasses.getTomorrowClasses();
            if(s!=null)
                return s;
        }
        return null;
    }

    private static boolean getBoolean(String key,Context context,boolean defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,defValue);
    }

    public static boolean isShowAll(Context context){
        return getBoolean(SHOW_ALL_CLASSES,context,false);
    }

    public static boolean isNotification(Context context){
        return getBoolean(NOTIFICATION_TOMORROW,context,true);
    }
    private static void saveSetting(String key,Context context,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SH_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static void saveShowAllSettig(Context context,boolean value){
        saveSetting(SHOW_ALL_CLASSES,context,value);
    }

    public static void saveNotificationSetting(Context context,boolean value){
       saveSetting(NOTIFICATION_TOMORROW,context,value);
    }
}
