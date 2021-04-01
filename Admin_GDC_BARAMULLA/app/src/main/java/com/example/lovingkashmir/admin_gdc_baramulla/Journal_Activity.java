package com.example.lovingkashmir.admin_gdc_baramulla;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Journal_Activity extends AppCompatActivity {
    ListView mylistview;
    ProgressBar mypbar;

    ArrayList<Journal_Post> myJournalPosts;

    JournalPostsDataAdapter myadapter;
    private android.support.v7.widget.Toolbar toolbar;
    ActionBar mytoolbar;

    ImageView img_btn_addpost;
    ImageView author_image;
    Uri selectedImage;
    Boolean isauthorImageChosed=false;
    String authorImageUrl="null";



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

        img_btn_addpost=findViewById(R.id.img_add_Jpost);

        //   mylistview.setVisibility(View.INVISIBLE);
        setUpToolBar();
        setUpListView();
        setUpDatabase();

        img_btn_addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddJournalPost();
            }
        });
    }

    public void setUpToolBar()
    {


        toolbar= findViewById(R.id.toolbar_in_journal_activity);
        setSupportActionBar(toolbar);
        mytoolbar=getSupportActionBar();
        mytoolbar.setTitle(0);


        ImageView btnbackimg= findViewById(R.id.imgback_in_jornal_toolbar);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {

                //  btndelete.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                btnbackimg.setBackground(getResources().getDrawable(R.drawable.circle_bg_ripple));
                img_btn_addpost.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));

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
                        Intent intent = new Intent(Journal_Activity.this,com.example.lovingkashmir.admin_gdc_baramulla.show_journal_post_activity.class);
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


     EditText et_postTitle,etPostBody,etauthorDetails;
     AlertDialog alertDialog;
    public void AddJournalPost()
    {
        AlertDialog.Builder dialogbuilder;
    //    final AlertDialog alertDialog;
        TextView btn_tv_submit_post;

        try {

            LayoutInflater inflater = getLayoutInflater();
            final View dialogview = inflater.inflate(R.layout.dialog_addpost_journal, null);
            et_postTitle=dialogview.findViewById(R.id.tv_post_title_full_d);
            etPostBody=dialogview.findViewById(R.id.Tv_post_details_d);
            etauthorDetails=dialogview.findViewById(R.id.tv_post_author_details_d);
            btn_tv_submit_post=dialogview.findViewById(R.id.Tv_btn_submit_JPost);
            author_image=dialogview.findViewById(R.id.img_post_author_d);
            dialogbuilder = new AlertDialog.Builder(this);
            dialogbuilder.setView(dialogview);
            alertDialog = dialogbuilder.create();
            showDialog(alertDialog);
            btn_tv_submit_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(et_postTitle.getText().toString().isEmpty() || etPostBody.getText().toString().isEmpty() || etauthorDetails.getText().toString().isEmpty())
                    {
                        et_postTitle.setError("Please Enter All Details");
                    }
                    else{
                        AddNewPostDetails(alertDialog);

                    }
                }
            });

            author_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // getImage();
                }
            });

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

//Pickup_Image From Gallery
   final int PICK_IMAGE_REQUEST=898;
    public void getImage()
    {

        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            selectedImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                // Log.d(TAG, String.valueOf(bitmap));
                // ImageView imageView = (ImageView) findViewById(R.id.imageView);
                author_image.setImageBitmap(bitmap);
                isauthorImageChosed=true;
                uploadPost();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void AddNewPostDetails(AlertDialog dialog)
    {
        getImage();
    }

    public void  uploadPost()
    {



          // getImage();
            //displaying a progress dialog while upload is going on
        final DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Journal_Posts");
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Post..");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            StorageReference storageReference= FirebaseStorage.getInstance().getReference();
            StorageReference riversRef = storageReference.child("images/"+System.currentTimeMillis()+"pic.jpg");
            riversRef.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();
                          //  authorImageUrl =taskSnapshot.getDownloadUrl().toString();
                            Journal_Post mypost=new Journal_Post(et_postTitle.getText().toString(),etPostBody.getText().toString(),etauthorDetails.getText().toString(),taskSnapshot.getDownloadUrl().toString());

                            myDatabaseRef.push().setValue(mypost);
                            HideDialog(alertDialog);
                            Toast.makeText(getApplicationContext(), "Post Uploaded ", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                           // progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploading Post.. " + ((int) progress) + "%...");
                        }
                    });
        }

    }



