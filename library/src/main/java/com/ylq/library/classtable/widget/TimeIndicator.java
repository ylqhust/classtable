package com.ylq.library.classtable.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.ylq.library.R;

/**
 * Created by apple on 16/7/10.
 */
public class TimeIndicator extends View {

    private int mIndicatorColor = Color.parseColor("#f57c00");
    private Path mPath;
    private Paint mPaint;
    public TimeIndicator(Context context) {
        super(context);
    }

    public TimeIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TimeIndicator,
                0,0);
        try {
            mIndicatorColor = array.getColor(R.styleable.TimeIndicator_timeIndicatorColor,
                    Color.parseColor("#f57c00"));
        }finally {
            array.recycle();
        }
    }

    public TimeIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimeIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        initPath();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mIndicatorColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initPath() {
        mPath = new Path();
        mPath.moveTo(0,0);
        mPath.lineTo(getMeasuredWidth(),0);
        mPath.lineTo(getMeasuredWidth(),4);
        mPath.lineTo(getMeasuredWidth()/40,4);
        mPath.lineTo(0,getMeasuredHeight());
        /**
        float tri_x = getMeasuredWidth()/40;//三角形的中间那个顶点的x坐标
        float tri_y = getMeasuredHeight()/2-2;//三角形中间那个顶点的y坐标
        mPath.lineTo(tri_x,tri_y);
        mPath.lineTo(getMeasuredWidth(),tri_y);
        mPath.lineTo(getMeasuredWidth(),tri_y+4);
        mPath.lineTo(tri_x,tri_y+4);
        mPath.lineTo(0,getMeasuredHeight());
         **/
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }
}
