package com.example.fabian.matchit;

import android.os.Bundle;

import com.dd.CircularProgressButton;

public class ActivityThemaBekijken extends ABaseActivity {

    private CircularProgressButton circularButton1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thema_single);

        createToolbar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }

}
