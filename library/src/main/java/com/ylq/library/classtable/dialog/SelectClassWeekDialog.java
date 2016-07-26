package com.ylq.library.classtable.dialog;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylq.library.R;
import com.ylq.library.common.ClickGuard;
import com.ylq.library.classtable.viewHolder.AnimationEndCallBack;
import com.ylq.library.classtable.widget.SelectWeekView;
import com.ylq.library.common.ClasstableBaseDialog;

/**
 * Created by apple on 16/7/18.
 */
public class SelectClassWeekDialog extends ClasstableBaseDialog implements View.OnClickListener {

    private RelativeLayout mRela;
    private LinearLayout mLinear;
    private TextView mCancle;
    private TextView mOk;
    private SelectWeekView mSelectWeekView;
    private OnOkButtonClick mListener;


    public SelectClassWeekDialog(Context context, OnOkButtonClick listener, byte[] weekChecked) {
        super(R.layout.classtable_select_class_weeks_dialog, context);
        mListener = listener;
        mSelectWeekView.setData(weekChecked);
    }

    private SelectClassWeekDialog(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        mRela = findR(R.id.classtable_select_class_dialog_rela_background);
        mLinear = findL(R.id.classtable_select_class_dialog_linear);
        mCancle = findT(R.id.classtable_select_class_dialog_text_cancle);
        mOk = findT(R.id.classtable_select_class_dialog_text_ok);
        mSelectWeekView = (SelectWeekView) findViewById(R.id.classtable_select_class_dialog_selectweekview);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(this,mLinear, mCancle, mOk, mRela);
    }

    @Override
    public void unGard() {
        ClickGuard.unGuard(mLinear,mCancle, mOk,mRela);
    }


    @Override
    public void onClick(View v) {
        if (v == mCancle || v == mRela)
            back();
        else if (v == mOk) {
            if(mListener!=null)
                mListener.onOkClick(mSelectWeekView.getChecked());
            else
                back();
        }
    }

    @Override
    public void in(long duration) {
        inImpl(duration, mLinear);
    }

    @Override
    public void leave(long duration, final AnimationEndCallBack callBack) {
        leaveImpl(duration, callBack, mLinear);
    }
}
