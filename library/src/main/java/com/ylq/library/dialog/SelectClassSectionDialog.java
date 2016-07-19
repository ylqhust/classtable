package com.ylq.library.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylq.library.R;
import com.ylq.library.util.ClickGuard;
import com.ylq.library.viewHolder.AnimationEndCallBack;

/**
 * Created by apple on 16/7/19.
 */
public class SelectClassSectionDialog extends ClasstableBaseDialog implements View.OnClickListener {

    public static final String[] WEEKS = {"周一", "周二", "周三", "周四", "周五", "周六", "周七"};
    public static final String[] SECTIONS = {"第1节", "第2节", "第3节", "第4节",
            "第5节", "第6节", "第7节", "第8节",
            "第9节", "第10节", "第11节", "第12节"};
    public static final String[] TO_SECTIONS = {
            "到第1节", "到第2节", "到第3节", "到第4节",
            "到第5节", "到第6节", "到第7节", "到第8节",
            "到第9节", "到第10节", "到第11节", "到第12节"};
    private NumberPicker mWeekPicker;
    private NumberPicker mFirstSectionPicker;
    private NumberPicker mSecondSectonPicker;
    private RelativeLayout mRelaBack;
    private TextView mCancle;
    private TextView mOk;
    private LinearLayout mLinear;
    private OnOkButtonClick mListener;

    public SelectClassSectionDialog(Context context, int[] justData,OnOkButtonClick listener) {
        super(R.layout.classtable_select_class_section_dialog, context);
        mListener = listener;
        mWeekPicker.setDisplayedValues(WEEKS);
        mWeekPicker.setMinValue(0);
        mWeekPicker.setMaxValue(WEEKS.length - 1);
        mWeekPicker.setValue(justData[0]);

        mFirstSectionPicker.setDisplayedValues(SECTIONS);
        mFirstSectionPicker.setMinValue(0);
        mFirstSectionPicker.setMaxValue(SECTIONS.length - 1);
        mFirstSectionPicker.setValue(justData[1]);
        mFirstSectionPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int secondValue = mSecondSectonPicker.getValue();
                if (newVal > secondValue)
                    mSecondSectonPicker.setValue(newVal);
            }
        });

        mSecondSectonPicker.setDisplayedValues(TO_SECTIONS);
        mSecondSectonPicker.setMinValue(0);
        mSecondSectonPicker.setMaxValue(SECTIONS.length - 1);
        mSecondSectonPicker.setValue(justData[2]);
        mSecondSectonPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int firstValue = mFirstSectionPicker.getValue();
                if (newVal < firstValue)
                    mFirstSectionPicker.setValue(newVal);
            }
        });

    }

    private SelectClassSectionDialog(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        mWeekPicker = (NumberPicker) findViewById(R.id.classtable_select_class_section_dialog_week);
        mFirstSectionPicker = (NumberPicker) findViewById(R.id.classtable_select_class_section_dialog_firstSection);
        mSecondSectonPicker = (NumberPicker) findViewById(R.id.classtable_select_class_section_dialog_secondSection);
        mRelaBack = findR(R.id.classtable_select_class_dialog_rela_background);
        mCancle = findT(R.id.classtable_select_class_dialog_text_cancle);
        mOk = findT(R.id.classtable_select_class_dialog_text_ok);
        mLinear = findL(R.id.classtable_select_class_dialog_linear);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(this, mLinear, mCancle, mOk, mRelaBack);
    }

    @Override
    public void unGard() {
        ClickGuard.unGuard(mCancle, mOk, mRelaBack, mLinear);
    }

    @Override
    public void in(long duration) {
        inImpl(duration, mLinear);
    }

    @Override
    public void leave(long duration, AnimationEndCallBack callBack) {
        leaveImpl(duration, callBack, mLinear);
    }

    @Override
    public void onClick(View v) {
        if (v == mRelaBack || v == mCancle)
            back();
        else if (v == mOk) {
            int[] value = new int[3];
            value[0] = mWeekPicker.getValue();
            value[1] = mFirstSectionPicker.getValue();
            value[2] = mSecondSectonPicker.getValue();
            if (mListener != null)
                mListener.onOkClick(value);
            else
                back();
        }
    }
}
