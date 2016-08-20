package com.ylq.library.classtable;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ylq.library.R;
import com.ylq.library.classtable.model.AllClasses;
import com.ylq.library.classtable.viewHolder.ClassPageHolder;
import com.ylq.library.classtable.viewHolder.WBY_HubLoginPageHolder;
import com.ylq.library.common.ClasstableBaseActivity;


public class ClasstableMainActivity extends ClasstableBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classtable_page_container);
        fitSystemWindow();
        //fake();
        if(Store.isNotification(this))
            ClasstableNotification.start(this);
        if(!Store.isLocalHaveData(this))
            holderIn(new WBY_HubLoginPageHolder(this));
        else {
            AllClasses allClasses = Store.getLocalData(this);
            if(allClasses == null)
                holderIn(new WBY_HubLoginPageHolder(this));
            else
                holderIn(new ClassPageHolder(this,allClasses));
        }

    }

    /**
     * hub系统会崩溃，就用这个方法设置一些测试数据
     */
    private void fake() {
        SharedPreferences sharedPreferences = getSharedPreferences(Store.SH_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
      //  editor.putString("add_by_myself_data",null);
        editor.putBoolean("save_success",true);
        editor.putString("saved_class_table",getResources().getString(R.string.fake_data));
        editor.commit();
    }

    @Override
    public boolean onKeyDown(int code, KeyEvent event){
        if(code == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if(backTwice())
                back();
            back();
            return true;
        }else{
            return super.onKeyDown(code,event);
        }
    }

    @Override
    public void onBackPressed(){
        if(backTwice())
            back();
        back();
        super.onBackPressed();
    }
}
