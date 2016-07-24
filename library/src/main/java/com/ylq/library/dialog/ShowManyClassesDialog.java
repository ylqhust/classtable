package com.ylq.library.dialog;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ylq.library.R;
import com.ylq.library.adapter.ShowManyClassesAdapter;
import com.ylq.library.model.ClassUnit;
import com.ylq.library.util.ClickGuard;
import com.ylq.library.viewHolder.AnimationEndCallBack;
import com.ylq.library.widget.HorDragLayout;

import java.util.List;
import static com.ylq.library.adapter.ShowManyClassesAdapter.onItemClickListener;
/**
 * Created by ylq on 16/7/23.
 * 当有重合的课程的时候，点击课程后显示这个dialog，显示多个课程
 */
public class ShowManyClassesDialog extends ClasstableBaseDialog implements View.OnClickListener{

    private HorDragLayout mDragLayout;
    private RelativeLayout mRelaBack;
    private ShowManyClassesAdapter mAdapter;
    private onItemClickListener mListener;

    public ShowManyClassesDialog(Context context, ClassUnit topLayer, List<ClassUnit> bottomLayer,onItemClickListener listener) {
        this(R.layout.classtable_show_many_classes_dialog, context,topLayer,bottomLayer);
        mListener = listener;
    }

    private ShowManyClassesDialog(int layoutId, final Context context, ClassUnit topLayer, List<ClassUnit> bottomLayer) {
        super(layoutId, context);
        mAdapter = new ShowManyClassesAdapter(topLayer, bottomLayer, context, new ShowManyClassesAdapter.onItemClickListener() {
            @Override
            public void onItemClick(String s) {
                back();
                if(mListener!=null)
                    mListener.onItemClick(s);
            }
        });
        mDragLayout.setAdapter(mAdapter);
    }

    @Override
    public void initView() {
        mRelaBack = findR(R.id.classtable_show_many_classes_rela);
        mDragLayout = (HorDragLayout) findViewById(R.id.classtable_show_many_classes_dialog_hordraglayout);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(this,mRelaBack);
    }

    @Override
    public void unGard() {
        mAdapter.unGuard();
        ClickGuard.unGuard(mRelaBack);
    }

    @Override
    public void in(long duration) {
        inImpl(duration, mDragLayout);
    }

    @Override
    public void leave(long duration, AnimationEndCallBack callBack) {
        leaveImpl(duration, callBack, mDragLayout);
    }

    @Override
    public void onClick(View v) {
        if(v==mRelaBack)
            back();
    }
}
