package com.ylq.library.viewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylq.library.R;
import com.ylq.library.activity.ClasstableBaseActivity;
import com.ylq.library.util.ClickGuard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/7/18.
 */
public class AddClassPageHolder extends ClasstableBaseHolder implements View.OnClickListener {

    private EditText mClassName;
    private EditText mTeacherName;
    private EditText mClassAddress;
    private TextView mSelectWeek;
    private TextView mSelectSection;
    private RelativeLayout mRelaBack;
    private RelativeLayout mRelaOK;
    private TextView mAddNewTime;//添加新的时间段
    private LinearLayout mLinearContainer;
    private List<NewTimeContent> mNewTimeContents = new ArrayList<>();
    private boolean mIsDialogShowing = false;


    public AddClassPageHolder(Context context) {
        super(R.layout.classtable_add_class_page, context);
    }

    public AddClassPageHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        mClassAddress = findE(R.id.classtable_add_class_page_edit_classAddress);
        mTeacherName = findE(R.id.classtable_add_class_page_edit_teacherName);
        mSelectWeek = findT(R.id.classtable_add_class_page_text_select_week);
        mSelectSection = findT(R.id.classtable_add_class_page_text_select_section);
        mClassName = findE(R.id.classtable_add_class_page_edit_className);
        mRelaBack = findR(R.id.classtable_toolbar_rela_back);
        mRelaOK = findR(R.id.classtable_toolbar_ok_rela);
        mAddNewTime = findT(R.id.classtable_add_class_page_text_add_new_time);
        mLinearContainer = findL(R.id.classtable_add_class_page_linear_child_container);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(this, mRelaBack, mRelaOK, mSelectSection, mSelectWeek, mAddNewTime);
    }

    @Override
    public void unGurad() {
        ClickGuard.unGuard(mRelaOK, mRelaBack, mSelectSection, mSelectWeek, mAddNewTime);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.classtable_toolbar_rela_back)
            back();
        else if (id == R.id.classtable_toolbar_ok_rela) {

        } else if (id == R.id.classtable_add_class_page_text_add_new_time) {
            String address = getAddress();
            mNewTimeContents.add(new NewTimeContent(address));
            mLinearContainer.addView(mNewTimeContents.get(mNewTimeContents.size() - 1).view);
        }else if(id==R.id.classtable_add_class_page_text_select_section){

        }else if(id==R.id.classtable_add_class_page_text_select_week){
            ViewGroup viewGroup = (ViewGroup) ((ClasstableBaseActivity)getContext()).getWindow().getDecorView();
            viewGroup.addView(LayoutInflater.from(getContext()).inflate(R.layout.classtable_select_class_dialog,null));
            mIsDialogShowing = true;
        }
    }


    private String getAddress() {
        for (int i = mNewTimeContents.size() - 1; i >= 0; i--) {
            String s = mNewTimeContents.get(i).getAddress();
            if (s != null)
                return s;
        }
        String s = mClassAddress.getText().toString().trim();
        if (s == null || s.length() == 0)
            return null;
        return s;
    }

    class NewTimeContent implements View.OnClickListener {
        View view;
        TextView selectWeek;
        TextView selectSection;
        EditText address;
        TextView delete;
        TextView anotherTime;

        NewTimeContent(String adrs) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.classtable_add_class_page_child_content, null);
            selectSection = (TextView) view.findViewById(R.id.classtable_add_class_page_child_text_select_section);
            selectWeek = (TextView) view.findViewById(R.id.classtable_add_class_page_child_text_select_week);
            address = (EditText) view.findViewById(R.id.classtable_add_class_page_child_edit_classAddress);
            delete = (TextView) view.findViewById(R.id.classtable_add_class_page_child_text_delete);
            anotherTime = (TextView) view.findViewById(R.id.classtable_add_class_page_child_text_another_time);
            if (adrs != null)
                address.setText(adrs);
            setTitle(mNewTimeContents.size() + 1);
            this.Guard();
        }

        private void Guard() {
            ClickGuard.guard(this, selectSection, selectWeek, delete);
        }

        private void unGuard() {
            ClickGuard.unGuard(selectWeek, selectSection, delete);
        }

        String getAddress() {
            String s = address.getText().toString().trim();
            if (s == null || s.length() == 0)
                return null;
            return s;
        }

        void setTitle(int index) {
            anotherTime.setText("其它时段" + index);
        }


        @Override
        public void onClick(View v) {
            if (v == selectWeek) {
            } else if (v == selectSection) {

            } else if (v == delete) {
                mLinearContainer.removeView(view);
                this.unGuard();
                int index = mNewTimeContents.indexOf(this);
                mNewTimeContents.remove(this);
                for (int i = index; i < mNewTimeContents.size(); i++)
                    mNewTimeContents.get(i).setTitle(i + 1);
            }
        }
    }

    @Override
    public void back(){
        if(mIsDialogShowing){
            ViewGroup viewGroup = (ViewGroup) ((ClasstableBaseActivity)getContext()).getWindow().getDecorView();
            viewGroup.removeViewAt(viewGroup.getChildCount()-1);
        }else
            super.back();
    }
}
