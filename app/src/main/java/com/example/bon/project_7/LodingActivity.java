package com.example.bon.project_7;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kdh on 2017-03-27.
 */

public class LodingActivity extends Activity{
    EditText userId;
    EditText userPass;
    Button btnLogin;
    phpDown task;
    TextView txtView;
    ArrayList<ListItem> listItem = new ArrayList<ListItem>();
    String lgId , lgPass;
    String loginId, loginPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        task = new phpDown();
        task.execute("http://10.0.2.2/user/select.php?id=test&pass=1234");
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        loginId = auto.getString("inputId",null);
        loginPass = auto.getString("inputPass",null);
    }
    private class phpDown extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... urls) {
            //문자열을 쉽게 수정하기 위해 사용한다
            StringBuilder jsonHtml = new StringBuilder();
            try {
                //연결 url 설정
                URL url = new URL(urls[0]);
                //커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //HttpURLConnection 객체를 통해서 특정 URL의 html코드를 읽음

                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    // 연결되었음 코드가 리턴되면
                    Log.i("getResponseCode",Integer.toString(conn.getResponseCode()));
                    Log.i("HttpURLConnection",Integer.toString(HttpURLConnection.HTTP_OK));
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        //conn.getResponseCode()의 Result 코드 값으로 연결상태를 확인
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        for (; ; ) {
                            //웹상에 보여지는 텍스트를 라인 단위로 읽어 저장
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여 넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception ex) {
                Log.e("LOG4 ERROR", ex.toString());
                ex.printStackTrace();
            }
            Log.i("jsonResult:",jsonHtml.toString());
            return jsonHtml.toString();
        }


        protected void onPostExecute(String str) {
            userId = (EditText) findViewById(R.id.loginId);
            userPass = (EditText) findViewById(R.id.loginPass);
            btnLogin = (Button) findViewById(R.id.btnLogin);

            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    lgId = jo.getString("id");
                    lgPass = jo.getString("pass");

                    listItem.add(new ListItem(lgId, lgPass));
                }
                if (loginId != null && loginPass != null) {
                    if (loginId.equals(lgId) && loginPass.equals(lgPass)) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getBaseContext(), NavigatActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                        Toast.makeText(LodingActivity.this, loginId + "자동 로그인", Toast.LENGTH_SHORT).show();
                    }
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
            } catch (JSONException e) {
                Log.e("LOG4 ERROR", e.toString());
                e.printStackTrace();
            }
        }
    }
    private void startLoading() {
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

