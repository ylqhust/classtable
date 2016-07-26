package com.ylq.library.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylq.library.R;
import com.ylq.library.util.ClickGuard;
import com.ylq.library.viewHolder.AnimationEndCallBack;

/**
 * Created by ylq on 16/7/25.
 */
public class MessageDialog extends ClasstableBaseDialog implements View.OnClickListener {

    private RelativeLayout mRelaBack;
    private TextView mNav;
    private TextView mPositive;
    private TextView mTitle;
    private TextView mMessage;
    private Builder mBuilder;
    private LinearLayout mLinear;

    private MessageDialog(int layoutId, Context context, Builder builder) {
        super(layoutId, context);
        this.mBuilder = builder;
        mTitle.setText(mBuilder.title);
        mMessage.setText(mBuilder.message);
    }

    @Override
    public void initView() {
        mRelaBack = findR(R.id.classtable_dialog_message_rela_background);
        mNav = findT(R.id.classtable_dialog_message_text_nav);
        mPositive = findT(R.id.classtable_dialog_message_text_positive);
        mTitle = findT(R.id.classtable_dialog_message_text_title);
        mMessage = findT(R.id.classtable_dialog_message_text_message);
        mLinear = findL(R.id.classtable_dialog_message_linear);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(this, mRelaBack, mPositive, mNav);
    }

    @Override
    public void unGard() {
        ClickGuard.unGuard(mRelaBack, mPositive, mNav);
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
        if (v == mRelaBack) {
            if (mBuilder.touchBackDismiss)
                back();

        } else if (v == mNav){
            if(mBuilder.navLis!=null)
                mBuilder.navLis.onClick(v);
            else
                back();
        }else if(v==mPositive){
            if(mBuilder.posiLis!=null)
                mBuilder.posiLis.onClick(v);
            else
                back();
        }

    }

    public static class Builder {
        private Context cxt;
        private String title = "";
        private String message = "";
        private View.OnClickListener posiLis;
        private View.OnClickListener navLis;
        private boolean touchBackDismiss = true;

        public Builder(Context context) {
            cxt = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setNavListener(View.OnClickListener listener) {
            this.navLis = listener;
            return this;
        }

        public Builder setPosiListener(View.OnClickListener listener) {
            this.posiLis = listener;
            return this;
        }

        public Builder setTouchBackDismiss(boolean value) {
            this.touchBackDismiss = value;
            return this;
        }

        public MessageDialog create() {
            return new MessageDialog(R.layout.classtable_dialog_message, cxt, this);
        }
    }


}
