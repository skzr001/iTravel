package com.example.travelguide;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.location.LocationManagerProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RoadActivity extends Activity{
	private Spinner spinner1,spinner2;
	private List<String> data_list;
	private ArrayAdapter<String> arr_adapter;
	private double mm,nn;
	private AMap aMap;
	private OnLocationChangedListener mListener;
    private LocationManagerProxy mLocationManagerProxy;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_road);
		Intent intent_accept = getIntent();           //创建一个接收意图
        Bundle bundle = intent_accept.getExtras();    //创建Bundle对象，用于接收Intent数据
		mm = bundle.getDouble("dangqian1");               //获取Intent的内容age
        nn = bundle.getDouble("dangqian2");  
        spinner1 = (Spinner) findViewById(R.id.spinnerroad1);
	    
        //数据
        data_list = new ArrayList<String>();
        data_list.add("我的位置");
        data_list.add("万家超市");
        data_list.add("顺丰快递");
        data_list.add("美阁");
        data_list.add("京东快递");
        data_list.add("西子正装");
        data_list.add("儿画工作室");
        data_list.add("韵达快递");
        data_list.add("圆通快递");
        data_list.add("诺曼服饰正装");
        data_list.add("悦生活");
        
        
        
        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner1.setAdapter(arr_adapter);
        spinner2 = (Spinner) findViewById(R.id.spinnerroad2);
	    
        //数据
        data_list = new ArrayList<String>();
        
        data_list.add("万家超市");
        data_list.add("顺丰快递");
        data_list.add("美阁");
        data_list.add("京东快递");
        data_list.add("西子正装");
        data_list.add("儿画工作室");
        data_list.add("韵达快递");
        data_list.add("圆通快递");
        data_list.add("诺曼服饰正装");
        data_list.add("悦生活");
        
       
        
        
        
        
        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner2.setAdapter(arr_adapter);
        
        
        
      
				
        
        
        
		Button btn = (Button)findViewById(R.id.queren1);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				
				
				String text1 = (String) spinner1.getSelectedItem();
				
				
				String text2  = (String) spinner2.getSelectedItem();
				
//				万嘉超市   119.190722,26.051058
//              顺丰快递   119.190829,26.050947
//				美阁     119.190862,26.05085
//				京东快递     119.190878,26.050754
//				西子正装    119.190894,26.050677
//				儿画工作室   119.190915,26.050571
//				韵达快递    119.190937,26.050474
//				圆通速递    119.190926,26.050392
//				诺曼服饰正装（最新地图上已有）   119.191028,26.050296
//				悦生活     119.191028,26.050132



				String s0 = "我的位置";
 				String s1 = "万家超市";
				String s2 = "顺丰快递";
				String s3 = "美阁";
				String s4 = "京东快递";
				String s5 = "西子正装";
				String s6 = "儿画工作室";
				String s7 = "韵达快递";
				String s8 = "圆通速递";
				String s9 = "诺曼服饰正装";
				String s10 = "悦生活";
				

				
				
				int a=0,b=0;
				
				if(text1.equals(s1))
				{
					a=41;
				}
				else if(text1.equals(s2))
				{
					a=42;
				}
				else if(text1.equals(s3))
				{
					a=43;
				}
				else if(text1.equals(s4))
				{
					a=44;
				}
				else if(text1.equals(s5))
				{
					a=45;
				}
				else if(text1.equals(s6))
				{
					a=46;
				}
				else if(text1.equals(s7))
				{
					a=47;
				}
				else if(text1.equals(s8))
				{
					a=48;
				}
				else if(text1.equals(s9))
				{
					a=49;
				}
				else if(text1.equals(s10))
				{
					a=410;
				}
				
				else if(text1.equals(s0))
				{
					a=100;
				}
				
				
				if(text2.equals(s1))
				{
					b=41;
				}
				else if(text2.equals(s2))
				{
					b=42;
				}
				else if(text2.equals(s3))
				{
					b=43;
				}
				else if(text2.equals(s4))
				{
					b=44;
				}
				else if(text2.equals(s5))
				{
					b=45;
				}
				else if(text2.equals(s6))
				{
					b=46;
				}
				else if(text2.equals(s7))
				{
					b=47;
				}
				else if(text2.equals(s8))
				{
					b=48;
				}
				else if(text2.equals(s9))
				{
					b=49;
				}
				else if(text2.equals(s10))
				{
					b=410;
				}
				

				
				
				/* 新建一个Intent对象 */
		        Intent intent = new Intent();
		        intent.putExtra("number5",a);    
		        intent.putExtra("number6",b);
		        intent.putExtra("number7",mm);
		        intent.putExtra("number8",nn);
		        intent.putExtra("name3",4);    
		        /* 指定intent要启动的类 */
		        intent.setClass(RoadActivity.this, SecondActivity.class);
		        /* 启动一个新的Activity */
		        RoadActivity.this.startActivity(intent);
		        finish();
		        
				
			}
		});
	}
	

}
