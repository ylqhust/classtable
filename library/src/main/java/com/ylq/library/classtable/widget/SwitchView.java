package com.ylq.library.classtable.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ylq.library.R;

/**
 * Created by ylq on 16/7/28.
 */
public class SwitchView extends View {

    private int mOnColor, mOffColor, mOnTextSize, mOffTextSize, mOnTextColor, mOffTextColor;
    private String mLeftText, mRightText;
    private Paint mBackgroundPaint, mOnAraePaint, mOnTextPaint, mOffTextPaint;
    private int width, height;
    private RoundRect mBackgroundRR, mOnAreaRR;
    private int mOnArea;

    public SwitchView(Context context) {
        this(context, null);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SwitchView);
        mOnColor = array.getColor(R.styleable.SwitchView_onColor, 0xff87bbff);
        mOffColor = array.getColor(R.styleable.SwitchView_offColor, 0xffe8f5fc);
        mOnTextSize = array.getDimensionPixelSize(R.styleable.SwitchView_onTextSize, 30);
        mOffTextSize = array.getDimensionPixelSize(R.styleable.SwitchView_offTextSize, 30);
        mOnTextColor = array.getColor(R.styleable.SwitchView_onTextColor, 0xffffffff);
        mOffTextColor = array.getColor(R.styleable.SwitchView_offTextColor, 0xff918e8e);
        mLeftText = array.getString(R.styleable.SwitchView_leftText);
        mLeftText = mLeftText == null ? "开" : mLeftText;
        mRightText = array.getString(R.styleable.SwitchView_rightText);
        mRightText = mRightText == null ? "关" : mRightText;
        mOnArea = array.getInt(R.styleable.SwitchView_onArea, 0);
        array.recycle();
        initPaint();
        mBackgroundRR = new RoundRect(mBackgroundPaint, new RectF(0, 0, width, height), height / 6, height / 6);
        mOnAreaRR = new RoundRect(mOnAraePaint, new RectF(0, 0, 0, 0), height / 6, width / 6);
    }

    private void initPaint() {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mOffColor);

        mOnAraePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOnAraePaint.setStyle(Paint.Style.FILL);
        mOnAraePaint.setColor(mOnColor);

        mOnTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOnTextPaint.setStyle(Paint.Style.FILL);
        mOnTextPaint.setColor(mOnTextColor);
        mOnTextPaint.setTextSize(mOnTextSize);
        mOnTextPaint.setTextAlign(Paint.Align.CENTER);

        mOffTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOffTextPaint.setStyle(Paint.Style.FILL);
        mOffTextPaint.setColor(mOffTextColor);
        mOffTextPaint.setTextSize(mOffTextSize);
        mOffTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wm = MeasureSpec.getMode(widthMeasureSpec);
        int hm = MeasureSpec.getMode(heightMeasureSpec);
        int ws = MeasureSpec.getSize(widthMeasureSpec);
        int hs = MeasureSpec.getSize(heightMeasureSpec);
        if (wm == MeasureSpec.AT_MOST && hm == MeasureSpec.AT_MOST)
            setMeasuredDimension(getDW(), getDH());
        else if (wm == MeasureSpec.AT_MOST)
            setMeasuredDimension(getDW(), hs);
        else if (hm == MeasureSpec.AT_MOST)
            setMeasuredDimension(ws, getDH());
        else
            setMeasuredDimension(ws, hs);

    }

    private int getDH() {
        return 2 * Math.max(mOnTextSize, mOffTextSize);
    }

    private int getDW() {
        int maxTextSize = Math.max(mOnTextSize, mOffTextSize);
        return (mLeftText.length() + mRightText.length() + 3) * maxTextSize;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        mBackgroundRR.rectF.right = width;
        mBackgroundRR.rectF.bottom = height;
        mBackgroundRR.rx = height / 3;
        mBackgroundRR.ry = height / 3;
        mOnAreaRR.rx = height / 3;
        mOnAreaRR.ry = height / 3;
        setOnAreaRectF(mOnAreaRR.rectF);
    }

    private void setOnAreaRectF(RectF rectF) {
        switch (mOnArea) {
            case 0: //left on
                rectF.left = 0;
                rectF.top = 0;
                rectF.right = width / 2;
                rectF.bottom = height;
                break;
            case 1: // right on
                rectF.left = width / 2;
                rectF.top = 0;
                rectF.right = width;
                rectF.bottom = height;
                break;
            default:
                throw new IllegalArgumentException("SwithcView->onArea argument is illegal:" + mOnArea);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(mBackgroundRR.rectF,mBackgroundRR.rx,mBackgroundRR.ry,mBackgroundRR.paint);
        canvas.drawRoundRect(mOnAreaRR.rectF,mOnAreaRR.rx,mOnAreaRR.ry,mOnAreaRR.paint);
        if(mOnArea==0){
            canvas.drawText(mLeftText,width/4,height/2+mOnTextSize*2/5,mOnTextPaint);
            canvas.drawText(mRightText,width*3/4,height/2+mOffTextSize*2/5,mOffTextPaint);
        }else if(mOnArea==1){
            canvas.drawText(mLeftText,width/4,height/2+mOffTextSize*2/5,mOffTextPaint);
            canvas.drawText(mRightText,width*3/4,height/2+mOnTextSize*2/5,mOnTextPaint);
        }else
        throw new IllegalArgumentException("SwithcView->onArea argument is illegal:" + mOnArea);
    }


    private class RoundRect {
        RectF rectF;
        float rx, ry;
        Paint paint;

        public RoundRect(Paint paint, RectF rectF, float rx, float ry) {
            this.paint = paint;
            this.rectF = rectF;
            this.rx = rx;
            this.ry = ry;
        }
    }

}
