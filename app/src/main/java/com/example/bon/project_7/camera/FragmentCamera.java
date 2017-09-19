package com.example.bon.project_7.camera;

/**
 * Created by kdh on 2017-04-10.
 */


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bon.project_7.NetworkUtil;
import com.example.bon.project_7.PHPRequest;
import com.example.bon.project_7.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import static android.R.attr.editable;


public class FragmentCamera extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public void adsd(){
       //ㅋ 못쓰게 해놧네ㅐ
        // 일단 this = Fragment or SwipeRefreshLayout.OnRefreshListener
        // 요넘 들임 ㅇㅋ? 아 상속한거나 implem한거?ㅇㅇ
        // 걔들한테 잇는거 쓸수잇음 thisㄴ ㅇㅋㅇㅋ아 ㅋㅋㅋ 잠만
    }

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    ArrayList<DataAdapter> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerViewAdapter recyclerViewadapter;

    String GET_JSON_DATA_HTTP_URL = "http://133.130.99.167/mimamo/public/imageSend";
    String JSON_TAGET_NAME = "user_name";
    String JSON_SNAPSHOT_TYPE = "snapshot_type";
    String JSON_SNAPSHOT_DATE = "date";
    String JSON_IMAGE_URL = "image_url";

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    //    //  TCP연결 관련
    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private int port = 5227;
    private final String ip = "192.168.43.245"; //133.130.99.167
    private MyHandler myHandler;
    private MyThread myThread;

    EditText targetSearch;
    TextView tv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, null);

        GetDataAdapter1 = new ArrayList<DataAdapter>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview1);
        targetSearch = (EditText) view.findViewById(R.id.search_box);
        tv = (TextView) view.findViewById(R.id.empty_text);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         mSwipeRefreshLayout.setRefreshing(true);

                                         JSON_DATA_WEB_CALL();
                                     }
                                 }
        );



//        JSON_DATA_WEB_CALL();

//        btn = (Button) view.findViewById(R.id.cameraBtn);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//        });
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("11111111","111111111111111");
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                Log.e("2222222222","222222222222222");
                StrictMode.setThreadPolicy(policy);
                Log.e("333333333","333333333333333");
                try {
                    Log.e("44444444444","44444444444");
                    clientSocket = new Socket(ip, port);
                    Log.e("5555555555","55555555555555");
                    socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    Log.e("66666666666","6666666666666");
                    socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
                    Log.e("77777777777","7777777777777");
//                    clientSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                socketOut.println("123");
                Log.e("123","123");
                Snackbar.make(view, "遠隔撮影を成功しました。.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getContext());
        recyclerView.setAdapter(recyclerViewadapter);



        return view;
    }


    @Override
    public void onRefresh() {
        JSON_DATA_WEB_CALL();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void JSON_DATA_WEB_CALL() {
        mSwipeRefreshLayout.setRefreshing(true);

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() > 0) {
                            GetDataAdapter1.clear();

                            for (int i = 0; i < response.length(); i++) {

                                DataAdapter GetDataAdapter2 = new DataAdapter();
                                  JSONObject json = null;
                                try {

                                    json = response.getJSONObject(i);

                                    Log.e("asdasd", json.toString());
                                    GetDataAdapter2.setTargetName(json.getString(JSON_TAGET_NAME));
                                    GetDataAdapter2.setCameraName(json.getString(JSON_SNAPSHOT_TYPE));
                                    GetDataAdapter2.setCameraDate(json.getString(JSON_SNAPSHOT_DATE));
                                    GetDataAdapter2.setImageUrl("http://133.130.99.167/mimamo/public/images/monitor/snapShot/" + json.getString(JSON_IMAGE_URL));
                                    Log.e("asdasd", GetDataAdapter2.toString());

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }

                                GetDataAdapter1.add(GetDataAdapter2);
                            }
                            recyclerViewadapter.updateData(GetDataAdapter1);

                            Log.d("GetDataAdapter1Size : ", String.valueOf(GetDataAdapter1.size()));
                            recyclerViewadapter.notifyDataSetChanged();

                            targetSearch.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged (CharSequence charSequence,int i, int i1, int i2){

                                }

                                @Override
                                public void onTextChanged (CharSequence charSequence,int i, int i1, int i2){

                                }

                                @Override
                                public void afterTextChanged (Editable editable){
                                    Log.e("addTextChangedListener","확인1");
                                    String text = targetSearch.getText().toString().toLowerCase(Locale.getDefault());
                                    Log.e("addTextChangedListener","확인2");
                                    recyclerViewadapter.filter(text);
                                }
                            });
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

        requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(jsonArrayRequest);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    // InputStream의 값을 읽어와서 data에 저장
                    String data = socketIn.readLine();
                    // Message 객체를 생성, 핸들러에 정보를 보낼 땐 이 메세지 객체를 이용
                    Message msg = myHandler.obtainMessage();
                    msg.obj = data;
                    myHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            tv.setText(msg.obj.toString());
        }
    }
}


