package com.example.ma.bts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by Mohammed_Aqrabawi on 10/18/2018.
 */

public class TrackDriverInfo extends AppCompatActivity {

	FirebaseDatabase Firedatabase;
	DatabaseReference myRef;
	SharedPreferences pref ;
	TextView location;
	String lng,latt;
	String id;
	ListView lv;
	Data d;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackdriverinfo);
		d=new Data();
		pref=this.getSharedPreferences("com.example.ma.bts", Context.MODE_PRIVATE);
		id = String.valueOf((pref.getInt("index", 0)));
		location= (TextView) findViewById(R.id.Vi);

		lv= (ListView) findViewById(R.id.driver);
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,d.driverInfo(id)));

		busLocation();
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
							location.setText("Location : "+latt+","+lng);
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
}
