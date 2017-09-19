package com.example.bon.project_7.camera;

/**
 * Created by bon on 2017-06-25.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Juned on 2/8/2017.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.android.volley.toolbox.ImageLoader;
import com.example.bon.project_7.R;
import com.example.bon.project_7.matching.MatchingContent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Toast;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    Context context;

    List<DataAdapter> dataAdapters;
    ArrayList<DataAdapter> arrayList;
    ImageLoader imageLoader;
    ImageView content;


    public RecyclerViewAdapter(List<DataAdapter> getDataAdapter, Context context){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
        arrayList = new ArrayList<DataAdapter>();
        arrayList.addAll(dataAdapters);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Viewholder, final int position) {
        final DataAdapter dataAdapterOBJ =  dataAdapters.get(position);

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(dataAdapterOBJ.getImageUrl(), ImageLoader.getImageListener(
                        Viewholder.VollyImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.VollyImageView.setImageUrl(dataAdapterOBJ.getImageUrl(), imageLoader);

        Viewholder.TargetName.setText(dataAdapterOBJ.getTargetName());
        Viewholder.CameraNameTextView.setText(dataAdapterOBJ.getCameraName());
        Viewholder.CameraDateTextView.setText(dataAdapterOBJ.getCameraDate());
        Viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CameraDetail.class);
                intent.putExtra("image",dataAdapterOBJ.getImageUrl());
                intent.putExtra("cameraname",dataAdapterOBJ.getCameraName());
                intent.putExtra("cameradate", dataAdapterOBJ.getCameraDate());
                context.startActivity(intent);
            }
        });


    }
    public void updateData(ArrayList<DataAdapter> array) {
        arrayList.clear();
        arrayList.addAll(array);
        notifyDataSetChanged();
        MLog.e(MyData.getToday());
    }

    @Override
    public int getItemCount() {
        if(dataAdapters.size() == 0) {
            
        }
        return dataAdapters.size();
    }
    public void filter(String charText) {
        Log.e("filter------------","확인1");
        charText = charText.toLowerCase(Locale.getDefault());
        Log.e("filter------------","확인2");
        dataAdapters.clear();
        Log.e("filter------------","확인3");
        if (charText.length() == 0) {
            Log.e("filter------------","확인4");
            dataAdapters.addAll(arrayList);
            Log.e("filter------------","확인5");
        } else {
            Log.e("filter------------","확인6");
            for (DataAdapter dataadapter : arrayList) {
                Log.e("filter------------","확인7");
                String name = dataadapter.getTargetName();
                Log.e("filter------------","확인8");
                if (name.toLowerCase().contains(charText)) {
                    Log.e("filter------------","확인9");
                    dataAdapters.add(dataadapter);
                    Log.e("filter------------","확인10");
                }
            }
        }
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TargetName;
        public NetworkImageView VollyImageView ;
        public TextView CameraNameTextView;
        public TextView CameraDateTextView;

        public ViewHolder(View itemView) {

            super(itemView);
            TargetName = (TextView) itemView.findViewById(R.id.ImageNameTextView) ;
            CameraNameTextView = (TextView) itemView.findViewById(R.id.CameraName1) ;
            CameraDateTextView = (TextView) itemView.findViewById(R.id.CameraDate1) ;

            VollyImageView = (NetworkImageView) itemView.findViewById(R.id.VolleyImageView) ;

        }
    }

}