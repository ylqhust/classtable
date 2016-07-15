package com.ylq.library.viewHolder;

import android.content.Context;

/**
 * Created by apple on 16/7/13.
 */
public abstract class ZeroAlphaDialogHolder extends ClasstableBaseHolder {
    public ZeroAlphaDialogHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void in(long duration,AnimationEndCallBack callBack){
        if(callBack!=null)
            callBack.end();
    }

    @Override
    public void leave(long duration,AnimationEndCallBack callBack){
        if(callBack!=null)
            callBack.end();
    }

    @Override
    public boolean HideLastPage(){
        return false;
    }

    @Override
    public boolean backTwice(){
        return true;
    }
}
