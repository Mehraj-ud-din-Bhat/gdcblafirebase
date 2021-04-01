package com.example.lovingkashmir.admin_gdc_baramulla;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.net.ContentHandler;
import java.util.ArrayList;

import static com.example.lovingkashmir.admin_gdc_baramulla.R.drawable.ripple_button_design;

/**
 * Created by MEHRAJ UD DIN BHAT on 3/22/2019.
 */

public class EditDialog {
     String DialogName,DialogSubName,btnName;
    AlertDialog.Builder dialogbuilder;
    AlertDialog alertDialog;


    LayoutInflater inflater;
    Context mycontext;
    TextView tvactionname,btnok;
    EditText etenteredname;
    DatabaseReference myRef;
    ArrayList<String> compareList;
    View myview;//For Displaying SnackBar;
    String textforsnackbar="Stream Added";

    public EditDialog(FragmentActivity activity, String actionName, String btnName, LayoutInflater inflater,DatabaseReference myREF,ArrayList<String> comparelist,View myview) {
        mycontext=activity;
        this.inflater=inflater;
        this.myRef=myREF;
        SetupDialog(actionName,btnName);
        compareList=new ArrayList<String>();
        compareList=comparelist;
        this.myview=myview;
    }

    public EditDialog(FragmentActivity activity, String streamName,String actionName, String btnName, LayoutInflater inflater,DatabaseReference myREF,ArrayList<String> comparelist,View myview) {
        mycontext=activity;
        this.inflater=inflater;
        this.myRef=myREF;
        SetupDialog(actionName,btnName);
        etenteredname.setText(streamName);
        compareList=new ArrayList<String>();
        compareList=comparelist;
        this.myview=myview;
    }
    public void EditDialog()
    {}
public void setSnackBarText(String text)
{
    this.textforsnackbar=text;
}


        public void SetupDialog(String actionName,String btnName)
        {
        try {

           // LayoutInflater inflater = getLayoutInflater();
            final View dialogview = inflater.inflate(R.layout.dialog_edit_add_stream, null);
            tvactionname=(TextView)dialogview.findViewById(R.id.tvDialogactionName);
            etenteredname=(EditText) dialogview.findViewById(R.id.etentered_add_edit_name);
            btnok=(TextView)dialogview.findViewById(R.id.tvActionButton);

                tvactionname.setText(actionName);
                btnok.setText(btnName);
            //  Distext=(TextView)dialogview.findViewById(R.id.dialogtext);
         //   btnaddshayer=(Button)dialogview.findViewById(R.id.btnaddshayer);
           // btncanceladdshayer=(Button)dialogview.findViewById(R.id.btncanceldialogsh);
            //etshayer=(EditText) dialogview.findViewById(R.id.etshayer);


            btnok.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //deleteCategory(position);
                    if(etenteredname.getText().toString().isEmpty())
                    {
                        etenteredname.setError("Please Enter Stream Name");
                    }else if(compareList.contains(etenteredname.getText().toString().trim().toLowerCase()))
                    {
                        etenteredname.setError("Stream Name Already Exists");
                    }else
                    {
                        updateInDatabase(etenteredname.getText().toString());


                        HideDialog();
                    }
                }
            });
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {

                    //  btndelete.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                    btnok.setBackground(mycontext.getDrawable(R.drawable.ripple_button_design));

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

    public void updateInDatabase(String StreamName)
    {
      final  String Excep="Stream Added Succesfully";
         myRef.setValue(StreamName.toUpperCase()).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            Snackbar mysnack=new Snackbar(myview,textforsnackbar);

        }

    });

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

}
