package com.ylq.library.classtable.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import static android.widget.LinearLayout.HORIZONTAL;

/**
 * Created by ylq on 16/7/23.
 */
public class HorDragLayout extends HorizontalScrollView {

    public static final int HALF_SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels / 2;
    private static final int FAST_DRAG_SENSITIVE = 3000;//越大越不敏感

    private ViewDragHelper mDragHelper;
    private LinearLayout mLinearContainer;
    private HorDragAdapter mAdapter;
    private int mSuggestPosition = 0;
    private int mLeft;

    public HorDragLayout(Context context) {
        this(context, null);
    }

    public HorDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mLinearContainer;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                mSuggestPosition = getPosition(left);
                mLeft = left - dx;
                if (mAdapter != null) {
                    mAdapter.animation(left);
                    if (left + mAdapter.getYouThinkMidLineToTheViewLeft(0) > (HALF_SCREEN_WIDTH))
                        return HALF_SCREEN_WIDTH - mAdapter.getYouThinkMidLineToTheViewLeft(0);
                    else if ((left + mAdapter.getAccumulateWidth(mAdapter.getCount() - 1) + mAdapter.getYouThinkMidLineToTheViewLeft(mAdapter.getCount() - 1)) < (HALF_SCREEN_WIDTH))
                        return HALF_SCREEN_WIDTH - mAdapter.getAccumulateWidth(mAdapter.getCount() - 1) - mAdapter.getYouThinkMidLineToTheViewLeft(mAdapter.getCount() - 1);
                }
                return left;
            }


            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == mLinearContainer) {
                    if (mAdapter != null) {
                        mSuggestPosition += (-xvel / FAST_DRAG_SENSITIVE);
                        mSuggestPosition = mSuggestPosition < 0 ? 0 : (mSuggestPosition >= mAdapter.getCount() ? mAdapter.getCount() - 1 : mSuggestPosition);
                        mDragHelper.settleCapturedViewAt(HALF_SCREEN_WIDTH - mAdapter.getAccumulateWidth(mSuggestPosition) - mAdapter.getYouThinkMidLineToTheViewLeft(mSuggestPosition), 0);
                        invalidate();
                    }

                }
            }
        });

        mLinearContainer = new LinearLayout(getContext());
        mLinearContainer.setOrientation(HORIZONTAL);
        mLinearContainer.setGravity(Gravity.CENTER);
        addView(mLinearContainer, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private int getPosition(int left) {
        if (mAdapter == null)
            return -1;
        int position = -1;
        int minLenToMidLine = 99999;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            int len = mAdapter.getAccumulateWidth(i) + mAdapter.getYouThinkMidLineToTheViewLeft(i);
            int delt = Math.abs(HALF_SCREEN_WIDTH - left - len);
            if (delt < minLenToMidLine) {
                position = i;
                minLenToMidLine = delt;
            }
        }
        return position;
    }


    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            mLeft = mDragHelper.getCapturedView().getLeft();
            if (mAdapter != null)
                mAdapter.animation(mLeft);
            invalidate();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //将mLinearContainer中的第一个view移动到屏幕中央
        if (mAdapter == null)
            return;
        int marginLeft = HALF_SCREEN_WIDTH - mAdapter.getYouThinkMidLineToTheViewLeft(0);
        mLeft = marginLeft;
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) mLinearContainer.getLayoutParams();
        marginLayoutParams.leftMargin = marginLeft;
        mLinearContainer.setLayoutParams(marginLayoutParams);
        //初始化动画
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.animation(mLeft);
            }
        }, 10);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        mDragHelper.shouldInterceptTouchEvent(event);
        return true;
    }

    int xDown, yDown, moveCount;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        event.setLocation(event.getX() - mLeft - getPaddingLeft(), event.getY() - getPaddingTop());//修正x轴方向上的坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getX();
                yDown = (int) event.getY();
                mLinearContainer.dispatchTouchEvent(event);
                moveCount = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                moveCount++;
                System.out.println(moveCount);
                break;
            case MotionEvent.ACTION_UP:
                int xUp = (int) event.getX();
                int yUp = (int) event.getY();
                int deltaX = Math.abs(xUp-xDown);
                int deltaY = Math.abs(yUp-yDown);
                if (moveCount <= 5 && deltaX<=2 && deltaY <=2)//移动数量不大于5,x，y轴方向上的移动改变小于等于2才认为是一次点击事件
                    mLinearContainer.dispatchTouchEvent(event);
                break;
        }
        return true;
    }

    public void setAdapter(HorDragAdapter adapter) {
        mLinearContainer.removeAllViews();
        this.mAdapter = adapter;
        for (int i = 0; i < mAdapter.getCount(); i++)
            mLinearContainer.addView(mAdapter.getView(i));
    }

    public static abstract class HorDragAdapter {
        public int getAccumulateWidth(int position) {
            int width = 0;
            for (int i = 0; i < position; i++)
                width += getView(i).getMeasuredWidth();
            return width;
        }

        public abstract void animation(int left);

        public abstract int getCount();

        public abstract int getYouThinkMidLineToTheViewLeft(int position);

        public abstract View getView(int position);
    }
}
