package com.ylq.library.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ylq.library.model.AllClasses;
import com.ylq.library.model.ClassUnit;
import com.ylq.library.widget.ClassBoxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/7/17.
 */
public class ClassViewPagerAdapter2 extends PagerAdapter {
    private List<ClassBoxLayout> mListViews = new ArrayList<>();

    public ClassViewPagerAdapter2(final Context context, AllClasses allClasses){
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 1; i <= allClasses.getAllWeekCount(); i++) {
            ClassBoxLayout view = new ClassBoxLayout(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setAdapter(new ClassDataAdapter(context, allClasses.getOneWeek(i), null, new ClassBoxLayout.OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull ClassUnit topLayer, List<ClassUnit> bottomLayer) {
                    Toast.makeText(context,topLayer.mClassName+"@"+topLayer.mClassAddress,Toast.LENGTH_LONG).show();
                }
            }));
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
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListViews.get(position));
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mListViews.get(position), 0);
        return mListViews.get(position);
    }

    public void finish(){
        for(int i=0;i<mListViews.size();i++)
            mListViews.get(i).finish();
    }
}
