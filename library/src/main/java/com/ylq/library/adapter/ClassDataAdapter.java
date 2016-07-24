package com.ylq.library.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.ylq.library.R;
import com.ylq.library.model.ClassUnit;
import com.ylq.library.model.Common;
import com.ylq.library.model.OneWeekClasses;
import com.ylq.library.util.ClickGuard;
import com.ylq.library.widget.ClassBoxLayout;
import com.ylq.library.widget.FoldTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apple on 16/7/17.
 */
public class ClassDataAdapter extends ClassBoxLayout.ClassBoxAdapter {
    public static String[] colorArray;
    private FoldTextView[][] mArea = new FoldTextView[12][7];
    private List<View> mViews = new ArrayList<>();
    private List<ClassBoxLayout.LayoutParams> mParams = new ArrayList<>();
    private OneWeekClasses mOneWeek;//本周的课
    private List<OneWeekClasses> mNotTheWeekClass;//非本周的课
    private ClassBoxLayout.OnItemClickListener mListener;
    private HashMap<ClassUnit, List<ClassUnit>> mMap = new HashMap<>();
    private HashMap<FoldTextView, ClassUnit> mMap2 = new HashMap<>();
    private Context mContext;
    private int mCount = 0;

    public ClassDataAdapter(@NonNull Context context,
                            @NonNull OneWeekClasses oneWeekClasses,
                            List<OneWeekClasses> notTheWeekClass,
                            ClassBoxLayout.OnItemClickListener listener) {
        this.mContext = context;
        this.mOneWeek = oneWeekClasses;
        this.mNotTheWeekClass = notTheWeekClass;
        this.mListener = listener;
        if (colorArray == null)
            colorArray = context.getResources().getStringArray(R.array.ClassColorArray);
        init();
    }

    private void init() {
        processOneWeek(mOneWeek, true);
        if (mNotTheWeekClass == null)
            return;
        for (OneWeekClasses oneWeekClasses : mNotTheWeekClass)
            processOneWeek(oneWeekClasses, false);
    }

    private void processOneWeek(OneWeekClasses oneWeekClasses, boolean isTheWeek) {
        List<ClassUnit> theWeekClasses = oneWeekClasses.getAllUnit();//本周的所有课程
        for (int i = 0; i < theWeekClasses.size(); i++) {
            final ClassUnit unit = theWeekClasses.get(i);
            int x = unit.mWeek - 1;
            int yStart = unit.mFrom - 1;
            int yEnd = unit.mTimes + yStart - 1;
            String s = unit.mClassName + "@" + unit.mClassAddress;
            //检查索要申请的区域是否已经被占有
            for (int j = yStart; j <= yEnd; j++) {
                if (mArea[j][x] != null) {
                    yStart++;
                    if (!mArea[j][x].getText().toString().equals(s)) {//判断是否是同一门课程，只通过名称和地点判断
                        mArea[j][x].setFold(true);
                        FoldTextView topLayer = mArea[j][x];
                        ClassUnit topUnit = mMap2.get(topLayer);
                        if (!mMap.containsKey(topUnit))
                            mMap.put(topUnit, new ArrayList<ClassUnit>());
                        if(!MMapContainUnit(mMap.get(topUnit),unit))
                            mMap.get(topUnit).add(unit);
                    }
                }
            }
            if (yStart > yEnd)
                continue;//说明没有地方可以放下这个FoldTextView
            int color = Color.parseColor(colorArray[unit.mColor]);
            FoldTextView foldTextView = origin(mContext, s, color);
            mMap2.put(foldTextView, unit);
            ClickGuard.guard(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null)
                        return;
                    mListener.onItemClick(unit, mMap.get(unit));
                }
            }, foldTextView);
            if (!isTheWeek)
                foldTextView.setBelongTheWeek(false);
            mViews.add(foldTextView);
            for (int j = yStart; j <= yEnd; j++)
                mArea[j][x] = foldTextView;
            ClassBoxLayout.LayoutParams params = new ClassBoxLayout.LayoutParams(x, yStart, yEnd);
            mParams.add(params);
            mCount++;
        }
    }

    /**
     * 判断是否包含只通过课程名和课程地址是否相同判断
     * @param classUnits
     * @param unit
     * @return
     */
    private boolean MMapContainUnit(List<ClassUnit> classUnits, ClassUnit unit) {
        String s = unit.mClassName+"@"+unit.mClassAddress;
        for(int i=0;i<classUnits.size();i++){
            String s1 = classUnits.get(i).mClassName+"@"+classUnits.get(i).mClassAddress;
            if(s.equals(s1))
                return true;
        }
        return false;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public View getView(int position) {
        return mViews.get(position);
    }

    @Override
    public boolean isContainToday() {
        return mOneWeek.isContainToday();
    }

    @Override
    public Common.SEASON getSEASON() {
        return mOneWeek.getSEASON();
    }

    @Override
    public void unGurad() {
        for (View v : mViews)
            ClickGuard.unGuard(v);
    }

    @Override
    public ClassBoxLayout.LayoutParams getParams(int position) {
        return mParams.get(position);
    }
}
