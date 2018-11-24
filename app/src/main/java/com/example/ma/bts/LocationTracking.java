package com.example.ma.bts;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by macbookpro on 10/13/18.
 */

public class LocationTracking implements LocationListener {
    Context context;
    String      BusNum;
    FirebaseDatabase  fdatabase = FirebaseDatabase.getInstance();
    DatabaseReference fDatabaseRef = fdatabase.getReference();


    public  LocationTracking (Context context,String BusNum){
        this.context = context;
        this.BusNum  = BusNum;


    }
    @Override
    public void onLocationChanged(Location location) {
        // execute whenever bus driver location change and will get the new location (lon & lat)
        Location childLocation = new Location("");
        childLocation.setLatitude(34.999);
        childLocation.setLongitude(32.009);
        Log.i("distanceTo",""+location.distanceTo(childLocation));
        Toast.makeText(context,location.getAccuracy()+"changed long="+location.getLongitude()+" lat= "+location.getLatitude(),Toast.LENGTH_SHORT).show();
        driverLocationUpdater1(location.getLongitude(),location.getLatitude());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    // when location is either enabled or disabled
        Toast.makeText(context,"GPS was Changed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(context,"GPS is Enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context,"GPS is not Enabled",Toast.LENGTH_LONG).show();
    }

    void driverLocationUpdater1(double lan,double lat){
        Log.i("LocationTracking",BusNum);
        fDatabaseRef.child("Bus").child(BusNum).child("latt").setValue(lat);
        fDatabaseRef.child("Bus").child(BusNum).child("long").setValue(lan);


    }
}
