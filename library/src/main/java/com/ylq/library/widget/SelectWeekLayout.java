package com.ylq.library.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by apple on 16/7/18.
 */
public class SelectWeekLayout extends FrameLayout {

    public static final int TOP_TRIANGLE_HEIGHT = 20;
    public static final int TOP_TRIANGLE_BOTTOM_LINE_LENGTH = 40;
    public static final int CIRCLE_RADIUS = 20;
    public static final int TOP_TRIANGLE_COLOR = Color.parseColor("#d0fafeff");
    private Paint mPaint;
    private Path mPath;

    public SelectWeekLayout(Context context) {
        super(context);
        initPaint();
    }

    public SelectWeekLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SelectWeekLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectWeekLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(TOP_TRIANGLE_COLOR);
    }


    private void initPath(int w, int h){
        mPath = new Path();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPath.moveTo(0,TOP_TRIANGLE_HEIGHT+CIRCLE_RADIUS);
            mPath.lineTo(CIRCLE_RADIUS,TOP_TRIANGLE_HEIGHT);
            mPath.addArc(0,TOP_TRIANGLE_HEIGHT,CIRCLE_RADIUS*2,TOP_TRIANGLE_HEIGHT+CIRCLE_RADIUS*2,180,90);
            mPath.lineTo(w/2-TOP_TRIANGLE_BOTTOM_LINE_LENGTH/2,TOP_TRIANGLE_HEIGHT);
            mPath.lineTo(w/2,0);
            mPath.lineTo(w/2+TOP_TRIANGLE_BOTTOM_LINE_LENGTH/2,TOP_TRIANGLE_HEIGHT);
            mPath.lineTo(w-CIRCLE_RADIUS,TOP_TRIANGLE_HEIGHT);
            mPath.lineTo(w,TOP_TRIANGLE_HEIGHT+CIRCLE_RADIUS);
            mPath.addArc(w-CIRCLE_RADIUS*2,TOP_TRIANGLE_HEIGHT,w,TOP_TRIANGLE_HEIGHT+CIRCLE_RADIUS*2,270,90);
            mPath.lineTo(w,h-CIRCLE_RADIUS);
            mPath.lineTo(w-CIRCLE_RADIUS,h);
            mPath.addArc(w-CIRCLE_RADIUS*2,h-CIRCLE_RADIUS*2,w,h,0,90);
            mPath.lineTo(CIRCLE_RADIUS,h);
            mPath.lineTo(0,h-CIRCLE_RADIUS);
            mPath.addArc(0,h-CIRCLE_RADIUS*2,CIRCLE_RADIUS*2,h,90,90);
            mPath.lineTo(0,TOP_TRIANGLE_HEIGHT+CIRCLE_RADIUS);
            mPath.lineTo(w-CIRCLE_RADIUS,TOP_TRIANGLE_HEIGHT+CIRCLE_RADIUS);
            mPath.lineTo(w-CIRCLE_RADIUS,h-CIRCLE_RADIUS);
        }else {
            mPath.moveTo(0,TOP_TRIANGLE_HEIGHT);
            mPath.lineTo(w/2-TOP_TRIANGLE_BOTTOM_LINE_LENGTH/2,TOP_TRIANGLE_HEIGHT);
            mPath.lineTo(w/2,0);
            mPath.lineTo(w/2+TOP_TRIANGLE_BOTTOM_LINE_LENGTH/2,TOP_TRIANGLE_HEIGHT);
            mPath.lineTo(w,TOP_TRIANGLE_HEIGHT);
            mPath.lineTo(w,h);
            mPath.lineTo(0,h);
            mPath.lineTo(0,TOP_TRIANGLE_HEIGHT);
        }

    }

    @Override
    protected void onMeasure(int width,int height){
        int w = MeasureSpec.getSize(width);
        int h = MeasureSpec.getSize(height);
        initPath(w,h);
        super.onMeasure(width,height);
    }
    @Override
    protected void onLayout(boolean changed,int l,int t,int r,int b){
        if(getChildCount()==0)
            return;
        View view = getChildAt(0);
        if(!(view instanceof ListView))
            return;
        ListView listView = (ListView) view;
        int paddingLeft = getPaddingLeft();
        int paddingTop  =getPaddingTop();
        int w = listView.getMeasuredWidth();
        int h = listView.getMeasuredHeight();
        listView.layout(paddingLeft,paddingTop+TOP_TRIANGLE_HEIGHT,paddingLeft+w,h+paddingTop+TOP_TRIANGLE_HEIGHT);
    }


    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawPath(mPath,mPaint);
    }
}
