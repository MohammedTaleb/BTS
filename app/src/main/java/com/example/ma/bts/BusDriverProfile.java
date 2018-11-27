package com.example.ma.bts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


/**
 * Created by BTS on 10/31/18.
 */

public class BusDriverProfile extends Fragment {
    String driverkey;
    public BusDriverProfile() {
    }

    public BusDriverProfile(String driverkey) {
        this.driverkey = driverkey;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bus_driver_profile,container,false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseDatabase Firedatabase;
        DatabaseReference myRef;
        final TextView BusId,dp_nationality,dp_age,dp_phone,dp_email,dp_location,dp_name;
        final ImageView  driverPic;
        BusId = view.findViewById(R.id.dp_busid);
        dp_nationality = view.findViewById(R.id.dp_nationality)  ;
        dp_age = view.findViewById(R.id.dp_age);
        dp_phone = view.findViewById(R.id.dp_phone);
        dp_email = view.findViewById(R.id.dp_email);
        dp_location = view.findViewById(R.id.dp_location);
        dp_name = view.findViewById(R.id.driverName);
        driverPic = view.findViewById(R.id.driver_tPic);

        Firedatabase = FirebaseDatabase.getInstance();
        myRef = Firedatabase.getReference().child("Driver").child(driverkey);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            DriverInfo driverInfo = dataSnapshot.getValue(DriverInfo.class);
            BusId.setText(driverInfo.busId);
            dp_nationality.setText(driverInfo.nationality);
            dp_age.setText(driverInfo.age);
            dp_phone.setText(driverInfo.number);
            dp_email.setText(driverInfo.email);
            dp_location.setText(driverInfo.address);
            dp_name.setText(driverInfo.name);
            Picasso.with(view.getContext())
                        .load(driverInfo.image)
                        .into(driverPic);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
