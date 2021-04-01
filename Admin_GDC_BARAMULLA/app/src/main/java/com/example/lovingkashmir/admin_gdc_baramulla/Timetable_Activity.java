package com.example.lovingkashmir.admin_gdc_baramulla;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class Timetable_Activity extends AppCompatActivity implements Fragment_Timetable_UG.OnFragmentInteractionListener,Fragmet_Timetable_PG.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener{



    private android.support.v7.widget.Toolbar toolbar;
    ActionBar mytoolbar;
    ImageView btnbacktomain;
    DrawerLayout myDrawLayout;

    NavigationView myNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_);
       setUpToolBar();
       setUpViews();
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabview);
        tabLayout.addTab(tabLayout.newTab().setText("UG TIMETABLE"));
        tabLayout.addTab(tabLayout.newTab().setText("PG TIMETABLE"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager myviewpager=(ViewPager)findViewById(R.id.viewpager);
        final PagerAdapter myadapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        myviewpager.setAdapter(myadapter);




        try {
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(myviewpager));
            myviewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }catch (Exception e){}
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void setUpToolBar()
    {

        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarintimetable);
        myDrawLayout=(DrawerLayout)findViewById(R.id.draw_layout);
        setSupportActionBar(toolbar);
        mytoolbar=getSupportActionBar();
        mytoolbar.setTitle(0);
        //mytoolbar.setDisplayHomeAsUpEnabled(false);
        //mytoolbar.setDisplayShowHomeEnabled(false);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,myDrawLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        myDrawLayout.addDrawerListener(toggle);
        toggle.syncState();


    }
    public void setUpViews()
    {
        myNavView=(NavigationView)findViewById(R.id.myNavigationDrawerView);
        myNavView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_notification_item: {
                myDrawLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(this, com.example.lovingkashmir.admin_gdc_baramulla.Notifications_Activity.class));
                break;
            }

            case R.id.nav_item_app_admins: {
              //  myDrawLayout.closeDrawer(GravityCompat.START);
             //   Toast.makeText(getApplicationContext(),"Toast Rweday" ,Toast.LENGTH_SHORT).show();
             //   startActivity(new Intent(this, com.example.lovingkashmir.admin_gdc_baramulla.Admin_List_Activity.class));
                break;
            }

            case R.id.nav_item_college_journal: {
                myDrawLayout.closeDrawer(GravityCompat.START);
              //  Toast.makeText(getApplicationContext(),"Toast Rweday" ,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, com.example.lovingkashmir.admin_gdc_baramulla.Journal_Activity.class));
                break;
            }
        }
        //close navigation drawer
        //  myDrawLayout.closeDrawer(GravityCompat.START);
        return true;

    }


 /*   public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)
        {
            case android.R.id.home:
                //Toast.makeText(this, "Home key pressed", Toast.LENGTH_LONG).show();
               // finish();
                break;

        }

        return  true;
    }
*/



    @Override
    public void onBackPressed() {

        if(myDrawLayout.isDrawerOpen(GravityCompat.START))
        {
            myDrawLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }




}
