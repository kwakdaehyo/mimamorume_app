package com.example.bon.project_7.camera;

import java.util.Date;

/**
 * Created by bon on 2017-06-25.
 */

public class DataAdapter
{
    public String ImageURL;
    public String TargetName;
    public String CameraName;
    public String CameraDate;

    public String getImageUrl() {

        return ImageURL;
    }

    public void setImageUrl(String imageServerUrl) {

        this.ImageURL = imageServerUrl;
    }

    public String getTargetName() {

        return TargetName;
    }

    public void setTargetName(String targetname) {

        this.TargetName = targetname;
    }

    public String getCameraName() {

        return CameraName;
    }

    public void setCameraName(String Cameraname) {

        this.CameraName = Cameraname;
    }

    public String getCameraDate() {

        return CameraDate;
    }

    public void setCameraDate(String CameraDate) {

        this.CameraDate = CameraDate;
    }

}