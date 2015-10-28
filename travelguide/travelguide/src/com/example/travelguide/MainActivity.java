package com.example.travelguide;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;


import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_first);
		
		Button btn = (Button)findViewById(R.id.searchText);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				
				/* 新建一个Intent对象 */
		        Intent intent = new Intent();
		        intent.putExtra("name","LeiPei");    
		        /* 指定intent要启动的类 */
		        intent.setClass(MainActivity.this, SecondActivity.class);
		        /* 启动一个新的Activity */
		        MainActivity.this.startActivity(intent);
		        /* 关闭当前的Activity */
		        MainActivity.this.finish();
				
			}
		});
		
		
	   
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
