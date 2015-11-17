
package com.example.travelguide;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ThirdActivity extends Activity {
	private Spinner spinner1,spinner2;
	private List<String> data_list;
	private ArrayAdapter<String> arr_adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_third);
        spinner1 = (Spinner) findViewById(R.id.spinnerthird1);
	    
        //数据
        data_list = new ArrayList<String>();
        
        data_list.add("清卜茶园");
        data_list.add("圆通");
        data_list.add("速跑");
        data_list.add("意祥驾校");
        
        
        
        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner1.setAdapter(arr_adapter);
        spinner2 = (Spinner) findViewById(R.id.spinnerthird2);
	    
        //数据
        data_list = new ArrayList<String>();
        
        data_list.add("清卜茶园");
        data_list.add("圆通");
        data_list.add("速跑");
        data_list.add("意祥驾校");
        
        
        
        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner2.setAdapter(arr_adapter);
		Button btn = (Button)findViewById(R.id.queren);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				
				
				String text1 = (String) spinner1.getSelectedItem();
				
				
				String text2  = (String) spinner2.getSelectedItem();
				
//				速跑     26.05502  119.18683
//				圆通    26.05500   119.186838
//				意祥驾校    26.05502    119.18705
//				清卜茶园    26.05383    119.18704
				
				String s1 = "速跑";
				String s2 = "圆通";
				String s3 = "意祥驾校";
				String s4 = "清卜茶园";
				
				
				int a=0,b=0;
				
				if(text1.equals(s1))
				{
					a=1;
				}
				else if(text1.equals(s2))
				{
					a=2;
				}
				else if(text1.equals(s3))
				{
					a=3;
				}
				else if(text1.equals(s4))
				{
					a=4;
				}
				else if(text1.length() == 0)
				{
					a=100;
				}
				
				
				if(text2.equals(s1))
				{
					b=1;
				}
				else if(text2.equals(s2))
				{
					b=2;
				}
				else if(text2.equals(s3))
				{
					b=3;
				}
				else if(text2.equals(s4))
				{
					b=4;
				}
				
				
				/* 新建一个Intent对象 */
		        Intent intent = new Intent();
		        intent.putExtra("number1",a);    
		        intent.putExtra("number2",b);
		        /* 指定intent要启动的类 */
		        intent.setClass(ThirdActivity.this, SecondActivity.class);
		        /* 启动一个新的Activity */
		        ThirdActivity.this.startActivity(intent);
		        /* 关闭当前的Activity */
		        ThirdActivity.this.finish();
		        
				
			}
		});
	}  
  
}
