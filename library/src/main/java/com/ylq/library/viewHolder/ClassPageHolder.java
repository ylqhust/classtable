package com.ylq.library.viewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.DragEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylq.library.R;
import com.ylq.library.adapter.ClassViewPagerAdapter;
import com.ylq.library.adapter.SelectWeekAdapter;
import com.ylq.library.model.AllClasses;
import com.ylq.library.model.Common;
import com.ylq.library.model.OneWeekClasses;
import com.ylq.library.util.ClickGuard;

/**
 * 课程表首页的Holder
 * Created by apple on 16/7/11.
 */
public class ClassPageHolder extends ClasstableBaseHolder implements View.OnClickListener {

    private RelativeLayout mRelaBack;//返回按钮
    private RelativeLayout mRelaWeek;//选择当前周的按钮
    private TextView mTVCurrentWeek;//显示当前周的TextView
    private RelativeLayout mRelaSetting;//设置按钮
//    private ClassBoxViewPM mClassbox;//放具体课程的view
    private ViewPager mViewPager;
    private AllClasses mAllClasses;
    private int mCurrentWeek;//当前周
    private int mSelectWeek;

    public ClassPageHolder(Context context, AllClasses allClasses) {
        super(R.layout.classtable_class_page, context);
        mAllClasses = allClasses;
        mAllClasses.addFiveEmptyWeek();
        mCurrentWeek = getCurrentWeek();
        mSelectWeek = mCurrentWeek;
        mViewPager.setAdapter(new ClassViewPagerAdapter(getContext(),allClasses));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPageData(position+1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setPageData(mCurrentWeek);
        mViewPager.setCurrentItem(mCurrentWeek-1,true);
    }

    private void setPageData(int weekth) {
        mSelectWeek = weekth;
        OneWeekClasses oneWeekClasses = mAllClasses.getOneWeek(weekth);
        if(oneWeekClasses == null)
            throw new IllegalArgumentException("ClassPageHolder->setPageData->weekth is "+weekth);
        if(oneWeekClasses.getSEASON()== Common.SEASON.SUMMER)
            useSummer();
        else
            useWinter();
    //    mClassbox.setData(oneWeekClasses);
        setMonthAndDateText(oneWeekClasses);
        if(weekth==mCurrentWeek){
            mTVCurrentWeek.setText("第"+weekth+"周");
            mTVCurrentWeek.setTextColor(Color.WHITE);
        }else {
            mTVCurrentWeek.setText("第"+weekth+"周(非本周)");
            mTVCurrentWeek.setTextColor(Color.RED);
        }
    }

    private void setMonthAndDateText(OneWeekClasses oneWeekClasses) {
        findT(R.id.classtable_day_week_tv_day1).setText(oneWeekClasses.getMonthAndDateText(0));
        findT(R.id.classtable_day_week_tv_day2).setText(oneWeekClasses.getMonthAndDateText(1));
        findT(R.id.classtable_day_week_tv_day3).setText(oneWeekClasses.getMonthAndDateText(2));
        findT(R.id.classtable_day_week_tv_day4).setText(oneWeekClasses.getMonthAndDateText(3));
        findT(R.id.classtable_day_week_tv_day5).setText(oneWeekClasses.getMonthAndDateText(4));
        findT(R.id.classtable_day_week_tv_day6).setText(oneWeekClasses.getMonthAndDateText(5));
        findT(R.id.classtable_day_week_tv_day7).setText(oneWeekClasses.getMonthAndDateText(6));
    }

    /**
     * 根据mAllClasses中的数据获取当前周
     * @return
     */
    private int getCurrentWeek() {
        return mAllClasses.getCurrentWeek();
    }


    public ClassPageHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        this.mRelaBack = findR(R.id.classtable_toolbar_rela_back);
        this.mRelaWeek = findR(R.id.classtable_toolbar_week_rela);
        this.mRelaSetting = findR(R.id.classtable_toolbar_setting_rela);
  //      this.mClassbox = (ClassBoxViewPM) findViewById(R.id.classtable_activity_main_container_pm);
        this.mViewPager = (ViewPager) findViewById(R.id.classtable_class_page_viewpager);
        this.mTVCurrentWeek = findT(R.id.classtable_toolbar_tv);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.classtable_toolbar_rela_back) {
            back();
        } else if (id == R.id.classtable_toolbar_setting_rela) {
        } else if (id == R.id.classtable_toolbar_week_rela) {
            holderIn(new SelectWeekViewHolder(getContext(), mAllClasses.getAllWeekCount(), mCurrentWeek, mSelectWeek,new SelectWeekAdapter.SelectWeekCallback() {
                @Override
                public void selectWeek(int weekIndex) {
                    setPageData(weekIndex);
                    mViewPager.setCurrentItem(weekIndex-1,true);
                }
            }));
        }
    }

    @Override
    public void Guard() {
        ClickGuard.guard(this,mRelaBack,mRelaWeek,mRelaSetting);
    }

    @Override
    public void unGurad() {
        ClickGuard.unGuard(mRelaBack,mRelaWeek,mRelaSetting);
  //      mClassbox.finish();
    }


    /**
     * 使用夏令时
     */
    public void useSummer() {
        findT(R.id.classtable_left_time_tv_5).setText("14:30");
        findT(R.id.classtable_left_time_tv_6).setText("15:20");
        findT(R.id.classtable_left_time_tv_7).setText("16:25");
        findT(R.id.classtable_left_time_tv_8).setText("17:15");
        findT(R.id.classtable_left_time_tv_9).setText("19:00");
        findT(R.id.classtable_left_time_tv_10).setText("19:50");
        findT(R.id.classtable_left_time_tv_11).setText("20:45");
        findT(R.id.classtable_left_time_tv_12).setText("21:35");
    }

    /**
     * 使用冬令时
     */
    public void useWinter() {
        findT(R.id.classtable_left_time_tv_5).setText("14:00");
        findT(R.id.classtable_left_time_tv_6).setText("14:50");
        findT(R.id.classtable_left_time_tv_7).setText("15:55");
        findT(R.id.classtable_left_time_tv_8).setText("16:45");
        findT(R.id.classtable_left_time_tv_9).setText("18:30");
        findT(R.id.classtable_left_time_tv_10).setText("19:20");
        findT(R.id.classtable_left_time_tv_11).setText("20:15");
        findT(R.id.classtable_left_time_tv_12).setText("21:05");
    }
}
