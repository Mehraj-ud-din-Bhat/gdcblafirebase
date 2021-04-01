package com.college.baramulla.govtdegreecollegebaramulla;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register_grievance extends AppCompatActivity {

    EditText gName,gMobile,gEmail,gcourse,gGravance;
    TextView tv_submit_grivace;
    ProgressBar mypbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_grievance);
          setupViews();



    }


    void setupViews()
    {
       gName=findViewById(R.id.etentered_g_name);
       gMobile=findViewById(R.id.etentered_g_mobile);
       gEmail=findViewById(R.id.etentered_g_email);
       gcourse=findViewById(R.id.etentered_g_course);
       gGravance=findViewById(R.id.etentered_g_grivance);
       mypbar=findViewById(R.id.pgbar_in_grivace);
       mypbar.setVisibility(View.INVISIBLE);
       tv_submit_grivace=findViewById(R.id.tv_btn_submit_grivance);


      ImageView btnbackimg= findViewById(R.id.imgback_in_grivance);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {


                btnbackimg.setBackground(getResources().getDrawable(R.drawable.circle_bg_ripple));
                tv_submit_grivace.setBackground(getResources().getDrawable(R.drawable.design_button_design_ripple2));

            }catch (Exception e)
            {

            }
        }
        btnbackimg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });



        tv_submit_grivace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validateForm();
            }
        });

    }





    public void validateForm()
    {
      if(gName.getText().toString().isEmpty() ||
              gMobile.getText().toString().isEmpty() ||
              gEmail.getText().toString().isEmpty() ||
              gcourse.getText().toString().isEmpty() ||
              gGravance.getText().toString().isEmpty())
      {
        if(gName.getText().toString().isEmpty())
        {
            gName.setError("Please Enter Your Name");
        }

          if(gMobile.getText().toString().isEmpty())
          {
            gMobile.setError("Please Enter Your Mobile Number");
          }
          if(gEmail.getText().toString().isEmpty())
          {
           gEmail.setError("Please Enter Your Email");
          }
          if(gcourse.getText().toString().isEmpty())
          {
            gcourse.setError("Please Enter Your Course Details");
          }
          if(gGravance.getText().toString().isEmpty())
          {
          gGravance.setError("Please Enter Your Grievance");
          }




      }else {

            //makeToast("Deatils Entered Correctly");


   if(isEmailValid(gEmail.getText().toString()))
   {
     //  uplodaGrivanceInDatabase();
       isFirebaseOnline();
   }
   else {
       gEmail.setError("Enter Valid Email");
   }

      }


    }



    void uplodaGrivanceInDatabase()
    {
        mypbar.setVisibility(View.VISIBLE);
        Grievance newGrievance=new Grievance(gName.getText().toString(),
                gEmail.getText().toString(),
                gMobile.getText().toString(),
                gcourse.getText().toString(),
                gGravance.getText().toString());
        DatabaseReference myDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Grievances");
        myDatabaseRef.push().setValue(newGrievance).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                mypbar.setVisibility(View.INVISIBLE);
                makeToast("Grievance Sent Succesfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                mypbar.setVisibility(View.INVISIBLE);
                makeToast("Failed to Send !");
            }
        });


    }


    void makeToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();




    }


    public boolean isEmailValid(String strEmail)
    {
      Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
      Matcher matcher = pattern.matcher(strEmail);

        if (strEmail.isEmpty()) {
            return false;
        } else return matcher.matches();
    }


    public void isFirebaseOnline()
    {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                 //  getDataFromDatabase();
                    uplodaGrivanceInDatabase();
                } else {
                   makeToast("Connection Error");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }






}
