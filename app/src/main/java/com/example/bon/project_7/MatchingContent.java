package com.example.bon.project_7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bon on 2017-05-21.
 */

public class MatchingContent extends Activity {
    TextView work_content, work_place;
    Button returnBtn;
    Toast toast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_item_clicked);
        work_content = (TextView) findViewById(R.id.work_content);
        work_place = (TextView) findViewById(R.id.work_place);
        returnBtn = (Button) findViewById(R.id.work_returnBtn);
        Intent intent = getIntent();
//        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("persons");
        work_content.setText(intent.getStringExtra("content"));
        work_place.setText(intent.getStringExtra("place"));



        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

