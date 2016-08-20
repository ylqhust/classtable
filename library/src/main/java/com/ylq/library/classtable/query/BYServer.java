package com.ylq.library.classtable.query;

import retrofit.http.*;
import retrofit.http.Query;

/**
 * Created by ylq on 16/8/14.
 */
public interface BYServer {
    public static final String ENDPOINT = "https://api.hustonline.net/course";

    @POST("/upload")
    void upload(@Query("json") String jsonData,retrofit.Callback<String> callback);

}


