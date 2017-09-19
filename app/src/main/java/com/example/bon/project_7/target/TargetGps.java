package com.example.bon.project_7.target;

/**
 * Created by bon on 2017-06-22.
 */

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.TextView;

import android.widget.Toast;


import com.example.bon.project_7.BackPressCloseHandler;
import com.example.bon.project_7.ExplanationActivity;
import com.example.bon.project_7.MainActivity;
import com.example.bon.project_7.NetworkTask;
import com.example.bon.project_7.PHPRequest;
import com.example.bon.project_7.R;
import com.example.bon.project_7.RegisterUserClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import retrofit.http.POST;


/**
 * GPS
 */

public class TargetGps extends AppCompatActivity implements LocationListener {
    private BackPressCloseHandler backPressCloseHandler;

    private GpsInfo gps;
    LocationManager mLM;

    double longitude;    //경도
    double latitude;        //위도
    float accuracy;       //오차범위

    String phone;
    Button btn1;
    Context context;
    String targerPhone = "01024420326";

    BluetoothAdapter mBluetoothAdapter;
    //블루투스 활성 상태의 변경 결과를 App으로 알려줄 때 식별자로 사용됨
    final static int REQUEST_ENABLE_BT = 10;
    int mPariedDeviceCount = 0;
    Set<BluetoothDevice> mDevices;
    BluetoothDevice mRemoteDevice;
    BluetoothSocket mSocket = null;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    String mStrDelimiter = "\n";
    char mCharDelimiter = '\n';

    Thread mWorkerTread = null;
    byte[] readBuffer;
    int readBufferPosition;
//    Context context;
//    EditText mEditReceive;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_gps);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        targerPhone = auto.getString("TargetNumber", null);
        mLM = (LocationManager) getSystemService(context.LOCATION_SERVICE);
        chkGpsService();
//        registerLocationUpdates();
        checkBlueTooth();



        backPressCloseHandler = new BackPressCloseHandler(this);


        btn1 = (Button) findViewById(R.id.btn_start);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                Toast.makeText(TargetGps.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(TargetGps.this, ExplanationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

        private void registerLocationUpdates() {
        Log.e("registerLocationUpdates","1111");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("registerLocationUpdates","2222");
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.e("registerLocationUpdates","NETWORK_PROVIDER");
        mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000, 1, mLocationListener);
        Log.e("registerLocationUpdates","4444");
        mLM.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 1, mLocationListener);
        Log.e("registerLocationUpdates","GPS_PROVIDER");

        //1000은 1초마다, 1은 1미터마다 해당 값을 갱신한다는 뜻으로, 딜레이마다 호출하기도 하지만
        //위치값을 판별하여 일정 미터단위 움직임이 발생 했을 때에도 리스너를 호출 할 수 있다.
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.e("onLocationChanged","11111111");
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
                Log.e("onLocationChanged","22222222");
                //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
                longitude = location.getLongitude();    //경도
                latitude = location.getLatitude();         //위도
                accuracy = location.getAccuracy();    //신뢰도
                Log.e("onLocationChanged","33333333");
                JSONObject gpsJson = new JSONObject();
                try {

                    gpsJson.put("lat", latitude);
                    gpsJson.put("lng", longitude);
                    Log.e("onLocationChanged","444444444");
//
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NetworkTask networkTask = new NetworkTask(context, "gps", "GET", gpsJson);
                Log.e("gpsdata", gpsJson.toString());
                Log.e("onLocationChanged","555555555555");
                networkTask.execute();

                Log.e("Phone", targerPhone.toString());
                Log.e("longitude", Integer.toString((int) longitude));
                Log.e("latitude", Integer.toString((int) latitude));
                Log.e("accuracy", Integer.toString((int) accuracy));
            } else {
//Network 위치제공자에 의한 위치변화
//Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            }
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private boolean chkGpsService() {

        String gps = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        Log.d("aaaa",gps);

        if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {

            // GPS OFF 일때 Dialog 표시
            AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
            gsDialog.setTitle("위치 서비스 설정");
            gsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?");
            gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // GPS설정 화면으로 이동
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    }).create().show();
            return false;

        } else {
            return true;
        }
    }

    BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice = null;

        for (BluetoothDevice device : mDevices) {
            if (name.equals(device.getName())) {
                selectedDevice = device;
                break;
            }
        }
        return selectedDevice;
    }


    void connectToSelectedDevice(String selectedDeviceName) {
        mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);
        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            // 소켓 생성, RFCOMM 채널을 통한 연결
            mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();

            // 데이터 송수신을 위한 스트림
            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();
            Log.e("asdasdasd1111111","asdasd");
            // 데이터 수신 준비
            beginListenForData();
        } catch (Exception e) {
            String exceptionMessage = "블루투스 연결 중 오류 : " + e.getMessage();
            Log.i("bt connect error : ", exceptionMessage);
        }
    }

    // 데이터 수신
    void beginListenForData() {
        final Handler handler = new Handler();

        readBufferPosition = 0;
        readBuffer = new byte[1024];

        mWorkerTread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        int byteAvailable = mInputStream.available();
                        if (byteAvailable > 0) {
                            byte[] packetBytes = new byte[byteAvailable];
                            mInputStream.read(packetBytes);
                            for (int i = 0; i < byteAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == mCharDelimiter) {
                                    //buffer로부터 받아온 값을 byte형태로 저장하는 변수
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);

                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        public void run() {
                                            //수신된 데이터(data)
                                            try {
                                                data.charAt(data.length() - 1);

                                                JSONObject jsonData = new JSONObject();

                                                if (data.length() - 2 == 2) {
                                                    jsonData.put("pulse", data.substring(0, 2));
                                                    jsonData.put("targetNum", data.substring(2, 3));
                                                } else if (data.length() - 2 == 3) {
                                                    jsonData.put("pulse", data.substring(0, 3));
                                                    jsonData.put("targetNum", data.substring(3, 4));
                                                }

//                                                변수 확인(디버깅용)
//                                                mEditReceive.setText(mEditReceive.getText().toString() + data + mStrDelimiter);
                                                NetworkTask networkTask = new NetworkTask(context, "chartBluetooth", "POST", jsonData);
                                                networkTask.execute();
                                                registerLocationUpdates();


                                            } catch (Exception e) {
                                                //데이터 web
                                                Log.i("send error : ", e.getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.i("bt data get error : ", e.getMessage());
                    }
                }
            }
        });
        mWorkerTread.start();
    }

    void checkBlueTooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            //장치가 블루투스를 지원하지 않는 경우
        } else {
            //장치가 블루투스를 지원하는 경우
            if (!mBluetoothAdapter.isEnabled()) {
                //블루투스가 비활성화 상태일 경우
                Toast.makeText(getApplicationContext(), "현재 블루투스가 비활성 상태입니다.", Toast.LENGTH_LONG).show();
                //블루투스를 활성상태로 바꾸기 위해 사용자 동의 요청
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                //블루투스가 활성 상태일 경우
                //페어링된 기기 목록을 보여주고 연결할 장치를 선택
                selectDevice();
            }
        }
    }

    protected void onDestroy() {
        try {
            mWorkerTread.interrupt();
            mInputStream.close();
            mSocket.close();
        } catch (Exception e) {
            Log.i("error : ", e.getMessage());
        }
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    //블루투스가 활성 상태로 변경됨
                    selectDevice();
                } else if (resultCode == RESULT_CANCELED) {
                    Log.i("revitalize error : ", "not revitalize");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void selectDevice() {
        mDevices = mBluetoothAdapter.getBondedDevices();
        mPariedDeviceCount = mDevices.size();

        if (mPariedDeviceCount == 0) {
            Log.i("paried error : ", "no paried");
        }

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("블루투스 장치 선택");

        List<String> listItems = new ArrayList<String>();
        for (BluetoothDevice device : mDevices) {
            listItems.add(device.getName());
        }
        listItems.add("취소");

        //CharSequence : 변경 가능한 문자열
        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);

        listItems.toArray(new CharSequence[listItems.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == mPariedDeviceCount) {
                    // 연결할 장치를 선택하지 않고 '취소' 를 누른 경우.
                    Toast.makeText(getApplicationContext(), "연결할 장치를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    // 연결할 장치를 선택한 경우, 선택한 장치와 연결을 시도함.
                    connectToSelectedDevice(items[item].toString());
//
                }
            }

        });

        builder.setCancelable(false);
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }



}
