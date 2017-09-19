package com.example.bon.project_7;

/**
 * Created by bon on 2017-06-22.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bon.project_7.target.TargetGps;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TargetLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit1;

    String phoneNum;
    String result;
    String str2;
    String string = "+8210";
    private BackPressCloseHandler backPressCloseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_login_main);

        edit1 = (EditText) findViewById(R.id.phonNumber);
        Button btn5 = (Button) findViewById(R.id.btnLogin2);
        btn5.setOnClickListener(this);

        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneNum = telManager.getLine1Number();
        Log.e("11515156156156",phoneNum.toString());
        backPressCloseHandler = new BackPressCloseHandler(this);





    }

    public void onClick(View v) {

        try {
            String str = edit1.getText().toString().trim();
            int rt = Integer.parseInt(str);
            String str2 = Integer.toString(rt);
            String result = string + str2;
            Log.e("11515156156156",result.toString());

            if(result.equals(phoneNum)){
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autoLogin = auto.edit();
                autoLogin.putString("TargetNumber", phoneNum);
                autoLogin.commit();
                Intent intent = new Intent(getBaseContext(), TargetGps.class);
                startActivity(intent);
                finish();

                Toast.makeText(this, str2 + "利用者がログインしました。", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "正確な番号を入力してください", Toast.LENGTH_SHORT).show();
            }


        } catch (NumberFormatException e) {
            Toast.makeText(this, "数字だけ入力してください", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }


}
