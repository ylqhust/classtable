package com.ylq.library.util;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.HashMap;

/**
 * Created by apple on 16/7/12.
 * 防止多次点击
 */
public class ClickGuard {
    private static HashMap<View, Wrap> mHashMap = new HashMap<>();
    private static final long SENSITIVI = 2000;//敏感度2000毫秒，也就是多次点击之间时间差小于2秒都只算一次点击
    private static View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (chickPass(v)) {
                Wrap wrap = mHashMap.get(v);
                wrap.resetLastTime();
                wrap.onClickListener.onClick(v);
            }
        }
    };

    private static boolean chickPass(View v) {
        long currentTime = System.currentTimeMillis();
        long lastTime = mHashMap.get(v).lastTime;
        if (currentTime - lastTime <= SENSITIVI)
            return false;
        return true;
    }

    public static void guard(@NonNull View.OnClickListener listener, @NonNull View... vs) {
        for (View v : vs) {
            if (mHashMap.containsKey(v))
                mHashMap.remove(v);
            mHashMap.put(v, new Wrap(0, listener));
            v.setOnClickListener(mListener);
        }
    }

    /**
     * 必须调用此函数，防止内存泄露
     *
     * @param v
     */
    public static void unGuard(@NonNull View... vs) {
        for(View v:vs)
            mHashMap.remove(v);
    }

    static class Wrap {
        private View.OnClickListener onClickListener;
        private long lastTime;

        public Wrap(long lastTime, View.OnClickListener onClickListener) {
            this.lastTime = lastTime;
            this.onClickListener = onClickListener;
        }

        public void resetLastTime() {
            this.lastTime = System.currentTimeMillis();
        }
    }
}
