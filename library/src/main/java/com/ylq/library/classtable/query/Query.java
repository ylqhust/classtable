package com.ylq.library.classtable.query;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author 王博煜
 * @time 2015/8/2 0002
 * @email bijiaoshenqi@qq.com
 */
public class Query {
    private static final String TAG = Query.class.getSimpleName();
    private static final boolean DEBUG = true;
    private static Query sInstance = null;

    private HubServer mHubServer; // hub服务器

    private Query() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(HubServer.ENDPOINT)
                .setLogLevel(DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();
        mHubServer = adapter.create(HubServer.class);
    }

    @NonNull
    public static Query getInstance() {
        if (sInstance == null) {
            synchronized (Query.class) {
                if (sInstance == null) {
                    sInstance = new Query();
                }
            }
        }

        return sInstance;
    }


    /**
     * @param who
     * @param cookie
     */
    public void hubCourse(@NonNull final Who who, @NonNull String cookie, final Callback callback) {
        hubCourse(who, cookie, Config.getStartTimeInMillis(), Config.getEndTimeInMillis(), callback);
    }

    public void hubCourse(@NonNull final Who who, @NonNull String cookie, long start, long end, final Callback callback) {
        callback.begin(who);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        mHubServer.hubCourse(cookie, format.format(new Date(start)), format.format(new Date(end)), new retrofit.Callback<ArrayList<HubBean.Data>>() {

            @Override
            public void success(ArrayList<HubBean.Data> datas, Response response) {
                Gson gson = new Gson();
                HubBean hubBean = new HubBean();
                hubBean.datas = datas;


                for (HubBean.Data data : datas) {
                    data.formattedTxt = gson.fromJson(data.txt, HubBean.Txt.class);
                }
                callback.hubCourseNoError(who, hubBean, response);
                callback.finish(who);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.canNotConnectToServer(who, error);
                callback.finish(who);
            }
        });
    }
}
