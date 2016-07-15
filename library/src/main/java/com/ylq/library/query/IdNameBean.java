package com.ylq.library.query;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author 王博煜
 * @time 2015/8/2 0002
 * @email bijiaoshenqi@qq.com
 */
public class IdNameBean {
    @SerializedName("code") protected int code;
    @SerializedName("msg") protected String msg;
    @SerializedName("data") protected ArrayList<Data> datas;

    public static class Data {
        @SerializedName("id") protected String id;
        @SerializedName("name") protected String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public ArrayList<Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<Data> datas) {
        this.datas = datas;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
