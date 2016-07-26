package com.ylq.library.classtable.viewHolder;

/**
 * Created by apple on 16/7/11.
 */
public interface PageAnimator {
    boolean HideLastPage();
    void in(long duration,AnimationEndCallBack callBack);
    void hide(long duration,AnimationEndCallBack callBack);
    void show(long duration,AnimationEndCallBack callBack);
    void leave(long duration,AnimationEndCallBack callBack);

}

