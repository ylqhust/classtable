package com.ylq.library.query;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * > {
 * > code: 200,
 * > msg: "success",
 * > data: [
 * >     {
 * >         id: "00ed38ad441431f72d",
 * >         name: "理论力学",
 * >         teacher: "",
 * >         units: [
 * >             {
 * >                 dayofweek: 4,
 * >                 timeslots: "3,4",
 * >                 place: "西十二-N310",
 * >                 weeks: "1-18"
 * >             },
 * >             {
 * >                 dayofweek: 2,
 * >                 timeslots: "3,4",
 * >                 place: "西十二-N310",
 * >                 weeks: "1-19"
 * >             }
 * >         ]
 * >     },
 * >     {
 * >         id: "093bf06a4dd151f6e2",
 * >         name: "大学体育（三）",
 * >         teacher: "",
 * >         units: [
 * >             {
 * >                 dayofweek: 5,
 * >                 timeslots: "5,6",
 * >                 place: "西操场",
 * >                 weeks: "5-19"
 * >             }
 * >         ]
 * >     },
 * >     ...
 * >     ]
 * > }
 *
 * @author 王博煜
 * @time 2015/8/2 0002
 * @email bijiaoshenqi@qq.com
 */
public class CourseBean {
    @SerializedName("code") protected int code;
    @SerializedName("msg") protected String msg;
    @SerializedName("data") protected ArrayList<Data> datas;

    public static class Data {
        @SerializedName("id") protected String id;
        @SerializedName("name") protected String name;
        @SerializedName("teacher") protected String teacher;
        @SerializedName("units") protected ArrayList<Unit> units;

        public static class Unit {
            @SerializedName("dayofweek") protected int dayOfWeek;
            @SerializedName("timeslots") protected String timeslots;
            @SerializedName("place") protected String place;
            @SerializedName("weeks") protected String weeks;

            public int getDayOfWeek() {
                return dayOfWeek;
            }

            public void setDayOfWeek(int dayOfWeek) {
                this.dayOfWeek = dayOfWeek;
            }

            public String getTimeslots() {
                return timeslots;
            }

            public void setTimeslots(String timeslots) {
                this.timeslots = timeslots;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getWeeks() {
                return weeks;
            }

            public void setWeeks(String weeks) {
                this.weeks = weeks;
            }
        }

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

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public ArrayList<Unit> getUnits() {
            return units;
        }

        public void setUnits(ArrayList<Unit> units) {
            this.units = units;
        }
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

    public ArrayList<Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<Data> datas) {
        this.datas = datas;
    }
}
