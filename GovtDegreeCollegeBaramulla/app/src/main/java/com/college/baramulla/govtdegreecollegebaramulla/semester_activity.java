package com.college.baramulla.govtdegreecollegebaramulla;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class semester_activity extends AppCompatActivity {

    View myloadingview,mymainview,parentLayout;
    ListView mylistview;
    View loadingView,mainView;
    ArrayList<String> keyList,compareList;
    ArrayList<Semester> mysemesterList;
    SimpleAdapter myadapter;
    private android.support.v7.widget.Toolbar toolbar;
    ActionBar mytoolbar;
    TextView tvtoolbartitle;
    ImageView btnbackimg;
    String streamkey="null";
    String timetablefetch="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_activity);
        //Intilizing Adapters:
        keyList =new ArrayList<>();
        keyList.add("NULL");
        compareList =new ArrayList<>();
        compareList.add("null");
        mysemesterList =new ArrayList<>();
        mysemesterList.add(new Semester("Null"));

        setUpviews();
        setUpListView();
        setUpDatabase();



    }
    public void setUpToolBar()
    {


        toolbar= findViewById(R.id.toolbar_in_semester_activity);
        setSupportActionBar(toolbar);
        mytoolbar=getSupportActionBar();
        mytoolbar.setTitle(0);
        String title="Null";
        title=getIntent().getStringExtra("streamName");
        timetablefetch=getIntent().getStringExtra("fetch");
        tvtoolbartitle.setText(title+"\t TimeTable");
       // tvcatname.setText(categoryname);
      //  mytoolbar.setDisplayHomeAsUpEnabled(true);
       // mytoolbar.setDisplayShowHomeEnabled(true);


    }


    //ENd MEnu COde

    public  void setUpviews()
    {
        mylistview= findViewById(R.id.listview_show_semesterList);
        parentLayout= findViewById(R.id.parenlayout_in_semester_activity);
        tvtoolbartitle= findViewById(R.id.tv_in_semester_toolbar);
        btnbackimg= findViewById(R.id.imgback_in_semester_toolbar);
        parentLayout= findViewById(R.id.parenlayout_in_semester_activity);
        streamkey=getIntent().getStringExtra("streamKey");
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
        TextView tvStreamname;
        TextView tvfirtsleter;
        ImageView btndelete,btnedit;
        private ImageView imageview;

        public SimpleAdapter(Context context) {
            mcontext = context;
            layoutInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return mysemesterList.size();
        }

        @Override
        public Object getItem(int i) {
            return mysemesterList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.stream_single_item, null);
            }
            try {
                tvStreamname = convertView.findViewById(R.id.tvstreamname);
                tvfirtsleter = convertView.findViewById(R.id.tvstreamfleter);
                tvStreamname.setText(mysemesterList.get(position).getSemesterName());
                tvfirtsleter.setText(mysemesterList.get(position).getSemesterName().substring(0, 1));
                //Backgroud Ripple Set
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {


                        tvStreamname.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));

                    }catch (Exception e)
                    {

                    }
                }


                // Click Listener For LIST ITEM CLICKED
                tvStreamname.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(semester_activity.this,com.college.baramulla.govtdegreecollegebaramulla.Display_Timetable_of_Semester.class);
                        intent.putExtra("semesterName",mysemesterList.get(position).getSemesterName());
                        intent.putExtra("streamKey",streamkey);
                        intent.putExtra("semesterKey",keyList.get(position));
                        intent.putExtra("fetch",timetablefetch);

                        //  intent.putExtra("position",position);
                        //   makeToast(position);
                        startActivity(intent);
                    }
                });

            }catch(Exception e)
            {}

            return convertView;

        }

        public void clearData() {
            // clear the data
            mysemesterList.clear();
            keyList.clear();
            compareList.clear();

        }

        public boolean isEmpty()
        {

            return mysemesterList.isEmpty();
        }


    }

    //Data base Setupu
    public  void    setUpDatabase()
    {


     DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("TimeTable").child(timetablefetch).child(streamkey).child("semesters");
        myDatabaseRef.keepSynced(true);

        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    myadapter.clearData();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        try {
                            keyList.add(dsp.getKey());//Add Key to KeyLIst


                            Semester semester;
                            semester=dsp.getValue(Semester.class);
                            mysemesterList.add(semester);
                            compareList.add(semester.getSemesterName().trim().toLowerCase());//Add Stream Names To CompareList


                        }catch(Exception e)
                        {}

                    }


                    setUpListView();
                    myadapter.notifyDataSetChanged();
                    mylistview.setVisibility(View.VISIBLE);//SET VIEWS VISIBLE
                   // myLoadinglayout.setVisibility(View.INVISIBLE);

                    if(myadapter.isEmpty())
                    {
                        new    Snackbar(parentLayout," Timetable  Not Added Yet");
                    }



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






    //Semester






}
