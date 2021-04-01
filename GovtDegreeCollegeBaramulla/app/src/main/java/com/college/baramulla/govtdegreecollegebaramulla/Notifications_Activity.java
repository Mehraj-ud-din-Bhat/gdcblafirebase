package com.college.baramulla.govtdegreecollegebaramulla;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Notifications_Activity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    ActionBar mytoolbar;
    ListView mylistview;
    ArrayList<Notification> notificationList;
     SimpleAdapter myadapter;

     ImageView btnbackimg;

     View cardLayoutHalfNotice;
    ProgressBar mypbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_);
        notificationList=new ArrayList<>();
        notificationList.add(new Notification("NULL","Null"));
        setUpViews();
        setUpToolBar();
        setUpListView();
        setUpDatabase();

    }

    public void setUpViews()
    {
        mylistview= findViewById(R.id.listview_in_notification);
       mylistview.setVisibility(View.INVISIBLE);
       mypbar=findViewById(R.id.pgbar_in_notifications_ac);
        mypbar.setVisibility(View.VISIBLE);


        btnbackimg= findViewById(R.id.imgback_in_notification_toolbar_notification);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {

                //  btndelete.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                btnbackimg.setBackground(getResources().getDrawable(R.drawable.circle_bg_ripple));

            }catch (Exception e)
            {

            }
        }
        btnbackimg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void setUpListView()
    {

        myadapter = new SimpleAdapter(this);
        mylistview.setAdapter(myadapter);
        //   mylistview.setVisibility(View.INVISIBLE);
        // myLoadinglayout.setVisibility(View.VISIBLE);
    }
    public void setUpToolBar()
    {
        toolbar= findViewById(R.id.toolbar_in_notification_activity);
        setSupportActionBar(toolbar);
        mytoolbar=getSupportActionBar();
        mytoolbar.setTitle(0);



    }

    public class SimpleAdapter extends BaseAdapter {
        private Context mcontext;
        private LayoutInflater layoutInflater;
        TextView tvNoticetitle;
        TextView tvNoticebody;


        public SimpleAdapter(Context context) {
            mcontext = context;
            layoutInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return notificationList.size();
        }

        @Override
        public Object getItem(int i) {
            return notificationList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.notice_title_single_item, null);
            }
       //     try {
                tvNoticetitle = convertView.findViewById(R.id.tvnoticetitle);
                cardLayoutHalfNotice= convertView.findViewById(R.id.layout_card_halfNotice);
                tvNoticetitle.setText(notificationList.get(position).getNotificationtitle());

                // Click Listener For LIST ITEM CLICKED
                cardLayoutHalfNotice.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                      /*  Intent intent = new Intent(Notifications_Activity.this,com.college.baramulla.govtdegreecollegebaramulla.show_journal_post_activity.class);
                        //intent.putExtra("category",categoryname);
                        intent.putExtra("NoticeTitle",notificationList.get(position).getNotificationtitle());
                     //   intent.putExtra("NoticeBody",notificationList.get(position).getNotificationbody());
                        intent.putExtra("NoticeLink",notificationList.get(position).getNoticeLink());
                        startActivity(intent);*/


                        Intent pdfIntent=new Intent();
                        pdfIntent.setData(Uri.parse(notificationList.get(position).getNoticeLink()));
                        if(pdfIntent.resolveActivity(getPackageManager()) !=null)
                        {
                            startActivity(pdfIntent);
                        }

                    }
                });

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {
                     tvNoticetitle.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));

                    }catch (Exception e)
                    {

                    }
                }




          //  }catch(Exception e)
        //    {}

            return convertView;

        }

        public void clearData() {
            notificationList.clear();
        }


    }

    public  void    setUpDatabase()
    {



        DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Notifications");
     //   myDatabaseRef.push().setValue(notificationList.get(0));
        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                    myadapter.clearData();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        //try {

                         Notification notification;
                         notification=dsp.getValue(Notification.class);
                         notificationList.add(notification);

                       // }catch(Exception e)
                       // {
                           // Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                     //   }

                    }


                   // setUpListView();
                    Collections.reverse(notificationList);
                    myadapter.notifyDataSetChanged();
                    mypbar.setVisibility(View.INVISIBLE);
                    mylistview.setVisibility(View.VISIBLE);//SET VIEWS VISIBLE
                    // myLoadinglayout.setVisibility(View.INVISIBLE);



                }

                catch(Exception e)
                {

                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //databaseError.getMessage().toString();
                //  Toast.makeText(this, "Server Unreachable", Toast.LENGTH_LONG).show();

            }
        });
    }



}
