package com.example.fabian.matchit;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dd.CircularProgressButton;

public class ThemaBekijken extends ABaseActivity {

    private CircularProgressButton circularButton1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thema_bekijken);

        createToolbar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }

}
