package com.ylq.library.viewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ylq.library.R;
import com.ylq.library.dialog.MessageDialog;
import com.ylq.library.util.ClickGuard;
import com.ylq.library.util.Store;

import java.util.List;

/**
 * Created by ylq on 16/7/24.
 */
public class ClassDetailViewHolder extends ClasstableBaseHolder implements View.OnClickListener {


    private RelativeLayout mRelaBack;
    private RelativeLayout mRelaDelete;
    private TextView mTextClassName;
    private TextView mTextTeacher;
    private LinearLayout mLinearContainer;
    private String mClassName,mAddress;

    public ClassDetailViewHolder(Context context,@NonNull String cnAndca){//cnAndca 是className@classAddress
        this(R.layout.classtable_class_detail_information_page,context);
        String[] ss = cnAndca.split("@");
        String className = ss[0];
        mClassName = className;
        String address;
        if(ss.length==2)
            address = ss[1];
        else
            address = "未知";
        mAddress = address;
        String teacher = Store.findTeacher(className,address);
        mTextClassName.setText(mClassName);
        mTextTeacher.setText(teacher);
        List<String> weekAndSection = Store.findWeekAndSection(className,address);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for(int i=0;i<weekAndSection.size();i++){
            String[] ws = weekAndSection.get(i).split("@");
            View child = inflater.inflate(R.layout.classtable_class_detail_information_page_child_content,null);
            ((TextView)child.findViewById(R.id.classtable_class_detail_information_page_child_content_text1)).setText(ws[0]);
            ((TextView)child.findViewById(R.id.classtable_class_detail_information_page_child_content_text2)).setText(ws[1]);
            ((TextView)child.findViewById(R.id.classtable_class_detail_information_page_child_content_text3)).setText(address);
            mLinearContainer.addView(child);
        }
    }

    private ClassDetailViewHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        mRelaBack = findR(R.id.classtable_toolbar_rela_back);
        mRelaDelete = findR(R.id.classtable_toolbar_rela_delete);
        mTextClassName = findT(R.id.classtable_class_detail_information_page_content_text1);
        mTextTeacher = findT(R.id.classtable_class_detail_information_page_content_text2);
        mLinearContainer = findL(R.id.classtable_class_detail_information_page_linear_container);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(this,mRelaBack,mRelaDelete);
    }

    @Override
    public void unGurad() {
        ClickGuard.unGuard(mRelaDelete,mRelaBack);
    }

    @Override
    public void onClick(View v) {
        if(v==mRelaBack)
            back();
        else if(v==mRelaDelete){
            MessageDialog dialog = new MessageDialog.Builder(getContext())
                    .setTitle("删除课程")
                    .setMessage("你确定要删除本课程吗?")
                    .setPosiListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Store.deleteByClassNameAndAddress(getContext(),mClassName,mAddress)){
                                Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                                back();
                                back();
                                anotherBack();
                                holderIn(new ClassPageHolder(getContext(),Store.getLocalData(getContext())));
                            }
                            else{
                                Toast.makeText(getContext(),"删除失败",Toast.LENGTH_SHORT).show();
                                back();
                                back();
                            }
                        }
                    }).create();
            dialogIn(dialog);
        }
    }
}
