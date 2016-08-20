package com.ylq.library.classtable.dialog;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ylq.library.R;
import com.ylq.library.classtable.viewHolder.AnimationEndCallBack;
import com.ylq.library.common.ClasstableBaseDialog;
import com.ylq.library.common.ClickGuard;

/**
 * Created by ylq on 16/7/28.
 */
public class IngLoginHubDialog extends ClasstableBaseDialog {

    private RelativeLayout mRela;
    private ImageView img1,img2;

    public IngLoginHubDialog(Context context){
        this(R.layout.classtable_ing_login_hub_dialog,context);
    }
    private IngLoginHubDialog(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        mRela = findR(R.id.classtable_ing_login_hub_dialog_rela_background);
        img1 = (ImageView) findViewById(R.id.classtable_ing_login_hub_img1);
        img2 = (ImageView) findViewById(R.id.classtable_ing_login_hub_img2);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        },mRela);
    }

    @Override
    public void unGard() {
        ClickGuard.unGuard(mRela);
    }

    @Override
    public void in(long duration) {
        inImpl(duration,null);
        int width = getContext().getResources().getDimensionPixelSize(R.dimen.classtable_ing_login_hub_dialog_img_width);
        long du = 1000*2;
        Animation t1 = new TranslateAnimation(-width,0,0,0);
        t1.setInterpolator(new LinearInterpolator());
        t1.setRepeatMode(Animation.RESTART);
        t1.setRepeatCount(10000);
        t1.setDuration(du);
        img1.setAnimation(t1);
        img2.setAnimation(t1);
        t1.start();
    }

    @Override
    public void leave(long duration, AnimationEndCallBack callBack) {
        leaveImpl(duration,callBack,null);
    }

    @Override
    public boolean backTwice(){
        return true;
    }
}
