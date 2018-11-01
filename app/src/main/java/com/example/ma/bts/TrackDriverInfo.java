package com.example.ma.bts;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



/**
 * Created by Mohammed_Aqrabawi on 10/18/2018.
 */

public class TrackDriverInfo extends AppCompatActivity {

	FirebaseDatabase Firedatabase;
	DatabaseReference myRef;
	SharedPreferences pref ;
	TextView location;
	String lng,latt,locate;
	String id;
	ListView lv;
	Data d;
	int min10=0,min5=0;
//ServiceNotification sv=new ServiceNotification();
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackdriverinfo);
		pref=this.getSharedPreferences("com.example.ma.bts", Context.MODE_PRIVATE);

		id = String.valueOf((pref.getInt("index", 0)));

//		Intent svb=new Intent(this, ServiceNotification.class);
//		svb.putExtra("id",id);
//		startService(svb);
//		sv.onStart(svb,0);
		//startService(new Intent(this, ServiceNotification.class));

		d=new Data();
		location= (TextView) findViewById(R.id.Vi);

		lv= (ListView) findViewById(R.id.driver);
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,d.driverInfo(id)));



		busLocation();
		//sv.busLocation(id);
		//NewMessageNotification newMessageNotification = new NewMessageNotification();
		//newMessageNotification.notify(this,"Hola",1);
	}

	public void busLocation(){
		Firedatabase = FirebaseDatabase.getInstance();

		myRef = Firedatabase.getReference();
		myRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				try {
					for(DataSnapshot snap: dataSnapshot.child("Bus").child(id+"").getChildren()){
						if (snap.getKey().equals("latt")){
							latt=snap.getValue().toString();
						}
						else if (snap.getKey().equals("long")){
							lng=snap.getValue().toString();
							locate=latt+","+lng;
							location.setText("Location : "+latt+","+lng);
							busLocation(id);
						}

					}

				}catch (Exception e){}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});



	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent=new Intent(this,ChildBusId.class);
		startActivity(intent);
		finish();
	}
	//----------------------------------------------------------------

	public void busLocation(final String index){
		Firedatabase = FirebaseDatabase.getInstance();
		final String[] latt = new String[1];
		final String[] lng = new String[1];
		myRef = Firedatabase.getReference();
		myRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				try {
					if (dataSnapshot.child("Bus").child(index).child("latt").exists()){
						latt[0] =dataSnapshot.child("Bus").child(index).child("latt").getValue().toString();

					}
					if (dataSnapshot.child("Bus").child(index).child("long").exists()){
						lng[0] =dataSnapshot.child("Bus").child(index).child("long").getValue().toString();

					}

					Log.i("beforecall",latt[0]+","+index);
					distance(latt[0],lng[0]);

				}catch (Exception e){}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});



	}

	public void  distance(String latt,String lng){
		Location location1 = new Location("");
		location1.setLatitude(Double.parseDouble(latt));
		location1.setLongitude(Double.parseDouble(lng));

		Location location2 = new Location("");
		location2.setLatitude(Double.parseDouble(Data.latt));
		location2.setLongitude(Double.parseDouble(Data.lng));

		float distanceInMeters = location1.distanceTo(location2);
		Log.i("dim",distanceInMeters +" ");

		int speedIs400MetersPerMinute = 200;
		float estimatedDriveTimeInMinutes = distanceInMeters / speedIs400MetersPerMinute;
		Log.i("edtim",estimatedDriveTimeInMinutes +" ");

		if (estimatedDriveTimeInMinutes<10.0&&estimatedDriveTimeInMinutes>5.0){
			if (min10==0) {
				addNotification(" 10Min ",locate);
				min10 = 1;
				Log.i("onest", estimatedDriveTimeInMinutes + " ");
			}
		}
		if (estimatedDriveTimeInMinutes<5.0) {
			if(min5==0){
			addNotification("arrive! less than 5 minutes",locate);
			min5=1;
			Log.i("oneest", estimatedDriveTimeInMinutes + " ");
			}
		}
	}
	NewMessageNotification newMessageNotification = new NewMessageNotification();

	public void addNotification(String destance,String loc) {

		newMessageNotification.notify(this,destance,loc,1);
		Log.i("atAddNot","destance");


	}
}
