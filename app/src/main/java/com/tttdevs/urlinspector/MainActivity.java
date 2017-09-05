package com.tttdevs.urlinspector;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView txtUrl;
    Button btnCleanTrack, btnCleanAll, btnOpen;
    String mUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUrl = (TextView) findViewById(R.id.txtUrl);

        btnCleanTrack = (Button) findViewById(R.id.btnCleanTrack);
        btnCleanTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrl = mUrl.replaceAll("utm_[\\S]+&?","");
                txtUrl.setText(mUrl);
            }
        });

        btnCleanAll = (Button) findViewById(R.id.btnCleanAll);
        btnCleanAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrl = mUrl.split("\\?")[0].split("#")[0];
                txtUrl.setText(mUrl);
            }
        });

        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(String.valueOf(txtUrl.getText())));
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        Log.d("TEST", intent.toString());

        String action = intent.getAction();

        if (action.equalsIgnoreCase(Intent.ACTION_SEND) && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String s = intent.getStringExtra(Intent.EXTRA_TEXT);
            try {
                URL u = new URL(s);
                String protocol = u.getProtocol();
                String host = u.getHost();
                String path = u.getPath();
                String query = u.getQuery();

                mUrl = protocol + "://" + host + path;
                if (query!=null) mUrl += "?"+query;

                txtUrl.setText(mUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        if (action.equalsIgnoreCase(Intent.ACTION_VIEW)) {
            Uri data = intent.getData();
            mUrl = data.getScheme()+"://"+data.getHost()+"/"+data.getPath()+data.getQuery();
            txtUrl.setText(mUrl);  // .replaceAll("utm_[\\S]+&?","")
        }

    }

}
