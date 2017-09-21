package com.pawanjia.progressbardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LevelProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        LevelProgressBar pb = (LevelProgressBar) findViewById(R.id.pb);
        pb.setMaxValue(18000).setProgress(10000);
    }
}
