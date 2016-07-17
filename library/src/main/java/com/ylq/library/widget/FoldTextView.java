package com.ylq.library.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by apple on 16/7/16.
 */
public class FoldTextView extends TextView {

    private boolean mFold = false;//默认不是折叠的
    private boolean mbelongTheWeek = true;
    private Drawable mDrawable_YLQ;
    private static final int FOLD_AREA_RIGHT_TOP_COLOR = Color.parseColor("#ffe2ecee");
    private static final int FOLD_AREA_LEFT_BOTTOM_COLOR = Color.parseColor("#50e2ecee");
    private static final int TOTAL_BACKGROUND_COLOR = Color.parseColor("#e0d7ecef");
    private Paint mPaintR;
    private Paint mPaintL;
    private Paint mPaintT;
    private Path mPathR;
    private Path mPathL;
    private Path mPathT;

    public FoldTextView(Context context) {
        super(context);
        init();
    }

    public FoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public FoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FoldTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        mPaintR = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintR.setColor(FOLD_AREA_RIGHT_TOP_COLOR);
        mPaintR.setStyle(Paint.Style.FILL);
        mPaintL = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintL.setColor(FOLD_AREA_LEFT_BOTTOM_COLOR);
        mPaintL.setStyle(Paint.Style.FILL);
        mPaintT = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintT.setColor(TOTAL_BACKGROUND_COLOR);
        mPaintT.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        initPath();
    }

    private void initPath() {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mPathR = new Path();
        mPathL = new Path();
        mPathT = new Path();
        mPathR.moveTo(width * 4 / 5, 0);
        mPathR.lineTo(width, 0);
        mPathR.lineTo(width, width / 5);
        mPathR.lineTo(width * 4 / 5, 0);
        mPathL.moveTo(width * 4 / 5, 0);
        mPathL.lineTo(width, width / 5);
        mPathL.lineTo(width * 4 / 5, width / 5);
        mPathL.lineTo(width * 4 / 5, 0);
        mPathT.moveTo(0, 0);
        mPathT.lineTo(width, 0);
        mPathT.lineTo(width, height);
        mPathT.lineTo(0, height);
        mPathT.lineTo(0, 0);
    }

    public void setFold(boolean ifFold) {
        this.mFold = ifFold;
        invalidate();
    }

    /**
     * 设置这节课是否属于本周，如果不属于，就会将这个课调整为半透明显示
     * @param ifBelong
     */
    public void setBelongTheWeek(boolean ifBelong) {
        this.mbelongTheWeek = ifBelong;
        if(ifBelong){
            setTextColor(Color.WHITE);
            if(mDrawable_YLQ!=null)
                setBackground(mDrawable_YLQ);
        }else {
            mDrawable_YLQ = getBackground();
            setBackground(null);
            setTextColor(Color.parseColor("#99a9a6"));
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!mbelongTheWeek){
            canvas.drawPath(mPathT,mPaintT);
        }
        super.onDraw(canvas);
        if (mFold) {
            canvas.drawPath(mPathR, mPaintR);
            canvas.drawPath(mPathL, mPaintL);
        }
    }

}
