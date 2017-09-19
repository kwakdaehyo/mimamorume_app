package com.example.bon.project_7;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//앱 설명 액티비티
public class ExplanationActivity extends Activity {
    private ViewFlipper flipper;
    private int m_nPreTouchPosX = 0;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        flipper.setOnTouchListener(MyTouchListener);

        backPressCloseHandler = new BackPressCloseHandler(this);




        Button btn_login1 = (Button) findViewById(R.id.btn1);
        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SitterLoginActivity.class);
                startActivity(intent);
                finish();

            }

        });

        Button btn_login2 = (Button) findViewById(R.id.btn2);
        btn_login2.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v) {
               Intent intent = new Intent(getBaseContext(), TargetLoginActivity.class);
               startActivity(intent);
               finish();
           }
        });
    }

    private void MoveNextView() {
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.appear_from_right));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.disappear_to_left));
        flipper.showNext();
    }

    private void MovePreviousView() {
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.appear_from_left));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.disappear_to_right));
        flipper.showPrevious();
    }

    View.OnTouchListener MyTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                m_nPreTouchPosX = (int) event.getX();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int nTouchPosX = (int) event.getX();

                if (nTouchPosX < m_nPreTouchPosX) {
                    MoveNextView();
                } else if (nTouchPosX > m_nPreTouchPosX) {
                    MovePreviousView();
                }
                m_nPreTouchPosX = nTouchPosX;
            }
            return true;
        }
    };

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }


}

