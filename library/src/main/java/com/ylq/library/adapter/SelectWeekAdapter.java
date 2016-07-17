package com.ylq.library.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ylq.library.R;

/**
 * Created by apple on 16/7/15.
 */
public class SelectWeekAdapter extends BaseAdapter {
    private String[] mListViewData;
    private int mSelectWeek;
    private SelectWeekCallback mCallBack;
    private Context mContext;
    private LayoutInflater mInflater;

    public SelectWeekAdapter(@NonNull String[] listViewData, int selectWeek,
                             SelectWeekCallback callback, Context context) {
        mListViewData = listViewData;
        mSelectWeek = selectWeek;
        mCallBack = callback;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mListViewData.length;
    }

    @Override
    public Object getItem(int position) {
        return mListViewData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) mInflater.inflate(R.layout.classtable_item_select_week, null);
        textView.setText(mListViewData[position]);
        if ((position + 1) == mSelectWeek) {
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.select_week_item_select_shape));
            textView.setTextColor(Color.WHITE);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null)
                    mCallBack.selectWeek(position + 1);
            }
        });
        return textView;
    }

    public static interface SelectWeekCallback {
        void selectWeek(int weekIndex);
    }
}
