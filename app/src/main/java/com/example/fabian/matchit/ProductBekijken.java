package com.example.fabian.matchit;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.dd.CircularProgressButton;

public class ProductBekijken extends ABaseActivity {

    private CircularProgressButton circularButton1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_bekijken);

        createToolbar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentTabsProduct fragment = new FragmentTabsProduct();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();

        circularButton1 = (CircularProgressButton) findViewById(R.id.btnProgress);
        circularButton1.setProgress(0);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circularButton1.setProgress(100);
            }
        });


    }

}
