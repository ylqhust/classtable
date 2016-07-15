package com.ylq.library.util;

import android.content.Context;
import android.content.res.Resources;

import java.lang.reflect.Field;

/**
 * Created by apple on 16/7/14.
 */
public class DimenUtils {

    public static int getStatuBarHeight(){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = Resources.getSystem().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getWindowHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getWindowWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getDimensionPixelSize(Context context,int id) {
        return context.getResources().getDimensionPixelSize(id);
    }
}
