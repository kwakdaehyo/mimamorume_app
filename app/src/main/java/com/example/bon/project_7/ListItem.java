package com.example.bon.project_7;

/**
 * Created by kdh on 2017-03-23.
 */

public class ListItem {
    private String[] mData;

    public ListItem(String[] data) {

        mData = data;
    }

    public ListItem(String lgId, String lgPass) {
        mData = new String[2];
        mData[0] = lgId;
        mData[1] = lgPass;
    }

    public String[] getData() {
        return mData;
    }

    public String getData(int index) {
        return mData[index];
    }

    public void setData(String[] data) {
        mData = data;
    }
}
