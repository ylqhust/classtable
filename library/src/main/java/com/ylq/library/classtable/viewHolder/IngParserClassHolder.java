package com.ylq.library.classtable.viewHolder;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylq.library.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apple on 16/7/13.
 */
public class IngParserClassHolder extends ZeroAlphaDialogHolder {

    private RelativeLayout mRela;
    private TextView mText;
    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String text = (String) msg.obj;
            mText.setText(text);
            return true;
        }
    });

    public IngParserClassHolder(Context context){
        super(R.layout.classtable_dialog_ing_parser_class,context);
    }
    public IngParserClassHolder(int layoutId, Context context) {
        super(layoutId, context);
    }

    @Override
    public void initView() {
        mRela = findR(R.id.classtable_dialog_ing_parser_class_rela);
        mText = findT(R.id.classtable_ing_parser_class_text);
    }

    @Override
    public void Guard() {
        mRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void unGurad() {

    }

    private int index=0;
    @Override
    public void in(long duration,AnimationEndCallBack callBack){
        final String[] array = getContext().getResources().getStringArray(R.array.ing_parser_class);
        if(callBack!=null)
            callBack.end();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj = array[index%(array.length)];
                mHandler.sendMessage(msg);
                index++;
            }
        },0,500);

    }
}
