package com.example.bon.project_7;

/**
 * Created by bon on 2017-04-20.
 */

import android.annotation.SuppressLint;
import android.os.StrictMode;
public class NetworkUtil {
    @SuppressLint("NewApi")
    static public void setNetworkPolicy() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
}
