package com.example.lovingkashmir.admin_gdc_baramulla;

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


        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_in_semester_activity);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_semester_activity_timetable, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add_semester_menu_item:
                addSemester();
                // addnewStream();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //ENd MEnu COde

    public  void setUpviews()
    {
        mylistview=(ListView)findViewById(R.id.listview_show_semesterList);
        parentLayout=(View)findViewById(R.id.parenlayout_in_semester_activity);
        tvtoolbartitle=(TextView)findViewById(R.id.tv_in_semester_toolbar);
        btnbackimg=(ImageView)findViewById(R.id.imgback_in_semester_toolbar);
        parentLayout=(View)findViewById(R.id.parenlayout_in_semester_activity);
        streamkey=getIntent().getStringExtra("streamKey");
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
                tvStreamname = (TextView) convertView.findViewById(R.id.tvstreamname);
                tvfirtsleter = (TextView) convertView.findViewById(R.id.tvstreamfleter);
                btndelete=(ImageView)convertView.findViewById(R.id.imgedeletestream);
                btnedit=(ImageView)convertView.findViewById(R.id.imgeditstream);
                tvStreamname.setText(mysemesterList.get(position).getSemesterName());
                tvfirtsleter.setText(mysemesterList.get(position).getSemesterName().substring(0, 1));
                //Backgroud Ripple Set
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {

                        btndelete.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                        btnedit.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                        tvStreamname.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));

                    }catch (Exception e)
                    {

                    }
                }
                btnedit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        confrimActionEdit(mysemesterList.get(position).getSemesterName(),keyList.get(position));




                    }
                });
                btndelete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        confirmActionDelete(keyList.get(position),mysemesterList.get(position).getSemesterName());
                    }
                });
                // Click Listener For LIST ITEM CLICKED
                tvStreamname.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(semester_activity.this,com.example.lovingkashmir.admin_gdc_baramulla.Display_Timetable_of_Semester.class);
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
            //keylist.clear();
        }


    }

    //Data base Setupu
    public  void    setUpDatabase()
    {


     DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("TimeTable").child(timetablefetch).child(streamkey).child("semesters");


        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    myadapter.clearData();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        try {
                            keyList.add(dsp.getKey().toString());//Add Key to KeyLIst


                            Semester semester;
                            semester=dsp.getValue(Semester.class);
                            mysemesterList.add(semester);
                            compareList.add(semester.getSemesterName().trim().toLowerCase());//Add Stream Names To CompareList
                            mylistview.setVisibility(View.VISIBLE);


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
    public void editSemester(String streamName,String key)
    {

        LayoutInflater inflater=getLayoutInflater();
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("TimeTable").child(timetablefetch).child(streamkey).child("semesters").child(key).child("semesterName");
        EditDialog editDialog= new EditDialog(semester_activity.this,streamName,"Enter New Semester  Name","Update Now",inflater,myRef,compareList,parentLayout);
        editDialog.setSnackBarText("Semester Name Updated");

    }

    public void deleteSemester(String key)
    {
        try {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("TimeTable").child(timetablefetch)
                    .child(streamkey).child("semesters")
                    .child(key);
            myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //   Toast.makeText(mycontext,Excep , Toast.LENGTH_LONG).show();
                    //  streanKeylist.remove(index);
                    com.example.lovingkashmir.admin_gdc_baramulla.Snackbar mysnack=new com.example.lovingkashmir.admin_gdc_baramulla.Snackbar(parentLayout,"Semester Deleted");


                }

            });
        }catch (Exception e){}
    }

    public void addSemester()
    {
        LayoutInflater inflater=getLayoutInflater();
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("TimeTable").child(timetablefetch).child(streamkey).child("semesters").push().child("semesterName");
        EditDialog editDialog= new EditDialog(semester_activity.this,"Enter New Semester  Name","Add Now",inflater,myRef,compareList,parentLayout);
        editDialog.setSnackBarText("Semester Added");

    }

    //Semester

    public void confirmActionDelete(final String key , String Name)
    {

        android.support.design.widget.Snackbar mysnak;
        mysnak= android.support.design.widget.Snackbar.make(parentLayout,"Do You  Want to Delete "+Name, android.support.design.widget.Snackbar.LENGTH_LONG);
        mysnak .setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSemester(key);
            }
        });
        mysnak.show();
    }


    public void confrimActionEdit(final String Name, final String key)
    {

        android.support.design.widget.Snackbar mysnak;
        mysnak= android.support.design.widget.Snackbar.make(parentLayout,"Do You  Want to Edit "+Name, android.support.design.widget.Snackbar.LENGTH_LONG);
        mysnak .setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  deleteStream(key,Index);
                editSemester(Name,key);
            }
        });
        mysnak.show();

    }




}
