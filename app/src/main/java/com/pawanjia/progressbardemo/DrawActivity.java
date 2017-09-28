package com.pawanjia.progressbardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        final ClinicLevelProgressBar pb = (ClinicLevelProgressBar) findViewById(R.id.pb);
        pb.post(new Runnable() {
            @Override
            public void run() {
                pb.setProgressLevel("2").setProgress(60000);
            }
        });


    }
}
