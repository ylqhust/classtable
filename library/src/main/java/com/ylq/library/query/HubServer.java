package com.ylq.library.query;

import java.util.ArrayList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface HubServer {
    String ENDPOINT = "http://s.hub.hust.edu.cn";

    /**
     * 从hub上抓课表
     *
     * @param cookie 登陆hub之后得到的cookie
     * @param start  yyyy-mm-dd
     * @param end    yyyy-mm-dd
     */
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
    })
    @POST("/aam/score/CourseInquiry_ido.action")
    void hubCourse(@Header("Cookie") String cookie, @Field("start") String start, @Field("end") String end, retrofit.Callback<ArrayList<HubBean.Data>> callback);
}
