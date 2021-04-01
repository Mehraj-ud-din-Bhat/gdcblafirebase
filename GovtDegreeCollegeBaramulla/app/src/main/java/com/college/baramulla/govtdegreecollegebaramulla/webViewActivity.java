package com.college.baramulla.govtdegreecollegebaramulla;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

public class webViewActivity extends AppCompatActivity {

    WebView webView;

    private ParcelFileDescriptor parcelFileDescriptor;

    ImageView btnbackimg;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        connect_XML();
        WebSettings setting = this.webView.getSettings();
        setting.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= 11) {
            setting.setDisplayZoomControls(false);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("file:///android_asset/");

        stringBuilder.append("college_about.html");

        webView.loadUrl(stringBuilder.toString());
        this.webView.setWebViewClient(new WebViewClient());

    }
    public void connect_XML() {

        webView = findViewById(R.id.Imgdisplay);
        btnbackimg= findViewById(R.id.imgback_in_webview);

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


}
