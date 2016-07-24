package com.ylq.library.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylq.library.R;
import com.ylq.library.model.ClassUnit;
import com.ylq.library.util.ClickGuard;
import com.ylq.library.widget.HorDragLayout;

import java.util.ArrayList;
import java.util.List;

import static com.ylq.library.widget.HorDragLayout.HALF_SCREEN_WIDTH;

/**
 * Created by ylq on 16/7/23.
 */
public class ShowManyClassesAdapter extends HorDragLayout.HorDragAdapter implements View.OnClickListener{

    public static int[] colorArrays;
    public static final int DEFAULT_Z = HALF_SCREEN_WIDTH;//默认的垂直高度是700
    public static final int WIDTH = HALF_SCREEN_WIDTH*2/3;
    public static final int HEIGHT = (int) (Resources.getSystem().getDisplayMetrics().density*150);
    private onItemClickListener mListener;

    private List<View> mListView = new ArrayList<>();

    public ShowManyClassesAdapter(ClassUnit topLayer, List<ClassUnit> bottomLayer, Context context,onItemClickListener listener){
        mListener = listener;
        if(colorArrays==null){
            String[] colorStrings = context.getResources().getStringArray(R.array.ClassColorArray);
            colorArrays = new int[colorStrings.length];
            for(int i=0;i<colorStrings.length;i++)
                colorArrays[i] = Color.parseColor(colorStrings[i]);
        }
        TextView t = obtain(topLayer.mClassName+"@"+topLayer.mClassAddress,topLayer.mColor,context);
        ClickGuard.guard(this,t);
        mListView.add(t);
        for(int i=0;i<bottomLayer.size();i++){
            TextView tv = obtain(bottomLayer.get(i).mClassName+"@"+bottomLayer.get(i).mClassAddress,bottomLayer.get(i).mColor,context);
            ClickGuard.guard(this,tv);
            mListView.add(tv);
        }
    }

    @Override
    public void animation(int left) {
        int len, seta;
        for (int i = 0; i < getCount(); i++) {
            len = left + getAccumulateWidth(i) + getYouThinkMidLineToTheViewLeft(i);
            len = HALF_SCREEN_WIDTH - len;
            seta = (int) (180 / 3.14 * Math.atan((float)len / DEFAULT_Z));
            getView(i).setRotationY(seta);
        }
    }

    public void unGuard(){
        for(View v:mListView)
            ClickGuard.unGuard(v);
    }
    @Override
    public int getCount() {
        return mListView.size();
    }


    /**
     * 获取你认为的这个位置为position的view的x轴上的中线距离这个view的左边界的长度
     * 如果不是特别的view，可以直接返回width/2
     * @param position
     * @return
     */
    @Override
    public int getYouThinkMidLineToTheViewLeft(int position) {
        return WIDTH/2;
    }

    @Override
    public View getView(int position) {
        return mListView.get(position);
    }

    private TextView obtain(String s,int colorIndex,Context context){
        TextView tx = new TextView(context);
        tx.setText(s);
        tx.setTextColor(Color.WHITE);
        tx.setGravity(Gravity.CENTER);
        Drawable drawable = context.getResources().getDrawable(R.drawable.classtable_unit_circle_coner);
        drawable.setColorFilter(colorArrays[colorIndex], PorterDuff.Mode.ADD);
        tx.setBackground(drawable);
        tx.setLayoutParams(new LinearLayout.LayoutParams(WIDTH, (int) (HEIGHT*0.8)));//这里的高度一定必须比父容器的高度小一些，否则会造成视觉上的错误，感觉没有完全旋转
        return tx;
    }

    @Override
    public void onClick(View v) {
        if(mListener!=null){
            mListener.onItemClick(((TextView)v).getText().toString());
        }
    }

    public static interface onItemClickListener{
        public void onItemClick(String s);
    }
}
