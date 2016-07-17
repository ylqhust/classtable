package com.ylq.classtable;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    private byte[][] area = new byte[12][7];

    @Test
    public void test() throws JSONException {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("1", "x");
        JSONObject object1 = new JSONObject();
        object1.put("2", "xx");
        JSONObject object2 = new JSONObject();
        object2.put("3", "xxx");
        array.put(object).put(object1).put(object2);
        System.out.println(array.toString());
    }

}