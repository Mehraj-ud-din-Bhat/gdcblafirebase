package com.college.baramulla.govtdegreecollegebaramulla;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class admins_Activity extends AppCompatActivity {


   ArrayList<Admin> myAdminList;
   AdminDataAdapter myadapter;


    ListView mylistview;
    ProgressBar mypbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_);
        myAdminList=new ArrayList<>();
        myAdminList.add(new Admin("null","null","null"));
      // myAdminList.add(new Admin("null","null","null"));
        //myAdminList.add(new Admin("null","null","null"));
        //myAdminList.add(new Admin("null","null","null"));
        mypbar=findViewById(R.id.pgbar_in_admin_ac);

        setUpListView();
        setUpDatabase();


        ImageView btnbackimg= findViewById(R.id.imgback_in_admins_ac);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {


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

   void setUpListView()
   {
           mylistview=findViewById(R.id.listview_show_admins);
       mypbar.setVisibility(View.VISIBLE);
       mylistview.setVisibility(View.INVISIBLE);
           myadapter=new AdminDataAdapter(this);
           mylistview.setAdapter(myadapter);


   }

    public class AdminDataAdapter extends BaseAdapter {
        private Context mcontext;
        private LayoutInflater layoutInflater;
        TextView tv_adminDetails;
        ImageView imgadmin_photo;
        TextView tv_btn_contact_admin;
        String admin_ph_url;


        public AdminDataAdapter(Context context) {
            mcontext = context;
            layoutInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return myAdminList.size();
        }

        @Override
        public Object getItem(int i) {
            return myAdminList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.admin_single_item, null);
            }
            try {
                //   tvStreamname = convertView.findViewById(R.id.tvstreamname);
                 tv_adminDetails = convertView.findViewById(R.id.tvv_admin_details);
                 imgadmin_photo=convertView.findViewById(R.id.imge_admin_single);
                 tv_btn_contact_admin=convertView.findViewById(R.id.btn_tv_contact_admin);

                 tv_adminDetails.setText(myAdminList.get(position).adminDetails);
                  admin_ph_url=myAdminList.get(position).adminPhotourl;

                 Picasso.get().load(admin_ph_url)
                         .placeholder(getResources().getDrawable(R.drawable.ic_person_black_24dp))
                         .error(getResources().getDrawable(R.drawable.ic_person_black_24dp))
                         .into(imgadmin_photo);
              //  makeToast(myAdminList.get(position).adimEmail);
                //makeToast(myAdminList.get(position).getAdimEmail());
             //

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {

                        //  btndelete.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                        tv_btn_contact_admin.setBackground(getResources().getDrawable(R.drawable.design_button_design_ripple2));

                    }catch (Exception e)
                    {

                    }
                }



                 tv_btn_contact_admin.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                      //   Toast.makeText(this,"",Toast.LENGTH_LONG);

                         String mail=myAdminList.get(position).getAdminEmail();
                         Intent email= new Intent(Intent.ACTION_SENDTO);
                         email.setData(Uri.parse("mailto:"));
                         email.putExtra(Intent.EXTRA_EMAIL,new String[]{mail});
                         email.putExtra(Intent.EXTRA_SUBJECT,"Contact Admin");
                         if(email.resolveActivity(getPackageManager()) !=null)
                         {
                             startActivity(email);
                         }
                     }
                 });




                return convertView;

            }

            catch (Exception e) {

               return convertView;
            }
        }

        public void clearData() {
            // clear the data
            myAdminList.clear();


        }

        public boolean isEmpty()
        {

            return myAdminList.isEmpty();
        }


    }


    public void setUpDatabase()
    {


      DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Admins");
 //   for(int i=1;i<=4;i++)
   //  myDatabaseRef.push().setValue(myAdminList.get(0));
        myDatabaseRef.keepSynced(true);
        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            try {
                    // Toast.makeText(getActivity(), "Step0 pass", Toast.LENGTH_LONG).show();
                    myadapter.clearData();

                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        try {

                          Admin admin;
                          admin=dsp.getValue(Admin.class);
                          myAdminList.add(admin);
                         //   makeToast(admin.getAdimEmail());



                        }catch(Exception e)
                        {}

                    }


                    setUpListView();
                mypbar.setVisibility(View.INVISIBLE);
                mylistview.setVisibility(View.VISIBLE);
                    myadapter.notifyDataSetChanged();

                    if(myadapter.isEmpty())
                    {

                    }



                }

                catch(Exception e)
                {



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //databaseError.getMessage().toString();


            }
        });
    }


    public void makeToast(String text)
    {
    Toast.makeText(this,text,Toast.LENGTH_LONG).show();

    }

}
