package com.example.ma.bts;

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

	static ArrayList<String> child=new ArrayList<>();
	static ArrayList<String> id=new ArrayList<>();
	static ArrayList<String> name=new ArrayList<>();
	static ArrayList<String> businfo=new ArrayList<>();
	FirebaseDatabase Firedatabase;
	DatabaseReference myRef;
	String lng,latt,location;


	public ArrayList<String> fillChild(final String key){

		child=new ArrayList<>();
		id=new ArrayList<>();
		name=new ArrayList<>();
		if (!(Data.child.isEmpty()))Data.child.clear();
		if (!(Data.id.isEmpty()))Data.id.clear();
		if (!(Data.name.isEmpty()))Data.name.clear();

		final String [] names={"Child Name : ","    ID     : "};


		Firedatabase = FirebaseDatabase.getInstance();
		myRef = Firedatabase.getReference();
		myRef.addValueEventListener(new ValueEventListener() {
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
						if (snap.getKey().equals("latt"))
							continue;
						else if(snap.getKey().equals("long")) continue;
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