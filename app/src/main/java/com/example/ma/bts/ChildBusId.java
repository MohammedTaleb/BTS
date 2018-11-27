package com.example.ma.bts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by BTS on 10/12/2018.
 */

public class ChildBusId extends Fragment implements View.OnClickListener {
	TextView hello;
	EditText busID;
	Button start;
	ListView childInfo;
	SharedPreferences sp;
	Data data;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.parent_busid,container,false);
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
        getActivity().setTitleColor(getResources().getColor(R.color.white));
		data = new Data();
		sp=this.getActivity().getSharedPreferences("com.example.ma.bts", Context.MODE_PRIVATE);
		childInfo= (ListView) view.findViewById(R.id.name);
		childInfo.setAdapter(new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1
				,data.fillChild(sp.getString("key","null"))));
		childInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				sp.edit().putInt("index", Integer.parseInt(Data.id.get(i))).apply();
				//data.driverInfo(Data.id.get(sp.getInt("index",0)));
//				Intent svb=new Intent(getApplicationContext(), ServiceNotification.class);
//				svb.putExtra("id",Data.id.get(i));
//				startService(svb);
				Intent intent=new Intent(getActivity(),TrackDriverInfo.class);
				startActivity(intent);
			}
		});
		hello= (TextView) view.findViewById(R.id.hello);
		hello.setText("Hello "+sp.getString("name",""));
		start= (Button) view.findViewById(R.id.start);
		start.setOnClickListener(this);
		busID= (EditText) view.findViewById(R.id.busid);


	}


//    public void start(View v){
//
//        sp.edit().putInt("index",Integer.valueOf(String.valueOf(busID.getText()))).apply();
//        //data.driverInfo(String.valueOf(sp.getInt("index",0)));
////		Intent svb=new Intent(getApplicationContext(), ServiceNotification.class);
////		svb.putExtra("id",String.valueOf(sp.getInt("index",1)));
////		startService(svb);
//        Intent intent=new Intent(getActivity(),TrackDriverInfo.class);
//        startActivity(intent);
//
//    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.start ){
        sp.edit().putInt("index",Integer.valueOf(String.valueOf(busID.getText()))).apply();
        Intent intent=new Intent(getActivity(),TrackDriverInfo.class);
        startActivity(intent);}
    }
}
