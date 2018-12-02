package com.example.ma.bts;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.ma.bts.R.id.image;


/**
 * Created by BTS on 10/18/2018.
 */

public class TrackDriverInfo extends AppCompatActivity {

	FirebaseDatabase Firedatabase;
	DatabaseReference myRef,busRef,driverRef;
	SharedPreferences pref ;
	TextView location,bNum,Bcapacity,bModel,BMFG,BDriver,bIdentity;
	String lng,latt,locate;
	String id,DriverId,sTime,eTime;
	ListView lv;
	Data d;
    ImageView bImage,driverPic;
	int min10=0,min5=0,arr=0,key=0,akey=0,bkey=0;
    Dialog myDialog;
    TextView BusId,dp_nationality,dp_age,dp_phone,dp_email,dp_location,dp_name;


    //ServiceNotification sv=new ServiceNotification();
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.trackdriverinfo);
		pref=this.getSharedPreferences("com.example.ma.bts", Context.MODE_PRIVATE);

		id = String.valueOf((pref.getInt("index", 0)));
        myDialog= new Dialog(this);
//		Intent svb=new Intent(this, ServiceNotification.class);
//		svb.putExtra("id",id);
//		startService(svb);
//		sv.onStart(svb,0);
		//startService(new Intent(this, ServiceNotification.class));



		d=new Data();
		//location= (TextView) findViewById(R.id.Vi);
        bNum= (TextView) findViewById(R.id.busNumberer);
        Bcapacity= (TextView) findViewById(R.id.buscapacity);
        bModel= (TextView) findViewById(R.id.busModel);
        BMFG= (TextView) findViewById(R.id.mfg);
        bIdentity = (TextView) findViewById(R.id.busIdentity);
        BDriver = (TextView) findViewById(R.id.DriverInfo);

        bIdentity.setText("Bus No."+id);
        bImage = (ImageView) findViewById(R.id.bus_pic);

        Firedatabase = FirebaseDatabase.getInstance();
        Log.i("busId",id);
        busRef = Firedatabase.getReference().child("Bus").child(id+"");
        busRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BusInfo busInfo = dataSnapshot.getValue(BusInfo.class);
                bNum.setText(busInfo.busNumber);
                Log.i("busNumber",busInfo.busNumber+" ");
                Bcapacity.setText(busInfo.capacity);
                bModel.setText(busInfo.busModel);
                BMFG.setText(busInfo.manufacturerCompany);
                Picasso.with(getApplicationContext())
                        .load(busInfo.image)
                        .into(bImage);
                DriverId = busInfo.busDriverId;
                getDriverInfo(DriverId,"Id");
	            busLocation();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



		//sv.busLocation(id);
		//NewMessageNotification newMessageNotification = new NewMessageNotification();
		//newMessageNotification.notify(this,"Hola",1);
	}

    private void getDriverInfo(String driverId , final String key) {
        Log.i("driverId",driverId+"");
        driverRef = Firedatabase.getReference().child("Driver").child(DriverId);
        driverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DriverInfo driverInfo = dataSnapshot.getValue(DriverInfo.class);

                if(key == "Id"){
                BDriver.setText("Driver: "+driverInfo.name);}
                else{
                    //BusId.setText(driverInfo.busId);
                    dp_nationality.setText(driverInfo.nationality);
                    dp_age.setText(driverInfo.age);
                    dp_phone.setText(driverInfo.number);
                    dp_email.setText(driverInfo.email);
                    dp_location.setText(driverInfo.address);
                    dp_name.setText(driverInfo.name);
                    Log.i("Imaged","hola"+driverInfo.image);
                    Picasso.with(getApplicationContext())
                            .load(driverInfo.image)
                            .into(driverPic);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void busLocation(){
	    Log.i("dim","test ");

		myRef = Firedatabase.getReference();
		myRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				try {
					for(DataSnapshot snap: dataSnapshot.child("Bus").child(id+"").getChildren()){
						Log.i("dim",snap.getKey());
						if (snap.getKey().equals("Key"))
							key=Integer.parseInt(snap.getValue().toString());
						if (key==1){
							sTime= (String) dataSnapshot.child("busIdKey").child(id+"").child("startTime").getValue();
						}

						if (key==0){
							eTime= (String) dataSnapshot.child("busIdKey").child(id+"").child("endTime").getValue();
						}

						if (snap.getKey().equals("latt")){
							latt=snap.getValue().toString();
							Log.i("dim","test1 ");

						}
						else if (snap.getKey().equals("long")){
							lng=snap.getValue().toString();
							locate=latt+","+lng;
							//location.setText("Location : "+latt+","+lng);
							Log.i("dim","test2 ");
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

//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//		Intent intent=new Intent(this,ChildBusId.class);
//		startActivity(intent);
//		finish();
//	}
	//----------------------------------------------------------------

	public void busLocation(final String index){
		Log.i("dim","test3 ");

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
		if (akey==0) {
			if (key == 1) {
				addNotification(" Start round "+sTime, locate);
				akey=1;
				Log.i("onest", estimatedDriveTimeInMinutes + " ");
			}
		}
		if (bkey==0) {
			if (key == 0) {
				addNotification(" arriving school "+eTime, locate);
				bkey=1;
				Log.i("onest", estimatedDriveTimeInMinutes + " ");
			}
		}
		if (estimatedDriveTimeInMinutes<10.0&&estimatedDriveTimeInMinutes>5.0){
			if (min10==0) {
				addNotification(" 10Min ",locate);
				min10 = 1;
				Log.i("onest", estimatedDriveTimeInMinutes + " ");
			}
		}
		if (estimatedDriveTimeInMinutes < 5.0 && estimatedDriveTimeInMinutes > 1.0) {
			if (min5 == 0) {
				addNotification("arrive! less than 5 minutes", locate);
				min5 = 1;
				Log.i("oneest", estimatedDriveTimeInMinutes + " ");
			}
		}
		if (estimatedDriveTimeInMinutes < 1.0) {
			if (arr == 0) {
				addNotification("arrive! ", locate);
				arr = 1;
				Log.i("oneest", estimatedDriveTimeInMinutes + " ");
			}
		}


	}
	NewMessageNotification newMessageNotification = new NewMessageNotification();

	public void addNotification(String destance,String loc) {

		newMessageNotification.notify(this,destance,loc,1);
		Log.i("atAddNot","destance");


	}

    public void viewDriverDetails(View view) {
        myDialog.setContentView(R.layout.driver_profile_popup);
        driverPic = (ImageView) myDialog.findViewById(R.id.driverPic);

        TextView txtclose;
        Button btnFollow;
        BusId = (TextView) myDialog.findViewById(R.id.dp_busid);
        BusId.setText(id);
        dp_nationality = (TextView) myDialog.findViewById(R.id.dp_nationalityp);
        dp_age = (TextView) myDialog.findViewById(R.id.dp_age);
        dp_phone = (TextView) myDialog.findViewById(R.id.dp_phone);
        dp_email = (TextView) myDialog.findViewById(R.id.dp_email);
        dp_location = (TextView) myDialog.findViewById(R.id.dp_location);
        dp_name = (TextView) myDialog.findViewById(R.id.driver_name);
        getDriverInfo("0","notId");
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

    public void findBusLocation(View view) {

        String label = "Current Bus Location";
        String uriBegin = "geo:12,34";
        String query = latt+","+lng+"(" + label + ")";
        String encodedQuery = Uri.encode( query  );
        String uriString = uriBegin + "?q=" + encodedQuery;
        Uri uri = Uri.parse("geo:"+latt+","+lng+"?q="+encodedQuery);
        Intent intent2 = new Intent(android.content.Intent.ACTION_VIEW, uri );
        startActivity( intent2 );

    }
}
