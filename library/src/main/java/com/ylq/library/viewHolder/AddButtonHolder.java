package com.ylq.library.viewHolder;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylq.library.R;
import com.ylq.library.util.ClickGuard;

/**
 * Created by apple on 16/7/18.
 */
public class AddButtonHolder extends ZeroAlphaDialogHolder implements View.OnClickListener {


    private TextView mCengke;
    private TextView mFromHub;
    private TextView mAddBM;
    private FloatingActionButton mCancle;
    private RelativeLayout mRelaBackground;

    public AddButtonHolder(Context context) {
        super(R.layout.classtable_add_button_page, context);
    }

    public AddButtonHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        mCengke = findT(R.id.classtable_add_button_page_text_cengke);
        mFromHub = findT(R.id.classtable_add_button_page_text_get_from_hub);
        mAddBM = findT(R.id.classtable_add_button_page_text_add_by_myself);
        mCancle = (FloatingActionButton) findViewById(R.id.classtable_add_button_page_float_button);
        mRelaBackground = findR(R.id.classtable_add_button_page_rela);
    }

    @Override
    public void Guard() {
        ClickGuard.guard(this, mCengke, mFromHub, mAddBM, mCancle, mRelaBackground);
    }

    @Override
    public void unGurad() {
        ClickGuard.unGuard(mCancle, mFromHub, mAddBM, mCengke, mRelaBackground);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.classtable_add_button_page_text_cengke) {

        } else if (id == R.id.classtable_add_button_page_text_get_from_hub) {
            back();
            holderIn(new WBY_HubLoginPageHolder(getContext(),true));
        } else if (id == R.id.classtable_add_button_page_text_add_by_myself) {
            back();
            holderIn(new AddClassPageHolder(getContext()));
        } else if (id == R.id.classtable_add_button_page_float_button) {
            back();
        } else if (id == R.id.classtable_add_button_page_rela) {
            back();
        }
    }


    @Override
    public void in(long duration, AnimationEndCallBack callBack) {
        rotationCancleButton(duration,0,45);
        YTranslate(duration,100,0,mCengke);
        YTranslate(duration*2,200,0,mFromHub);
        YTranslate(duration*3,300,0,mAddBM);
    }

    @Override
    public void leave(long duration, final AnimationEndCallBack callback){
        if(callback !=null)
            callback.end();
    }




    private void rotationCancleButton(long duration, int fromD, int toD) {
        Animation rota= new RotateAnimation(fromD, toD, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rota.setDuration(duration);
        rota.setFillAfter(true);
        rota.setInterpolator(new AnticipateOvershootInterpolator());
        mCancle.setAnimation(rota);
        rota.start();
    }

    private void YTranslate(long duration,int fromYD,int toYD,View v){
        Animation animation = new TranslateAnimation(0,0,fromYD,toYD);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setInterpolator(new OvershootInterpolator());
        v.setAnimation(animation);
        animation.start();
    }

    @Override
    public boolean backTwice() {
        return false;
    }
}
