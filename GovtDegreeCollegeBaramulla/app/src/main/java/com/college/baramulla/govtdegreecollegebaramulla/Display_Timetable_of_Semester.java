package com.college.baramulla.govtdegreecollegebaramulla;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display_Timetable_of_Semester extends AppCompatActivity {

    View parentLayout,lodadinview;
    ListView mylistview;
    ArrayList<Period> periodList;

    ArrayList<String> keyList;
    private android.support.v7.widget.Toolbar toolbar;
    ActionBar mytoolbar;
    TextView tvtoolbartitle;
    ImageView btnbackimg;

    String streamkey="null";
    String semesterKey="null";
    SimpleAdapter myadapter;
    RelativeLayout Rlistview;
    String timetablefetch="null";


    ArrayList<String> compareListStartTime,compareListEndTime,compareListDis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__timetable_of__semester);
        periodList=new ArrayList<>();
        periodList.add(new Period("00:00","00:00","null"));
        keyList=new ArrayList<>();
        compareListDis=new ArrayList<>();


        compareListEndTime=new ArrayList<String>();
        compareListEndTime.add("NULL");
        compareListStartTime=new ArrayList<String>();
        compareListStartTime.add("NULL");
        compareListDis.add("null");

        keyList.add("null");

        setUpviews();
        setUpListView();
        setUpToolBar();
        setUpDatabase();
    }

    public void setUpToolBar()
    {


        toolbar= findViewById(R.id.toolbar_in_displat_timetableof_semester_activity);
        setSupportActionBar(toolbar);
        mytoolbar=getSupportActionBar();
        mytoolbar.setTitle(0);
        String title="Null";
       title=getIntent().getStringExtra("semesterName");
        timetablefetch=getIntent().getStringExtra("fetch");
        tvtoolbartitle.setText(title);

        //  mytoolbar.setDisplayHomeAsUpEnabled(true);
        // mytoolbar.setDisplayShowHomeEnabled(true);


    }



    //ENd MEnu COde

    public  void setUpviews()
    {
         mylistview= findViewById(R.id.listview_display_timetabele);
         parentLayout= findViewById(R.id.parent_layout_in_display_timetable_of_sem);
         tvtoolbartitle= findViewById(R.id.tv_in_displayTimetable_semester_toolbar);
         btnbackimg= findViewById(R.id.imgback_in_display_timetable_of_semester_toolbar);
         streamkey=getIntent().getStringExtra("streamKey");
         semesterKey=getIntent().getStringExtra("semesterKey");
        setUpToolBar();
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


    public class SimpleAdapter extends BaseAdapter {
        private Context mcontext;
        private LayoutInflater layoutInflater;
        TextView tvStarttime,tvendtime,tvperioddetail,tvperiodNo;
        
        ImageView btndelete,btnedit;
        private ImageView imageview;

        public SimpleAdapter(Context context) {
            mcontext = context;
            layoutInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return periodList.size();
        }

        @Override
        public Object getItem(int i) {
            return periodList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.period_single_item, null);
            }
                    tvStarttime= convertView.findViewById(R.id.tvperiodstarttime);
                    tvendtime= convertView.findViewById(R.id.tvperiodendtime);
                    tvperiodNo= convertView.findViewById(R.id.tvperiodno);
                   tvperioddetail = convertView.findViewById(R.id.tvperidname);
                   Rlistview= convertView.findViewById(R.id.period_layout_view);

                   tvStarttime.setText(periodList.get(position).getStartTime());
                    tvendtime.setText(periodList.get(position).getEndTime());
                   tvperioddetail.setText(periodList.get(position).getPeriodDetail());
                    Rlistview.setBackgroundColor(getResources().getColor(R.color.BgColor));
                   if(periodList.get(position).getPeriodDetail().toLowerCase().contains("break"))
                   {
                       Rlistview.setBackgroundColor(getResources().getColor(R.color.breakcolor));
                   }
                   else if(periodList.get(position).getPeriodDetail().toLowerCase().contains("lab"))
                   {
                       Rlistview.setBackgroundColor(getResources().getColor(R.color.labcolor));
                   }

                  //For Period Number
                   int myno=position;
                   tvperiodNo.setText(Integer.toString(++myno));

            return convertView;

        }

        public void clearData() {
            periodList.clear();
            keyList.clear();
            compareListDis.clear();
            compareListStartTime.clear();
            compareListEndTime.clear();

        }


        public boolean isEmpty()
        {

            return periodList.isEmpty();
        }


    }

//End SEt Up LISTVIEW
//DataBase SetUp
    public  void    setUpDatabase() {


        DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("TimeTable").child(timetablefetch).child(streamkey).child("semesters").child(semesterKey).child("periods");
        myDatabaseRef.keepSynced(true);

        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                   myadapter.clearData();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        try {
                             keyList.add(dsp.getKey());//Add Key to KeyLIst
                             Period myperiod;
                             myperiod=dsp.getValue(Period.class);
                             periodList.add(myperiod);
                             compareListEndTime.add(myperiod.getEndTime().toLowerCase().trim());
                             compareListStartTime.add(myperiod.getStartTime().toLowerCase().trim());
                             compareListDis.add(myperiod.getPeriodDetail().toLowerCase().trim());

                        }catch(Exception e)
                        {}

                    }


                   setUpListView();
                    myadapter.notifyDataSetChanged();
                    mylistview.setVisibility(View.VISIBLE);//SET VIEWS VISIBLE
                    // myLoadinglayout.setVisibility(View.INVISIBLE);
                    if(myadapter.isEmpty())
                    {
                        new    Snackbar(parentLayout," TimeTable Not Added Yet");
                    }



                }

                catch(Exception e)
                {



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }


}
