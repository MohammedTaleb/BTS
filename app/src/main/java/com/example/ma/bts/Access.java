package com.example.ma.bts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Access extends AppCompatActivity {
	Button login;
	EditText access;
	FirebaseDatabase Firedatabase;
	DatabaseReference myRef;
	SharedPreferences pref ;
	String keyAccess;
	Data d;
	ProgressBar pb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_access);

		pref=this.getSharedPreferences("com.example.ma.bts", Context.MODE_PRIVATE);
		pb= (ProgressBar) findViewById(R.id.pb);
		login= (Button) findViewById(R.id.login);
		access= (EditText) findViewById(R.id.access);



	}
	public void getAccess(View v){
		keyAccess=access.getText().toString();
		pb.setVisibility(View.VISIBLE);
		userChecking ();

	}

	private void userChecking() {
		Firedatabase = FirebaseDatabase.getInstance();

		myRef = Firedatabase.getReference();
		myRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				if ((!(keyAccess.isEmpty())) && dataSnapshot.child("Driver").child(keyAccess).exists()) {
					Log.i("FoundUserDriver", "Yes");
					pref.edit().putString("key", keyAccess).apply();
					pref.edit().putInt("acc", 1).apply();

					Log.i("passwordDoc", "Yes");
					String name = dataSnapshot.child("Driver").child(keyAccess).child("name").getValue().toString();

					pref.edit().putString("name", name).apply();
					pref.edit().putString("type", "Driver").apply();

					Intent intent = new Intent(Access.this, Access.class);
					startActivity(intent);
					finish();
					Toast.makeText(Access.this, "Welcome Mr." + name, Toast.LENGTH_LONG).show();
					pb.setVisibility(View.INVISIBLE);
				} else {

					if ((!(keyAccess.isEmpty())) && dataSnapshot.child("Parent").child(keyAccess).exists()) {
						Log.i("FoundUserParent", "Yes");
						pref.edit().putString("key", keyAccess).apply();
						pref.edit().putInt("acc", 2).apply();

						String name = String.valueOf(dataSnapshot.child("Parent")
								.child(keyAccess).child("name").getValue());

						pref.edit().putString("name", name).apply();

						pref.edit().putString("type", "parent").apply();

						d=new Data();
						//d.fillChild(keyAccess);
//						if (!(Data.child.isEmpty()))Data.child.clear();
//						if (!(Data.id.isEmpty()))Data.id.clear();
//						if (!(Data.name.isEmpty()))Data.name.clear();
						Intent intent1 = new Intent(Access.this, ChildBusId.class);
						startActivity(intent1);
						finish();
						Toast.makeText(Access.this, "Welcome Mr." + name, Toast.LENGTH_LONG).show();
						pb.setVisibility(View.INVISIBLE);
						return;
					} else {
						Log.i("NotFoundUser", "no");
						Toast.makeText(Access.this, "Please contact the school to get/check your Access Key"
								, Toast.LENGTH_LONG).show();
						pb.setVisibility(View.INVISIBLE);
					}

				}


			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}

}