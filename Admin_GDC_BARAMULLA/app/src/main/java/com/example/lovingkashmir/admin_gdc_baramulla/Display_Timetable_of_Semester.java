package com.example.lovingkashmir.admin_gdc_baramulla;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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


        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_in_displat_timetableof_semester_activity);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_display_timetable_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add_class_menu_item:
             //   addSemester();
                // addnewStream();
                addPeriod();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //ENd MEnu COde

    public  void setUpviews()
    {
         mylistview=(ListView)findViewById(R.id.listview_display_timetabele);
         parentLayout=(View)findViewById(R.id.parent_layout_in_display_timetable_of_sem);
         tvtoolbartitle=(TextView)findViewById(R.id.tv_in_displayTimetable_semester_toolbar);
         btnbackimg=(ImageView)findViewById(R.id.imgback_in_display_timetable_of_semester_toolbar);
         streamkey=getIntent().getStringExtra("streamKey");
         semesterKey=getIntent().getStringExtra("semesterKey");
        mylistview.setVisibility(View.INVISIBLE);
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

        ImageView btn_edit_period,btn_delete_period;
        
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
                    tvStarttime=(TextView)convertView.findViewById(R.id.tvperiodstarttime);
                    tvendtime=(TextView)convertView.findViewById(R.id.tvperiodendtime);
                    tvperiodNo=(TextView)convertView.findViewById(R.id.tvperiodno);
                   tvperioddetail =(TextView)convertView.findViewById(R.id.tvperidname);
                   Rlistview=(RelativeLayout)convertView.findViewById(R.id.period_layout_view);
                   btn_delete_period=convertView.findViewById(R.id.imgedelete_period);
                   btn_edit_period=convertView.findViewById(R.id.imgedit_period);

                   tvStarttime.setText(periodList.get(position).getStartTime());
                    tvendtime.setText(periodList.get(position).getEndTime());
                   tvperioddetail.setText(periodList.get(position).getPeriodDetail());
                    Rlistview.setBackgroundColor(getResources().getColor(R.color.BgColor));
                   if(periodList.get(position).getPeriodDetail().toString().toLowerCase().contains("break"))
                   {
                       Rlistview.setBackgroundColor(getResources().getColor(R.color.breakcolor));
                   }
                   else if(periodList.get(position).getPeriodDetail().toString().toLowerCase().contains("lab"))
                   {
                       Rlistview.setBackgroundColor(getResources().getColor(R.color.labcolor));
                   }
                   //For Period Number
                   int myno=position;
                   tvperiodNo.setText(Integer.toString(++myno));



            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {

                    //  btndelete.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                    btn_edit_period.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                    btn_delete_period.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));

                }catch (Exception e)
                {

                }
            }

            btn_delete_period.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDeletePeriod(keyList.get(position));

                }
            });
            btn_edit_period.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPeriod(keyList.get(position),periodList.get(position),position);

                }
            });




            return convertView;

        }

        public void clearData() {
            periodList.clear();
            keyList.clear();
            compareListDis.clear();
            compareListStartTime.clear();
            compareListEndTime.clear();

        }


    }

//End SEt Up LISTVIEW
//DataBase SetUp
    public  void    setUpDatabase() {


        DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("TimeTable").child(timetablefetch).child(streamkey).child("semesters").child(semesterKey).child("periods");


        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                   myadapter.clearData();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        try {
                             keyList.add(dsp.getKey().toString());//Add Key to KeyLIst
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



                }

                catch(Exception e)
                {



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //databaseError.getMessage().toString();
                //  Toast.makeText(this, "Server Unreachable", Toast.LENGTH_LONG).show();

            }
        });
    }
public void addPeriod()
{
    DatabaseReference myref= FirebaseDatabase.getInstance().getReference().child("TimeTable").child(timetablefetch).child(streamkey).child("semesters").child(semesterKey)
        .child("periods");
    LayoutInflater inflater=getLayoutInflater();
    PeriodDialog newPeriod= new PeriodDialog(Display_Timetable_of_Semester.this,"Add New Class","Add",inflater);
    newPeriod.setUpDataEnd(compareListStartTime,compareListEndTime,compareListDis,myref);
    newPeriod.setSnackBarText("New Class Added",parentLayout);
}

public void editPeriod(String key,Period period,int position)
{
    LayoutInflater inflater=getLayoutInflater();
    DatabaseReference myref= FirebaseDatabase.getInstance().getReference().child("TimeTable").child(timetablefetch).child(streamkey).child("semesters").child(semesterKey)
            .child("periods").child(key);

    PeriodDialog newPeriod=new PeriodDialog(Display_Timetable_of_Semester.this,"Edit Period","Update Period",inflater);
    compareListEndTime.remove(position);
    compareListStartTime.remove(position);
    compareListDis.remove(position);
    newPeriod.setUpDataEnd(compareListStartTime,compareListEndTime,compareListDis,myref);
    newPeriod.editPeriod(key,period);

}

public void deletePeriod(String key)
{
    DatabaseReference myref= FirebaseDatabase.getInstance().getReference().child("TimeTable").child(timetablefetch).child(streamkey).child("semesters").child(semesterKey)
            .child("periods").child(key);

    myref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            new Snackbar(parentLayout,"Deleted ");

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

            new Snackbar(parentLayout,"Failed to Delete ");

        }
    });
}


public void confirmDeletePeriod(final String key)
{
    android.support.design.widget.Snackbar mysnak;
    mysnak= android.support.design.widget.Snackbar.make(parentLayout,"Do You  Want to Delete ", android.support.design.widget.Snackbar.LENGTH_LONG);
    mysnak .setAction("Yes", new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            deletePeriod(key);
        }
    });
    mysnak.show();

}


}
