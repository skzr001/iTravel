package com.example.travelguide;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

import android.app.Activity;
import android.view.Menu;

public class SecondActivity extends Activity {

	private MapView mapView;
    private AMap aMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    mapView = (MapView) findViewById(R.id.map);
	    mapView.onCreate(savedInstanceState);// ±ØÐëÒªÐ´
	    aMap = mapView.getMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

}
