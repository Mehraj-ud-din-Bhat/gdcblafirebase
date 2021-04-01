package com.example.lovingkashmir.admin_gdc_baramulla;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;

public class Notifications_Activity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    ActionBar mytoolbar;
    ListView mylistview;
    ArrayList<Notification> notificationList;
    SimpleAdapter myadapter;

    ArrayList<String> myNoticeKeyList;

    ImageView btnbackimg;

    View cardLayoutHalfNotice;

    View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_);
        notificationList=new ArrayList<>();
        notificationList.add(new Notification("NULL","Null"));
        myNoticeKeyList=new ArrayList<>();
        myNoticeKeyList.add("Null");
        setUpViews();
        setUpToolBar();
        setUpListView();
        setUpDatabase();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_item_add_notice:
                // addnewStream();
                SetupDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUpViews()
    {
        mylistview=(ListView)findViewById(R.id.listview_in_notification);
        mylistview.setVisibility(View.INVISIBLE);

        btnbackimg=(ImageView)findViewById(R.id.imgback_in_notification_toolbar_notification);
        rootLayout=findViewById(R.id.rootLayout_notification_activity);

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
        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_in_notification_activity);
        setSupportActionBar(toolbar);
        mytoolbar=getSupportActionBar();
        mytoolbar.setTitle(0);



    }

    public class SimpleAdapter extends BaseAdapter {
        private Context mcontext;
        private LayoutInflater layoutInflater;
        TextView tvNoticetitle;
        TextView tvNoticebody;
        ImageView img_del_notice;


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
            tvNoticetitle = (TextView) convertView.findViewById(R.id.tvnoticetitle);
            cardLayoutHalfNotice=(View)convertView.findViewById(R.id.layout_card_halfNotice);
            img_del_notice=convertView.findViewById(R.id.imgedelete_notice);
            tvNoticetitle.setText(notificationList.get(position).getNotificationtitle());


            // Click Listener For LIST ITEM CLICKED
            cardLayoutHalfNotice.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                  /*  Intent intent = new Intent(Notifications_Activity.this,show_notification.class);
                    //intent.putExtra("category",categoryname);
                    intent.putExtra("NoticeTitle",notificationList.get(position).getNotificationtitle());
                    //   intent.putExtra("NoticeBody",notificationList.get(position).getNotificationbody());
                    intent.putExtra("NoticeLink",notificationList.get(position).getNoticeLink());
                    startActivity(intent);*/


                  Intent pdfIntent=new Intent();
                  pdfIntent.setData(Uri.parse(notificationList.get(position).getNoticeLink()));
                  startActivity(pdfIntent);
                }
            });
            img_del_notice.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                  /*  Intent intent = new Intent(Notifications_Activity.this,show_notification.class);
                    //intent.putExtra("category",categoryname);
                    intent.putExtra("NoticeTitle",notificationList.get(position).getNotificationtitle());
                    //   intent.putExtra("NoticeBody",notificationList.get(position).getNotificationbody());
                    intent.putExtra("NoticeLink",notificationList.get(position).getNoticeLink());
                    startActivity(intent);*/
                 // makeToast(notificationList.get(position).noticeLink);
                    android.support.design.widget.Snackbar.make(rootLayout, "Do You Want to Delete Notice", Snackbar.LENGTH_LONG)
                            .setAction("Yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteNotice(notificationList.get(position).noticeLink,myNoticeKeyList.get(position));
                                }
                            }).show();



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
            myNoticeKeyList.clear();
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
                        myNoticeKeyList.add(dsp.getKey());
                        notificationList.add(notification);

                        // }catch(Exception e)
                        // {
                        // Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        //   }

                    }


                    // setUpListView();
                    Collections.reverse(notificationList);
                    Collections.reverse(myNoticeKeyList);
                    myadapter.notifyDataSetChanged();
                    mylistview.setVisibility(View.VISIBLE);//SET VIEWS VISIBLE
                    // myLoadinglayout.setVisibility(View.INVISIBLE);



                }

                catch(Exception e)
                {

                    Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //databaseError.getMessage().toString();
                //  Toast.makeText(this, "Server Unreachable", Toast.LENGTH_LONG).show();

            }
        });
    }


// Notitifaction Upload Section


final static int PICK_PDF_CODE = 2342;

    public void uploadNotice()
    {


    }



    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            Toast.makeText(this, "Allow Storage Permission ", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
               uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
                tv_upload_button.setClickable(true);
            }
        }
    }


    // Final Upload
    private void uploadFile(Uri data) {
      //  progressBar.setVisibility(View.VISIBLE);

        StorageReference    mStorageReference = FirebaseStorage.getInstance().getReference();
        StorageReference sRef = mStorageReference.child(et_notice_name.getText().toString()+"_gdc_boys_baramulla_" + System.currentTimeMillis() + ".pdf");

        final   DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Notifications");

        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       // progressBar.setVisibility(View.GONE);
                        //textViewStatus.setText("File Uploaded Successfully");
                        Notification newNotice = new Notification(et_notice_name.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                          //taskSnapshot.getMetadata().getName();
                        myDatabaseRef.push().setValue(newNotice).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                HideDialog();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        tv_upload_button.setClickable(false);
                        tv_upload_button.setText((int) progress + "% Uploading..."+"\n Please Wait");
                    }
                });

    }

               // Dialog Setup

    AlertDialog.Builder dialogbuilder;
    AlertDialog alertDialog;
    TextView tv_upload_button,tvstatus;
    EditText et_notice_name;

    public void SetupDialog()
    {
        try {

            LayoutInflater inflater = getLayoutInflater();
            final View dialogview = inflater.inflate(R.layout.dialog_upload_notice, null);
            tv_upload_button=(TextView)dialogview.findViewById(R.id.tv_button_buttonUploadFile);
            tvstatus=(TextView)dialogview.findViewById(R.id.textViewStatus);
            et_notice_name=(EditText) dialogview.findViewById(R.id.editText_notice_Name);



            //  Distext=(TextView)dialogview.findViewById(R.id.dialogtext);
            //   btnaddshayer=(Button)dialogview.findViewById(R.id.btnaddshayer);
            // btncanceladdshayer=(Button)dialogview.findViewById(R.id.btncanceldialogsh);
            //etshayer=(EditText) dialogview.findViewById(R.id.etshayer);


            tv_upload_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //deleteCategory(position);
                    if(et_notice_name.getText().toString().isEmpty())
                    {
                        et_notice_name.setError("Please Enter Notice Name");
                    }else
                    {
                       // tv_upload_button.setClickable(false);
                       getPDF();

                       // HideDialog();
                    }
                }
            });
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {

                    //  btndelete.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                    tv_upload_button.setBackground(this.getDrawable(R.drawable.ripple_button_design));

                }catch (Exception e)
                {

                }
            }

            dialogbuilder = new AlertDialog.Builder(this);
            dialogbuilder.setView(dialogview);
            alertDialog = dialogbuilder.create();
            showDialog();

        } catch(Exception e){


        }
    }

    public void showDialog(){
        //Distext.setText(Dtext);
        alertDialog.setCanceledOnTouchOutside(false);
        // alertDialog.setCancelable(false);
        alertDialog.show();
    }
    public void HideDialog(){
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.hide();
        alertDialog.dismiss();
    }

    // End Notice Upload Section


    // Notice Delete Section

    public void deleteNotice(String noticeUrl, final String databaseKey)
    {
   //   noticeUrl="gs://admingdcbaramulla.appspot.com/hgg_gdc_boys_baramulla_1561043325507.pdf";

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(noticeUrl);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully

                DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Notifications");
                myDatabaseRef.child(databaseKey)
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       makeToast("Notice Deleted Succesfully");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        makeToast("PDF File Deleted From Storage Only");
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                makeToast("Error Occured While Deleting The Notice" + exception.getMessage());

            }
        });
    }



    //Toast
 void  makeToast(String text)
  {

      Toast.makeText(this,text, Toast.LENGTH_SHORT).show();
  }

  // Snackbar



}
