package com.example.bon.project_7;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bon.project_7.target.TargetGps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by kdh on 2017-03-27.
 */

public class LodingActivity extends Activity {
    //    public static final String USER_ID = "USER_ID";
//    public static final String PASSWORD = "PASSWORD";
    private static final String LOGIN_URL = "http://133.130.99.167/mimamo/public/auth/applogin";
//    http://10.0.2.2/gps/gps_insert.php
//    private static final String TAG_RESULTS = "result";
//    private static final String TAG_ID = "id";
//    private static final String TAG_PASS = "title";


    JSONArray user = null;
    ArrayList<HashMap<String, String>> personList;


    private Button btnLogin;
    String userid, userpass;
    String loginId, loginPass, targetNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//        NetworkUtil.setNetworkPolicy();

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        loginId = auto.getString("inputId", null);
        loginPass = auto.getString("inputPass", null);
        targetNumber = auto.getString("TargetNumber", null);

        userLogin(userid, userpass);
    }


    private void userLogin(final String userid, final String userpass) {
        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equalsIgnoreCase("success")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);
                    Toast.makeText(LodingActivity.this, loginId + "自動ログイン", Toast.LENGTH_SHORT).show();

                } else if (targetNumber != null) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), TargetGps.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);
                    Toast.makeText(LodingActivity.this, "対象者様自動ログイン", Toast.LENGTH_SHORT).show();

                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), ExplanationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("id", loginId);
                data.put("pw", loginPass);

                Log.e("id", data.toString());

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(LOGIN_URL, data);
                return result;
            }

        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(userid, userpass);
    }

}



