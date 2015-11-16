package com.example.travelguide;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;







import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class FirstActivity extends Activity {
	private Spinner spinner;
	private List<String> data_list;
	private ArrayAdapter<String> arr_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_first);
		spinner= (Spinner) findViewById(R.id.spinnerfirst);
	    
        //����
        data_list = new ArrayList<String>();
        
        data_list.add("���ݴ�ѧ��������");
        
        
        //������
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //������ʽ
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //����������
        spinner.setAdapter(arr_adapter);
		Button btn = (Button)findViewById(R.id.searchText);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				
				/* �½�һ��Intent���� */
		        Intent intent = new Intent();
		        intent.putExtra("name","LeiPei");    
		        /* ָ��intentҪ�������� */
		        intent.setClass(FirstActivity.this, SecondActivity.class);
		        /* ����һ���µ�Activity */
		        FirstActivity.this.startActivity(intent);
		        /* �رյ�ǰ��Activity */
		        FirstActivity.this.finish();
		        
				
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