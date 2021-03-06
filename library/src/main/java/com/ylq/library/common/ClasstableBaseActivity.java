package com.ylq.library.common;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ylq.library.R;
import com.ylq.library.classtable.viewHolder.AnimationEndCallBack;
import com.ylq.library.classtable.viewHolder.ClasstableBaseHolder;

import java.lang.reflect.Field;

/**
 * Created by apple on 16/7/11.
 */
public class ClasstableBaseActivity extends AppCompatActivity {

    final private static int DEFAULT_DURATION = 200;
    private Stack<ClasstableBaseHolder> mStack = new Stack<>();
    private ClasstableBaseDialog mDialog;
    private boolean mShowing = true;

    @Override
    protected void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        initSystemBarTint(true);
    }


    public void initSystemBarTint(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(on);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(on);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.classtable_theme_color));
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     *
     */
    public void fitSystemWindow() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            int statusBarHeight = getStatusBarHeight(this);
            View child = getActivityView();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            marginLayoutParams.topMargin = statusBarHeight;
            child.setLayoutParams(marginLayoutParams);
        }
    }

    /**
     * 获取手机状态栏的高度
     *
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
    @Override
    public void onStart(){
        super.onStart();
        mShowing = true;
    }

    @Override
    public void onStop(){
        super.onStop();
        mShowing = false;
    }

    public void holderIn(ClasstableBaseHolder holder) {
        if(!mShowing)
            return;
        if (mStack.size() == 0) {
            mStack.push(holder);
            getContainer().removeAllViews();
            getContainer().addView(holder.getView());
            holder.in(DEFAULT_DURATION, null);
        } else {
            final ClasstableBaseHolder holderWillHide = mStack.top();
            mStack.push(holder);
            getContainer().addView(holder.getView());
            holder.in(DEFAULT_DURATION, null);
            if(holder.HideLastPage())
            holderWillHide.hide(DEFAULT_DURATION, new AnimationEndCallBack() {
                @Override
                public void end() {
                    getContainer().removeView(holderWillHide.getView());
                }
            });
        }
    }

    public void dialogIn(ClasstableBaseDialog dialog){
        if(!mShowing)
            return;
        if(mDialog!=null)
            throw new IllegalStateException("同一时间只能有一个Dialog");
        mDialog = dialog;
        ViewGroup mDecorView = (ViewGroup) getWindow().getDecorView();
        mDecorView.addView(mDialog.getView());
        mDialog.in(DEFAULT_DURATION);
    }

    private boolean dismissDialog(){
        if(mDialog==null)
            return false;
        final ViewGroup mDecorView = (ViewGroup) getWindow().getDecorView();
        mDialog.leave(DEFAULT_DURATION, new AnimationEndCallBack() {
            @Override
            public void end() {
                mDecorView.removeView(mDialog.getView());
                mDialog.unGard();
                mDialog = null;
            }
        });
        return true;
    }

    /**
     * 回退
     */
    public void back() {
        if(dismissDialog())
            return;
        if (mStack.size() <= 1) {
            ClasstableBaseHolder classtableBaseHolder = mStack.pop();
            if (classtableBaseHolder != null)
                classtableBaseHolder.unGurad();
            finish();
            return;
        }
        final ClasstableBaseHolder holderWillLeave = mStack.pop();
        ClasstableBaseHolder holderWillShow = mStack.top();
        holderWillLeave.leave(DEFAULT_DURATION, new AnimationEndCallBack() {
            @Override
            public void end() {
                getContainer().removeView(holderWillLeave.getView());
                holderWillLeave.unGurad();
            }
        });
        if(holderWillLeave.HideLastPage()){
            getContainer().addView(holderWillShow.getView(), 0);
            holderWillShow.show(DEFAULT_DURATION, null);
        }
    }


    public void anotherBack() {
        if(dismissDialog())
            return;
        if(mStack.size()!=1)
            return;
        final ClasstableBaseHolder classtableBaseHolder = mStack.pop();
        classtableBaseHolder.leave(DEFAULT_DURATION, new AnimationEndCallBack() {
            @Override
            public void end() {
                getContainer().removeView(classtableBaseHolder.getView());
                classtableBaseHolder.unGurad();
            }
        });
    }

    private View getActivityView() {
        ViewGroup conteentParent = (ViewGroup) findViewById(android.R.id.content);
        View child = conteentParent.getChildAt(0);//这个Child就是Activity的view
        return child;
    }

    private FrameLayout getContainer() {
        return (FrameLayout) getActivityView();
    }

    public boolean backTwice(){
        if(mDialog!=null)
            return mDialog.backTwice();
        if(mStack.top()!=null)
            return mStack.top().backTwice();
        return false;
    }


    public void reStart(ClasstableBaseHolder holder) {
        dismissDialog();
        while(mStack.top()!=null){
            mStack.pop().unGurad();
        }
        mStack.push(holder);
        getContainer().removeAllViews();
        getContainer().addView(holder.getView());
        holder.in(DEFAULT_DURATION,null);
    }

    public boolean isShown(ClasstableBaseHolder holder) {
        for(int i=mStack.size()-1;i>=0;i--){
            ClasstableBaseHolder c = mStack.get(i);
            if(c==null)
                return false;
            if(!c.HideLastPage())
                continue;
            return c==holder;
        }
        return false;
    }
}
