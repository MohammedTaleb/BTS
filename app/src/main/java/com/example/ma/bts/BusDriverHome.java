package com.example.ma.bts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by Belal on 18/09/16.
 */


public class BusDriverHome extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference myref;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        return inflater.inflate(R.layout.fragment_menu_1, container, false);
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");
        getActivity().setTitleColor(getResources().getColor(R.color.white));
        recyclerView = (RecyclerView)view.findViewById(R.id.studentsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        myref= FirebaseDatabase.getInstance().getReference().child("Child");
        FirebaseRecyclerAdapter<Child,ChildViewHolder> recyclerAdapter=new FirebaseRecyclerAdapter<Child,ChildViewHolder>(
                Child.class,
                R.layout.recycler_item,
                ChildViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(BusDriverHome.ChildViewHolder viewHolder, Child model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setGrade(model.getGrade());
                viewHolder.setImage(model.getImage());
                viewHolder.setParentID(model.getparentId());
            }



        };
        recyclerView.setAdapter(recyclerAdapter);



    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Name,Grade,ParentIDe;
        ImageButton callParent,parentLocation;
        de.hdodenhof.circleimageview.CircleImageView childPic;
        View mView;
        FirebaseDatabase Firedatabase;
        DatabaseReference myRef;
        String Number;
        double latitude,longitude;
        Context context;


        public ChildViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            Name = (TextView)itemView.findViewById(R.id.stdname);
            Grade = (TextView) itemView.findViewById(R.id.stdgrade);
            childPic = itemView.findViewById(R.id.studentPic);
            ParentIDe = itemView.findViewById(R.id.parentIdentity);
            callParent = itemView.findViewById(R.id.callparent);
            callParent.setOnClickListener(this);
            parentLocation = itemView.findViewById(R.id.locationparent);
            parentLocation.setOnClickListener(this);

            context = itemView.getContext();;

            Firedatabase = Firedatabase.getInstance();
            myRef        = Firedatabase.getReference();
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Number = dataSnapshot.child("Parent").child((String)
                            ParentIDe.getText()).child("number").getValue().toString();
                    latitude = (double) dataSnapshot.child("Parent").child((String) ParentIDe.getText()).child("latt").getValue();
                    longitude = (double) dataSnapshot.child("Parent").child((String) ParentIDe.getText()).child("long").getValue();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        public void setName(String name) {
            Name.setText(name);
        }

        public void setGrade(String grade) {
            Grade.setText(grade);
        }

        public void setImage(String image) {
            Picasso.with(mView.getContext())
                    .load(image)
                    .into(childPic);    }




        public void setParentID(String parentId) {
            ParentIDe.setText(parentId);
        }

        @Override
        public void onClick(View v) {



            switch (v.getId()){
                case R.id.callparent:
                    Intent intent = new Intent (Intent.ACTION_DIAL, Uri.parse("tel:" + Number));
                    context.startActivity(intent);
                    break;
                case R.id.locationparent:
                    Log.i("lattlong",latitude+" "+longitude);
//                    String uri = String.format(Locale.ENGLISH, "geo:",   latitude, longitude+"?q=Cinnamon & Toast");
//                    Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                    context.startActivity(map);


                    String label = "Student's Home";
                    String uriBegin = "geo:12,34";
                    String query = latitude+","+longitude+"(" + label + ")";
                    String encodedQuery = Uri.encode( query  );
                    String uriString = uriBegin + "?q=" + encodedQuery;
                    Uri uri = Uri.parse("geo:"+latitude+","+longitude+"?q="+encodedQuery);
                    Intent intent2 = new Intent(android.content.Intent.ACTION_VIEW, uri );
                    context.startActivity( intent2 );
                    break;

            }
        }
    }



}

