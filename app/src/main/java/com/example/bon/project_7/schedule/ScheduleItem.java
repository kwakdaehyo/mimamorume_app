package com.example.bon.project_7.schedule;

/**
 * Created by a on 2017-08-07.
 */

public class ScheduleItem{

    ScheduleItem(String aDay, String aSub, boolean atest){
        day = aDay;
        sub = aSub;
        test = atest;
    }
    String day;
    String sub;
    Boolean test = false;
}

//    ScheduleItem(String ayear , String amonth ,String aDay, String aSub, boolean atest){
//        year = ayear;
//        month = amonth;
//        day = aDay;
//        sub = aSub;
//        test = atest;
//    }
//    String year;
//    String month;
//    String day;
//    String sub;
//    Boolean test = false;
//}
