package com.example.bon.project_7.work;

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

public class WorkContent extends Activity {
    TextView work_num;
    TextView work_sitter;
    TextView work_target;
    TextView work_work_date;
    TextView work_created;
    TextView work_log_num;
    TextView work_content_type;
    TextView work_content;
    TextView work_name;
    TextView work_medicine;
    TextView work_start_date;
    TextView work_end_date;
    TextView work_time;

    Button returnBtn;
    Toast toast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_item_clicked);
        work_sitter = (TextView) findViewById(R.id.work_sitter);
        work_work_date = (TextView) findViewById(R.id.work_work_date);
        work_created = (TextView) findViewById(R.id.work_created);
        work_content_type = (TextView) findViewById(R.id.work_content_type);
        work_content = (TextView) findViewById(R.id.work_content);
        work_name = (TextView) findViewById(R.id.work_name);
        work_medicine = (TextView) findViewById(R.id.work_medicine_name);
        work_start_date = (TextView) findViewById(R.id.work_start_date);
        work_end_date = (TextView) findViewById(R.id.work_end_date);
        work_time = (TextView) findViewById(R.id.work_time);
        returnBtn = (Button) findViewById(R.id.work_returnBtn);
        Intent intent = getIntent();
//        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("persons");
        work_sitter.setText(intent.getStringExtra("sitter_id"));
        work_work_date.setText(intent.getStringExtra("work_date"));
        work_created.setText(intent.getStringExtra("created_at"));
        work_content_type.setText(intent.getStringExtra("content_type"));
        work_content.setText(intent.getStringExtra("content"));
        work_name.setText(intent.getStringExtra("name"));
        work_medicine.setText(intent.getStringExtra("medicine_name"));
        work_start_date.setText(intent.getStringExtra("start_date"));
        work_end_date.setText(intent.getStringExtra("end_date"));
        work_time.setText(intent.getStringExtra("time"));




        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}