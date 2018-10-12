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
	ArrayList<String> child;
	FirebaseDatabase Firedatabase;
	DatabaseReference myRef;


	public void fillChild(final String key){
		child=new ArrayList<>();
		final String [] names={"Child Name : ","ID     : "};


		Firedatabase = FirebaseDatabase.getInstance();
		myRef = Firedatabase.getReference();
		myRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot.child("Parent").child(key).child("ID").exists()) {
					if (dataSnapshot.child("Parent").child(key).child("ID")
							.hasChildren()) {
						for (DataSnapshot postSnapshot: dataSnapshot.child("Parent").child(key).child("ID").getChildren()){
							child.add(names[0]+(String) postSnapshot.getKey()+names[1]+(String) postSnapshot.getValue());
						}
					}

				}
				else {child.add("Please contact your school to check your Data");}


			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}
}