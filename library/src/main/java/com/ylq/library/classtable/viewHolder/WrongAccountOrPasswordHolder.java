package com.ylq.library.classtable.viewHolder;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RelativeLayout;

import com.ylq.library.R;

/**
 * Created by apple on 16/7/13.
 */
public class WrongAccountOrPasswordHolder extends ZeroAlphaDialogHolder{

    private RelativeLayout mRela;
    Handler mHandler = new Handler(Looper.getMainLooper(),null);

    public WrongAccountOrPasswordHolder(Context context){
        super(R.layout.classtable_dialog_wrong_account_or_password,context);

    }
    public WrongAccountOrPasswordHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        mRela = findR(R.id.classtable_dialog_wrong_account_or_password_rela);
    }

    @Override
    public void Guard() {
        mRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void unGurad() {

    }

    @Override
    public void in(long duration,AnimationEndCallBack callBack){
        if(callBack!=null)
            callBack.end();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                back();
            }
        },2000);
    }

}
