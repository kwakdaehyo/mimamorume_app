package com.example.bon.project_7.work;

/**
 * Created by kdh on 2017-04-10.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bon.project_7.R;
import com.example.bon.project_7.SitterLoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


public class FragmentWork extends Fragment {
    String myJSON;

//    private static final String TAG_RESULTS = "string";
    private static final String TAG_RESULTS = "log";
    private static final String TAG_NUM = "num";
    private static final String TAG_SITTER_ID = "sitter_id";
    private static final String TAG_TARGET_NUM = "target_num";
    private static final String TAG_WORK_DATE = "work_date";
    private static final String TAG_CREATED_AT ="created_at";
    private static final String TAG_LOG_NUM = "log_num";
    private static final String TAG_CONTENT_TYPE = "content_type";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_NAME = "name";
    private static final String TAG_MEDICINE_NAME = "medicine_name";
    private static final String TAG_START_DATE = "start_date";
    private static final String TAG_END_DATE = "end_date";
    private static final String TAG_TIME = "time";
    private static final String MyPREFERENCES="auto" ;

    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    String loginId;
    TextView tv;
    ListView listWork;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, null);
        listWork = (ListView) view.findViewById(R.id.listWork);
        tv = (TextView) view.findViewById(R.id.empty_text);
        listWork.setEmptyView(tv);
        personList = new ArrayList<HashMap<String, String>>();
        SharedPreferences auto = getActivity().getSharedPreferences("auto", 0);
        loginId = auto.getString("inputId", null);
        Log.e("assdasdasd" , loginId);
        getData("http://133.130.99.167/mimamo/public/appIndex?id=" + loginId);

        return view;

    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String num  = c.getString(TAG_NUM);
                String sitter = c.getString(TAG_SITTER_ID);
                String start_date = c.getString(TAG_START_DATE);
                String target = c.getString(TAG_TARGET_NUM);
                String workDate = c.getString(TAG_WORK_DATE);
                String created  = c.getString(TAG_CREATED_AT);
                String log = c.getString(TAG_LOG_NUM);
                String contentType = c.getString(TAG_CONTENT_TYPE);
                String content = c.getString(TAG_CONTENT);
                String name = c.getString(TAG_NAME);
                String medicine = c.getString(TAG_MEDICINE_NAME);
                String end_data = c.getString(TAG_END_DATE);
                String time = c.getString(TAG_TIME);

//                Log.e("asdasd", title);
//                Log.e("asdasd", content);
//                Log.e("asdasd", place);


                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_NUM, num);
                persons.put(TAG_SITTER_ID, sitter);
                persons.put(TAG_START_DATE, start_date);
                persons.put(TAG_TARGET_NUM, target);
                persons.put(TAG_WORK_DATE, workDate);
                persons.put(TAG_CREATED_AT, created);
                persons.put(TAG_LOG_NUM, log);
                persons.put(TAG_CONTENT_TYPE, contentType);
                persons.put(TAG_CONTENT, content);
                persons.put(TAG_NAME, name);
                persons.put(TAG_MEDICINE_NAME, medicine);
                persons.put(TAG_END_DATE, end_data);
                persons.put(TAG_TIME,time);

                persons.put(TAG_START_DATE, start_date);
                personList.add(persons);
            }

            final ListAdapter adapter = new SimpleAdapter(
                    getContext(), personList, R.layout.work_item,
                    new String[]{TAG_NUM, TAG_SITTER_ID, TAG_WORK_DATE},
                    new int[] {R.id.num, R.id.sitter, R.id.work_date}
            );

            listWork.setAdapter(adapter);
            Log.e("asdasd",listWork.toString());
            //리스트 아이템 클릭 이벤트
            listWork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
                    //HashMap에 담겨 있는 리스트 아이템을 가져옴
                    HashMap<String, String> persons = (HashMap<String, String>) adapter.getItem(position);
                    //인텐트 시 workcontent 액티비티 호출
                    Intent intent = new Intent(getContext(), WorkContent.class);
                    //HashMap에 키 값에 담겨 있는 데이터를 workcontent 액티비티로 전송
                    intent.putExtra("num", persons.get(TAG_NUM));
                    intent.putExtra("sitter_id", persons.get(TAG_SITTER_ID));
                    intent.putExtra("target_num", persons.get(TAG_TARGET_NUM));
                    intent.putExtra("work_date", persons.get(TAG_WORK_DATE));
                    intent.putExtra("created_at", persons.get(TAG_CREATED_AT));
                    intent.putExtra("log_num", persons.get(TAG_LOG_NUM));
                    intent.putExtra("content_type", persons.get(TAG_CONTENT_TYPE));
                    intent.putExtra("content", persons.get(TAG_CONTENT));
                    intent.putExtra("name", persons.get(TAG_NAME));
                    intent.putExtra("medicine_name", persons.get(TAG_MEDICINE_NAME));
                    intent.putExtra("start_date", persons.get(TAG_START_DATE));
                    intent.putExtra("end_date", persons.get(TAG_END_DATE));
                    intent.putExtra("time", persons.get(TAG_TIME));
                    startActivity(intent);

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    Log.e("asdasd",bufferedReader.toString());
                    String json;

                    while ((json = bufferedReader.readLine()) != null) {
                        Log.i("json loading", "readLine success");
                        Log.e("asdad",json.toString());

                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}




















