
package com.example.travelguide;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class ThirdActivity extends Activity {
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
		setContentView(R.layout.activity_third);
		Intent intent_accept = getIntent();           //创建一个接收意图
        Bundle bundle = intent_accept.getExtras();    //创建Bundle对象，用于接收Intent数据
		mm = bundle.getDouble("dangqian1");               //获取Intent的内容age
        nn = bundle.getDouble("dangqian2");  
        spinner1 = (Spinner) findViewById(R.id.spinnerthird1);
	    
        //数据
        data_list = new ArrayList<String>();
        data_list.add("我的位置");
        data_list.add("清卜茶园");
        data_list.add("速跑");
        data_list.add("意祥驾校");
        data_list.add("电脑之家");
        data_list.add("千艺美发");
        data_list.add("花样年华");
        data_list.add("知发者");
        data_list.add("小春花屋");
        data_list.add("福伯烧仙草");
        data_list.add("万家");
        data_list.add("冰凌城下");
        data_list.add("圆通快递点");
        
        
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
        data_list.add("速跑");
        data_list.add("意祥驾校");
        data_list.add("电脑之家");
        data_list.add("千艺美发");
        data_list.add("花样年华");
        data_list.add("知发者");
        data_list.add("小春花屋");
        data_list.add("福伯烧仙草");
        data_list.add("万家");
        data_list.add("冰凌城下");
        data_list.add("圆通快递点");
        
       
        
        
        
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
//				速跑  26.051906，119.191682/
//				万家 26.051067，119.192433/
//				冰凌城下 26.051241，119.191629

//				addMarker(26.051246,119.19283,"电脑之家","1.png");//
//				addMarker(26.050908,119.19187,"千艺美发","1.png");//
//				addMarker(26.050932,119.191784,"清卟茶园","1.png");//
//				addMarker(26.051824,119.191677,"花样年华","1.png");//
//				addMarker(26.052002,119.191704,"圆通快递点","1.png");//
//				addMarker(26.052103,119.191725,"意祥驾校","1.png");//
//				addMarker(26.052171,119.191816,"知发者","1.png");//
//				addMarker(26.052171,119.191913,"小春花屋","1.png");//
//				addMarker(26.052195,119.192122,"福伯烧仙草","1.png");//
//				
//				万家 26.051067，119.192433
//				冰凌城下 26.051241，119.191629

				String s0 = "我的位置";
 				String s1 = "速跑";
				String s2 = "圆通快递点";
				String s3 = "意祥驾校";
				String s4 = "清卜茶园";
				String s5 = "电脑之家";
				String s6 = "千艺美发";
				String s7 = "花样年华";
				String s8 = "知发者";
				String s9 = "小春花屋";
				String s10 = "福伯烧仙草";
				String s11= "万家";
				String s12= "冰凌城下";

				
				
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
				else if(text1.equals(s5))
				{
					a=5;
				}
				else if(text1.equals(s6))
				{
					a=6;
				}
				else if(text1.equals(s7))
				{
					a=7;
				}
				else if(text1.equals(s8))
				{
					a=8;
				}
				else if(text1.equals(s9))
				{
					a=9;
				}
				else if(text1.equals(s10))
				{
					a=10;
				}
				else if(text1.equals(s11))
				{
					a=11;
				}
				else if(text1.equals(s12))
				{
					a=12;
				}
				else if(text1.equals(s0))
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
				else if(text2.equals(s5))
				{
					b=5;
				}
				else if(text2.equals(s6))
				{
					b=6;
				}
				else if(text2.equals(s7))
				{
					b=7;
				}
				else if(text2.equals(s8))
				{
					b=8;
				}
				else if(text2.equals(s9))
				{
					b=9;
				}
				else if(text2.equals(s10))
				{
					b=10;
				}
				else if(text2.equals(s11))
				{
					b=11;
				}
				else if(text2.equals(s12))
				{
					b=12;
				}

				
				
				/* 新建一个Intent对象 */
		        Intent intent = new Intent();
		        intent.putExtra("number1",a);    
		        intent.putExtra("number2",b);
		        intent.putExtra("number3",mm);
		        intent.putExtra("number4",nn);
		        /* 指定intent要启动的类 */
		        intent.setClass(ThirdActivity.this, SecondActivity.class);
		        /* 启动一个新的Activity */
		        ThirdActivity.this.startActivity(intent);
		        finish();
		        
				
			}
		});
	}
	


}
