package com.example.bon.project_7.matching;

/**
 * Created by kdh on 2017-04-10.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bon.project_7.BackPressCloseHandler;
import com.example.bon.project_7.R;
import com.example.bon.project_7.work.FragmentWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kdh on 2017-04-10.
 */


public class FragmentMatching extends Fragment {
    String myJSON;

    private BackPressCloseHandler backPressCloseHandler;

    private static final String TAG_RESULTS = "contract";
    private static final String TAG_FAMILY = "family_id";
    private static final String TAG_SITTER = "sitter_id";
    private static final String TAG_WOKR_WEEK = "work_week";
    private static final String TAG_WOKR_START = "work_start";
    private static final String TAG_WOKR_END = "work_end";
    private static final String TAG_WOKR_START_TIME = "work_start_time";
    private static final String TAG_WOKR_END_TIME = "work_end_time";
    private static final String TAG_FAMLIY_NAME = "name";
    private static final String TAG_SITTER_NAME = "name";

//    private static final String TAG_PLACE = "place";
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;

    ImageView imageView1;
    ListView listMatching;
    String loginId;
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matching, null);

        imageView1 = (ImageView) view.findViewById(R.id.matching_img);
        listMatching = (ListView) view.findViewById(R.id.listMacthing);
        tv = (TextView) view.findViewById(R.id.empty_text);
        listMatching.setEmptyView(tv);
        personList = new ArrayList<HashMap<String, String>>();
        SharedPreferences auto = getActivity().getSharedPreferences("auto", 0);
        loginId = auto.getString("inputId", null);
        Log.e("assdasdasd",loginId);
        getData("http://133.130.99.167/mimamo/public/appmatching?id=" + loginId);

        backPressCloseHandler = new BackPressCloseHandler((Activity) getContext());

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("실행한다","매칭에서onCreate");
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String family = c.getString(TAG_FAMILY);
                String sitter = c.getString(TAG_SITTER);
                String work_week = c.getString(TAG_WOKR_WEEK);
                String work_start = c.getString(TAG_WOKR_START);
                String work_end = c.getString(TAG_WOKR_END);
                String work_start_time = c.getString(TAG_WOKR_START_TIME);
                String work_end_time = c.getString(TAG_WOKR_END_TIME);

//                String place = c.getString(TAG_PLACE);

                Log.e("11111", family);
                Log.e("111222", sitter);



                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_FAMILY, family);
                persons.put(TAG_SITTER, sitter);
                persons.put(TAG_WOKR_WEEK, work_week);
                persons.put(TAG_WOKR_START, work_start);
                persons.put(TAG_WOKR_END, work_end);
                persons.put(TAG_WOKR_START_TIME, work_start_time);
                persons.put(TAG_WOKR_END_TIME, work_end_time);

//                persons.put(TAG_PLACE, place);

                personList.add(persons);


            }

            final ListAdapter adapter = new SimpleAdapter(
                    getContext(), personList, R.layout.matching_item,
                    new String[]{TAG_FAMILY, TAG_SITTER},
                    new int[]{R.id.family, R.id.sitter}
            );
            listMatching.setAdapter(adapter);
            Log.e("asdasd",listMatching.toString());
            //리스트 아이템 클릭 이벤트
            listMatching.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //HashMap에 담겨 있는 리스트 아이템을 가져옴
                    HashMap<String, String> persons = (HashMap<String, String>) adapter.getItem(position);
                    //인텐트 시 workcontent 액티비티 호출
                    Intent intent = new Intent(getContext(), MatchingContent.class);
                    //HashMap에 키 값에 담겨 있는 데이터를 workcontent 액티비티로 전송
                    intent.putExtra("family_id", persons.get(TAG_FAMILY));
                    intent.putExtra("sitter_id", persons.get(TAG_SITTER));
                    intent.putExtra("work_week", persons.get(TAG_WOKR_WEEK));
                    intent.putExtra("work_start", persons.get(TAG_WOKR_START));
                    intent.putExtra("work_end", persons.get(TAG_WOKR_END));
                    intent.putExtra("work_start_time", persons.get(TAG_WOKR_START_TIME));
                    intent.putExtra("work_end_time", persons.get(TAG_WOKR_END_TIME));
                    startActivity(intent);


                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void setImage(int resId) {
        imageView1.setImageResource(resId);
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
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }


}