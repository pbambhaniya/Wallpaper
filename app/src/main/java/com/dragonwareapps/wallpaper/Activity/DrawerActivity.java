package com.dragonwareapps.wallpaper.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dragonwareapps.wallpaper.Fragment.AbstractImageFragment;
import com.dragonwareapps.wallpaper.Fragment.AllImageFragment;
import com.dragonwareapps.wallpaper.Fragment.DeshBoardFragment;
import com.dragonwareapps.wallpaper.Fragment.FavouriteImageFragment;
import com.dragonwareapps.wallpaper.Fragment.Love_ImageFragment;
import com.dragonwareapps.wallpaper.Fragment.Three_D_ImageFragment;
import com.dragonwareapps.wallpaper.R;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout lnr_all_image, lnr_3d_image, lnr_abstract, lnr_love_image, lnr_history, lnr_animal_img, lnr_car_img, lnr_city_image, lnr_flower_image, lnr_food_image, lnr_nature_image, lnr_sport_image, lnr_music_image;
LinearLayout lnr_favourite;
    DrawerLayout drawer;
    String android_device_id;
    Context context;
    TextView txt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        lnr_history = (LinearLayout) findViewById(R.id.lnr_history);
        lnr_all_image = (LinearLayout) findViewById(R.id.lnr_all_image);
        lnr_3d_image = (LinearLayout) findViewById(R.id.lnr_3d_image);
        lnr_abstract = (LinearLayout) findViewById(R.id.lnr_abstract);
        lnr_favourite = (LinearLayout) findViewById(R.id.lnr_favourite);
        lnr_love_image = (LinearLayout) findViewById(R.id.lnr_love_image);
        txt_name = (TextView) findViewById(R.id.txt_name);

        android_device_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        openFragment(new DeshBoardFragment(), "DashBoard");
        txt_name.setText("Dashboard");


        lnr_3d_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(new DeshBoardFragment(), "DashBoard");
            }
        });
        lnr_all_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                AllImageFragment ldf = new AllImageFragment();
                Bundle args = new Bundle();
                args.putString("android_id", android_device_id);
                ldf.setArguments(args);

//Inflate the fragment

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, ldf).commit();
            }
        });
        lnr_abstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                AbstractImageFragment ldf = new AbstractImageFragment();
                Bundle args = new Bundle();

                args.putString("id", "2");
                args.putString("android_id", android_device_id);
                ldf.setArguments(args);

//Inflate the fragment

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, ldf).commit();
            }
        });
        lnr_love_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openFragment(new Love_ImageFragment(), "3D");
//                Love_ImageFragment ldf = new Love_ImageFragment ();
//                Bundle args = new Bundle();
//                args.putString("id", "1");
//                ldf.setArguments(args);
                drawer.closeDrawer(GravityCompat.START);
                Love_ImageFragment ldf = new Love_ImageFragment();
                Bundle args = new Bundle();
                args.putString("id", "1");
                args.putString("android_id", android_device_id);
                ldf.setArguments(args);

//Inflate the fragment

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, ldf).commit();
            }
        });
        lnr_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                FavouriteImageFragment ldf = new FavouriteImageFragment();
                Bundle args = new Bundle();
                args.putString("android_id", android_device_id);
                ldf.setArguments(args);

//Inflate the fragment

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, ldf).commit();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void openFragment(Fragment fragment, String title) {

        getSupportActionBar().setTitle(title);

        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } catch (Exception e) {

        }
    }
}
