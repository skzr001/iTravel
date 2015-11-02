package com.example.travelguide;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondActivity extends Activity {

	private MapView mapView;
    private AMap aMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_second);
		
	    mapView = (MapView) findViewById(R.id.map);
	    mapView.onCreate(savedInstanceState);// ±ØÐëÒªÐ´
	    aMap = mapView.getMap();
	    Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
					
					   Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
					  // intent.setClass(SecondActivity.this,ThirdActivity.class);
					   startActivity(intent);
					   //finish();
				}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

}
