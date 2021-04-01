package com.college.baramulla.govtdegreecollegebaramulla;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

public class Timetable_Activity extends AppCompatActivity implements Fragment_Timetable_UG.OnFragmentInteractionListener,Fragmet_Timetable_PG.OnFragmentInteractionListener , NavigationView.OnNavigationItemSelectedListener{





    private android.support.v7.widget.Toolbar toolbar;
    ActionBar mytoolbar;
    ImageView btnbacktomain;

    DrawerLayout myDrawLayout;

    NavigationView myNavView;

    private String updateurl = "null", versionclosed = "null";//ForUpdate Section
    String databaseVersionLink="Version1dot0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_);
       setUpToolBar();
       setUpViews();
       appupdate();
        TabLayout tabLayout= findViewById(R.id.tabview);
        tabLayout.addTab(tabLayout.newTab().setText("UG TIMETABLE"));
        tabLayout.addTab(tabLayout.newTab().setText("PG TIMETABLE"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager myviewpager= findViewById(R.id.viewpager);
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

        toolbar= findViewById(R.id.toolbarintimetable);
        myDrawLayout= findViewById(R.id.draw_layout);
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
      //  btnbacktomain=(ImageView)findViewById(R.id.imgback);
        myNavView= findViewById(R.id.myNavigationDrawerView);
        myNavView.setNavigationItemSelectedListener(this);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {

                //  btndelete.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                btnbacktomain.setBackground(getResources().getDrawable(R.drawable.circle_bg_ripple));

            }catch (Exception e)
            {

            }
        }

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
 //navigation Click listener
 @Override
 public boolean onNavigationItemSelected(@NonNull MenuItem item) {
     // Handle navigation view item clicks here.
     switch (item.getItemId()) {

         case R.id.nav_notification_item: {
             myDrawLayout.closeDrawer(GravityCompat.START);
             if(updateurl.equalsIgnoreCase("null")) {

                 startActivity(new Intent(this, com.college.baramulla.govtdegreecollegebaramulla.Notifications_Activity.class));
                 //   myDrawLayout.closeDrawer(GravityCompat.START);
             }else {SetupDialogUpdate();}
             break;
         }

             case R.id.nav_aboutcollege_item: {
                myDrawLayout.closeDrawer(GravityCompat.START);
                 if(updateurl.equalsIgnoreCase("null")) {
                     startActivity(new Intent(this, com.college.baramulla.govtdegreecollegebaramulla.webViewActivity.class));
                 }else {
                     SetupDialogUpdate();
                 }
                     // myDrawLayout.closeDrawer(GravityCompat.START);
                     break;

                     }


         case R.id.nav_menu_item_about_app: {
             myDrawLayout.closeDrawer(GravityCompat.START);

             displayAboutAppDialog();
            // myDrawLayout.closeDrawer(GravityCompat.START);
             break;
         }


             case R.id.nav_item_register_grivace: {
                myDrawLayout.closeDrawer(GravityCompat.START);
                 if(updateurl.equalsIgnoreCase("null")) {
                     startActivity(new Intent(this, com.college.baramulla.govtdegreecollegebaramulla.register_grievance.class));
                     //  myDrawLayout.closeDrawer(GravityCompat.START);
                 }else {

                     SetupDialogUpdate();
                 }
                 break;
             }
         case R.id.nav_item_app_admins: {

             myDrawLayout.closeDrawer(GravityCompat.START);
             if(updateurl.equalsIgnoreCase("null")) {
                 startActivity(new Intent(this, com.college.baramulla.govtdegreecollegebaramulla.admins_Activity.class));
                 // myDrawLayout.closeDrawer(GravityCompat.START);
             }else {
                 SetupDialogUpdate();
             }
             break;
         }

         case R.id.nav_item_college_journal: {
             myDrawLayout.closeDrawer(GravityCompat.START);
             if(updateurl.equalsIgnoreCase("null")) {
                 startActivity(new Intent(this, com.college.baramulla.govtdegreecollegebaramulla.Journal_Activity.class));
             }else {
                 SetupDialogUpdate();
             }
             break;
         }


         case R.id.nav_item_feedback: {
             myDrawLayout.closeDrawer(GravityCompat.START);
             Intent email= new Intent(Intent.ACTION_SENDTO);
             email.setData(Uri.parse("mailto:"));
             email.putExtra(Intent.EXTRA_EMAIL,new String[]{"feedback.gdcbaramulla@gmail.com"});
             email.putExtra(Intent.EXTRA_SUBJECT,"Feedback From College App");
             if(email.resolveActivity(getPackageManager()) !=null)
             {
                 startActivity(email);
             }
             break;
         }


         case R.id.nav_menu_item_privicy_policy: {
             myDrawLayout.closeDrawer(GravityCompat.START);
             if(updateurl.equalsIgnoreCase("null")) {
                 privacyPolicy();
             }else {
                 SetupDialogUpdate();
             }
             break;
         }


         case R.id.nav_menu_item_share_app: {
             myDrawLayout.closeDrawer(GravityCompat.START);
             if(updateurl.equalsIgnoreCase("null")) {
                shareApp();
             }else {
                 SetupDialogUpdate();
             }
             break;
         }


     }
     //close navigation drawer
   //  myDrawLayout.closeDrawer(GravityCompat.START);
     return true;
 }

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


    // About App Dialog


    public void displayAboutAppDialog()
    {
        AlertDialog.Builder dialogbuilder;
        AlertDialog alertDialog;



            try {

                LayoutInflater inflater = getLayoutInflater();
                final View dialogview = inflater.inflate(R.layout.dialog_about_app, null);
                dialogbuilder = new AlertDialog.Builder(this);
                dialogbuilder.setView(dialogview);
                alertDialog = dialogbuilder.create();
                showDialog(alertDialog);

            } catch(Exception e){


            }







    }


    public void showDialog(  AlertDialog alertDialog)
    {
        //Distext.setText(Dtext);
        alertDialog.setCanceledOnTouchOutside(false);
        // alertDialog.setCancelable(false);
        alertDialog.show();
    }
    public void HideDialog(  AlertDialog alertDialog){
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.hide();
        alertDialog.dismiss();
    }

//App Update Section

    public void appupdate() {
        try {


        DatabaseReference mydatabase = FirebaseDatabase.getInstance().getReference().child("Appupdates").child(databaseVersionLink).child("UpdateLink");
            mydatabase.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {

                        updateurl = dataSnapshot.getValue().toString();
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
        }


        try {
          DatabaseReference  mydatabase2 = FirebaseDatabase.getInstance().getReference().child("Appupdates").child(databaseVersionLink).child("VersionClosed");
            mydatabase2.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {

                        versionclosed = dataSnapshot.getValue().toString();
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        } catch (Exception e) {
        }


    }


    public void SetupDialogUpdate() {

        try {

            AlertDialog.Builder dialogbuilder;
            final AlertDialog alertDialog;
            TextView btnupdatenow, btnupdatelater;
            LayoutInflater inflater = getLayoutInflater();
            final View dialogview = inflater.inflate(R.layout.dialog_update, null);
            btnupdatenow = dialogview.findViewById(R.id.btnupdatenow);
            btnupdatelater = dialogview.findViewById(R.id.btnupdatelater);


            if (android.os.Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP) {
                try {
                    btnupdatelater.setBackground(getResources().getDrawable(R.drawable.design_button_design_ripple2));
                    btnupdatenow.setBackground(getResources().getDrawable(R.drawable.design_button_design_ripple2));


                }catch (Exception e)
                {

                }
            }

            dialogbuilder = new AlertDialog.Builder(this);
            dialogbuilder.setView(dialogview);
            alertDialog = dialogbuilder.create();
            showDialog(alertDialog);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            btnupdatenow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // applicationUpdate();
                    gotoPlayStore();
                }
            });


            btnupdatelater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (versionclosed.equalsIgnoreCase("0")) {
                        updateurl="null";
                        HideDialog(alertDialog);
                    } else {
                        //applicationUpdate();
                        //gotoPlayStore();

                    }
                }
            });


        } catch (Exception e) {


        }

 }


    public void gotoPlayStore() {

        Intent play = new Intent(Intent.ACTION_VIEW);
        play.setData(Uri.parse(updateurl));
        startActivity(play);

    }





    //End Update Section


    public void privacyPolicy()
    {
        Intent play = new Intent(Intent.ACTION_VIEW);
        play.setData(Uri.parse("https://gdcbaramullaprivicypolicyandroidapp.blogspot.com/2019/06/privacy-policy-body-font-family.html"));
        startActivity(play);
    }



    public void  shareApp()
    {

        Intent share= new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(android.content.Intent.EXTRA_SUBJECT,"Download Android OF GDC Boys Baramulla\nTap on Below Link to Download\n");
        share.putExtra(android.content.Intent.EXTRA_TEXT,"");
        startActivity(Intent.createChooser(share,"Share via"));
    }




    public void rateApp()
    {

        Intent play = new Intent(Intent.ACTION_VIEW);
        play.setData(Uri.parse("https://gdcbaramullaprivicypolicyandroidapp.blogspot.com/2019/06/privacy-policy-body-font-family.html"));
        startActivity(play);
    }
}
