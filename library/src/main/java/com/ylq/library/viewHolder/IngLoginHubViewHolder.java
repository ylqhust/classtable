package com.ylq.library.viewHolder;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylq.library.R;
import com.ylq.library.util.ClickGuard;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apple on 16/7/13.
 */
public class IngLoginHubViewHolder extends ZeroAlphaDialogHolder {

    private RelativeLayout mRelaBackground;
    private TextView mText;
    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String text = (String) msg.obj;
            mText.setText(text);
            return true;
        }
    });
    private Timer mTimer;

    public IngLoginHubViewHolder(Context context){
        super(R.layout.classtable_dialog_ing_login_hub,context);
    }

    public IngLoginHubViewHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        mRelaBackground = findR(R.id.classtable_dialog_ing_login_hub_rela);
        mText = findT(R.id.classtable_dialog_ing_login_hub_text);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        },mRelaBackground);
    }

    @Override
    public void unGurad() {
        ClickGuard.unGuard(mRelaBackground);
        if(mTimer!=null)
            mTimer.cancel();
        mTimer = null;
    }

    private int index=0;
    @Override
    public void in(long duration,AnimationEndCallBack callBack){
        final String[] array = getContext().getResources().getStringArray(R.array.ing_login_hub);
        if(callBack!=null)
            callBack.end();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj = array[index%(array.length)];
                mHandler.sendMessage(msg);
                index++;
            }
        },0,500);

    }

}
