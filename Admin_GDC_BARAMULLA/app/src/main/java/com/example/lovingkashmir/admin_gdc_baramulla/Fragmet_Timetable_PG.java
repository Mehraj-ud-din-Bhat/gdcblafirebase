package com.example.lovingkashmir.admin_gdc_baramulla;


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

       return  myview;//Return SHould Be At Last
    }
    //CODE  FOR OPTION MENU
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_pg_timetable, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add_pg_stream_menu_item:
                addnewStream();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //ENd OPTION  MENU CODE
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    //Rest Time Table Code
    public void setUpviews()
    {

        mylistview=(ListView)myview.findViewById(R.id.listviewTpg);
        myLoadinglayout=(View)myview.findViewById(R.id.Timetable_pg_loadingview);
        parentLayout=(FrameLayout)myview.findViewById(R.id.parentlayout_pg_timetable);

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
                tvStreamname = (TextView) convertView.findViewById(R.id.tvstreamname);
                tvfirtsleter = (TextView) convertView.findViewById(R.id.tvstreamfleter);
                btndelete=(ImageView)convertView.findViewById(R.id.imgedeletestream);
                btnedit=(ImageView)convertView.findViewById(R.id.imgeditstream);
                tvStreamname.setText(streamList.get(position).getStreamName());
                tvfirtsleter.setText(streamList.get(position).getStreamName().substring(0, 1));
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

                        confrimActionEdit(streamList.get(position).getStreamName(),streanKeylist.get(position));



                    }
                });
                btndelete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        confirmActionDelete(streanKeylist.get(position),position,streamList.get(position).getStreamName());
                    }
                });


                // Click Listener For LIST ITEM CLICKED
                tvStreamname.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),com.example.lovingkashmir.admin_gdc_baramulla.semester_activity.class);
                        //intent.putExtra("category",categoryname);
                        intent.putExtra("streamKey",streanKeylist.get(position));
                        intent.putExtra("streamName",streamList.get(position).getStreamName());
                        intent.putExtra("fetch","TimeTable_PG");
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
            streamList.clear();
            streanKeylist.clear();
            compareListStreamNames.clear();
            //keylist.clear();
        }


    }
    //End Data Adapter

    //Database Set Up
    public  void    setUpDatabase()
    {


        myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("TimeTable").child("TimeTable_PG");
        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    // Toast.makeText(getActivity(), "Step0 pass", Toast.LENGTH_LONG).show();
                    myadapter.clearData();

                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        try {
                            streanKeylist.add(dsp.getKey().toString());//Add Key to KeyLIst
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

    public void editStream(String streamName,String key)
    {
        LayoutInflater inflater=getLayoutInflater();
        //EditDialog =new EditDialog(getActivity().this,"HELLO","HELLo",inflater);
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("TimeTable").child("TimeTable_PG").child(key).child("streamName");
        EditDialog editDialog= new EditDialog(getActivity(),streamName,"Update PG Stram Name","Update",inflater,myRef,compareListStreamNames,parentLayout);


    }

    public void deleteStream(String key,final int index)
    {
        try{

            FirebaseDatabase.getInstance().getReference().child("TimeTable").child("TimeTable_PG")
                    .child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //   Toast.makeText(mycontext,Excep , Toast.LENGTH_LONG).show();
                    //  streanKeylist.remove(index);
                    com.example.lovingkashmir.admin_gdc_baramulla.Snackbar mysnack=new com.example.lovingkashmir.admin_gdc_baramulla.Snackbar(myview,"Stream Deleted");


                }

            });



        }catch (Exception e)
        {}

    }

    public void addnewStream()
    {
        LayoutInflater inflater=getLayoutInflater();
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("TimeTable").child("TimeTable_PG").push().child("streamName");
        EditDialog editDialog= new EditDialog(getActivity(),"Enter New PG Stram Name","Add Now",inflater,myRef,compareListStreamNames,parentLayout);


    }
    //END METHOD

    public void confirmActionDelete(final String key , final int Index, String Name)
    {

        android.support.design.widget.Snackbar mysnak;
        mysnak= android.support.design.widget.Snackbar.make(parentLayout,"Do You  Want to Delete "+Name, android.support.design.widget.Snackbar.LENGTH_LONG);
        mysnak .setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStream(key,Index);
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
                editStream(Name,key);
            }
        });
        mysnak.show();

    }

}
