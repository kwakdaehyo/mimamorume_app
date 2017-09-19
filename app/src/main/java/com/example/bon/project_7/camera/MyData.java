package com.example.bon.project_7.camera;

import java.util.Date;

/**
 * Created by hyo on 2017-08-24.
 */

public class MyData {
    public static String getToday(){
        Date d = new Date(System.currentTimeMillis());
        return d.toGMTString();
    }
}