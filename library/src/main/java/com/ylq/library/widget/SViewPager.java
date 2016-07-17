package com.ylq.library.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by apple on 16/7/17.
 * http://blog.csdn.net/mydreamongo/article/details/30468543
 */
public class SViewPager extends ViewPager {
    public SViewPager(Context context) {
        super(context);
    }

    public SViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float preX;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        boolean res = super.onInterceptTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            preX = event.getX();
        } else {
            if( Math.abs(event.getX() - preX)> 4 ) {
                return true;
            } else {
                preX = event.getX();
            }
        }
        return res;
    }
}
