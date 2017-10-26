package com.pawanjia.progressbardemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        Log.d("tag","MainActivity"+"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("tag","MainActivity"+"onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag","MainActivity"+"onResume");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn1:
                startActivity(new Intent(this,CreditProgressActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this,DrawActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this,PayProgressActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(this,LevelProgressActivity.class));
                break;
            default:
        }
    }
}
