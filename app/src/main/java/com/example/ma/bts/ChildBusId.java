package com.example.ma.bts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Mohammed_Aqrabawi on 10/12/2018.
 */

public class ChildBusId extends AppCompatActivity {
	TextView hello;
	EditText busID;
	Button start;
	ListView childInfo;
	SharedPreferences sp;
	Data data;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parent_busid);
		data = new Data();

		sp=this.getSharedPreferences("com.example.ma.bts", Context.MODE_PRIVATE);

		childInfo= (ListView) findViewById(R.id.name);
		childInfo.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1
				,data.fillChild(sp.getString("key","null"))));
		childInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				sp.edit().putInt("index", Integer.parseInt(Data.id.get(i))).apply();
				//data.driverInfo(Data.id.get(sp.getInt("index",0)));
//				Intent svb=new Intent(getApplicationContext(), ServiceNotification.class);
//				svb.putExtra("id",Data.id.get(i));
//				startService(svb);
				Intent intent=new Intent(getApplicationContext(),TrackDriverInfo.class);
				startActivity(intent);
				finish();
			}
		});


		hello= (TextView) findViewById(R.id.hello);
		hello.setText("Hello Mr."+sp.getString("name",""));
		start= (Button) findViewById(R.id.start);
		busID= (EditText) findViewById(R.id.busid);



	}
	public void start(View v){

		sp.edit().putInt("index",Integer.valueOf(String.valueOf(busID.getText()))).apply();
		//data.driverInfo(String.valueOf(sp.getInt("index",0)));
//		Intent svb=new Intent(getApplicationContext(), ServiceNotification.class);
//		svb.putExtra("id",String.valueOf(sp.getInt("index",1)));
//		startService(svb);
		Intent intent=new Intent(getApplicationContext(),TrackDriverInfo.class);
		startActivity(intent);
		finish();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}
}
