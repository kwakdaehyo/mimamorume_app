package com.example.bon.project_7.matching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bon.project_7.R;

/**
 * Created by bon on 2017-05-21.
 */

public class MatchingContent extends Activity {
    TextView family;
    TextView sitter;
    TextView work_week;
    TextView work_start;
    TextView work_end;
    TextView work_start_time;
    TextView work_end_time;


    Button returnBtn;
    Toast toast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_item_clicked);
        family = (TextView) findViewById(R.id.family_id);
        sitter = (TextView) findViewById(R.id.sitter_id);
        work_week = (TextView) findViewById(R.id.work_week);
        work_start = (TextView) findViewById(R.id.work_start);
        work_end = (TextView) findViewById(R.id.work_end);
        work_start_time = (TextView) findViewById(R.id.work_start_time);
        work_end_time = (TextView) findViewById(R.id.work_end_time);
        returnBtn = (Button) findViewById(R.id.matching_returnBtn);
        Intent intent = getIntent();
//        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("persons");
        family.setText(intent.getStringExtra("family_id"));
        sitter.setText(intent.getStringExtra("sitter_id"));
        work_week.setText(intent.getStringExtra("work_week"));
        work_start.setText(intent.getStringExtra("work_start"));
        work_end.setText(intent.getStringExtra("work_end"));
        work_start_time.setText(intent.getStringExtra("work_start_time"));
        work_end_time.setText(intent.getStringExtra("work_end_time"));



        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}