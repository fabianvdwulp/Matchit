package com.example.fabian.matchit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;


/**
 * Created by fvdwulp on 10-11-2014.
 */
public abstract class ABaseActivity extends AppCompatActivity{

    private ProgressDialog pdListLoading;
    private DrawerLayout mDrawerLayout, mDrawerShoppingCar;
    private ActionBarDrawerToggle mDrawerToggle, mDrawerToggle2;
    private Toolbar toolbar;
    private ListView mDrawerList;

    // In lollipop is Actionbar gedegradeerd. Daarom de toolbar
    protected void createToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    protected void getShopping(){

        RelativeLayout rlShoppingCard= (RelativeLayout) findViewById(R.id.rlShoppingCar);
        mDrawerShoppingCar = (DrawerLayout) findViewById(R.id.drawer_layout);

        rlShoppingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerShoppingCar.openDrawer(Gravity.END);
            }
        });


    }


    // Voeg een drawer toggle toe. Op de tablet zal dit worden overgeslagen.
    protected void createDrawerToggle() {

        mDrawerList = (ListView) findViewById(R.id.lvNavigationDrawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar ,  R.string.hello_world, R.string.hello_world) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        // Set Listener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // Check status
        mDrawerToggle.syncState();
    }

    protected void createDrawer() {

        mDrawerList = (ListView) findViewById(R.id.lvNavigationDrawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
                startActivity(intent);
            }
        });

    }

    public void pdMessage(String naam, boolean bShowHide){

        if(bShowHide == true){
            pdListLoading = new ProgressDialog(ABaseActivity.this);
            pdListLoading.setMessage(naam);
            pdListLoading.setIndeterminate(false);
            pdListLoading.setCancelable(false);
            pdListLoading.show();
        }
        else{
            pdListLoading.dismiss();
        }
    }


}
