package com.ylq.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ylq.library.model.ClassUnit;
import com.ylq.library.util.DateUtils;

/**
 * Created by apple on 16/7/9.
 */

/**
 * 获取用于显示课程的TextView
 * 根据参数将TextView设置好
 */
public class ClassUnitFactory {

    private static String[] colorArray;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static TextView obtain(@IntRange(from = 1, to = 12) int from,//从第几节课开始
                                   @IntRange(from = 1,to = 12) int times,//这个课持续几节，一般就是2或者4
                                   @NonNull String className,
                                   @NonNull String address,//上课地点
                                   @NonNull Context context,
                                   @IntRange(from = 0,to = 9) int colorIndex,
                                   int width,
                                   int height,
                                   int topMargin,
                                   int leftMargin){
        TextView textView = new TextView(context);
        Drawable drawable = context.getDrawable(R.drawable.classtable_unit_circle_coner);
        //Drawable drawable = context.getResources().getDrawable(R.drawable.classtable_unit_circle_coner);
        drawable.setTint(Color.parseColor(colorArray[colorIndex]));
        textView.setBackground(drawable);
        textView.setText(className+"@"+address);
        textView.setTextSize(context.getResources().getDimension(R.dimen.classtable_text_size));
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new FrameLayout.LayoutParams(width,height));
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
        marginLayoutParams.leftMargin = leftMargin;
        marginLayoutParams.topMargin = topMargin;
        textView.setLayoutParams(marginLayoutParams);
        return  textView;
    }

    /**
     * 我认为应该这么设计ui
     * @param classUnit
     * @param context
     * @return
     */
    public static TextView obtainI(ClassUnit classUnit,@NonNull Context context){
        init(context);
        int week = classUnit.mWeek;
        int times = classUnit.mTimes;
        int from = classUnit.mFrom;
        int width = DateUtils.getWeekday()==week?context.getResources().getDimensionPixelSize(R.dimen.classtable_long_unit_width):context.getResources().getDimensionPixelSize(R.dimen.classtable_unit_width);
        int height = (int) (context.getResources().getDimension(R.dimen.classtable_unit_height)*times+(times-1)*context.getResources().getDimension(R.dimen.ccc_view_height));
        int leftMargin = (int) (DateUtils.getWeekday()>=week?context.getResources().getDimension(R.dimen.classtable_unit_width)*(week-1):(context.getResources().getDimension(R.dimen.classtable_unit_width)*(week-2)+context.getResources().getDimension(R.dimen.classtable_long_unit_width)));
        int topMargin = (int) (context.getResources().getDimension(R.dimen.classtable_unit_height)*(from-1)+(from-1)*context.getResources().getDimension(R.dimen.ccc_view_height));

        return obtain(classUnit.mFrom,classUnit.mTimes,classUnit.mClassName,
                classUnit.mClassAddress,context,classUnit.mColor,width,height,topMargin,leftMargin);
    }

    /**
     * PM认为应该这么设计UI
     * @param classUnit
     * @param context
     * @param containerWidth
     * @param containerHeight
     * @return
     */

    public static TextView obtainPM(ClassUnit classUnit,@NonNull Context context,int containerWidth,int containerHeight){
        init(context);
        int topMargin = (int) (containerHeight/12*(classUnit.mFrom-1));
        int leftMargin = containerWidth/7*(classUnit.mWeek-1);
        return obtain(classUnit.mFrom,classUnit.mTimes,classUnit.mClassName,classUnit.mClassAddress,
                context,classUnit.mColor,containerWidth/7,containerHeight/12*classUnit.mTimes,topMargin,leftMargin);
    }

    private static void init(Context context) {
        if(colorArray==null)
            colorArray = context.getResources().getStringArray(R.array.ClassColorArray);
    }

    public static FrameLayout.LayoutParams obtainParams(ClassUnit classUnit,int containerWidth,int containerHeight){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(0,0);
        params.width = containerWidth/7-1;
        params.height = containerHeight/12*classUnit.mTimes;
        params.leftMargin = containerWidth/7*(classUnit.mWeek-1);
        params.topMargin = containerHeight/12*(classUnit.mFrom-1);
        return params;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static TextView obtainTextView(ClassUnit classUnit, Context context){
        init(context);
        TextView textView = new TextView(context);
        Drawable drawable = context.getDrawable(R.drawable.classtable_unit_circle_coner);
        drawable.setTint(Color.parseColor(colorArray[classUnit.mColor]));
        textView.setBackground(drawable);
        textView.setText(classUnit.mClassName+"@"+classUnit.mClassAddress);
        textView.setTextSize(context.getResources().getDimension(R.dimen.classtable_text_size));
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        return  textView;
    }

}
