package com.ylq.library.classtable.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by apple on 16/7/18.
 */
public class SelectWeekView extends View {

    private Paint mRectPaint;
    private Paint mTextPaint;
    private Paint mWhiteTextPaint;
    private Paint mLinePaint;
    private Paint mBlueBlockPaint;

    private static final float STROKE_WIDTH = 1;
    private static final int STROKE_COLOR = Color.parseColor("#d1d1d1");
    private static final int BLUE_BLOCK_COLOR = Color.parseColor("#3b95e8");
    private static final int TEXT_SIZE = (int) (Resources.getSystem().getDisplayMetrics().density*13);
    private static final String[] TEXTS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"
            , "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "ALL"};
    private float mTextX[] = new float[5];
    private float mTextY[] = new float[5];

    private RectF mRectF = new RectF(0, 0, 0, 0);
    private RectF[] mRectFs = new RectF[25];

    private float[] mStartX = new float[8];
    private float[] mStartY = new float[8];
    private float[] mEndX = new float[8];
    private float[] mEndY = new float[8];
    private float UNITX, UNITY;
    private int mLastIndex = -1;
    private byte[] CHECKED = new byte[25];


    public SelectWeekView(Context context) {
        super(context);
        init();
    }

    public SelectWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public SelectWeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectWeekView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        int w = MeasureSpec.getSize(width);
        int h = MeasureSpec.getSize(height);
        UNITX = w / 5;
        UNITY = h / 5;
        mRectF.right = w - 1;
        mRectF.bottom = h - 1;
        initXYFloatArray(w, h);
        initTextCoor();
        initRectFs();
    }

    private void initRectFs() {
        for (int i = 0; i < 25; i++) {
            int x = i % 5;
            int y = i / 5;
            mRectFs[i] = new RectF();
            mRectFs[i].left = x * UNITX+1;
            mRectFs[i].top = y * UNITY+1;
            mRectFs[i].right = (x + 1) * UNITX-1;
            mRectFs[i].bottom = (y + 1) * UNITY-1;
        }
    }

    private void initTextCoor() {
        for (int i = 0; i < 5; i++) {
            mTextX[i] = i * UNITX + UNITX / 2;
            mTextY[i] = i * UNITY + UNITY / 2 + TEXT_SIZE / 2;
        }
    }

    private void initXYFloatArray(int w, int h) {
        for (int i = 0; i < 4; i++) {
            mStartX[i] = (i + 1) * (w / 5);
            mEndX[i] = mStartX[i];
            mEndX[i + 4] = w;
            mStartY[i + 4] = (i + 1) * (h / 5);
            mEndY[i] = h;
            mEndY[i + 4] = mStartY[i + 4];
        }
    }


    private void init() {
        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(STROKE_WIDTH);
        mRectPaint.setColor(STROKE_COLOR);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(TEXT_SIZE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mBlueBlockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlueBlockPaint.setStyle(Paint.Style.FILL);
        mBlueBlockPaint.setColor(BLUE_BLOCK_COLOR);

        mWhiteTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWhiteTextPaint.setStyle(Paint.Style.FILL);
        mWhiteTextPaint.setColor(Color.WHITE);
        mWhiteTextPaint.setTextSize(TEXT_SIZE);
        mWhiteTextPaint.setTextAlign(Paint.Align.CENTER);


        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(STROKE_COLOR);
        mLinePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(mRectF, mRectPaint);
        for (int i = 0; i < 8; i++)
            canvas.drawLine(mStartX[i], mStartY[i], mEndX[i], mEndY[i], mLinePaint);

        for (int i = 0; i < 25; i++)
            if (CHECKED[i] == 0)
                canvas.drawText(TEXTS[i], mTextX[i % 5], mTextY[(int) (i / 5)], mTextPaint);
            else {
                canvas.drawRect(mRectFs[i], mBlueBlockPaint);
                canvas.drawText(TEXTS[i], mTextX[i % 5], mTextY[(int) (i / 5)], mWhiteTextPaint);
            }
    }


    int indexDown;
    boolean isOutOf24;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getX()>UNITX*5 || event.getX()<0)
            return false;
        if(event.getY()>UNITY*5 || event.getY()<0)
            return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                indexDown = getIndex(event.getX(), event.getY());
                mLastIndex = indexDown;
                if (indexDown != 24) {
                    CHECKED[indexDown] = (byte) (CHECKED[indexDown] == 0 ? 1 : 0);
                    if (selectAll())
                        CHECKED[24] = 1;
                    else
                        CHECKED[24] = 0;
                    invalidate();
                } else
                    isOutOf24 = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int index = getIndex(event.getX(), event.getY());
                if (indexDown == 24 || index==24) {
                    if (index != 24)
                        isOutOf24 = true;
                    return true;
                }

                if (index != mLastIndex) {
                    mLastIndex = index;
                    CHECKED[index]= (byte) (CHECKED[index]==0?1:0);
                    if(selectAll())
                        CHECKED[24]=1;
                    else
                        CHECKED[24]=0;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                int index1 = getIndex(event.getX(),event.getY());
                if(indexDown==24 && (!isOutOf24) && index1==24){
                    //此处判定ALL是被点击的
                    CHECKED[24]= (byte) (CHECKED[24]==0?1:0);
                    for(int i=0;i<24;i++)
                        CHECKED[i] = CHECKED[24];
                    invalidate();
                }
                break;
        }
        return true;
    }

    private boolean selectAll() {
        for (int i = 0; i < 24; i++)
            if (CHECKED[i] == 0)
                return false;
        return true;
    }

    public int getIndex(float x, float y) {
        int X = (int) (x / UNITX);
        int Y = (int) (y / UNITY);
        return  Y*5+X;
    }

    public byte[] getChecked() {
        return CHECKED;
    }

    public void setData(byte[] weekChecked) {
        if(weekChecked!=null)
            CHECKED = weekChecked;
    }
}
