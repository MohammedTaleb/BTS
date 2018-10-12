package com.example.ma.bts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parent_busid);
		childInfo= (ListView) findViewById(R.id.name);
		sp=this.getSharedPreferences("com.example.ma.bts", Context.MODE_PRIVATE);






	}
}
