package com.example.bon.project_7;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;


public class SitterLoginActivity extends AppCompatActivity implements View.OnClickListener {
//    public static final String USER_ID = "USER_ID";
//    public static final String PASSWORD = "PASSWORD";
    private static final String LOGIN_URL = "http://133.130.99.167/mimamo/public/auth/applogin";
//    http://10.0.2.2/gps/gps_insert.php
//    private static final String TAG_RESULTS = "result";
//    private static final String TAG_ID = "id";
//    private static final String TAG_PASS = "title";

    private BackPressCloseHandler backPressCloseHandler;


    JSONArray user = null;
    ArrayList<HashMap<String, String>> personList;

    private EditText userId;
    private EditText userPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sitter_login_main);
//        NetworkUtil.setNetworkPolicy();
        userId = (EditText) findViewById(R.id.loginId);
        userPass = (EditText) findViewById(R.id.loginPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        backPressCloseHandler = new BackPressCloseHandler(this);

    }

    private void login() {
        String userid = userId.getText().toString().trim();
        String userpass = userPass.getText().toString().trim();

        if (userid.toString().length() == 0) {
            Toast.makeText(SitterLoginActivity.this, "アイディーを入力してください", Toast.LENGTH_SHORT).show();
        } else if (userpass.toString().length() == 0) {
            Toast.makeText(SitterLoginActivity.this, "パスワードを入力してください", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
            SharedPreferences.Editor autoLogin = auto.edit();
            autoLogin.putString("inputId", userid);
            autoLogin.putString("inputPass", userpass);
            autoLogin.commit();
            userLogin(userid,userpass);
        }


    }

    private void userLogin (final String userid, final String userpass) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SitterLoginActivity.this, "Please Wait", null, true, true);
                loading.dismiss();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equalsIgnoreCase("success")) {
                    Intent intent = new Intent(SitterLoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(SitterLoginActivity.this,"ログイン成功しました。",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SitterLoginActivity.this,s,Toast.LENGTH_LONG).show();

                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("id", params[0]);
                data.put("pw", params[1]);

                Log.e("asdasd",data.toString());

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(LOGIN_URL,data);
                return result;
            }

        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(userid,userpass);
    }
    @Override
    public void onClick(View v) {
        if(v==btnLogin) {
            login();
        }
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

}



//btnLogin = (Button) findViewById(R.id.btnLogin);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Log.e("Userid", userId.getText().toString());
//                    Log.e("Userpass", userPass.getText().toString());
//                    PHPRequest request = new PHPRequest("http://133.130.99.167/mimamo/public/auth/applogin");
////                    String result = request.PhPtest1(String.valueOf(userId),String.valueOf(userPass));
//                    String result = request.PhPtest1(userId.getText().toString(),userPass.getText().toString());
//                    Log.e("asdasd",result.toString());
//
//                    if(result.equals("1")) {
//
//                        Log.i("UserId", "들어감");
//                        Log.i("UserPass", "들어감");
//                    } else {
//                        Log.i("asdasd","안들어감!");
//                    }
//                }catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//            }
//        });