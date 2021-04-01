package com.college.baramulla.govtdegreecollegebaramulla;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmet_Timetable_PG extends Fragment {

   View myview;

    ListView mylistview;
    ArrayList<Streams> streamList;
    ArrayList<String>  streanKeylist;
    ArrayList<String> compareListStreamNames;
    SimpleAdapter myadapter;
    private DatabaseReference myDatabaseRef;
    View myLoadinglayout;
    View parentLayout;// View Of Layout USed In XMl File For SnackBar

    private String updateurl = "null", versionclosed = "null";//ForUpdate Section
    String databaseVersionLink="Version1dot0";
    public Fragmet_Timetable_PG() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
       myview= inflater.inflate(R.layout.fragment_fragmet__timetable__pg, container, false);

       setUpviews();
        streamList=new ArrayList<Streams>();
        streanKeylist=new ArrayList<String>();
        streamList.add(new Streams("Demo"));
        compareListStreamNames=new ArrayList<>();
        setUpListView();
        setUpDatabase();
        appupdate();

       return  myview;//Return SHould Be At Last
    }
    //CODE  FOR OPTION MENU


    //ENd OPTION  MENU CODE
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    //Rest Time Table Code
    public void setUpviews()
    {

        mylistview= myview.findViewById(R.id.listviewTpg);
        myLoadinglayout= myview.findViewById(R.id.Timetable_pg_loadingview);
        parentLayout= myview.findViewById(R.id.parentlayout_pg_timetable);

    }
    //List View Set Up
    public void setUpListView()
    {

        myadapter = new SimpleAdapter(getActivity());

        mylistview.setAdapter(myadapter);
        mylistview.setVisibility(View.INVISIBLE);
        myLoadinglayout.setVisibility(View.VISIBLE);

        //ClickListener On ListView


    }
    //End ListView Set Up
    //Data Adapter
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
            return streamList.size();
        }

        @Override
        public Object getItem(int i) {
            return streamList.get(i);
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

                tvStreamname.setText(streamList.get(position).getStreamName());
                tvfirtsleter.setText(streamList.get(position).getStreamName().substring(0, 1));
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

                        if(updateurl.equalsIgnoreCase("null")) {
                            Intent intent = new Intent(getActivity(), com.college.baramulla.govtdegreecollegebaramulla.semester_activity.class);
                            //intent.putExtra("category",categoryname);
                            intent.putExtra("streamKey", streanKeylist.get(position));
                            intent.putExtra("streamName", streamList.get(position).getStreamName());
                            intent.putExtra("fetch", "TimeTable_PG");
                            //  intent.putExtra("position",position);
                            //   makeToast(position);
                            startActivity(intent);
                        }else{SetupDialogUpdate();}
                    }
                });

            }catch(Exception e)
            {}

            return convertView;

        }

        public void clearData() {
            // clear the data
            streamList.clear();
            streanKeylist.clear();
            compareListStreamNames.clear();
            //keylist.clear();
        }

        public boolean isEmpty()
        {

            return streamList.isEmpty();
        }


    }
    //End Data Adapter

    //Database Set Up
    public  void    setUpDatabase()
    {


        myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("TimeTable").child("TimeTable_PG");
        myDatabaseRef.keepSynced(true);
        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    // Toast.makeText(getActivity(), "Step0 pass", Toast.LENGTH_LONG).show();
                    myadapter.clearData();

                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        try {
                            streanKeylist.add(dsp.getKey());//Add Key to KeyLIst
                            Streams stream;
                            stream=dsp.getValue(Streams.class);
                            streamList.add(stream);
                            compareListStreamNames.add(stream.getStreamName().trim().toLowerCase());//Add Stream Names To CompareList

                        }catch(Exception e)
                        {}

                    }


                    setUpListView();
                    myadapter.notifyDataSetChanged();
                    mylistview.setVisibility(View.VISIBLE);//SET VIEWS VISIBLE
                    myLoadinglayout.setVisibility(View.INVISIBLE);
                    if(myadapter.isEmpty())
                    {
                     //   new    Snackbar(parentLayout,"UG Streams Not Added Yet");
                    }



                }

                catch(Exception e)
                {



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //databaseError.getMessage().toString();
                Toast.makeText(getActivity(), "Server Unreachable", Toast.LENGTH_LONG).show();

            }
        });
    }

    //End Data Base Setup


    // ADD EDIT AND DELETE METHODS


//Update App Section


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

            dialogbuilder = new AlertDialog.Builder(getActivity());
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

                      //  HideDialog(alertDialog);
                        gotoPlayStore();
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



}
