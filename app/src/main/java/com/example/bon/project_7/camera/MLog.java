package com.example.bon.project_7.camera;

import android.util.Log;

/**
 * Created by hyo on 2017-08-24.
 */

public class MLog  {

    static String TAG = "곽대효의 로그";

    public static final void e(String message) { Log.e(TAG, buildLogMsg(message)); } /** Log Level Warning **/
    public static final void w(String message) { Log.w(TAG, buildLogMsg(message)); } /** Log Level Information **/
    public static final void i(String message) { Log.i(TAG, buildLogMsg(message)); } /** Log Level Debug **/
    public static final void d(String message) { Log.d(TAG, buildLogMsg(message)); } /** Log Level Verbose **/
    public static final void v(String message) { Log.v(TAG, buildLogMsg(message)); }

    public static String buildLogMsg(String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));// 클래스 이름
        sb.append("::");
        sb.append(ste.getMethodName()); // 함수이름 // 라인 없네 ㅋ 어쩃든 클래스이름, 함수이름 같이찍힘 로그찍으면
        sb.append("]");
        sb.append(message); // 내용
        return sb.toString();
    }



}
