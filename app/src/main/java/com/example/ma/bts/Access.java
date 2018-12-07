package com.example.ma.bts;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Access extends AppCompatActivity {

	EditText access;
	FirebaseDatabase Firedatabase;
	DatabaseReference myRef;
	SharedPreferences pref;
	String keyAccess;
	ProgressBar pb;
	RelativeLayout rellay1, rellay2;
	Dialog myDialog;


	Handler handler 	= new Handler();
	Runnable runnable 	= new Runnable() {
		@Override
		public void run() {
			rellay1.setVisibility(View.VISIBLE);
			rellay2.setVisibility(View.VISIBLE);

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.access);

		pb= (ProgressBar) findViewById(R.id.pb);

		rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
		rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

		handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash
		myDialog = new Dialog(this);

		myDialog = new Dialog(this);
		pref = this.getSharedPreferences("com.example.ma.bts", Context.MODE_PRIVATE);

		//login= (Button) findViewById(R.id.login);
		access = (EditText) findViewById(R.id.access);
		if(pref.getInt("Parentkey",0)==1){
			Intent intent1 = new Intent(Access.this, Parent.class);
			startActivity(intent1);
			finish();
		}
		Log.i("DriverKey",pref.getInt("Driverkey",0)+"");
		if(pref.getInt("Driverkey",0) == 1){
            Intent intent = new Intent(Access.this, BusDriver.class);
            startActivity(intent);
        }


	}

	public void getAccess(View v) {
		keyAccess = access.getText().toString();
		pb.setVisibility(View.VISIBLE);
		userChecking();


	}

	private void userChecking() {
		Firedatabase = FirebaseDatabase.getInstance();

		myRef = Firedatabase.getReference();
		myRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				if ((!(keyAccess.isEmpty())) && dataSnapshot.child("Driver").child(keyAccess).exists()) {
					pref.edit().putString("key", keyAccess).apply();
					pref.edit().putInt("acc", 1).apply();
					String name = dataSnapshot.child("Driver").child(keyAccess).child("name").getValue().toString();
					String busId = dataSnapshot.child("Driver").child(keyAccess).child("busId").getValue().toString();
					pref.edit().putString("name", name).apply();
					pref.edit().putString("type", "Driver").apply();
					Intent intent = new Intent(Access.this, BusDriver.class);
					pref.edit().putInt("Driverkey",1).apply();
					pref.edit().putString("busNumber",busId).apply();

					String time= String.valueOf(Calendar.getInstance().getTime());
					myRef.child("busIdKey").child(busId).child("startTime").setValue(time);
					pref.edit().putString("driverkey",keyAccess).apply();
					myRef.child("Bus").child(busId).child("Key").setValue("1");
					myRef.child("busIdKey").child(busId).child("Key").setValue("1");

					startActivity(intent);
					Log.i("back", "back");
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

						Data data = new Data();
						//data.fillChild(keyAccess);

						pref.edit().putInt("Parentkey",1).apply();

						Intent intent1 = new Intent(Access.this, Parent.class);
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

	public void ShowPopup(View view) {

		TextView txtclose;
		Button btnFollow;
		myDialog.setContentView(R.layout.popup);
		txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
		txtclose.setText("X");
		txtclose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myDialog.dismiss();
			}
		});
		myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		myDialog.show();

	}

	public void call(View view) {
		Toast.makeText(this,"call",Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "123456789"));
		startActivity(intent);
	}

	public void send_email(View view) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"bus@tracking.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT   , "body of email");
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}