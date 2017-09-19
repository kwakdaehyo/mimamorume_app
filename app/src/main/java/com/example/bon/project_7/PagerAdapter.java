package com.example.bon.project_7;


/**
 * Created by kdh on 2017-04-10.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.bon.project_7.camera.FragmentCamera;
import com.example.bon.project_7.matching.FragmentMatching;
import com.example.bon.project_7.schedule.FragmentSchedule;
import com.example.bon.project_7.work.FragmentWork;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Log.e("실행1","실행1");
                FragmentMatching tab1 = new FragmentMatching();
                return tab1;
            case 1:
                Log.e("실행2","실행2");
                FragmentWork tab2 = new FragmentWork();
                return tab2;
            case 2:
                Log.e("실행3","실행3");
                FragmentSchedule tab3 = new FragmentSchedule();
                return tab3;
            case 3:
                Log.e("실행4","실행4");
                FragmentCamera tab4 = new FragmentCamera();
                return tab4;
//            case 4:
//                TabFragment5 tab5 = new TabFragment5();
//                return tab5;
            default:
                Log.e("실행5","실행5");
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}