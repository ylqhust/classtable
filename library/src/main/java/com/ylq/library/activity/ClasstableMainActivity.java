package com.ylq.library.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ylq.library.ClasstableNotification;
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
     * hub系统会崩溃，就是用这个方法设置一些测试数据
     */
    private void fake() {
        SharedPreferences sharedPreferences = getSharedPreferences(Store.SH_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("save_success",true);
        editor.putString("saved_class_table","[{\"date7\":\"9.4\",\"season\":\"summer\",\"date6\":\"9.3\",\"date3\":\"8.31\",\"date2\":\"8.30\",\"date5\":\"9.2\",\"date4\":\"9.1\",\"date1\":\"8.29\",\"array\":[{\"month\":8,\"year\":2016,\"day\":29,\"array\":[{\"color\":0,\"times\":8,\"month\":8,\"day\":29,\"from\":1,\"class_name\":\"数字电路与逻辑课程设计\",\"week\":1,\"class_address\":\"南一楼807_1\"}],\"week\":1},{\"month\":8,\"year\":2016,\"day\":30,\"array\":[{\"color\":0,\"times\":8,\"month\":8,\"day\":30,\"from\":1,\"class_name\":\"数字电路与逻辑课程设计\",\"week\":2,\"class_address\":\"南一楼807_1\"}],\"week\":2},{\"month\":8,\"year\":2016,\"day\":31,\"array\":[{\"color\":0,\"times\":8,\"month\":8,\"day\":31,\"from\":1,\"class_name\":\"数字电路与逻辑课程设计\",\"week\":3,\"class_address\":\"南一楼807_1\"}],\"week\":3},{\"month\":9,\"year\":2016,\"day\":1,\"array\":[{\"color\":0,\"times\":8,\"month\":9,\"day\":1,\"from\":1,\"class_name\":\"数字电路与逻辑课程设计\",\"week\":4,\"class_address\":\"南一楼807_1\"}],\"week\":4},{\"month\":9,\"year\":2016,\"day\":2,\"array\":[{\"color\":0,\"times\":8,\"month\":9,\"day\":2,\"from\":1,\"class_name\":\"数字电路与逻辑课程设计\",\"week\":5,\"class_address\":\"南一楼807_1\"}],\"week\":5},{\"month\":9,\"year\":2016,\"day\":3,\"array\":[],\"week\":6},{\"month\":9,\"year\":2016,\"day\":4,\"array\":[],\"week\":7}]},{\"date7\":\"9.11\",\"season\":\"summer\",\"date6\":\"9.10\",\"date3\":\"9.7\",\"date2\":\"9.6\",\"date5\":\"9.9\",\"date4\":\"9.8\",\"date1\":\"9.5\",\"array\":[{\"month\":9,\"year\":2016,\"day\":5,\"array\":[{\"color\":0,\"times\":8,\"month\":9,\"day\":5,\"from\":1,\"class_name\":\"数字电路与逻辑课程设计\",\"week\":1,\"class_address\":\"南一楼807_1\"}],\"week\":1},{\"month\":9,\"year\":2016,\"day\":6,\"array\":[{\"color\":0,\"times\":8,\"month\":9,\"day\":6,\"from\":1,\"class_name\":\"数字电路与逻辑课程设计\",\"week\":2,\"class_address\":\"南一楼807_1\"}],\"week\":2},{\"month\":9,\"year\":2016,\"day\":7,\"array\":[{\"color\":0,\"times\":8,\"month\":9,\"day\":7,\"from\":1,\"class_name\":\"数字电路与逻辑课程设计\",\"week\":3,\"class_address\":\"南一楼807_1\"}],\"week\":3},{\"month\":9,\"year\":2016,\"day\":8,\"array\":[{\"color\":0,\"times\":8,\"month\":9,\"day\":8,\"from\":1,\"class_name\":\"数字电路与逻辑课程设计\",\"week\":4,\"class_address\":\"南一楼807_1\"}],\"week\":4},{\"month\":9,\"year\":2016,\"day\":9,\"array\":[{\"color\":0,\"times\":8,\"month\":9,\"day\":9,\"from\":1,\"class_name\":\"数字电路与逻辑课程设计\",\"week\":5,\"class_address\":\"南一楼807_1\"}],\"week\":5},{\"month\":9,\"year\":2016,\"day\":10,\"array\":[],\"week\":6},{\"month\":9,\"year\":2016,\"day\":11,\"array\":[],\"week\":7}]},{\"date7\":\"9.18\",\"season\":\"summer\",\"date6\":\"9.17\",\"date3\":\"9.14\",\"date2\":\"9.13\",\"date5\":\"9.16\",\"date4\":\"9.15\",\"date1\":\"9.12\",\"array\":[{\"month\":9,\"year\":2016,\"day\":12,\"array\":[{\"color\":1,\"times\":2,\"month\":9,\"day\":12,\"from\":1,\"class_name\":\"编译原理\",\"week\":1,\"class_address\":\"西十二楼N411\"},{\"color\":2,\"times\":2,\"month\":9,\"day\":12,\"from\":3,\"class_name\":\"数值分析\",\"week\":1,\"class_address\":\"西十二N411\"},{\"color\":3,\"times\":2,\"month\":9,\"day\":12,\"from\":5,\"class_name\":\"面向对象程序设计\",\"week\":1,\"class_address\":\"西十二N411\"}],\"week\":1},{\"month\":9,\"year\":2016,\"day\":13,\"array\":[{\"color\":4,\"times\":2,\"month\":9,\"day\":13,\"from\":1,\"class_name\":\"算法设计与分析\",\"week\":2,\"class_address\":\"西十二N411\"},{\"color\":5,\"times\":2,\"month\":9,\"day\":13,\"from\":9,\"class_name\":\"社会网络与计算\",\"week\":2,\"class_address\":\"西十二N411\"}],\"week\":2},{\"month\":9,\"year\":2016,\"day\":14,\"array\":[{\"color\":2,\"times\":2,\"month\":9,\"day\":14,\"from\":3,\"class_name\":\"数值分析\",\"week\":3,\"class_address\":\"西十二N411\"},{\"color\":1,\"times\":2,\"month\":9,\"day\":14,\"from\":3,\"class_name\":\"编译原理\",\"week\":3,\"class_address\":\"西十二楼N411\"},{\"color\":3,\"times\":2,\"month\":9,\"day\":14,\"from\":5,\"class_name\":\"面向对象程序设计\",\"week\":3,\"class_address\":\"西十二N411\"}],\"week\":3},{\"month\":9,\"year\":2016,\"day\":15,\"array\":[],\"week\":4},{\"month\":9,\"year\":2016,\"day\":16,\"array\":[{\"color\":3,\"times\":4,\"month\":9,\"day\":16,\"from\":1,\"class_name\":\"面向对象程序设计\",\"week\":5,\"class_address\":\"西十二N411\"}],\"week\":5},{\"month\":9,\"year\":2016,\"day\":17,\"array\":[],\"week\":6},{\"month\":9,\"year\":2016,\"day\":18,\"array\":[],\"week\":7}]},{\"date7\":\"9.25\",\"season\":\"summer\",\"date6\":\"9.24\",\"date3\":\"9.21\",\"date2\":\"9.20\",\"date5\":\"9.23\",\"date4\":\"9.22\",\"date1\":\"9.19\",\"array\":[{\"month\":9,\"year\":2016,\"day\":19,\"array\":[{\"color\":1,\"times\":2,\"month\":9,\"day\":19,\"from\":1,\"class_name\":\"编译原理\",\"week\":1,\"class_address\":\"西十二楼N411\"},{\"color\":2,\"times\":2,\"month\":9,\"day\":19,\"from\":3,\"class_name\":\"数值分析\",\"week\":1,\"class_address\":\"西十二N411\"},{\"color\":3,\"times\":2,\"month\":9,\"day\":19,\"from\":5,\"class_name\":\"面向对象程序设计\",\"week\":1,\"class_address\":\"西十二N411\"}],\"week\":1},{\"month\":9,\"year\":2016,\"day\":20,\"array\":[{\"color\":4,\"times\":2,\"month\":9,\"day\":20,\"from\":1,\"class_name\":\"算法设计与分析\",\"week\":2,\"class_address\":\"西十二N411\"},{\"color\":5,\"times\":2,\"month\":9,\"day\":20,\"from\":9,\"class_name\":\"社会网络与计算\",\"week\":2,\"class_address\":\"西十二N411\"}],\"week\":2},{\"month\":9,\"year\":2016,\"day\":21,\"array\":[{\"color\":2,\"times\":2,\"month\":9,\"day\":21,\"from\":1,\"class_name\":\"数值分析\",\"week\":3,\"class_address\":\"西十二N411\"},{\"color\":1,\"times\":2,\"month\":9,\"day\":21,\"from\":3,\"class_name\":\"编译原理\",\"week\":3,\"class_address\":\"西十二楼N411\"},{\"color\":3,\"times\":2,\"month\":9,\"day\":21,\"from\":5,\"class_name\":\"面向对象程序设计\",\"week\":3,\"class_address\":\"西十二N411\"}],\"week\":3},{\"month\":9,\"year\":2016,\"day\":22,\"array\":[{\"color\":4,\"times\":2,\"month\":9,\"day\":22,\"from\":7,\"class_name\":\"算法设计与分析\",\"week\":4,\"class_address\":\"西十二N411\"}],\"week\":4},{\"month\":9,\"year\":2016,\"day\":23,\"array\":[{\"color\":3,\"times\":4,\"month\":9,\"day\":23,\"from\":1,\"class_name\":\"面向对象程序设计\",\"week\":5,\"class_address\":\"西十二N411\"}],\"week\":5},{\"month\":9,\"year\":2016,\"day\":24,\"array\":[],\"week\":6},{\"month\":9,\"year\":2016,\"day\":25,\"array\":[],\"week\":7}]},{\"date7\":\"10.2\",\"season\":\"summer\",\"date6\":\"10.1\",\"date3\":\"9.28\",\"date2\":\"9.27\",\"date5\":\"9.30\",\"date4\":\"9.29\",\"date1\":\"9.26\",\"array\":[{\"month\":9,\"year\":2016,\"day\":26,\"array\":[{\"color\":1,\"times\":2,\"month\":9,\"day\":26,\"from\":1,\"class_name\":\"编译原理\",\"week\":1,\"class_address\":\"西十二楼N411\"},{\"color\":2,\"times\":2,\"month\":9,\"day\":26,\"from\":3,\"class_name\":\"数值分析\",\"week\":1,\"class_address\":\"西十二N411\"},{\"color\":3,\"times\":2,\"month\":9,\"day\":26,\"from\":5,\"class_name\":\"面向对象程序设计\",\"week\":1,\"class_address\":\"西十二N411\"}],\"week\":1},{\"month\":9,\"year\":2016,\"day\":27,\"array\":[{\"color\":4,\"times\":2,\"month\":9,\"day\":27,\"from\":1,\"class_name\":\"算法设计与分析\",\"week\":2,\"class_address\":\"西十二N411\"},{\"color\":5,\"times\":2,\"month\":9,\"day\":27,\"from\":3,\"class_name\":\"操作系统原理\",\"week\":2,\"class_address\":\"西十二N411\"},{\"color\":5,\"times\":2,\"month\":9,\"day\":27,\"from\":9,\"class_name\":\"社会网络与计算\",\"week\":2,\"class_address\":\"西十二N411\"}],\"week\":2},{\"month\":9,\"year\":2016,\"day\":28,\"array\":[{\"color\":2,\"times\":2,\"month\":9,\"day\":28,\"from\":1,\"class_name\":\"数值分析\",\"week\":3,\"class_address\":\"西十二N411\"},{\"color\":1,\"times\":2,\"month\":9,\"day\":28,\"from\":3,\"class_name\":\"编译原理\",\"week\":3,\"class_address\":\"西十二楼N411\"},{\"color\":3,\"times\":2,\"month\":9,\"day\":28,\"from\":5,\"class_name\":\"面向对象程序设计\",\"week\":3,\"class_address\":\"西十二N411\"}],\"week\":3},{\"month\":9,\"year\":2016,\"day\":29,\"array\":[{\"color\":5,\"times\":2,\"month\":9,\"day\":29,\"from\":5,\"class_name\":\"操作系统原理\",\"week\":4,\"class_address\":\"西十二N411\"},{\"color\":4,\"times\":2,\"month\":9,\"day\":29,\"from\":7,\"class_name\":\"算法设计与分析\",\"week\":4,\"class_address\":\"西十二N411\"}],\"week\":4},{\"month\":9,\"year\":2016,\"day\":30,\"array\":[{\"color\":3,\"times\":4,\"month\":9,\"day\":30,\"from\":1,\"class_name\":\"面向对象程序设计\",\"week\":5,\"class_address\":\"西十二N411\"}],\"week\":5},{\"month\":10,\"year\":2016,\"day\":1,\"array\":[],\"week\":6},{\"month\":10,\"year\":2016,\"day\":2,\"array\":[],\"week\":7}]},{\"date7\":\"10.9\",\"season\":\"winter\",\"date6\":\"10.8\",\"date3\":\"10.5\",\"date2\":\"10.4\",\"date5\":\"10.7\",\"date4\":\"10.6\",\"date1\":\"10.3\",\"array\":[{\"month\":10,\"year\":2016,\"day\":3,\"array\":[],\"week\":1},{\"month\":10,\"year\":2016,\"day\":4,\"array\":[{\"color\":4,\"times\":2,\"month\":10,\"day\":4,\"from\":1,\"class_name\":\"算法设计与分析\",\"week\":2,\"class_address\":\"西十二N411\"},{\"color\":5,\"times\":2,\"month\":10,\"day\":4,\"from\":3,\"class_name\":\"操作系统原理\",\"week\":2,\"class_address\":\"西十二N411\"},{\"color\":5,\"times\":2,\"month\":10,\"day\":4,\"from\":9,\"class_name\":\"社会网络与计算\",\"week\":2,\"class_address\":\"西十二N411\"}],\"week\":2},{\"month\":10,\"year\":2016,\"day\":5,\"array\":[{\"color\":2,\"times\":2,\"month\":10,\"day\":5,\"from\":1,\"class_name\":\"数值分析\",\"week\":3,\"class_address\":\"西十二N411\"},{\"color\":1,\"times\":2,\"month\":10,\"day\":5,\"from\":3,\"class_name\":\"编译原理\",\"week\":3,\"class_address\":\"西十二楼N411\"},{\"color\":3,\"times\":2,\"month\":10,\"day\":5,\"from\":5,\"class_name\":\"面向对象程序设计\",\"week\":3,\"class_address\":\"西十二N411\"}],\"week\":3},{\"month\":10,\"year\":2016,\"day\":6,\"array\":[{\"color\":5,\"times\":2,\"month\":10,\"day\":6,\"from\":5,\"class_name\":\"操作系统原理\",\"week\":4,\"class_address\":\"西十二N411\"},{\"color\":4,\"times\":2,\"month\":10,\"day\":6,\"from\":7,\"class_name\":\"算法设计与分析\",\"week\":4,\"class_address\":\"西十二N411\"}],\"week\":4},{\"month\":10,\"year\":2016,\"day\":7,\"array\":[{\"color\":3,\"times\":4,\"month\":10,\"day\":7,\"from\":1,\"class_name\":\"面向对象程序设计\",\"week\":5,\"class_address\":\"西十二N411\"}],\"week\":5},{\"month\":10,\"year\":2016,\"day\":8,\"array\":[],\"week\":6},{\"month\":10,\"year\":2016,\"day\":9,\"array\":[],\"week\":7}]}]");
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
