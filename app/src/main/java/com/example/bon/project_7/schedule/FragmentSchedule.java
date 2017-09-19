package com.example.bon.project_7.schedule;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;

import com.example.bon.project_7.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentSchedule extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    ArrayList<ScheduleItem> mItems = new ArrayList<ScheduleItem>();
    CustomScheduleAdapter mGridAdapter;
    TextView textYear;
    TextView textMon;
    MyDBHelper mDBHelper;
    String today;
    Cursor cursor;
    int currentYear;
    int currentMon;
    int currentDay;
    Calendar date;
    GridView gird;
    int year;
    int mon;
//    DatePickerDialog.OnDateSetListener callback;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, null);

        textYear = (TextView) view.findViewById(R.id.edit1);
        textMon = (TextView) view.findViewById(R.id.edit2);

        mGridAdapter = new CustomScheduleAdapter(getContext(), mItems);
        gird = (GridView) view.findViewById(R.id.grid1);
        gird.setOnItemClickListener(this);

        Date date = new Date();// 오늘에 날짜를 세팅 해준다.
//        year = date.getYear() + 1900;
//        mon = date.getMonth() + 1;
        currentYear = date.getYear() + 1900;
        currentMon = date.getMonth() + 1;
//        date = Calendar.getInstance();
//        currentYear = date.get(Calendar.YEAR);
//        currentMon = date.get(Calendar.MONTH)+1;
//        currentDay = date.get(Calendar.DAY_OF_MONTH);
        textYear.setText(currentYear + "");
        textMon.setText(currentMon + "");




        fillDate(currentYear, currentMon);
        gird.setAdapter(mGridAdapter);

        Button btnmove = (Button) view.findViewById(R.id.bt1);
        btnmove.setOnClickListener(this);


        return view;

    }


    //    @Override
//    public void onClick(View arg0) {
//        // TODO Auto-generated method stub
//        if (arg0.getId() == R.id.bt1) {
//            int year = Integer.parseInt(textYear.getText().toString());
//            int mon = Integer.parseInt(textMon.getText().toString());
//            fillDate(year, mon);
//        }
//    }
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        if (arg0.getId() == R.id.bt1) {
            Calendar date = Calendar.getInstance();
            currentYear = date.get(Calendar.YEAR);
            currentMon = date.get(Calendar.MONTH);
            currentDay = date.get(Calendar.DAY_OF_MONTH);

            showDialog(currentYear, currentMon, currentDay).show();
        }
    }
    DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Log.e("onDateSetyear",Integer.toString(year));
            Log.e("onDateSetmon",Integer.toString(month));
            textYear.setText(year + "");
            textMon.setText(month+1 + "");

            fillDate(year, month+1);
        }
    };

    protected DatePickerDialog showDialog(int year, int mon, int day) {
        DatePickerDialog dialog = new DatePickerDialog(getContext(),callBack,year,mon,day);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("실행 되는지 보자", Integer.toString(currentYear));
        Log.e("실행 되는지 보자", Integer.toString(currentMon));
    }

    public void setUpdateDate(String day) {
        String[] aa = day.split("/");
        Log.e("spilt", aa[0]);
        Log.e("spilt1", aa[1]);
        Log.e("spilt2", aa[2]);
        if (String.valueOf(currentYear).equals(aa[0])) {
            Log.e("이프문 1차 토오가", "통과");
            if (String.valueOf(currentMon).equals(aa[1])) {
                Log.e("갑니다아.", "달력 새로고침합니다 ( " + aa[2] + ")");
                mItems.get(Integer.valueOf(aa[2])).test = true;
                mGridAdapter.notifyDataSetChanged();
            }
        }

    }

    private void fillDate(int year, int mon) {
        mItems.clear();

        final Date current = new Date(year - 1900, mon - 1, 1);
        int day = current.getDay(); // 요일도 int로 저장.

        for (int i = 0; i < day; i++) {
            ScheduleItem item = new ScheduleItem("", "", false);
            mItems.add(item);
        }

        current.setDate(32);// 32일까지 입력하면 1일로 바꿔준다.
        int last = 32 - current.getDate();

        for (int i = 1; i <= last; i++) {
            ///////////
            mDBHelper = new MyDBHelper(getContext(), "Today.db", null, 1);
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM today WHERE date = '"
                    + year + "/" + mon + "/" + i + "'", null);
//            Cursor cursor = db.rawQuery("SELECT * FROM today",null);
//            Log.e("cursor", cursor.toString());
            cursor.moveToFirst();
            Log.e("zzzzzzzzzzzzz", "SELECT * FROM today WHERE date = '"
                    + year + "/" + mon + "/" + i + "'");

            ScheduleItem item;
            String data = null;
            String data2 = null;
            while (false == cursor.isAfterLast()) {
//                String data = cursor.getString(cursor.getColumnIndex("title"));
                data = cursor.getString(cursor.getColumnIndex("title"));
                data2 = cursor.getString(cursor.getColumnIndex("date"));
                Log.e("AAAAAAAAAAAAA", data);

                Log.e("bbbbbbbbbbbbbb", data2);
                cursor.moveToNext();

            }

            if (data2 != null) {
                //값이 있을때
                Log.e("aasdasd", data);
                item = new ScheduleItem(i + "", "" + i, true);
                Log.e("값있을때", item.day.toString());
            } else {

                //값이 없을때
                item = new ScheduleItem(i + "", "" + i, false);
                Log.e("값없을때", item.day.toString());
            }
            mItems.add(item);
        }

        mGridAdapter.setToday(0);
        if (year == Calendar.getInstance().get(Calendar.YEAR)) {
            if (mon == (Calendar.getInstance().get(Calendar.MONTH) + 1)) {
                int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                mGridAdapter.setToday(today);
            }
        }

        mGridAdapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        if (mItems.get(arg2).day.equals("")) {

        } else {
            Intent intent = new Intent(getContext(), Today.class);
            intent.putExtra("Param1", textYear.getText().toString()+ "/"
                    + textMon.getText().toString() + "/" + mItems.get(arg2).day);
            startActivityForResult(intent, 3);
        }
    }
}
