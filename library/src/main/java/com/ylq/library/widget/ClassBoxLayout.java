package com.ylq.library.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ylq.library.R;
import com.ylq.library.model.ClassUnit;
import com.ylq.library.model.Common;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apple on 16/7/16.
 */
public class ClassBoxLayout extends FrameLayout {

    private ClassBoxAdapter mAdapter;

    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            requestLayout();
            return true;
        }
    });

    private Timer mTimer;

    public ClassBoxLayout(Context context) {
        super(context);
        init(context);
    }


    public ClassBoxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClassBoxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClassBoxLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.classtable_classes_container_pm, this);
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                mHandler.sendMessage(msg);
            }
        },0,1000*60*2);
    }


    public void setAdapter(ClassBoxAdapter adapter) {
        if(this.mAdapter!=null)
            this.mAdapter.unGurad();
        this.mAdapter = adapter;
        showData();
    }

    @Override
    protected void onMeasure(int width, int height) {
        int w = MeasureSpec.getSize(width);
        int h = MeasureSpec.getSize(height);
        setMeasuredDimension(w, h);
        View v = getChildAt(0);
        v.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
        if (mAdapter == null)
            return;
        int count = getChildCount();
        int unitWidth = w / 7;
        int unitHeight = h / 12;
        for (int i = 1; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof TimeIndicator)
                child.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(20, MeasureSpec.EXACTLY));
            else {
                LayoutParams params = mAdapter.getParams(i - 1);
                int yStart = params.yStart;
                int yEnd = params.yEnd;
                child.measure(MeasureSpec.makeMeasureSpec(unitWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec((yEnd - yStart + 1) * unitHeight, MeasureSpec.EXACTLY));
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int height = b - t;
        int unitWidth = width / 7;
        int unitHeight = height / 12;
        View v = getChildAt(0);
        v.layout(0, 0, width, height);
        if (mAdapter == null)
            return;
        int count = getChildCount();
        for (int i = 1; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof TimeIndicator) {
                ViewGroup.LayoutParams params = child.getLayoutParams();
                int topMargin = getIndicatorTopMargin(height);
                child.layout(0, topMargin, width, topMargin + 20);
            } else {
                LayoutParams params = mAdapter.getParams(i - 1);
                int x = params.x;
                int yStart = params.yStart;
                int yEnd = params.yEnd;
                child.layout(x * unitWidth, yStart * unitHeight, (x + 1) * unitWidth-1, (yEnd + 1) * unitHeight-1);
            }
        }
    }

    private int getIndicatorTopMargin(int height) {
        if (!mAdapter.isContainToday())
            return 0;
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int allMinute = hour * 60 + minute;
        int time = 8 * 60;
        if (allMinute < time)
            return 0;
        time += 45;
        if (allMinute < time)
            return height / 12 * (allMinute - time + 45) / 45;
        time += 10;
        if (allMinute < time)
            return height / 12;
        time += 45;
        if (allMinute < time)
            return height / 12 + height / 12 * (allMinute - time + 45) / 45;
        time = 10 * 60 + 10;
        if (allMinute < time)
            return height / 12 * 2;
        time += 45;
        if (allMinute < time)
            return height / 12 * 2 + height / 12 * (allMinute - time + 45) / 45;
        time += 10;
        if (allMinute < time)
            return height / 12 * 3;
        time += 45;
        if (allMinute < time)
            return height / 12 * 3 + height / 12 * (allMinute - time + 45) / 45;
        int noonBegin, evenBegin;
        if (mAdapter.getSEASON() == Common.SEASON.SUMMER) {
            noonBegin = 14 * 60 + 30;
            evenBegin = 19 * 60;
        } else {
            noonBegin = 14 * 60;
            evenBegin = 18 * 60 + 30;
        }
        time = noonBegin;
        if (allMinute < time)
            return height / 12 * 4;
        time += 45;
        if (allMinute < time)
            return height / 12 * 4 + height / 12 * (allMinute - time + 45) / 45;
        time += 5;
        if (allMinute < time)
            return height / 12 * 5;
        time += 45;
        if (allMinute < time)
            return height / 12 * 5 + height / 12 * (allMinute - time + 45) / 45;
        time += 20;
        if (allMinute < time)
            return height / 12 * 6;
        time += 45;
        if (allMinute < time)
            return height / 12 * 6 + height / 12 * (allMinute - time + 45) / 45;
        time += 5;
        if (allMinute < time)
            return height / 12 * 7;
        time += 45;
        if (allMinute < time)
            return height / 12 * 7 + height / 12 * (allMinute - time + 45) / 45;
        time = evenBegin;
        if (allMinute < time)
            return height / 12 * 8;
        time += 45;
        if (allMinute < time)
            return height / 12 * 8 + height / 12 * (allMinute - time + 45) / 45;
        time += 5;
        if (allMinute < time)
            return height / 12 * 9;
        time += 45;
        if (allMinute < time)
            return height / 12 * 9 + height / 12 * (allMinute - time + 45) / 45;
        time += 10;
        if (allMinute < time)
            return height / 12 * 10;
        time += 45;
        if (allMinute < time)
            return height / 12 * 10 + height / 12 * (allMinute - time + 45) / 45;
        time += 5;
        if (allMinute < time)
            return height / 12 * 11;
        time += 45;
        if (allMinute < time)
            return height / 12 * 11 + height / 12 * (allMinute - time + 45) / 45;
        return height;
    }

    private void showData() {
        if (mAdapter == null)
            return;
        removeViews(1, getChildCount() - 1);
        for (int i = 0; i < mAdapter.getCount(); i++)
            addView(mAdapter.getView(i));
        addView(new TimeIndicator(getContext()));
    }


    public abstract static class ClassBoxAdapter {
        public abstract int getCount();

        public abstract View getView(int position);

        public abstract boolean isContainToday();

        public abstract Common.SEASON getSEASON();
        public abstract void unGurad();

        public FoldTextView origin(Context context, String s, int color) {
            FoldTextView tx = new FoldTextView(context);
            tx.setText(s);
            tx.setTextColor(Color.WHITE);
            tx.setGravity(Gravity.CENTER);
            Drawable drawable = context.getResources().getDrawable(R.drawable.classtable_unit_circle_coner);
            drawable.setColorFilter(color, PorterDuff.Mode.ADD);
            tx.setBackground(drawable);
            return tx;
        }

        public abstract LayoutParams getParams(int position);
    }

    public static class LayoutParams {
        public int x;
        public int yStart;
        public int yEnd;

        public LayoutParams(@IntRange(from = 0,to = 6) int x,
                            @IntRange(from = 0,to = 11) int yStart,
                            @IntRange(from = 0,to = 11) int yEnd) {
            this.x = x;
            this.yStart = yStart;
            this.yEnd = yEnd;
        }
    }

    public  static interface OnItemClickListener{
        public void onItemClick(@NonNull ClassUnit topLayer, List<ClassUnit> bottomLayer);
        //bottomLayer 或许存在，或许也没有，如果没有，就可以直接进入课程编辑界面
    }

    public void finish(){
        if(mTimer!=null)
            mTimer.cancel();
    }

}
