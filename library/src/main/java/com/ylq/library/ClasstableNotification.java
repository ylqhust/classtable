package com.ylq.library;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.ylq.library.activity.ClasstableMainActivity;
import com.ylq.library.query.Config;
import com.ylq.library.util.Store;

import java.util.Calendar;

/**
 * Created by apple on 16/7/16.
 * http://my.oschina.net/tengda/blog/215512?p={{totalPage}}
 */
public class ClasstableNotification {

    public static final String CLASS_TORMMROW_NOTIFICATION = "com.ylq.classtalbe.notification";
    public static final int CLASS_REQUEST_CODE = 0x19971010;

    public static void start(Context context){
        //发送闹钟请求
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(CLASS_TORMMROW_NOTIFICATION);
        intent.setType(CLASS_TORMMROW_NOTIFICATION);
        intent.setData(Uri.EMPTY);
        intent.addCategory(CLASS_TORMMROW_NOTIFICATION);
        intent.setClass(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,CLASS_REQUEST_CODE, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long time = getTomorrow21_30();
        am.set(AlarmManager.RTC_WAKEUP,time, pendingIntent);
        System.out.println("CalsstableNotification->start()");
    }

    private static long getTomorrow21_30() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        calendar.set(year,month,date, Config.NOTIFICATION_HOUR,Config.NOTIFICATION_MINUTE);
        long time = calendar.getTimeInMillis();
        long one_day_time_mill = 24*60*60*1000;
        long current = System.currentTimeMillis();
        while(time<=current)
            time+=one_day_time_mill;
        return time;
    }

    public static void cancle(Context context){
        //取消闹钟请求
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(CLASS_TORMMROW_NOTIFICATION);
        intent.setType(CLASS_TORMMROW_NOTIFICATION);
        intent.setData(Uri.EMPTY);
        intent.addCategory(CLASS_TORMMROW_NOTIFICATION);
        intent.setClass(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, CLASS_REQUEST_CODE, intent, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
    }

    public static class AlarmReceiver extends BroadcastReceiver{
        private NotificationManager manager;
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("ClasstableNotification->AlarmReceiver->onReceiver");
            start(context);//开始明天的提醒
            if(!Store.isNotification(context))
                return;
            String s = Store.getTomorrowClasses(context);
            if(s==null)
                return;
            manager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            String id = intent.getStringExtra("id");
            Intent playIntent = new Intent(context, ClasstableMainActivity.class);
            playIntent.putExtra("id", id);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(context.getResources().getString(R.string.classtable_notification_title)).setContentText(s).setSmallIcon(R.mipmap.ic_launcher).setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true).setSubText("");
            manager.notify(1, builder.build());
        }
    }
}
