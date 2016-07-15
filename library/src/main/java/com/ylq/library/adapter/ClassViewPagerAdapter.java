package com.ylq.library.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ylq.library.ClassBoxViewPM;
import com.ylq.library.model.AllClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/7/15.
 */
public class ClassViewPagerAdapter extends PagerAdapter {
    private List<View> mListViews = new ArrayList<>();

    public ClassViewPagerAdapter(Context context, AllClasses allClasses){
        LayoutInflater inflater = LayoutInflater.from(context);
        for(int i=1;i<=allClasses.getAllWeekCount();i++){
            ClassBoxViewPM view = new ClassBoxViewPM(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setData(allClasses.getOneWeek(i));
            mListViews.add(view);
        }
    }
    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(mListViews.get(position));
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mListViews.get(position), 0);
        return mListViews.get(position);
    }
}
