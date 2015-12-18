package com.example.travelguide;

import com.example.travelguide.MusicService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.travelguide.until.ToastUtil;

import android.widget.Toast;



public class MusicActivity extends Activity {

	Boolean mBound = false;
	
	MusicService mService;
	
	SeekBar seekBar;
	
	//多线程，后台更新UI
	Thread myThread;
	
	//控制后台线程退出
	boolean playStatus = true;
	
	
	//处理进度条更新
	Handler mHandler = new Handler(){
		@Override  
        public void handleMessage(Message msg){  
			switch (msg.what){
				case 0:
					//从bundle中获取进度，是double类型，播放的百分比
					double progress = msg.getData().getDouble("progress");
					
					//根据播放百分比，计算seekbar的实际位置 
		            int max = seekBar.getMax();  
		            int position = (int) (max*progress);
		            
		            //设置seekbar的实际位置
		            seekBar.setProgress(position);  
		            break;
		        default:
		        	break;
			}
            
        }  
	};
	private void showToast(String message) {
		ToastUtil.show(MusicActivity.this, message);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		
		//定义一个新线程，用来发送消息，通知更新UI
		myThread = new Thread(new MyThread());
		
		
		//绑定service;
		Intent serviceIntent = new Intent(this , MusicService.class);
		
		//如果未绑定，则进行绑定
		if(!mBound){
			bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
		}	
		
		Intent intent_audio_num = getIntent();
		Bundle bundle_audio_num = intent_audio_num.getExtras();  
		int audio_num=bundle_audio_num.getInt("num");
		if(audio_num==1){
			Intent intentTest=new Intent();
			intentTest.putExtra("num", 1);
		}

//		Intent service_num=new Intent();
//		

//		for(int i=1;i<26;i++){
//			if(audio_num==i){
//				Bundle mbundle = new Bundle();
//				mbundle.putInt("musicNum", i);
//				serviceIntent.putExtras(mbundle);
//			}

//		}	
//		}		

		//初始化播放按钮
		Button playButton = (Button)findViewById(R.id.playButton);
		playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				if(mBound){
					mService.setId(2);
					mService.play();
				}			
			}
			
		});
		
		//初始化暂停按钮
		Button pauseButton = (Button)findViewById(R.id.pauseButton);	
		pauseButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//首先需要判定绑定情况
				if(mBound){
					mService.pause();
				}
			}
		});
		
	    seekBar = (SeekBar)findViewById(R.id.seekbar);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			//手动调节进度
				// TODO Auto-generated method stub
				//seekbar的拖动位置
				int dest = seekBar.getProgress();
				//seekbar的最大值
				int max = seekBar.getMax();
				//调用service调节播放进度
				mService.setProgress(max, dest);
			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});		
				
	}
	
	//实现runnable接口，多线程实时更新进度条
	public class MyThread implements Runnable{
		
	
		//通知UI更新的消息
		
		
		//用来向UI线程传递进度的值
		Bundle data = new Bundle();

		//更新UI间隔时间
		int milliseconds = 100;
		double progress;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			//用来标	识是否还在播放状态，用来控制线程退出
			while(playStatus){  
				
                try {  
                	//绑定成功才能开始更新UI
                    if(mBound){
                    	
                    	//发送消息，要求更新UI
                    	
                    	Message msg = new Message();
                    	data.clear();
             	
                    	progress = mService.getProgress();
            			msg.what = 0;
            			
            			data.putDouble("progress", progress);
            			msg.setData(data);
            			mHandler.sendMessage(msg);
                    }
                    Thread.sleep(milliseconds);  
        			//Thread.currentThread().sleep(milliseconds);  
					//每隔100ms更新一次UI
        			
                } catch (InterruptedException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
			
			}
		}
		
	}
	
	 /** Defines callbacks for service binding, passed to bindService() */  
    private ServiceConnection mConnection = new ServiceConnection() {  
  
        @Override  
        public void onServiceConnected(ComponentName className,  
                IBinder binder) {  
            // We've bound to LocalService, cast the IBinder and get LocalService instance  
        	MyBinder myBinder = (MyBinder) binder;
            
        	//获取service
        	mService = (MusicService) myBinder.getService();  
            
            //绑定成功
            mBound = true;  
            
            //开启线程，更新UI
            myThread.start();
        }  
  
        @Override  
        public void onServiceDisconnected(ComponentName arg0) {  
            mBound = false;  
        }  
    };  
	   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onDestroy(){
		//销毁activity时，要记得销毁线程
		playStatus = false;
		super.onDestroy();
	}

}