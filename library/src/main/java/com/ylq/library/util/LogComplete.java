package com.ylq.library.util;

import android.util.Log;

/**
 * Created by apple on 15/12/30.
 * 有些字符串太长，超过4000个字符就会无法全部打印出来，因此写出这个类
 */
public class LogComplete {
    private final static int DEFAULT_OUTPUT_LENGTH = 1000;
    public static void Log(String tag,String longStr){
        Log(tag,longStr,DEFAULT_OUTPUT_LENGTH);
    }

    public static void Log(String tag,String longStr,int customOutputLength){
        while(longStr.length() > customOutputLength){
            Log.d(tag, longStr.substring(0,customOutputLength));
            longStr = longStr.substring(customOutputLength);
        }
        Log.d(tag,longStr);
    }
}
