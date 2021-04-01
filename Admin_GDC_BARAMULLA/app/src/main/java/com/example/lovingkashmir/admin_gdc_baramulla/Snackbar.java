package com.example.lovingkashmir.admin_gdc_baramulla;

import android.content.Context;
import android.view.View;

/**
 * Created by MEHRAJ UD DIN BHAT on 3/23/2019.
 */

public class Snackbar {
     View parentLayout;

    public Snackbar(View parentLayout, String text) {
        try {
            this.parentLayout = parentLayout;
            android.support.design.widget.Snackbar mysnak;
            mysnak= android.support.design.widget.Snackbar.make(parentLayout,text, android.support.design.widget.Snackbar.LENGTH_LONG)
                    .setActionTextColor(parentLayout.getResources().getColor(R.color.BgColor));
            View snackView= mysnak.getView();
            snackView.setBackgroundColor(parentLayout.getResources().getColor(R.color.color1));
            mysnak.show();
        }catch (Exception e)
        {

        }
    }
}
