package com.example.bon.project_7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

//앱 설명 액티비티
public class ExplanationActivity extends Activity {
    private ViewFlipper flipper;
    private int m_nPreTouchPosX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        flipper.setOnTouchListener(MyTouchListener);

        Button btn_login1 = (Button) findViewById(R.id.btn1);
        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);

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
}

