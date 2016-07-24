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
import com.ylq.library.model.OneWeekClasses;
import com.ylq.library.util.Store;
import com.ylq.library.widget.ClassBoxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/7/17.
 */
public class ClassViewPagerAdapter2 extends PagerAdapter {
    private List<ClassBoxLayout> mListViews = new ArrayList<>();
    private AllClasses mAllClasses;
    private Context mContext;
    private boolean lastedShowAll;
    private ClassBoxLayout.OnItemClickListener mListener;

    public ClassViewPagerAdapter2(final Context context, @NonNull AllClasses allClasses, ClassBoxLayout.OnItemClickListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mAllClasses = allClasses;
        mContext = context;
        lastedShowAll = Store.isShowAll(context);
        mListener = listener;
        for (int i = 1; i <= allClasses.getAllWeekCount(); i++) {
            ClassBoxLayout view = new ClassBoxLayout(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setAdapter(new ClassDataAdapter(context, allClasses.getOneWeek(i), getAfterTheWeek(i), new ClassBoxLayout.OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull ClassUnit topLayer, List<ClassUnit> bottomLayer) {
                    if(mListener!=null)
                        mListener.onItemClick(topLayer,bottomLayer);
                }
            }));
            mListViews.add(view);
        }

    }

    private List<OneWeekClasses> getAfterTheWeek(int i) {
        if (!Store.isShowAll(mContext))
            return null;
        return mAllClasses.getData().subList(i, mAllClasses.getData().size());
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
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

    public void finish() {
        for (int i = 0; i < mListViews.size(); i++)
            mListViews.get(i).finish();
    }

    /**
     * 检查显示全部的设置是否改变了
     */
    public void checkIfShowAll() {
        if (lastedShowAll == Store.isShowAll(mContext))
            return;
        lastedShowAll = Store.isShowAll(mContext);
        for (int i = 1; i <= mAllClasses.getAllWeekCount(); i++) {
            ClassBoxLayout view = mListViews.get(i - 1);
            view.setAdapter(new ClassDataAdapter(mContext, mAllClasses.getOneWeek(i), getAfterTheWeek(i), new ClassBoxLayout.OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull ClassUnit topLayer, List<ClassUnit> bottomLayer) {
                    if(mListener!=null)
                        mListener.onItemClick(topLayer,bottomLayer);
                }
            }));
        }
    }
}
