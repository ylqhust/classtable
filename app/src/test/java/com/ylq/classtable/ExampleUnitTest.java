package com.ylq.classtable;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void test() {
        HashMap<String,Integer> map = new HashMap<>();
        String s = new String("aaa");
        String s2 = new String("aaa");
        map.put(s,1);
        System.out.println(map.containsKey(s2));
    }

}