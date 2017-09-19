package com.example.bon.project_7.schedule;

/**
 * Created by bon on 2017-06-11.
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.bon.project_7.R;

public class Detail extends Activity implements OnClickListener {
    MyDBHelper mDBHelper;
    int mId;
    String today;
    EditText editDate, editTitle, editTime, editMemo;

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_detail);

        editDate = (EditText) findViewById(R.id.editdate);
        editTitle = (EditText) findViewById(R.id.edittitle);
        editTime = (EditText) findViewById(R.id.edittime);
        editMemo = (EditText) findViewById(R.id.editmemo);

        Intent intent = getIntent();
        mId = intent.getIntExtra("ParamID", -1);
        today = intent.getStringExtra("ParamDate");


        mDBHelper = new MyDBHelper(this, "Today.db", null, 1);

        if (mId == -1) {
            editDate.setText(today);
        } else {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM today WHERE _id='" + mId
                    + "'", null);

            if (cursor.moveToNext()) {
                editTitle.setText(cursor.getString(1));
                editDate.setText(cursor.getString(2));
                editTime.setText(cursor.getString(3));
                editMemo.setText(cursor.getString(4));
            }
            mDBHelper.close();
        }

        Button btn1 = (Button) findViewById(R.id.btnsave);
        btn1.setOnClickListener(this);
        Button btn2 = (Button) findViewById(R.id.btndel);
        btn2.setOnClickListener(this);
        Button btn3 = (Button) findViewById(R.id.btncancel);
        btn3.setOnClickListener(this);

        if (mId == -1) {
            btn2.setVisibility(View.INVISIBLE);

        }
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btnsave:
                if (mId != -1) {
                    db.execSQL("UPDATE today SET title='"
                            + editTitle.getText().toString() + "',date='"
                            + editDate.getText().toString() + "', time='"
                            + editTime.getText().toString() + "', memo='"
                            + editMemo.getText().toString() + "' WHERE _id='" + mId
                            + "';");

                } else {
                    db.execSQL("INSERT INTO today VALUES(null, '"
                            + editTitle.getText().toString() + "', '"
                            + editDate.getText().toString() + "', '"
                            + editTime.getText().toString() + "', '"
                            + editMemo.getText().toString() + "');");
                }

                Intent resultIntent = getIntent();
                resultIntent.putExtra("date_day", editDate.getText().toString());
                setResult(RESULT_OK, resultIntent);
                mDBHelper.close();
                break;
            case R.id.btndel:
                if (mId != -1) {
                    db.execSQL("DELETE FROM today WHERE _id='" + mId + "';");
                    mDBHelper.close();
                }
                setResult(RESULT_OK);
                break;
            case R.id.btncancel:
                setResult(RESULT_CANCELED);
                break;
        }

        finish();

    }
}
//    @Override
//    public void onResume() {
//        super.onResume();
//        fillData(년,월);
//    }

//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
//
//        switch (v.getId()) {
//            case R.id.btnsave:
//                if (mId != -1) {
//                    db.execSQL("UPDATE today SET title='"
//                            + editTitle.getText().toString() + "',date='"
//                            + editDate.getText().toString() + "', time='"
//                            + editTime.getText().toString() + "', memo='"
//                            + editMemo.getText().toString() + "' WHERE _id='" + mId
//                            + "';");
//                } else {
//                    db.execSQL("INSERT INTO today VALUES(null, '"
//                            + editTitle.getText().toString() + "', '"
//                            + editDate.getText().toString() + "', '"
//                            + editTime.getText().toString() + "', '"
//                            + editMemo.getText().toString() + "');");
//                }
//                mDBHelper.close();
//                setResult(RESULT_OK);
//                break;
//            case R.id.btndel:
//                if (mId != -1) {
//                    db.execSQL("DELETE FROM today WHERE _id='" + mId + "';");
//                    mDBHelper.close();
//                }
//                setResult(RESULT_OK);
//                break;
//            case R.id.btncancel:
//                setResult(RESULT_CANCELED);
//                break;
//        }
//        finish();
//    }
