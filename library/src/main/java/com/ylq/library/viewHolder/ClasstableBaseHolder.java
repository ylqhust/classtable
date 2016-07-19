package com.ylq.library.viewHolder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylq.library.activity.ClasstableBaseActivity;
import com.ylq.library.activity.ClasstableMainActivity;
import com.ylq.library.dialog.ClasstableBaseDialog;

/**
 * Created by apple on 16/7/11.
 */
public abstract class ClasstableBaseHolder implements PageAnimator{
    private View mView;
    private Context mContext;
    final static int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public ClasstableBaseHolder(int layoutId,Context context){
        mView = LayoutInflater.from(context)
                .inflate(layoutId,null);
        mContext = context;
        initView();
        Guard();
    }

    public View getView(){
        return mView;
    }

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

    @Override
    public void in(long duration, final AnimationEndCallBack callBack) {
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(SCREEN_WIDTH,0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                marginLayoutParams.rightMargin = -current;
                marginLayoutParams.leftMargin = current;
                mView.setLayoutParams(marginLayoutParams);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(callBack != null)
                    callBack.end();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(duration);
        animator.start();
    }

    @Override
    public void hide(long duration, final AnimationEndCallBack callBack) {
        ValueAnimator animator = ValueAnimator.ofFloat(1,0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (float) animation.getAnimatedValue();
                mView.setAlpha(current);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(callBack != null)
                    callBack.end();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(duration);
        animator.start();
    }

    @Override
    public void show(long duration,final AnimationEndCallBack callBack) {
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (float) animation.getAnimatedValue();
                mView.setAlpha(current);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(callBack != null)
                    callBack.end();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(duration);
        animator.start();
    }

    @Override
    public void leave(long duration, final AnimationEndCallBack callBack) {
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(0,SCREEN_WIDTH);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                marginLayoutParams.rightMargin = -current;
                marginLayoutParams.leftMargin = current;
                mView.setLayoutParams(marginLayoutParams);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(callBack != null)
                    callBack.end();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(duration);
        animator.start();
    }

    @Override
    public boolean HideLastPage(){
        return true;
    }

    public void back(){
        ((ClasstableMainActivity)getContext()).back();
    }

    public void anotherBack(){
        ((ClasstableBaseActivity)getContext()).anotherBack();
    }

    public void reStart(ClasstableBaseHolder holder){
        ((ClasstableBaseActivity)getContext()).reStart(holder);
    }

    public boolean isShown(){
        return ((ClasstableBaseActivity)getContext()).isShown(this);
    }

    public void holderIn(ClasstableBaseHolder holder){
        ((ClasstableBaseActivity)getContext()).holderIn(holder);
    }

    public void dialogIn(ClasstableBaseDialog dialog){
        ((ClasstableBaseActivity)getContext()).dialogIn(dialog);
    }

    public boolean backTwice(){
        return false;
    }


    public abstract void initView();
    public abstract void Guard();
    public abstract void unGurad();
}
