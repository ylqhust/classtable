package com.ylq.library.query;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HubBean {
    public ArrayList<Data> datas;

    public static class Data {
        @SerializedName("start") public String start;
        @SerializedName("end") public String end;
        @SerializedName("title") public String title;
        @SerializedName("txt") public String txt;
        @SerializedName("allDay") public boolean allDay;

        public Txt formattedTxt;
    }

    public static class Txt {
        @SerializedName("KCMC") public String name;
        @SerializedName("JSMC") public String location;
        @SerializedName("JGXM") public String teacher;
        @SerializedName("KTMC") public String belong;
    }
}
