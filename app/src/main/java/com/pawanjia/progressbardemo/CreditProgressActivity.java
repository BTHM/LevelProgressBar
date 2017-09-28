package com.pawanjia.progressbardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CreditProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_credit);
        //CreditProgressBar4 pb4 = (CreditProgressBar4) findViewById(R.id.pb4);
       // CreditProgressBar2 pb = (CreditProgressBar2) findViewById(R.id.pb2);
       // pb.setRadius(100).setSection(300);
        //CreditProgressBar2 pb2 = (CreditProgressBar2) findViewById(R.id.pb2);
        //pb.setMaxValue(24000).setProgressBar(24000);
//        MyCountDownTimer myCountDownTimer = new MyCountDownTimer(4000, 1000);
//        myCountDownTimer.start();
        CreditProgressBar pb = (CreditProgressBar) findViewById(R.id.pb);
        pb.setMaxValue(18000).setProgress(10000);

    }
}
