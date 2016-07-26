package com.ylq.library.classtable.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.sevenheaven.iosswitch.ShSwitchView;
import com.ylq.library.classtable.ClasstableNotification;
import com.ylq.library.R;
import com.ylq.library.common.ClickGuard;
import com.ylq.library.classtable.Store;

/**
 * Created by apple on 16/7/15.
 */
public class SettingPageViewHolder extends ClasstableBaseHolder {
    private RelativeLayout mRelaBack;
    private ShSwitchView mSwitchShowAll;
    private ShSwitchView mSwitchNotifi;

    public SettingPageViewHolder(Context context){
        super(R.layout.classtable_setting_page,context);
        mSwitchShowAll.setOn(Store.isShowAll(context));
        mSwitchNotifi.setOn(Store.isNotification(context));
        mSwitchShowAll.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                Store.saveShowAllSettig(getContext(),isOn);
            }
        });
        mSwitchNotifi.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                Store.saveNotificationSetting(getContext(),isOn);
                if(isOn)
                    ClasstableNotification.start(getContext());//开启通知
                else
                    ClasstableNotification.cancle(getContext());//取消通知
            }
        });
    }

    public SettingPageViewHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        mRelaBack = findR(R.id.classtable_toolbar_rela_back);
        mSwitchShowAll = (ShSwitchView) findViewById(R.id.classtable_setting_page_content_switch_show_all_classes);
        mSwitchNotifi = (ShSwitchView) findViewById(R.id.classtable_setting_page_content_switch_notification);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        },mRelaBack);

    }

    @Override
    public void unGurad() {
        ClickGuard.unGuard(mRelaBack);
    }
}
