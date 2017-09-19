package com.example.bon.project_7.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.bon.project_7.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by hyo on 2017-08-22.
 */

public class CameraDetail extends Activity {
    PhotoView CameraImage;
    Uri uri;
    String photo;
    Bitmap bm;
    TextView CameraName;
    TextView CameraDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cameradetail);

        CameraImage = (PhotoView) findViewById(R.id.CameraDetail);
        CameraName = (TextView) findViewById(R.id.CameraName2);
        CameraDate = (TextView) findViewById(R.id.CameraDate2);

        Intent intent = getIntent();
        photo = intent.getStringExtra("image");
        CameraName.setText(intent.getStringExtra("cameraname"));
        CameraDate.setText(intent.getStringExtra("cameradate"));
        Picasso.with(this)
                .load(photo)
                .into(CameraImage);


    }
}
