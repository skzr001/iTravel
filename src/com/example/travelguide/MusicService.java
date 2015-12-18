package com.example.travelguide;

import android.content.Context;
import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;
import android.content.ComponentName;
import android.content.ServiceConnection;


public class MusicService extends Service {

	public int flag=0;
	IBinder musicBinder  = new MyBinder();
	protected MediaPlayer mediaPlayer;
	
	//获取到activity的Handler，用来通知更新进度条
	Handler mHandler; 
	
	//播放音乐的媒体类
//	MediaPlayer mediaPlayer;
	
	//本地歌曲的路径
	
	
    String path="/res/raw/music.mp3";
//    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.music);
    
    
    private String TAG = "MyService";
	
	@Override  	
    public void onCreate() {  
        super.onCreate();  
        Log.d(TAG, "onCreate() executed");  
        
        
//        init();
//        mediaPlayer = new MediaPlayer();
		try {
			//初始化
//			mediaPlayer.setDataSource(path);//删掉
			
			
			mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.music);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
			
			// prepare 通过异步的方式装载媒体资源
			mediaPlayer.prepareAsync();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }  


	public void setId(int Id){

		if(Id==1) mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.music);
//		else if(Id==2) mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.music2);

		
			
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
	    //当绑定后，返回一个musicBinder
//		Bundle bundle = intent.getExtras(); 
//	    int numVal = bundle.getInt("testnum2");  //用于接收int类型数据
//	    if(numVal==1) mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.music);
		return musicBinder;
	}
	
	class MyBinder extends Binder{
		
		public Service getService(){
			return MusicService.this;
		}
//		public boolean onTransact (int code,Parcel data, Parcel reply,int flags){
//			return handleTransactions(code,data,reply,flags);
//		}
	}
	
	//初始化音乐播放
//	void init(){
//		//进入Idle
//		mediaPlayer = new MediaPlayer();
//		try {
//			//初始化
////			mediaPlayer.setDataSource(path);
//			mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.music);
//			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
//			
//			// prepare 通过异步的方式装载媒体资源
//			mediaPlayer.prepareAsync();
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	//返回当前的播放进度，是double类型，即播放的百分比
	public double getProgress(){
		int position = mediaPlayer.getCurrentPosition();  
        
        int time = mediaPlayer.getDuration();  
        
        double progress = (double)position / (double)time;
        
        return progress;  
	}
	
	//通过activity调节播放进度
	public void setProgress(int max , int dest){	
		int time = mediaPlayer.getDuration();
		mediaPlayer.seekTo(time*dest/max);
	}
	
	//测试播放音乐
	public void play(){		
		if(mediaPlayer != null){
			mediaPlayer.start();
		}
			
	}
	//暂停音乐   
    public void pause() {  
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {  
            mediaPlayer.pause();  
        }  
    }  
    
  //service 销毁时，停止播放音乐，释放资源
  	@Override
  	public void onDestroy() {
	     // 在activity结束的时候回收资源
	     if (mediaPlayer != null && mediaPlayer.isPlaying()) {
	         mediaPlayer.stop();
	         mediaPlayer.release();
	         mediaPlayer = null;
	     }
	     super.onDestroy();
     }
}