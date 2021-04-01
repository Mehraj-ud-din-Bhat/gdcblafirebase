package com.example.lovingkashmir.admin_gdc_baramulla;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    EditText etemail,etpassword;
    TextView btnlogin;
    AlertDialog.Builder dialogbuilder;
    AlertDialog alertDialog;
    TextView Distext,Dtexterror;
    Button Dbtnerror;
    ProgressBar Dprogbar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        mAuth = FirebaseAuth.getInstance();
        etemail=(EditText)findViewById(R.id.etemail);
        etpassword=(EditText)findViewById(R.id.etpassword);
        btnlogin=(TextView) findViewById(R.id.loginiok);
        SetupDialog();
        //   updateUI();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validatelogin();
            }
        });
        Dbtnerror.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HideDialog();
            }
        });


    }




    public void validatelogin()
    {
        if(etemail.getText().toString().isEmpty() || etpassword.getText().toString().isEmpty())
        {

            if (etemail.getText().toString().isEmpty()) {
                etemail.setError("Please Enter Email");
            }else if(etpassword.getText().toString().isEmpty())
            {
                etpassword.setError("Please Enter Password");
            }
        }
        else {

            authUser(etemail.getText().toString(),etpassword.getText().toString());
            etpassword.setText("");
        }
    }

    public void authUser( String email,String password){
        showDialog("Signing in Please wait..");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //  Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {

                            Dtexterror.setText("Sign in Error \n\n\n"+task.getException().getMessage().toString());
                            Distext.setText("");
                            Dprogbar.setVisibility(View.INVISIBLE);
                            Dbtnerror.setVisibility(View.VISIBLE);
                            //  task.getException().getMessage()
                            // If sign in fails, display a message to the user.
                            //   Log.w(TAG, "signInWithEmail:failure", task.getException());
                            // Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            //   Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //updateUI();
    }

    public void updateUI()
    {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            HideDialog();
            Intent intent=new Intent(Login_Activity.this,com.example.lovingkashmir.admin_gdc_baramulla.Timetable_Activity.class);
            startActivity(intent);

        } else {
            return;
            // No user is signed in
        }

    }
    public void SetupDialog()
    {

        try {

            LayoutInflater inflater = getLayoutInflater();
            final View dialogview = inflater.inflate(R.layout.dialoglayout2, null);
            Distext=(TextView)dialogview.findViewById(R.id.dialogtext);
            Dtexterror=(TextView)dialogview.findViewById(R.id.Dtverror);
            Dbtnerror=(Button)dialogview.findViewById(R.id.Dbtnok);
            Dprogbar=(ProgressBar)dialogview.findViewById(R.id.Dimg);
            dialogbuilder = new AlertDialog.Builder(this);
            dialogbuilder.setView(dialogview);
            alertDialog = dialogbuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
        } catch(Exception e){


        }

    }
    public void showDialog(String Dtext){
        Distext.setText(Dtext);

        alertDialog.show();
    }
    public void HideDialog(){

        Dprogbar.setVisibility(View.VISIBLE);
        Dtexterror.setText("");
        Dbtnerror.setVisibility(View.INVISIBLE);
        alertDialog.hide();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();

    }
}
