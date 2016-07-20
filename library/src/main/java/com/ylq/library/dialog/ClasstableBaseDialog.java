package com.ylq.library.dialog;

import android.content.Context;
import android.hardware.input.InputManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylq.library.activity.ClasstableBaseActivity;
import com.ylq.library.viewHolder.AnimationEndCallBack;

/**
 * Created by apple on 16/7/19.
 */
public abstract class ClasstableBaseDialog {
    private View mView;
    private Context mContext;
    public ClasstableBaseDialog(int layoutId, Context context){
        mView = LayoutInflater.from(context).inflate(layoutId,null);
        mContext = context;
        initView();
        Guard();
    }
    public View getView(){
        return mView;
    }

    public abstract void initView();
    public abstract void Guard();
    public abstract void unGard();

    public Context getContext(){
        return mContext;
    }
    public View findViewById(int id){
        return mView.findViewById(id);
    }

    public TextView findT(int id){
        return (TextView) findViewById(id);
    }

    public RelativeLayout findR(int id){

        return (RelativeLayout) findViewById(id);
    }

    public EditText findE(int id){

        return (EditText) findViewById(id);
    }

    public LinearLayout findL(int id){
        return (LinearLayout) findViewById(id);
    }

    public void back() {
        ((ClasstableBaseActivity)mContext).back();
    }

    public abstract void in(long duration);

    public abstract void leave(long duration, final AnimationEndCallBack callBack);

    public void inImpl(long duration,View view){
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        view.clearAnimation();
        Animation animation = new ScaleAnimation(0.9f,1f,0.9f,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(duration);
        animation.setInterpolator(new OvershootInterpolator());
        view.setAnimation(animation);
        animation.start();
    }

    public void leaveImpl(long duration,final AnimationEndCallBack callBack,View view){
        view.clearAnimation();
        Animation animation = new AlphaAnimation(1,0);
        animation.setDuration(duration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(callBack!=null)
                    callBack.end();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setAnimation(animation);
        animation.startNow();

    }

    public static interface OnOkButtonClick{
        void onOkClick(Object data);
    }
}
