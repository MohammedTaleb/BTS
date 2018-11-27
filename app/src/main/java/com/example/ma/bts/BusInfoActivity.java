package com.example.ma.bts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by BTS on 11/1/18.
 */

class BusInfoActivity extends android.support.v4.app.Fragment {
    String busNum;
    public BusInfoActivity(String busNum) {
        this.busNum = busNum;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bus_info,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView bNum,Bcapacity,bModel,BMFG,Bidentity;
        final ImageView bImage;
        bNum= (TextView) view.findViewById(R.id.b_busNumberer);
        Bcapacity= (TextView) view.findViewById(R.id.b_buscapacity);
        bModel= (TextView) view.findViewById(R.id.b_busModel);
        BMFG= (TextView) view.findViewById(R.id.b_mfg);
        bImage = view.findViewById(R.id.bus_pic);
        Bidentity = view.findViewById(R.id.busIdentity);
        Bidentity.setText("Bus No."+busNum);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference busRef = firebaseDatabase.getReference().child("Bus").child(busNum);
        busRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             BusInfo busInfo = dataSnapshot.getValue(BusInfo.class);

                bNum.setText(busInfo.busNumber);
                Bcapacity.setText(busInfo.capacity);
                bModel.setText(busInfo.busModel);
                BMFG.setText(busInfo.manufacturerCompany);
                Picasso.with(view.getContext())
                        .load(busInfo.image)
                        .into(bImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
