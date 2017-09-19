package com.example.bon.project_7.schedule;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bon.project_7.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by a on 2017-08-07.
 */

public class CustomScheduleAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ScheduleItem> mScheduleData;
    private LayoutInflater inf;
    private Calendar mCal;
    private int mToday;

    CustomScheduleAdapter(Context con, ArrayList<ScheduleItem> aScheduleData) {
        mContext = con;
        mScheduleData = aScheduleData;
        inf = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void setToday(int aToday){
        mToday = aToday;
    }

    @Override
    public int getCount() {
        return mScheduleData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        calHolder holder;
        if (view == null) {
            holder = new calHolder();
            view = inf.inflate(R.layout.schedule_grid_item, null);
            Log.e("aaaaaaaaaaaa","aaaaaaaaaaaaaaaa");
            holder.DayText = (TextView) view.findViewById(R.id.schedule_day);
            holder.SubText = (ImageView) view.findViewById(R.id.schedule_image);
            view.setTag(holder);
        } else {
            holder = (calHolder) view.getTag();
        }
        Log.e("겟뷰 호출", "겟뷰가 호출 되었습니다. (" + position + " / " + mScheduleData.get(position).test +")");
        //아까 햇던것

        holder.DayText.setText(mScheduleData.get(position).day);
        if(mScheduleData.get(position).test == false)  {
            Log.e("vvvvvvvvvvvvvv","vvvvvvvvvvvvvv");
            holder.SubText.setImageResource(0);
        } else {
            Log.e("wwwwwwwwwwwwwww","wwwwwwwwwwwwwww");
            holder.SubText.setImageResource(R.drawable.schedule1);
        }


        holder.DayText.setTextColor(Color.BLACK);
        if (position % 7 == 0) {
            holder.DayText.setTextColor(Color.RED);
        } else if (position % 7 == 6) {
            holder.DayText.setTextColor(Color.BLUE);
        }

        // 주말색 처리 다음에 처리하기 때문에 주말이라도 오늘이면 초록색으로 처리
        if(!mScheduleData.get(position).day.equals("")){
            if(mToday == Integer.valueOf(mScheduleData.get(position).day)){
                holder.DayText.setTextColor(Color.GREEN);
            }
        }


//        if(mScheduleData.get(position).day.equals("월")){
//            holder.SubText.setVisibility(View.GONE);
//        }else if(mScheduleData.get(position).day.equals("화")){
//            holder.SubText.setVisibility(View.GONE);
//        }else if(mScheduleData.get(position).day.equals("수")){
//            holder.SubText.setVisibility(View.GONE);
//        }else if(mScheduleData.get(position).day.equals("목")){
//            holder.SubText.setVisibility(View.GONE);
//        }else if(mScheduleData.get(position).day.equals("금")){
//            holder.SubText.setVisibility(View.GONE);
//        }else if(mScheduleData.get(position).day.equals("토")){
//            holder.SubText.setVisibility(View.GONE);
//        }else if(mScheduleData.get(position).day.equals("일")){
//            holder.SubText.setVisibility(View.GONE);
//        }

        return view;
    }

    private class calHolder {
        TextView DayText;
        ImageView SubText;
    }

}
