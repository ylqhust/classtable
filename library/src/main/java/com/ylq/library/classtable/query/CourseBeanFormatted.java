package com.ylq.library.classtable.query;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * @author 王博煜
 * @time 2015/8/16 0016
 * @email bijiaoshenqi@qq.com
 */
public class CourseBeanFormatted {
    public int code;
    public String msg;
    public ArrayList<Data> datas;

    public static class Data {
        public String id;
        public String name;
        public String teacher;
        public ArrayList<Unit> units;

        public static class Unit {
            public int dayOfWeek;
            public int sectionFrom;
            public int sectionTo;
            public String place;
            public int weekFrom;
            public int weekTo;
            public String belong; // 例如 电子1301-1304班 从hub上导入时有

            public String getBelong() {
                return belong;
            }

            public void setBelong(String belong) {
                this.belong = belong;
            }

            public int getDayOfWeek() {
                return dayOfWeek;
            }

            public void setDayOfWeek(int dayOfWeek) {
                this.dayOfWeek = dayOfWeek;
            }

            public int getSectionFrom() {
                return sectionFrom;
            }

            public void setSectionFrom(int sectionFrom) {
                this.sectionFrom = sectionFrom;
            }

            public int getSectionTo() {
                return sectionTo;
            }

            public void setSectionTo(int sectionTo) {
                this.sectionTo = sectionTo;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public int getWeekFrom() {
                return weekFrom;
            }

            public void setWeekFrom(int weekFrom) {
                this.weekFrom = weekFrom;
            }

            public int getWeekTo() {
                return weekTo;
            }

            public void setWeekTo(int weekTo) {
                this.weekTo = weekTo;
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

        public static Data fromCourseBeanData(@NonNull CourseBean.Data data) {
            Data dataFormatted = new Data();

            dataFormatted.id = data.getId();
            dataFormatted.name = data.getName();
            dataFormatted.teacher = data.getTeacher();
            dataFormatted.units = new ArrayList<>();

            for (CourseBean.Data.Unit unit : data.getUnits()) {
                String[] weeks = unit.weeks.split(",");
                for (String week : weeks) {
                    Unit unitFormatted = null;

                    String[] sections = unit.getTimeslots().split(",");
                    ArrayList<Integer> sectionList = new ArrayList<>();
                    for (String s : sections) {
                        sectionList.add(Integer.parseInt(s));
                    }
                    for (int i = 0; i < sectionList.size() - 1; i++) {
                        for (int j = i + 1; j < sectionList.size(); j++) {
                            int left = sectionList.get(i);
                            int right = sectionList.get(j);
                            if (left > right) {
                                sectionList.set(i, right);
                                sectionList.set(j, left);
                            }
                        }
                    }

                    for (int i = 0; i < sectionList.size(); ++i) {
                        final int section = sectionList.get(i);
                        if (unitFormatted == null || unitFormatted.sectionTo + 1 < section) {

                            unitFormatted = new Unit();
                            unitFormatted.dayOfWeek = unit.dayOfWeek;
                            unitFormatted.place = unit.place;

                            int slit = week.indexOf("-");
                            if (slit < 0) {
                                unitFormatted.weekFrom = Integer.parseInt(week);
                                unitFormatted.weekTo = unitFormatted.weekFrom;
                            } else {
                                unitFormatted.weekFrom = Integer.parseInt(week.substring(0, slit));
                                unitFormatted.weekTo = Integer.parseInt(week.substring(slit + 1));
                            }

                            unitFormatted.sectionFrom = section;
                            unitFormatted.sectionTo = section;

                            dataFormatted.units.add(unitFormatted);
                        } else {
                            unitFormatted.sectionTo = section;
                        }
                    }
                }
            }

            return dataFormatted;
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

    public static CourseBeanFormatted fromCourseBean(@Nullable CourseBean bean) {
        if (bean == null) return null;

        CourseBeanFormatted beanFormatted = new CourseBeanFormatted();
        beanFormatted.code = bean.code;
        beanFormatted.msg = bean.msg;
        beanFormatted.datas = new ArrayList<>();

        for (CourseBean.Data data : bean.datas) {
            beanFormatted.datas.add(Data.fromCourseBeanData(data));
        }

        return beanFormatted;
    }
}

