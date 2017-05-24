package com.example.bon.project_7;

/**
 * Created by kdh on 2017-04-10.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_PLACE = "place";
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;

    ImageView imageView1;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matching,null);

        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        listView = (ListView) view.findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://10.0.2.2/Worklist/worklist.php");

        return view;

    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String title = c.getString(TAG_TITLE);
                String content = c.getString(TAG_CONTENT);
                String place = c.getString(TAG_PLACE);

//                Log.i("asdasd", title);
//                Log.i("asdasd", content);
//                Log.i("asdasd", place);


                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);
                persons.put(TAG_TITLE, title);
                persons.put(TAG_CONTENT, content);
                persons.put(TAG_PLACE, place);

                personList.add(persons);




            }

            final ListAdapter adapter = new SimpleAdapter(
                    getContext(), personList, R.layout.matching_item,
                    new String[]{TAG_ID, TAG_TITLE, TAG_CONTENT, TAG_PLACE},
                    new int[]{R.id.id, R.id.title}
            );
            listView.setAdapter(adapter);
            //리스트 아이템 클릭 이벤트
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
                    //HashMap에 담겨 있는 리스트 아이템을 가져옴
                    HashMap<String, String> persons = (HashMap<String, String>) adapter.getItem(position);
                    //인텐트 시 workcontent 액티비티 호출
                    Intent intent = new Intent(getContext(),WorkContent.class);
                    //HashMap에 키 값에 담겨 있는 데이터를 workcontent 액티비티로 전송
                    intent.putExtra("content", persons.get(TAG_CONTENT));
                    intent.putExtra("place", persons.get(TAG_PLACE));
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
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
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




















