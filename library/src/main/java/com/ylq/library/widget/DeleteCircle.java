package com.ylq.library.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.ylq.library.R;

/**
 * Created by ylq on 16/7/26.
 */
public class DeleteCircle extends TextView{
    private static final float DENSITY = (int) Resources.getSystem().getDisplayMetrics().density;
    private static final int DEFAULT_COLOR = Color.parseColor("#ff336a");
    private static final int DEFAULT_WIDTH = (int) (40 * DENSITY);
    private static final int DEFAULT_HEIGHT = (int) (40 * DENSITY);

    private int mColor = DEFAULT_COLOR;
    private Paint mCirclePaint,mLinePaint;
    private float x,y,radius;
    private Path mLinePath;

    public DeleteCircle(Context context) {
        this(context, null);
    }

    public DeleteCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DeleteCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            TypedArray array = context.getResources().obtainAttributes(attrs, R.styleable.DeleteCircle);
            mColor = array.getColor(R.styleable.DeleteCircle_deleteCirclecolor, DEFAULT_COLOR);
            array.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mColor);
        mLinePaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST)
            setMeasuredDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        else if (widthMode == MeasureSpec.AT_MOST)
            setMeasuredDimension(DEFAULT_WIDTH, heightSize);
        else if (heightMode == MeasureSpec.AT_MOST)
            setMeasuredDimension(widthSize, DEFAULT_HEIGHT);
        else
            setMeasuredDimension(widthSize, heightSize);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        radius = Math.min(width,height)/2;
        radius = radius*5/6;
        x=width/2;
        y=height/2;
        mCirclePaint.setStrokeWidth(radius/6);

        mLinePath = new Path();
        mLinePath.moveTo(x-radius*2/3,y-radius/12);
        mLinePath.lineTo(x+radius*2/3,y-radius/12);
        mLinePath.lineTo(x+radius*2/3,y+radius/12);
        mLinePath.lineTo(x-radius*2/3,y+radius/12);
        mLinePath.lineTo(x-radius*2/3,y-radius/12);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(x,y,radius,mCirclePaint);
        canvas.drawPath(mLinePath,mLinePaint);
    }
}
