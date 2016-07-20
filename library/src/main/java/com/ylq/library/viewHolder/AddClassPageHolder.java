package com.ylq.library.viewHolder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ylq.library.R;
import com.ylq.library.dialog.ClasstableBaseDialog;
import com.ylq.library.dialog.SelectClassSectionDialog;
import com.ylq.library.dialog.SelectClassWeekDialog;
import com.ylq.library.model.NewClassDataWrap;
import com.ylq.library.util.ClickGuard;
import com.ylq.library.util.Copy;
import com.ylq.library.util.Store;

import org.json.JSONException;

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
    private byte[] mWeekChecked;
    private int[] mWeekFirstSecSecondSec = new int[3];


    public AddClassPageHolder(Context context) {
        super(R.layout.classtable_add_class_page, context);
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
        for (int i = 0; i < mNewTimeContents.size(); i++)
            mNewTimeContents.get(i).unGuard();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.classtable_toolbar_rela_back)
            back();
        else if (id == R.id.classtable_toolbar_ok_rela) {
            inputEnd();
        } else if (id == R.id.classtable_add_class_page_text_add_new_time) {
            String address = getAddress();
            mNewTimeContents.add(new NewTimeContent(address, getLastedSelectWeek()));
            mLinearContainer.addView(mNewTimeContents.get(mNewTimeContents.size() - 1).view);
        } else if (id == R.id.classtable_add_class_page_text_select_section) {
            dialogIn(new SelectClassSectionDialog(getContext(), mWeekFirstSecSecondSec, new ClasstableBaseDialog.OnOkButtonClick() {
                @Override
                public void onOkClick(Object data) {
                    mWeekFirstSecSecondSec = (int[]) data;
                    setSelectSectionTextString(mSelectSection, mWeekFirstSecSecondSec);
                    back();
                }
            }));

        } else if (id == R.id.classtable_add_class_page_text_select_week) {

            dialogIn(new SelectClassWeekDialog(getContext(), new ClasstableBaseDialog.OnOkButtonClick() {
                @Override
                public void onOkClick(Object data) {
                    mWeekChecked = (byte[]) data;
                    setSelectWeekTextString(mSelectWeek, mWeekChecked);
                    back();
                }
            }, mWeekChecked));
        }
    }

    /**
     * 用户输入结束。点击了确定后，进入此方法
     */
    private void inputEnd() {
        NewClassDataWrap usefulData = collectData();
        if (usefulData == null)
            return;
        try {
            if (Store.addNewClassData(getContext(), usefulData)) {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.classtable_add_class_success), Toast.LENGTH_SHORT).show();
                back();
                anotherBack();
                holderIn(new ClassPageHolder(getContext(), Store.getLocalData(getContext())));
            } else {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.classtable_add_class_failed), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.classtable_add_class_failed), Toast.LENGTH_SHORT).show();
        }

    }

    private NewClassDataWrap collectData() {
        NewClassDataWrap dataWrap = new NewClassDataWrap();
        //检查是否有课程名
        String className = mClassName.getText().toString().trim();
        if (TextUtils.isEmpty(className)) {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.classtable_class_name_is_null),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
        dataWrap.className = className;
        dataWrap.teacherName = mTeacherName.getText().toString().trim();
        if ((!isAllZero(mWeekChecked)) && notOriginalText(mSelectSection)) {
            dataWrap.weeks.add(mWeekChecked);
            dataWrap.sections.add(mWeekFirstSecSecondSec);
            dataWrap.addresses.add(mClassAddress.getText().toString().trim());
        }

        for (int i = 0; i < mNewTimeContents.size(); i++) {
            NewTimeContent content = mNewTimeContents.get(i);
            if ((!isAllZero(content.weekChecked)) && notOriginalText(content.selectSection)) {
                dataWrap.weeks.add(content.weekChecked);
                dataWrap.sections.add(content.weekFirstSecSecondSec);
                dataWrap.addresses.add(content.address.getText().toString().trim());
            }
        }

        if (dataWrap.weeks.size() == 0) {
            Toast.makeText(getContext(),
                    getContext().getResources().getString(R.string.classtable_not_exist_useful_time), Toast.LENGTH_LONG).show();
            return null;
        }
        return dataWrap;
    }

    private boolean notOriginalText(TextView selectSection) {
        String s = selectSection.getText().toString().trim();
        return !s.equals(getContext().getResources().getString(R.string.classtable_select_section));
    }

    private boolean isAllZero(byte[] weekCheck) {
        if(weekCheck==null)
            return true;
        for (int i = 0; i < weekCheck.length; i++)
            if (weekCheck[i] == 1)
                return false;
        return true;
    }


    private void setSelectSectionTextString(TextView textView, int[] sections) {
        String text = SelectClassSectionDialog.WEEKS[sections[0]];
        text += "  ";
        text += SelectClassSectionDialog.SECTIONS[sections[1]];
        if (sections[1] != sections[2])
            text += ("  " + SelectClassSectionDialog.TO_SECTIONS[sections[2]]);
        textView.setText(text);
    }

    private void setSelectWeekTextString(TextView textView, byte[] checked) {
        if (checked == null) {
            textView.setText(getContext().getResources().getString(R.string.classtable_select_weeks));
            return;
        }
        if (checked[24] == 1) {
            textView.setText("1-24周");
            return;
        }
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < 24; ) {
            if (checked[i] != 1) {
                i++;
                continue;
            }
            int j;
            for (j = i + 1; j < 24; j++) {
                if (checked[j] != 1) {
                    if (j == i + 1)
                        sb.append((i + 1) + ",");
                    else
                        sb.append((i + 1) + "-" + j + ",");
                    i = j + 1;
                    break;
                }
            }
            if (j == 24)
                break;
        }
        if (checked[i] != 0) {
            if (i == 23)
                sb.append("24,");
            else
                sb.append((i + 1) + "-24,");
        }
        String s = sb.toString();
        if (s != null && s.length() == 0)
            textView.setText(getContext().getResources().getString(R.string.classtable_select_weeks));
        else {
            s = s.substring(0, s.length() - 1);
            s += "周";
            textView.setText(s);
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

    private byte[] getLastedSelectWeek() {
        if (mNewTimeContents.size() > 1)//因为content是先添加到List集合中的，所以要判断是否大于1
            return Copy.copyByteArray(mNewTimeContents.get(mNewTimeContents.size() - 2).weekChecked);
        else
            return Copy.copyByteArray(mWeekChecked);
    }

    class NewTimeContent implements View.OnClickListener {
        View view;
        TextView selectWeek;
        TextView selectSection;
        EditText address;
        TextView delete;
        TextView anotherTime;
        byte[] weekChecked;
        int[] weekFirstSecSecondSec = new int[3];

        NewTimeContent(String adrs, byte[] lastedSelectWeek) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.classtable_add_class_page_child_content, null);
            selectSection = (TextView) view.findViewById(R.id.classtable_add_class_page_child_text_select_section);
            selectWeek = (TextView) view.findViewById(R.id.classtable_add_class_page_child_text_select_week);
            address = (EditText) view.findViewById(R.id.classtable_add_class_page_child_edit_classAddress);
            delete = (TextView) view.findViewById(R.id.classtable_add_class_page_child_text_delete);
            anotherTime = (TextView) view.findViewById(R.id.classtable_add_class_page_child_text_another_time);
            if (adrs != null)
                address.setText(adrs);
            setTitle(mNewTimeContents.size() + 1);
            weekChecked = lastedSelectWeek;
            setSelectWeekTextString(selectWeek, weekChecked);
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
                dialogIn(new SelectClassWeekDialog(getContext(), new ClasstableBaseDialog.OnOkButtonClick() {
                    @Override
                    public void onOkClick(Object data) {
                        weekChecked = Copy.copyByteArray((byte[]) data);
                        setSelectWeekTextString(selectWeek, weekChecked);
                        back();
                    }
                }, weekChecked));
            } else if (v == selectSection) {
                dialogIn(new SelectClassSectionDialog(getContext(), weekFirstSecSecondSec, new ClasstableBaseDialog.OnOkButtonClick() {
                    @Override
                    public void onOkClick(Object data) {
                        weekFirstSecSecondSec = Copy.copyIntArray((int[]) data);
                        setSelectSectionTextString(selectSection, weekFirstSecSecondSec);
                        back();
                    }
                }));
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
}
