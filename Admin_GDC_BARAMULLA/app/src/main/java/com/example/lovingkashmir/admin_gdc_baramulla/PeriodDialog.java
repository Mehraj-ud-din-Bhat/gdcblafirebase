package com.example.lovingkashmir.admin_gdc_baramulla;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by MEHRAJ UD DIN BHAT on 3/22/2019.
 */

public class PeriodDialog {

    AlertDialog.Builder dialogbuilder;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    Context mycontext;
    TextView tvactionname,btnok;
    EditText etstarttime,etendtime,etClassDis;
    ArrayList<String> compareListStartTime;
    ArrayList<String> compareListEndTime;
    ArrayList<String> compareListDiscription;

    View myview;//For Displaying SnackBar;
    String textforsnackbar="Class Added";
    ImageView btnImagcancel;
      DatabaseReference myref;

     boolean EdiitPeroidMode=false;
     String periodKey="null";

    public PeriodDialog(Context mcontext,String dialogName, String actionName, LayoutInflater inflater) {
        mycontext=mcontext;
        this.inflater=inflater;
        SetupDialog(dialogName,actionName);
    }

   public void  setUpDataEnd(ArrayList<String> cStartTime,ArrayList<String> cEndTime,ArrayList<String> CompareLisdis,DatabaseReference myRef)
   {
         compareListEndTime=cEndTime;
         compareListStartTime=cStartTime;
         this.myref=myRef;
         this.compareListDiscription=CompareLisdis;

   }
public void setSnackBarText(String text,View myview)
{
    this.textforsnackbar=text;
    this.myview=myview;//View For SnackBar
}

    public void SetupDialog(String actionName,String btnName)
    {

        try {

           // LayoutInflater inflater = getLayoutInflater();
            final View dialogview = inflater.inflate(R.layout.dialog_edit_add_period, null);
            tvactionname=(TextView)dialogview.findViewById(R.id.tvDialog_pediod_title);
            etstarttime=(EditText) dialogview.findViewById(R.id.et_period_start_time);
            etendtime=(EditText) dialogview.findViewById(R.id.et_period_end_time);
            etClassDis=(EditText) dialogview.findViewById(R.id.etentered_add_edit_period_dialog);
            btnok=(TextView)dialogview.findViewById(R.id.tvActionButton_period_dialog);
            btnImagcancel=(ImageView)dialogview.findViewById(R.id.imgback_dialog_period);

            tvactionname.setText(actionName);
                btnok.setText(btnName);

            btnImagcancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //deleteCategory(position);
                    HideDialog();
                }
            });

            btnok.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //deleteCategory(position);
                  if(etstarttime.getText().toString().isEmpty()||etstarttime.getText().toString().isEmpty()||etClassDis.getText().toString().isEmpty())
                    {
                        if(etstarttime.getText().toString().isEmpty())
                        {
                            etstarttime.setError("Please Enter Class Start Time");
                        } else if(etendtime.getText().toString().isEmpty())
                    {
                        etendtime.setError("Please Enter Class End Time");
                    } else if(etClassDis.getText().toString().isEmpty())
                        {
                            etClassDis.setError("Please Enter Class Discription");
                        }

                    }else
                    {
                        if(etstarttime.getText().toString().equalsIgnoreCase(etendtime.getText().toString()))
                        {
                            etstarttime.setError("Invalid Class Time");
                        }
                        else { performValidation();}

                    }
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {

                    //  btndelete.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                    btnok.setBackground(mycontext.getDrawable(R.drawable.ripple_button_design));
                    btnImagcancel.setBackground(mycontext.getDrawable(R.drawable.circle_bg_ripple));

                }catch (Exception e)
                {

                }
            }

            dialogbuilder = new AlertDialog.Builder(mycontext);
            dialogbuilder.setView(dialogview);
            alertDialog = dialogbuilder.create();
            showDialog();

        } catch(Exception e){


        }
    }

    public void updateInDatabase(Period newClass)
    {
      final  String Excep="Stream Added Succesfully";
       // Snackbar mysnack = new Snackbar(myview, periodKey);
      if(EdiitPeroidMode)
      {
         // Snackbar mysnack = new Snackbar(myview, periodKey);
      //    myref.child(periodKey);
                 myref.setValue(newClass).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  HideDialog();
                  Snackbar mysnack = new Snackbar(myview,"Period Updated !");

              }

          });

      }else {
          myref.push().setValue(newClass).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  HideDialog();
                  Snackbar mysnack = new Snackbar(myview, textforsnackbar);

              }

          });
      }

    }


    public void showDialog(){
        //Distext.setText(Dtext);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
    public void HideDialog(){
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.hide();
        alertDialog.dismiss();
    }
public void performValidation()
{
    //if(compareListStartTime.contains(et))
   String startTime=etstarttime.getText().toString();
   String endTime=etendtime.getText().toString();
   String dis=etClassDis.getText().toString();
    Period myclass = new Period(etstarttime.getText().toString().toUpperCase(), etendtime.getText().toString().toUpperCase(), etClassDis.getText().toString().toUpperCase());



 /*  if(compareListStartTime.contains(startTime.toLowerCase().trim()) || compareListEndTime.contains(endTime.toLowerCase().trim()) || compareListDiscription.contains(dis.toLowerCase().trim()))
   {
       if(compareListStartTime.contains(startTime.toLowerCase().trim()) && compareListEndTime.contains(endTime.toLowerCase().trim()) && compareListDiscription.contains(dis.toLowerCase().trim())) {
           etClassDis.setError("Class Already Exists");
       } else if(compareListStartTime.contains(startTime.toLowerCase().trim()) && compareListEndTime.contains(endTime.toLowerCase().trim())) {
           etClassDis.setError("Entered Time Slot Is Not Free");
       } else {
          updateInDatabase(myclass);//Updates in DB When Time Does Not Conflict
       }
   }
   else {
      // Period myclass = new Period(etstarttime.getText().toString().toUpperCase(), etendtime.getText().toString().toUpperCase(), etClassDis.getText().toString().toUpperCase());
       updateInDatabase(myclass);
   } COMPARING Will not be Performed*/

    updateInDatabase(myclass);

}


//Edit Period Section


    public void editPeriod(String PeriodKey,Period myPeriod)
    {
        periodKey=PeriodKey;
       EdiitPeroidMode=true;
         etstarttime.setText(myPeriod.getStartTime());
                etendtime.setText(myPeriod.getEndTime());
                        etClassDis.setText(myPeriod.getPeriodDetail());


    }




}
