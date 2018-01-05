package com.example.valleypioneer.androidupdate.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by valleypioneer on 2018/1/5.
 */

public class BaseActivity extends AppCompatActivity {
    private int layoutResourceID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutResourceID);
        findViews();
        initViews();
    }

    protected void findViews(){

    }

    protected void initViews(){

    }

    protected void setLayoutResourceID(int layoutResourceID){
        this.layoutResourceID = layoutResourceID;
    }
}
