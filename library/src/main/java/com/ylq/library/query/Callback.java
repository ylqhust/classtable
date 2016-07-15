package com.ylq.library.query;

import android.support.annotation.NonNull;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author 王博煜
 * @time 2015/8/2 0002
 * @email bijiaoshenqi@qq.com
 */
public interface Callback {

    void begin(@NonNull Who who);


    void hubCourseNoError(@NonNull Who who, @NonNull HubBean bean, @NonNull Response response);


    void canNotConnectToServer(@NonNull Who who, @NonNull RetrofitError error);


    void finish(@NonNull Who who);


}
