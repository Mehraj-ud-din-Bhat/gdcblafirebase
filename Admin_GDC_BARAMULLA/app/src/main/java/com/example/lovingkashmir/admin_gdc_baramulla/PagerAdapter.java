package com.example.lovingkashmir.admin_gdc_baramulla;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by MEHRAJ UD DIN BHAT on 7/2/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int NoOfTabs;

    public PagerAdapter(FragmentManager fm, int nooftabs) {

        super(fm);
        this.NoOfTabs=nooftabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment_Timetable_UG tm1 = new Fragment_Timetable_UG();
                return tm1;


            case 1: Fragmet_Timetable_PG up1=new Fragmet_Timetable_PG();
            return up1;

            default: return null;


        }
    }
    @Override
    public  int getCount()
    {
        return NoOfTabs;
    }


}
