package com.example.ma.bts;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Mohammed_Aqrabawi on 10/12/2018.
 */

public class Data {

	static ArrayList<String> child;
	static ArrayList<String> id;
	static ArrayList<String> name;
	static ArrayList<String> businfo=new ArrayList<>();
	FirebaseDatabase Firedatabase;
	DatabaseReference myRef;
	static String lng,latt,location;



	public ArrayList<String> fillChild(final String key){
//		Log.i("child1",child.size()+"");
		child=new ArrayList<>();
		id=new ArrayList<>();
		name=new ArrayList<>();
		if (!(child.equals(null))) child.clear();
		if (!(id.equals(null))) id.clear();
		if (!(name.equals(null))) name.clear();
		if (!(Data.child.isEmpty()))Data.child.removeAll(child);
		if (!(Data.id.isEmpty()))Data.id.removeAll(id);
		if (!(Data.name.isEmpty()))Data.name.removeAll(name);
		Log.i("child2",child.size()+"");
		final String [] names={"","    Bus ID     : "};


		Firedatabase = FirebaseDatabase.getInstance();
		myRef = Firedatabase.getReference();
		myRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot.child("Parent").child(key).child("ID").exists()) {
					if (dataSnapshot.child("Parent").child(key).child("ID")
							.hasChildren()) {
						for (DataSnapshot postSnapshot: dataSnapshot.child("Parent").child(key).child("ID").getChildren()){
							child.add(names[0]+postSnapshot.getKey()+"  |  "+names[1]+ postSnapshot.getValue());
							id.add( postSnapshot.getValue().toString());
						}
					}

				}
				else {child.add("Please contact your school to check your Data");}
				if (dataSnapshot.child("Parent").child(key).child("latt").exists()){
					latt=dataSnapshot.child("Parent").child(key).child("latt").getValue().toString();
				}
				if (dataSnapshot.child("Parent").child(key).child("long").exists()){
					lng=dataSnapshot.child("Parent").child(key).child("long").getValue().toString();
				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
		return child;
	}
	public ArrayList<String> driverInfo(final String bid){


		if (!(Data.businfo.isEmpty()))Data.businfo.clear();

		Firedatabase = FirebaseDatabase.getInstance();

		myRef = Firedatabase.getReference();
		myRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				for(DataSnapshot snap: dataSnapshot.child("Bus").child(bid).getChildren()){
					if (snap.getKey().equals("latt")){
						continue;}
					else if(snap.getKey().equals("long")){
						continue;}
					else
						businfo.add(snap.getKey()+"     :   "+snap.getValue());

				}



			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

		return businfo;
	}

}