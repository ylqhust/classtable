package com.ylq.library.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.ylq.library.R;
import com.ylq.library.model.AllClasses;
import com.ylq.library.util.Store;
import com.ylq.library.viewHolder.ClassPageHolder;
import com.ylq.library.viewHolder.WBY_HubLoginPageHolder;


public class ClasstableMainActivity extends ClasstableBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classtable_page_container);
        fitSystemWindow();
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
