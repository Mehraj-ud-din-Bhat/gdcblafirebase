package com.example.lovingkashmir.admin_gdc_baramulla;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class show_journal_post_activity extends AppCompatActivity {

    TextView tv_post_title,
            tvpost_body,tv_author_details;

    ImageView btnbackimg,author_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_journal_post_activity);
        setUpviewsAndData();
    }


    public void setUpviewsAndData() {

        try {
            btnbackimg = findViewById(R.id.imgback_View_post_activity_toolbar);
            //  imgNotice= findViewById(R.id.imageView_notice);
            //tvNotice_title = findViewById(R.id.tvnoticetitle_full);
            //   tvNoticebody = (TextView) findViewById(R.id.tvnoticebodyy_full);


            tv_post_title=findViewById(R.id.tv_post_title_full);
            tvpost_body=findViewById(R.id.Tv_post_details);
            tv_author_details=findViewById(R.id.tv_post_author_details);
            author_img=findViewById(R.id.img_post_author);


            //bacbutton code

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {

                    //  btndelete.setBackground(getResources().getDrawable(R.drawable.simple_bg_ripple));
                    btnbackimg.setBackground(getResources().getDrawable(R.drawable.circle_bg_ripple));

                } catch (Exception e) {

                }
            }
            btnbackimg.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onBackPressed();
                }
            });


            //Setting Data Fort the notice

            String posttitle=getIntent().getStringExtra("postTitle");
            String postbody=getIntent().getStringExtra("postDetails");
            String   authordetails=getIntent().getStringExtra("authorDetails");
            String authorPhotoLink=getIntent().getStringExtra("authorPhotoLink");
            // tvNoticebody.setText(body);
            tv_post_title.setText(posttitle);
            tvpost_body.setText(postbody);
            tv_author_details.setText(authordetails);
            Picasso.get().load(authorPhotoLink).placeholder(R.drawable.ic_person_black_24dp).error(R.drawable.ic_person_black_24dp).into(author_img);



        } catch (Exception e) {
        }
    }
}
