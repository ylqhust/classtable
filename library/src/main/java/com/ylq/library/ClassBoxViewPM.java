package com.ylq.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ylq.library.model.ClassUnit;
import com.ylq.library.model.Common;
import com.ylq.library.model.OneWeekClasses;
import com.ylq.library.util.DimenUtils;
import com.ylq.library.widget.TimeIndicator;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apple on 16/7/11.
 */
public class ClassBoxViewPM extends FrameLayout {
    private List<ClassUnit> mClassUnits;
    private OneWeekClasses mOneWeek;
    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            showTimeIndicator();
            return true;
        }
    });

    private Timer mTimer;

    public ClassBoxViewPM(Context context) {
        super(context);
        init(context);
    }


    public ClassBoxViewPM(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClassBoxViewPM(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClassBoxViewPM(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    public void setData(@NonNull OneWeekClasses oneWeekClasses) {
        this.mOneWeek = oneWeekClasses;
        this.mClassUnits = oneWeekClasses.getAllUnit();
        showClasses();
        showTimeIndicator();
    }


    private synchronized void  showTimeIndicator() {
        removeTimeIndicatorView();
        TimeIndicator timeIndicator = new TimeIndicator(getContext());
        LayoutParams params = new LayoutParams(0, 0);
        params.width = getW();
        params.height = 20;
        params.topMargin = getIndicatorTopMargin();
        params.leftMargin = 0;
        addView(timeIndicator, params);
    }

    private void removeTimeIndicatorView() {
        for (int i = 0; i < getChildCount(); i++)
            if (getChildAt(i) instanceof TimeIndicator) {
                removeView(getChildAt(i));
                i--;
            }
    }

    private int getIndicatorTopMargin() {
        if (!mOneWeek.isContainToday())
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
            return getH() / 12 * (allMinute - time + 45) / 45;
        time += 10;
        if (allMinute < time)
            return getH() / 12;
        time += 45;
        if (allMinute < time)
            return getH() / 12 + getH() / 12 * (allMinute - time + 45) / 45;
        time = 10 * 60 + 10;
        if (allMinute < time)
            return getH() / 12 * 2;
        time += 45;
        if (allMinute < time)
            return getH() / 12 * 2 + getH() / 12 * (allMinute - time + 45) / 45;
        time += 10;
        if (allMinute < time)
            return getH() / 12 * 3;
        time += 45;
        if (allMinute < time)
            return getH() / 12 * 3 + getH() / 12 * (allMinute - time + 45) / 45;
        int noonBegin, evenBegin;
        if (mOneWeek.getSEASON() == Common.SEASON.SUMMER) {
            noonBegin = 14 * 60 + 30;
            evenBegin = 19 * 60;
        } else {
            noonBegin = 14 * 60;
            evenBegin = 18 * 60 + 30;
        }
        time = noonBegin;
        if (allMinute < time)
            return getH() / 12 * 4;
        time += 45;
        if (allMinute < time)
            return getH() / 12 * 4 + getH() / 12 * (allMinute - time + 45) / 45;
        time += 5;
        if (allMinute < time)
            return getH() / 12 * 5;
        time += 45;
        if (allMinute < time)
            return getH() / 12 * 5 + getH() / 12 * (allMinute - time + 45) / 45;
        time += 20;
        if (allMinute < time)
            return getH() / 12 * 6;
        time += 45;
        if (allMinute < time)
            return getH() / 12 * 6 + getH() / 12 * (allMinute - time + 45) / 45;
        time += 5;
        if (allMinute < time)
            return getH() / 12 * 7;
        time += 45;
        if (allMinute < time)
            return getH() / 12 * 7 + getH() / 12 * (allMinute - time + 45) / 45;
        time = evenBegin;
        if (allMinute < time)
            return getH() / 12 * 8;
        time += 45;
        if (allMinute < time)
            return getH() / 12 * 8 + getH() / 12 * (allMinute - time + 45) / 45;
        time += 5;
        if (allMinute < time)
            return getH() / 12 * 9;
        time += 45;
        if (allMinute < time)
            return getH() / 12 * 9 + getH() / 12 * (allMinute - time + 45) / 45;
        time += 10;
        if (allMinute < time)
            return getH() / 12 * 10;
        time += 45;
        if (allMinute < time)
            return getH() / 12 * 10 + getH() / 12 * (allMinute - time + 45) / 45;
        time += 5;
        if (allMinute < time)
            return getH() / 12 * 11;
        time += 45;
        if (allMinute < time)
            return getH() / 12 * 11 + getH() / 12 * (allMinute - time + 45) / 45;
        return getH();
    }

    private void showClasses() {
        removeAllTextView();
        if (mClassUnits == null)
            return;
        int width = getW();
        int height = getH();
        System.out.println(width + " " + height);
        for (ClassUnit classUnit : mClassUnits)
            addView(ClassUnitFactory.obtainTextView(classUnit, getContext()), ClassUnitFactory.obtainParams(classUnit, width, height));

    }

    private int getH() {
        int h = DimenUtils.getWindowHeight();
        int statubarheight = DimenUtils.getStatuBarHeight();
        int toolbarheight = DimenUtils.getDimensionPixelSize(getContext(), R.dimen.classtable_class_page_toolbar_height);
        int dayWeekheight = DimenUtils.getDimensionPixelSize(getContext(), R.dimen.classtable_day_week_height);
        return h - statubarheight - toolbarheight - dayWeekheight - 30;
    }

    private int getW() {
        int w = DimenUtils.getWindowWidth();
        int leftTimeWidth = DimenUtils.getDimensionPixelSize(getContext(), R.dimen.classtable_left_time_width);
        return w - leftTimeWidth;
    }

    private void removeAllTextView() {
        int i = 0;
        while (i < getChildCount()) {
            if (getChildAt(i) instanceof TextView)
                removeView(getChildAt(i));
            else i++;
        }
    }

    public void finish() {
        mTimer.cancel();
    }
}

