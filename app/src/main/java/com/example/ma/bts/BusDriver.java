package com.example.ma.bts;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by BTS on 10/13/18.
 */

public class BusDriver extends AppCompatActivity {
    TextView BusId;
    String BusNum,DriverKey;
    SharedPreferences pref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_driver);
        pref=this.getSharedPreferences("com.example.ma.bts", Context.MODE_PRIVATE);
      //  Intent intent = getIntent();
        //Bundle bundle = intent.getExtras();
       // bundle.get("busNumber");
        DriverKey = pref.getString("driverkey","0");
        BusNum = pref.getString("busNumber","0");
        Log.i("drbn",DriverKey+BusNum);


        locationTracking = new LocationTracking(this,BusNum);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        // BusId = (TextView) findViewById(R.id.BusNumber);
       // BusId.setText("Bus number " + BusNum);
        CheckUserPermsions();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);

        }


        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(BusDriver.this,drawerLayout,toolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(BusDriver.this,"Opend",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toast.makeText(BusDriver.this,"Closed",Toast.LENGTH_SHORT).show();

            }
        };
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.driver_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawers();
                // Create a new fragment and specify the fragment to show based on nav item clicked
                Fragment fragment = null;
//                Class fragmentClass;
                switch(item.getItemId()) {
                    case R.id.home:
                        fragment = new BusDriverHome();
                        Toast.makeText(BusDriver.this,"Home",Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.profile:
                            fragment = new BusDriverProfile(DriverKey);
                        break;
                    case R.id.bus_info:
                            fragment = new BusInfoActivity(BusNum);
                        break;
                    case R.id.about:
                            fragment = new About();
                        break;
                    case R.id.logout:
                        logout();
                        break;
                }

                if (fragment != null) {
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.flContent,fragment);
                    ft.commit();


                }


                return false;
            }
        });
        BusDriverHome busDriverHome = new BusDriverHome();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContent,busDriverHome);
        ft.commit();



    }
	FirebaseDatabase Firedatabase;
	DatabaseReference myRef;
    private void logout() {
	    Firedatabase = FirebaseDatabase.getInstance();
	    myRef = Firedatabase.getReference();
	    myRef.child("Bus").child(BusNum).child("Key").setValue("0");
	    pref.edit().putInt("Driverkey",0).apply();
        pref.edit().putString("busNumber","0").apply();

        String time= String.valueOf(Calendar.getInstance().getTime());
        myRef.child("busIdKey").child(BusNum).child("endTime").setValue(time);
        myRef.child("busIdKey").child(BusNum).child("Key").setValue("0");
        Intent intent = new Intent(BusDriver.this,Access.class);
        onPause();
        startActivity(intent);
        finish();
    }
    LocationTracking locationTracking;
    LocationManager locationManager;
    void getLocation() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        Toast.makeText(this,"long="+location.getLongitude()+" lat= "+location.getLatitude(),Toast.LENGTH_LONG).show();

     //   locationTracking = new LocationTracking(this,BusNum);
      //   locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,locationTracking);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause","Hola");


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.removeUpdates(locationTracking);
    }

    //access to permsions
    void CheckUserPermsions(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        getLocation();

    }
    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    // Permission Denied
                    Toast.makeText( this,"Location Permission Denied" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
       finish();

    }


}
