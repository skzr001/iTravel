package com.example.travelguide;
import com.example.travelguide.until.*;


import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.net.ConnectivityManager;

import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.maps.LocationSource;





import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.example.travelguide.R;
import com.example.travelguide.SecondActivity;
import com.example.travelguide.until.AMapUtil;
import com.example.travelguide.until.Nameclass;
import com.example.travelguide.until.ToastUtil;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity implements  LocationSource, AMapLocationListener, OnMapClickListener, OnMarkerClickListener, 
OnMapLoadedListener, OnClickListener, TextWatcher, InfoWindowAdapter, OnPoiSearchListener,AMapNaviListener {

	
	private int area=0;
	private int store=0;
	private int LocationFuda=0;
	
	
	private AMapNavi mAMapNavi;
    private int sendnum1=0;
    private int sendnum2=0;
    private int sendnum3=0;
    private int sendnum4=0;
	// 起点终点坐标
	private double mm=0,nn=0;
	private double instant_lat=0,instant_lng=0;
	private NaviLatLng mNaviStart = new NaviLatLng(26.051000,119.192000);
	private NaviLatLng mNaviEnd = new NaviLatLng(26.051212,119.192369);
	// 起点终点列表
	private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
	private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
	// 规划线路
	private RouteOverLay mRouteOverLay;
	
	
	private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private LocationManagerProxy mLocationManagerProxy;
    private Marker mGPSMarker;
    private PendingIntent mPendingIntent;
    private Circle mCircle;
    private LatLng mar1=new LatLng(26.0537,119.1875);
    private TextView markerText;
    private LatLng mar2=new LatLng(26.05393,119.18717);
    
   
    //
    private AutoCompleteTextView searchText;// 输入搜索关键字
	private String keyWord = "";// 要输入的poi搜索关键字
	private ProgressDialog progDialog = null;// 搜索时进度条
	private PoiResult poiResult; // poi返回的结果
	private PoiSearch.Query query;// Poi查询条件类
	private PoiSearch poiSearch;// POI搜索

    
    public static final String GEOFENCE_BROADCAST_ACTION = "com.example.travelguide";
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_second);

		mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
	    init(savedInstanceState);
	    
	    
	    mAMapNavi = AMapNavi.getInstance(this);

		mAMapNavi.setAMapNaviListener(this);

		
		mRouteOverLay = new RouteOverLay(aMap, null);
		
		Intent intent_num1 = getIntent();
		Bundle bundle_num1 = intent_num1.getExtras();  
		sendnum1=bundle_num1.getInt("name1");
		if(sendnum1==3 || sendnum1==4) area=sendnum1;
		
		
		Intent intent_num2 = getIntent();
		Bundle bundle_num2 = intent_num2.getExtras();  
		sendnum2=bundle_num2.getInt("name2");
		if(sendnum2==3 || sendnum2==4) area=sendnum2;
		
		Intent intent_num3 = getIntent();
		Bundle bundle_num3 = intent_num3.getExtras();  
		sendnum3=bundle_num3.getInt("name3");
		if(sendnum3==3 || sendnum3==4) area=sendnum3;
		
		
		
		Intent intent_accept = getIntent();           //创建一个接收意图
        Bundle bundle = intent_accept.getExtras();    //创建Bundle对象，用于接收Intent数据
//        String name = bundle.getString("name");       //获取Intent的内容name
       
        int numb1=0;
        int numb2=0;
        int ss=0;
        if(area==3) numb1 = bundle.getInt("number1");               //获取Intent的内容age
        
        if(area==3) numb2 = bundle.getInt("number2");               //获取Intent的内容age
        
        if(area==4) numb1 = bundle.getInt("number5");               //获取Intent的内容age
        
        if(area==4) numb2 = bundle.getInt("number6");               //获取Intent的内容age
       
		
		if(numb1!=0 &&numb2!=0)
		{
			
			double m1=0,n1=0,m2=0,n2=0;

			
//			速跑     26.05502  119.18683
//			圆通    26.05500   119.186838
//			意祥驾校    26.05502    119.18705
//			清卜茶园    26.05383    119.18704	
//			addMarker(26.051246,119.19283,"电脑之家","1.png");//
//			addMarker(26.050908,119.19187,"千艺美发","1.png");//
//			addMarker(26.050932,119.191784,"清卟茶园","1.png");//
//			addMarker(26.051824,119.191677,"花样年华","1.png");//
//			addMarker(26.052002,119.191704,"圆通快递点","1.png");//
//			addMarker(26.052103,119.191725,"意祥驾校","1.png");//
//			addMarker(26.052171,119.191816,"知发者","1.png");//
//			addMarker(26.052171,119.191913,"小春花屋","1.png");//
//			addMarker(26.052195,119.192122,"福伯烧仙草","1.png");//	
//			万家 26.051067，119.192433
//			冰凌城下 26.051241，119.191629

			
//			String s1 = "速跑";
//			String s2 = "圆通";
//			String s3 = "意祥驾校";
//			String s4 = "清卜茶园";
//			String s5 = "电脑之家";
//			String s6 = "千艺美发";
//			String s7 = "花样年华";
//			String s8 = "知发者";
//			String s9 = "小春花屋";
//			String s10 = "福伯烧仙草";
//			String s11= "万家";
//			String s12= "冰凌城下";
//			String s13= "圆通快递点";

			//起始地点确认
			if(numb1==1)  
			{
				m1=26.051906;
				m2=119.191682;
			}
			else if(numb1==2)  
			{
				m1=26.052002;
				m2=119.191704;
			}
			else if(numb1==3)  
			{
				m1= 26.052103;
				m2=119.191725;
			}
			else if(numb1==4)  
			{
				m1=26.050932;
				m2=119.191784;
			}
			else if(numb1==5)  
			{
				m1=26.051246;
				m2=119.19283;
			}
			else if(numb1==6)  
			{
				m1=26.050908;
				m2=119.19187;
			}
			else if(numb1==7)  
			{
				m1=26.051824;
				m2=119.191677;
			}
			else if(numb1==8)  
			{
				m1=26.052171;
				m2=119.191816;
			}
			else if(numb1==9)  
			{
				m1=26.052171;
				m2=119.191913;
			}
			else if(numb1==10)  
			{
				m1=26.052195;
				m2=119.192122;
			}
			else if(numb1==11)  
			{
				m1=26.051067;
				m2=119.192433;
			}
			else if(numb1==12)  
			{
				m1=26.051241;
				m2=119.191629;
			}
			else if(numb1==41)  
			{
				m1=26.051058;
				m2=119.190722;
			}
			else if(numb1==42)  
			{
				m1=26.050947;
				m2=119.190829;
			}
			else if(numb1==43)  
			{
				m1= 26.05085;
				m2=119.190862;
			}
			else if(numb1==44)  
			{
				m1=26.050754;
				m2=119.190878;
			}
			else if(numb1==45)  
			{
				m1=26.050677;
				m2=119.190894;
			}
			else if(numb1==46)  
			{
				m1=26.050571;
				m2=119.190915;
			}
			else if(numb1==47)  
			{
				m1=26.050474;
				m2=119.190937;
			}
			else if(numb1==48)  
			{
				m1=26.050392;
				m2=119.190926;
			}
			else if(numb1==49)  
			{
				m1=26.050296;
				m2=119.191028;
			}
			else if(numb1==410)  
			{
				m1=26.050132;
				m2=119.191028;
			}
			
			
			else if(numb1==100)  
			{
				if(area==3) mm = bundle.getDouble("number3");               //获取Intent的内容age
				if(area==3) nn = bundle.getDouble("number4");               //获取Intent的内容age
				if(area==4) mm = bundle.getDouble("number7");               //获取Intent的内容age
				if(area==4) nn = bundle.getDouble("number8");               //获取Intent的内容age
				m1=mm;
				m2=nn;
				
			}
			
			
			
			//终止地点确认
			if(numb2==1)  
			{
				n1=26.051906;
				n2=119.191682;
			}
			else if(numb2==2)  
			{
				n1=26.052002;
				n2=119.191704;
			}
			else if(numb2==3)  
			{
				n1=26.052103;
				n2=119.191725;
			}
			else if(numb2==4)  
			{
				n1=26.050932;
				n2=119.191784;
			}
			else if(numb2==5)  
			{
				n1=26.051246;
				n2=119.19283;
			}
			else if(numb2==6)  
			{
				n1=26.050908;
				n2=119.19187;
			}
			else if(numb2==7)  
			{
				n1=26.051824;
				n2=119.191677;
			}
			else if(numb2==8)  
			{
				n1=26.052171;
				n2=119.191816;
			}
			else if(numb2==9)  
			{
				n1=26.052171;
				n2=119.191913;
			}
			else if(numb2==10)  
			{
				n1=26.052195;
				n2=119.192122;
			}
			else if(numb2==11)  
			{
				n1=26.051067;
				n2=119.192433;
			}
			else if(numb2==12)  
			{
				n1=26.051241;
				n2=119.191629;
			}
			else if(numb2==41)  
			{
				n1=26.051058;
				n2=119.190722;
			}
			else if(numb2==42)  
			{
				n1=26.050947;
				n2=119.190829;
			}
			else if(numb2==43)  
			{
				n1= 26.05085;
				n2=119.190862;
			}
			else if(numb2==44)  
			{
				n1=26.050754;
				n2=119.190878;
			}
			else if(numb2==45)  
			{
				n1=26.050677;
				n2=119.190894;
			}
			else if(numb2==46)  
			{
				n1=26.050571;
				n2=119.190915;
			}
			else if(numb2==47)  
			{
				n1=26.050474;
				n2=119.190937;
			}
			else if(numb2==48)  
			{
				n1=26.050392;
				n2=119.190926;
			}
			else if(numb2==49)  
			{
				n1=26.050296;
				n2=119.191028;
			}
			else if(numb2==410)  
			{
				n1=26.050132;
				n2=119.191028;
			}
			
			
			mNaviStart = new NaviLatLng(m1,m2);

			mNaviEnd = new NaviLatLng(n1,n2);
			
			
			mStartPoints.clear();
			mEndPoints.clear();
			mStartPoints.add(mNaviStart);
			mEndPoints.add(mNaviEnd);
			
			
			calculateFootRoute();
		}

		
	    
	    
	    Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			
			if (area==3) {
				
				Intent intent = new Intent();
		           intent.putExtra("dangqian1",mm);    
	               intent.putExtra("dangqian2",nn);
				   intent.setClass(SecondActivity.this,ThirdActivity.class);
				  // intent.setClass(SecondActivity.this,ThirdActivity.class);
				   startActivity(intent);
				   finish();
				   
			}
			          if(area==4) {
			        	 
			        	   Intent intent = new Intent();
				           intent.putExtra("dangqian1",mm);    
			               intent.putExtra("dangqian2",nn);
						   intent.setClass(SecondActivity.this,RoadActivity.class);
						  // intent.setClass(SecondActivity.this,ThirdActivity.class);
						   startActivity(intent);
						   finish();
					}
				}
		});
		
		//定位按钮自定义
		 Button btn2 = (Button)findViewById(R.id.LocationButton);
			btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						
				mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork,  -1, 3, SecondActivity.this);
				
//				showToast("mm="+mm+"  nn="+nn);
				}
			});
	}

    
    private void calculateFootRoute() {
		boolean isSuccess = mAMapNavi.calculateWalkRoute(mNaviStart, mNaviEnd);
		if (!isSuccess) {
			showToast("路线计算失败,检查参数情况");
		}
	}
	private void showToast(String message) {
		ToastUtil.show(SecondActivity.this, message);
	}
    
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}



	private void init(Bundle savedInstanceState) {
		markerText = (TextView) findViewById(R.id.mark_listenter_text);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
		aMap = mapView.getMap();
//		mapView = (MapView) findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);
//        aMap = mapView.getMap();
        
		//geoFence
        aMap.setOnMapClickListener(this);
		IntentFilter fliter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		fliter.addAction(GEOFENCE_BROADCAST_ACTION);
		registerReceiver(mGeoFenceReceiver, fliter);
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		
		Intent intent = new Intent(GEOFENCE_BROADCAST_ACTION);
		mPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
				intent, 0);

		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次
		//在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除



		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork		 
				, 2000, 15, this);


		MarkerOptions markOptions = new MarkerOptions();
		markOptions.icon(
				BitmapDescriptorFactory.fromBitmap(BitmapFactory
						.decodeResource(getResources(),
								R.drawable.location_marker)))
				.anchor(0.5f, 0.5f);
		mGPSMarker = aMap.addMarker(markOptions);
		aMap.setOnMapClickListener(this);
		addMarkersToMap();
    }
	
	
	
	
	private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接受广播
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                Bundle bundle = intent.getExtras();
                // 根据广播的status来确定是在区域内还是在区域外
                int status = bundle.getInt("status");
//                showToast("测试成功");
                if (status == 0) {
                	LocationFuda=0;
                	showToast("您不在该区域");
                } else {
                	LocationFuda=1;
               	showToast("您已进入该区域");
/*                	AlertDialog.Builder dialog=new AlertDialog.Builder(SecondActivity.this);
                	dialog.setTitle("提示信息");
                	dialog.setMessage("是否收听音频介绍");
                	dialog.setCancelable(false);
                	dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent1=new Intent();
							intent1.putExtra("num",store);
							intent1.setClass(SecondActivity.this, MusicActivity.class);
							startActivity(intent1);
						}
					});
                	dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
                	dialog.show();*/
                }
 
            }
//            else{
//            	showToast("您已进入景区");
//            }
        }
    };

	private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
        aMap.setOnMapLoadedListener(this);
        aMap.setOnMarkerClickListener(this);
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
        
//        addMarkersToMap();
        //
    	Button searButton = (Button) findViewById(R.id.searchButton);
		searButton.setOnClickListener(this);
		
		searchText = (AutoCompleteTextView) findViewById(R.id.keyWord);
		searchText.addTextChangedListener(this);// 添加文本输入框监听事件
		aMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
		aMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
    	//
        
    }
	//
	public void searchButton() {
		keyWord = AMapUtil.checkEditText(searchText);
		if ("".equals(keyWord)) {
			ToastUtil.show(SecondActivity.this, "请输入搜索关键字");
			return;
		} else {
			doSearchQuery();
		}
	}
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("正在搜索:\n" + keyWord);
		progDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery() {
		showProgressDialog();// 显示进度框

		query = new PoiSearch.Query(keyWord, "", "福州");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		poiSearch = new PoiSearch(this, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.searchPOIAsyn();
	}

	
	

	
	public void startAMapNavi(Marker marker) {
		//构造导航参数
		NaviPara naviPara=new NaviPara();
		//设置终点位置
		naviPara.setTargetPoint(marker.getPosition());
		//设置导航策略，这里是避免拥堵
		naviPara.setNaviStyle(AMapUtils.DRIVING_AVOID_CONGESTION);
		try {
			//调起高德地图导航
			AMapUtils.openAMapNavi(naviPara, getApplicationContext());
		} catch (com.amap.api.maps.AMapException e) {
			  //如果没安装会进入异常，调起下载页面
			 AMapUtils.getLatestAMapApp(getApplicationContext());
		}
		
		
 

	}


	/**
	 * 获取当前app的应用名字
	 */
	public String getApplicationName() {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = getApplicationContext().getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager
				.getApplicationLabel(applicationInfo);
		return applicationName;
	}

	
	

	

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String newText = s.toString().trim();
		Inputtips inputTips = new Inputtips(SecondActivity.this,
				new InputtipsListener() {

					@Override
					public void onGetInputtips(List<Tip> tipList, int rCode) {
						if (rCode == 0) {// 正确返回
							List<String> listString = new ArrayList<String>();
							for (int i = 0; i < tipList.size(); i++) {
								listString.add(tipList.get(i).getName());
							}
							ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
									getApplicationContext(),
									R.layout.route_inputs, listString);
							searchText.setAdapter(aAdapter);
							aAdapter.notifyDataSetChanged();
						}
					}
				});
		try {
			inputTips.requestInputtips(newText,  "福州");// 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号

		} catch (AMapException e) {
			e.printStackTrace();
		}
	}

	
	

	/**
	 * POI信息查询回调方法
	 */
	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		dissmissProgressDialog();// 隐藏对话框
		if (rCode == 0) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					poiResult = result;
					// 取得搜索到的poiitems有多少页
					List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

					if (poiItems != null && poiItems.size() > 0) {
						aMap.clear();// 清理之前的图标
						PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
						poiOverlay.removeFromMap();
						poiOverlay.addToMap();
						poiOverlay.zoomToSpan();
				
					} else {
						ToastUtil.show(SecondActivity.this,
								R.string.no_result);
					}
				}
			} else {
				ToastUtil.show(SecondActivity.this,
						R.string.no_result);
			}
		} else if (rCode == 27) {
			ToastUtil.show(SecondActivity.this,
					R.string.error_network);
		} else if (rCode == 32) {
			ToastUtil.show(SecondActivity.this, R.string.error_key);
		} else {
			ToastUtil.show(SecondActivity.this, getString(R.string.error_other) + rCode);
		}

	}

	/**
	 * Button点击事件回调方法
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/**
		 * 点击搜索按钮
		 */
		case R.id.searchButton:
			searchButton();
			break;
		
		
		default:
			break;
		}
	}
	//
	
	private void addMarkersToMap() {
//   	 	LatLng latLng = new LatLng(26.05124,119.19283);//电脑之家
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);	
//		CircleOptions circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 100))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.050908,119.19187);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.050932,119.191784);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.05182,119.191677);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.052002,119.191704);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.052103,119.191725);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.052171,119.191816);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//				
//		latLng = new LatLng(26.052171,119.191913);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.052195,119.192122);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.051906,119.191682);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.051067,119.192433);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
//		
//		latLng = new LatLng(26.051241,119.191629);
////		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
////				latLng.longitude, 10, 1000 * 60 * 30, mPendingIntent);
//		circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(10)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);
		
						/*福大B区*/
   	 	
		LatLng latLng = new LatLng(26.051125,119.192069);
		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
				latLng.longitude, 170, 1000 * 60 * 30, mPendingIntent);
		CircleOptions circleOptions = new CircleOptions();
		circleOptions.center(latLng).radius(170)
				.fillColor(Color.argb(180, 224, 171, 10))
				.strokeColor(Color.RED);
		mCircle = aMap.addCircle(circleOptions);//福大三区
		Nameclass nc=new Nameclass();
		
		MarkerOptions option = new MarkerOptions();

		//设置坐标点
		addMarker(nc.电脑之家,"电脑之家","2.png",0.1f,0.5f);//
		addMarker(nc.千艺美发,"千艺美发","qianyi.png",0.5f,0.1f);//
		addMarker(nc.清卟茶园,"清卟茶园","qingbu.png",0.5f,0.1f);//
		addMarker(nc.花样年华,"花样年华","huayang.png",0.1f,0.5f);//
		addMarker(nc.圆通快递,"圆通快递","yuantong.png",0.1f,0.5f);//
		addMarker(nc.意祥驾校,"意祥驾校","yixiang.png",0.1f,0.5f);//
		addMarker(nc.知发者,"知发者","zhifazhe.png",0.5f,0.1f);//
		addMarker(nc.小春花屋,"小春花屋","xiaochun.png",0.5f,0.1f);//
		addMarker(nc.福伯烧仙草,"福伯烧仙草","fubo.png",0.5f,0.1f);//
		addMarker(nc.中国移动,"中国移动","1.png",0.5f,0.5f);//
		addMarker(nc.万嘉超市,"万嘉超市","1.png",0.5f,0.5f);//
		addMarker(nc.福大洗衣坊,"福大洗衣坊","1.png",0.5f,0.5f);//
		addMarker(nc.赛亚数码城,"赛亚数码城","1.png",0.5f,0.5f);//
		addMarker(nc.睿智眼镜,"睿智眼镜","1.png",0.5f,0.5f);//
		addMarker(nc.速跑,"速跑","1.png",0.5f,0.5f);//
		addMarker(nc.世纪星文体,"世纪星文体","1.png",0.5f,0.5f);//
		addMarker(nc.洵美世家,"洵美世家","1.png",0.5f,0.5f);//
		addMarker(nc.冰临城下,"冰临城下","1.png",0.5f,0.5f);//
		addMarker(nc.圆通速递,"圆通速递","yuantongsu.png",0.9f,0.5f);//
		addMarker(nc.儿画工作室,"儿画工作室","erhua.png",0.9f,0.5f);//
		addMarker(nc.西子正装,"西子正装","xizi.png",0.9f,0.5f);//
		addMarker(nc.悦生活,"悦生活","1.png",0.5f,0.5f);//
		addMarker(nc.京东快递,"京东快递","jindong.png",0.9f,0.5f);//
		addMarker(nc.美阁,"美阁","meige.png",0.9f,0.5f);//
		addMarker(nc.韵达快递,"韵达快递","yunda.png",0.9f,0.5f);//
		addMarker(nc.万嘉超市4,"万嘉超市（4）区","wj4.png",0.9f,0.5f);//
		addMarker(nc.诺曼服饰正装,"诺曼服饰正装","nuoman.png",0.9f,0.5f);//
		addMarker(nc.顺丰快递,"顺丰快递","shunfeng.png",0.9f,0.5f);//
		}
	public void addMarker(LatLng a,String title,String picture,float s1,float s2){
		aMap.addMarker(new MarkerOptions().anchor(s1, s2).icon(BitmapDescriptorFactory.fromAsset(picture))
				.position(a).title(title).setFlat(true)
				.draggable(true));
	}
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		LatLng northeast = new LatLng(30,130 );
		LatLng southwest = new LatLng(20, 100);
		LatLngBounds bounds = new LatLngBounds.Builder().include(northeast).include(mar1)
		                                .include(southwest).build();
	}
	public boolean onMarkerClick(final Marker marker) {
		if(marker.getTitle().equals("电脑之家"))
		markerText.setText("这里是" + marker.getTitle()+"经营一些电脑有关的产品，比如鼠标，以及维修电脑。");
		if(marker.getTitle().equals("千艺美发"))
			markerText.setText("千亿美发，剪发便宜又实惠。");
		if(marker.getTitle().equals("清卟茶园"))
			markerText.setText("清卟茶园，小型聚会的好去处，提供各类牌类游戏哦");
		if(marker.getTitle().equals("花样年华"))
			markerText.setText("干什么的来着？");
		if(marker.getTitle().equals("圆通快递点"))
			markerText.setText("如题咯，圆通快递点。");
		if(marker.getTitle().equals("意祥驾校"))
			markerText.setText("福大周边的一家驾校的报名点。");
		if(marker.getTitle().equals("知发者"))
			markerText.setText("据说有点坑的理发店。");
		if(marker.getTitle().equals("小春花屋"))
			markerText.setText("一家主要出售各类小型绿色盆栽的小店。");
		if(marker.getTitle().equals("中国移动"))
			markerText.setText("福大3区的移动点。");
		if(marker.getTitle().equals("万嘉超市"))
			markerText.setText("福大3区的最大超市。");
		if(marker.getTitle().equals("福大洗衣坊"))
			markerText.setText("福大3区的洗衣坊，洗完帮忙晒干。");
		if(marker.getTitle().equals("赛亚数码城"))
			markerText.setText("福大3区周边一家比较好的数码店。");
		if(marker.getTitle().equals("睿智眼镜"))
			markerText.setText("一家眼镜店。");
		if(marker.getTitle().equals("速跑"))
			markerText.setText("一家主要以出售电动车的店铺，也提供电动车的维修。");
		if(marker.getTitle().equals("世纪星文体"))
			markerText.setText("一家文具店。");
		if(marker.getTitle().equals("洵美世家"))
			markerText.setText("一家衣服店:-)？");
		if(marker.getTitle().equals("韵达快递"))
			markerText.setText("韵达的快递点咯。");
		if(marker.getTitle().equals("冰临城下"))
			markerText.setText("一家冷饮店，类似于清卟茶园。");
		if(marker.getTitle().equals("美阁"))
			markerText.setText("所以说胡说乱编什么的臣妾果然还是做不到的。");
		if(marker.getTitle().equals("京东快递"))
			markerText.setText("京东快递点，如题咯。");
		if(marker.getTitle().equals("悦生活"))
			markerText.setText("嗯..嗯...就(wo)那(bu)样(zhi)吧(dao)。");
		if(marker.getTitle().equals("西子正装"))
			markerText.setText("没去过。。所以就写不出来咯 - - ");
		if(marker.getTitle().equals("儿画工作室"))
			markerText.setText("儿画工作室，那儿似乎还设计明信片？！。");
		if(marker.getTitle().equals("圆通速递"))
			markerText.setText("圆通的快递点咯。");
		if(marker.getTitle().equals("顺丰快递"))
			markerText.setText("顺丰的快递点咯。");
		if(marker.getTitle().equals("万嘉超市（4）区"))
			markerText.setText("万嘉超市的4区店。");
		if(marker.getTitle().equals("诺曼服饰正装"))
			markerText.setText("诺曼服饰。");
		return false;
	}
	private void updateLocation(double latitude, double longtitude) {
        if (mGPSMarker != null) {
            mGPSMarker.setPosition(new LatLng(latitude, longtitude));
        }
 
    }

    
     
    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getAMapException().getErrorCode() == 0) {
            	updateLocation(amapLocation.getLatitude(), amapLocation.getLongitude());
            	 LatLng latLng = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
            	 mm=amapLocation.getLatitude();
            	 nn=amapLocation.getLongitude();
            	 instant_lat=amapLocation.getLatitude();
            	 instant_lng=amapLocation.getLongitude();
            	 
            	 LatLng Fudathree=new LatLng(26.050908,119.19187);
            	 LatLng Fudafour=new LatLng(26.050677,119.190894);
//     			addMarker(26.050908,119.19187,"千艺美发","1.png");//
//            	 四区  119.190894,26.050677
            	 
            	 if(LocationFuda==0)
            	 {
            		 mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            		 if(area==3)
            		 {
            		 aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Fudathree,18));
            		 }
            		 if(area==4)
            		 {
            		 aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Fudafour,18));
            		 }

            		 
            		 showToast("请进入景区再进行定位");
            		
            	 }
            	 
            	 else{

            		 mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                 // 定位成功后把地图移动到当前可视区域内
            	 aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
            	 showToast("您已在目标景区");
            	 }
            }
        }
    }
    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationManagerProxy == null) {
        	mLocationManagerProxy = LocationManagerProxy.getInstance(this);
            //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
            //在定位结束后，在合适的生命周期调用destroy()方法     
            //其中如果间隔时间为-1，则定位只定一次
        	mLocationManagerProxy.requestLocationData(
                    LocationProviderProxy.AMapNetwork, -1, 10, this);
        }
    }
    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationManagerProxy != null) {
        	mLocationManagerProxy.removeUpdates(this);
        	mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }
	
	
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mLocationManagerProxy.removeGeoFenceAlert(mPendingIntent);
		mLocationManagerProxy.removeUpdates(this);
		mLocationManagerProxy.destroy();
		unregisterReceiver(mGeoFenceReceiver);
        mapView.onPause();
        deactivate();
    }
    
	protected void onStart() {
		super.onStart();
	}
	
	protected void onStop() {
		super.onStop();
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        // 销毁定位
       

        AMapNavi.getInstance(this).removeAMapNaviListener(this);

    }
	
	
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}




	
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}


	

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onArriveDestination() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onArrivedWayPoint(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCalculateRouteFailure(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCalculateRouteSuccess() {
		
		AMapNaviPath naviPath = mAMapNavi.getNaviPath();
		if (naviPath == null) {
			return;
		}
		// 获取路径规划线路，显示到地图上
		mRouteOverLay.setRouteInfo(naviPath);
		mRouteOverLay.addToMap();
	}


	@Override
	public void onEndEmulatorNavi() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onGetNavigationText(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onGpsOpenStatus(boolean arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onInitNaviFailure() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onInitNaviSuccess() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onLocationChange(AMapNaviLocation arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNaviInfoUpdate(NaviInfo arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNaviInfoUpdated(AMapNaviInfo arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onReCalculateRouteForTrafficJam() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onReCalculateRouteForYaw() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStartNavi(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTrafficStatusUpdate() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMapClick(LatLng latLng) {
//		mLocationManagerProxy.removeGeoFenceAlert(mPendingIntent);
//		if (mCircle != null) {
//			mCircle.remove();
//		}
//		//地理围栏使用时需要与定位请求方法配合使用
//		// 设置地理围栏，位置、半径、超时时间、处理事件
//		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
//				latLng.longitude, 1000,
//				 1000 * 60 * 30
//				 , mPendingIntent);
//		// 将地理围栏添加到地图上显示
//		CircleOptions circleOptions = new CircleOptions();
//		circleOptions.center(latLng).radius(1000)
//				.fillColor(Color.argb(180, 224, 171, 10))
//				.strokeColor(Color.RED);
//		mCircle = aMap.addCircle(circleOptions);

	}
	

	
	
}


	



