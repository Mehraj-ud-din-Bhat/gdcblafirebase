package com.college.baramulla.govtdegreecollegebaramulla;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class Journal_Activity extends AppCompatActivity {
    ListView mylistview;
    ProgressBar mypbar;

    ArrayList<Journal_Post> myJournalPosts;

    JournalPostsDataAdapter myadapter;
    private android.support.v7.widget.Toolbar toolbar;
    ActionBar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_);
        myJournalPosts=new ArrayList<>();
        myJournalPosts.add(new Journal_Post("null","null","null","null"));
        mypbar=findViewById(R.id.pg_bar_in_journal_ac);
        mypbar.setVisibility(View.VISIBLE);
        mylistview=findViewById(R.id.listview_In_journal_ac);
        mylistview.setVisibility(View.INVISIBLE);
        setUpToolBar();
        setUpListView();
        setUpDatabase();
    }

    //3 DOT MNU CODE
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jpost, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuitem_send_post:
                showDialogSendPost();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //END  3 DOT MENU CODE


    public void setUpToolBar()
    {


        toolbar= findViewById(R.id.toolbar_in_journal_activity);
        setSupportActionBar(toolbar);
        mytoolbar=getSupportActionBar();
        mytoolbar.setTitle(0);


        ImageView   btnbackimg= findViewById(R.id.imgback_in_jornal_toolbar);
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

        //  mytoolbar.setDisplayHomeAsUpEnabled(true);
        // mytoolbar.setDisplayShowHomeEnabled(true);


    }




    void setUpListView()
    {

        mypbar.setVisibility(View.VISIBLE);
    //    mylistview.setVisibility(View.INVISIBLE);
        myadapter=new JournalPostsDataAdapter(this);
        mylistview.setAdapter(myadapter);


    }

    public class JournalPostsDataAdapter extends BaseAdapter {
        private Context mcontext;
        private LayoutInflater layoutInflater;
        TextView tv_post_title;
        ImageView img_author_photo;



        public JournalPostsDataAdapter(Context context) {
            mcontext = context;
            layoutInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return myJournalPosts.size();
        }

        @Override
        public Object getItem(int i) {
            return myJournalPosts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.journal_titel_post_single_item, null);
            }
            try {
                //   tvStreamname = convertView.findViewById(R.id.tvstreamname);
                tv_post_title= convertView.findViewById(R.id.tv_jp_title);
                img_author_photo=convertView.findViewById(R.id.img_jornal_post_title);


              //  tv_adminDetails.setText(myAdminList.get(position).adminDetails);
                tv_post_title.setText(myJournalPosts.get(position).getPostTitle());
                String  admin_ph_url=myJournalPosts.get(position).postauthror_photourl;

                Picasso.get().load(admin_ph_url)
                        .placeholder(getResources().getDrawable(R.drawable.ic_person_black_24dp))
                        .error(getResources().getDrawable(R.drawable.ic_person_black_24dp))
                        .into(img_author_photo);
                //  makeToast(myAdminList.get(position).adimEmail);

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {

                         tv_post_title.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                      //  tv_btn_contact_admin.setBackground(getResources().getDrawable(R.drawable.design_button_design_ripple2));

                    }catch (Exception e)
                    {

                    }
                }

                tv_post_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //   Toast.makeText(this,"",Toast.LENGTH_LONG);
                        Intent intent = new Intent(Journal_Activity.this,com.college.baramulla.govtdegreecollegebaramulla.show_journal_post_activity.class);
                     intent.putExtra("postTitle",myJournalPosts.get(position).getPostTitle());
                     intent.putExtra("postDetails",myJournalPosts.get(position).getPostDeatils());
                     intent.putExtra("authorDetails",myJournalPosts.get(position).getPostauthor_Details());
                     intent.putExtra("authorPhotoLink",myJournalPosts.get(position).getPostauthror_photourl());
                     startActivity(intent);

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
            myJournalPosts.clear();


        }

        public boolean isEmpty()
        {

            return myJournalPosts.isEmpty();
        }


    }

    public void setUpDatabase()
    {


        DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Journal_Posts");
     //   myDatabaseRef.push().setValue(myJournalPosts.get(0));
        //   myDatabaseRef.push().setValue(myAdminList.get(0));
        myDatabaseRef.keepSynced(true);
        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    // Toast.makeText(getActivity(), "Step0 pass", Toast.LENGTH_LONG).show();
                    myadapter.clearData();

                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        try {

                            Journal_Post mypost;
                            mypost=dsp.getValue(Journal_Post.class);
                            myJournalPosts.add(mypost);



                        }catch(Exception e)
                        {}

                    }


                    setUpListView();
                    mypbar.setVisibility(View.INVISIBLE);
                    mylistview.setVisibility(View.VISIBLE);
                    myadapter.notifyDataSetChanged();
                    Collections.reverse(myJournalPosts);

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

    //


    public void showDialogSendPost()
    {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_help_icon);
            builder.setTitle("How to Send Article").setMessage("In this section  We Post Articles\nYou Can Also Send us Article \nif you want to send article that can be" +
                    " uploaded here  drop an email to Application Journal Admin he will get in touch with you").setPositiveButton("Contact Journal Admin", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Journal_Activity.this, com.college.baramulla.govtdegreecollegebaramulla.admins_Activity.class));

                }
            });


            builder.create().show();
        } catch (Exception e) {
        }

    }

}
