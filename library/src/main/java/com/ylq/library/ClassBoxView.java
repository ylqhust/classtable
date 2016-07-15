package com.ylq.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.ylq.library.model.ClassUnit;
import com.ylq.library.widget.TimeIndicator;

import java.util.List;

/**
 * Created by apple on 16/7/9.
 */
public class ClassBoxView extends FrameLayout {

    private List<ClassUnit> mClassUnits;
    public ClassBoxView(Context context) {
        super(context);
        init(context);
    }

    public ClassBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClassBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClassBoxView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.classtable_classes_container,this);
    }

    public void setData(@NonNull List<ClassUnit> classUnits) {
        this.mClassUnits = classUnits;
    }

    @Override
    protected void onMeasure(int w,int h){
        super.onMeasure(w,h);
        showClasses();
        showTimeIndicator();
    }

    private void showTimeIndicator() {
        TimeIndicator timeIndicator = new TimeIndicator(getContext());
        timeIndicator.setLayoutParams(new FrameLayout.LayoutParams(getMeasuredWidth(),getContext().getResources().getDimensionPixelSize(R.dimen.timeIndicator_height)));
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) timeIndicator.getLayoutParams();
        marginLayoutParams.topMargin = getTopMargin();
        timeIndicator.setLayoutParams(marginLayoutParams);
        addView(timeIndicator);
    }

    /**
     * 根据当前时间获取时间指示器和顶部的距离
     * @return
     */
    private int getTopMargin() {
       return 400;
    }

    private void showClasses() {
        System.out.println(getMeasuredWidth()+"--"+getMeasuredHeight());
        for(ClassUnit classUnit:mClassUnits)
            addView(ClassUnitFactory.obtainPM(classUnit,getContext(),getMeasuredWidth(),getMeasuredHeight()));
    }


}
