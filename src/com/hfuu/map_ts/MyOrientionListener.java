package com.hfuu.map_ts;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MyOrientionListener implements SensorEventListener {
	
	private SensorManager mSensorManager;
	private Context mContext;
	private Sensor mSensor;
	
	private float lastX;
	
	public MyOrientionListener(Context mContext) {
		this.mContext = mContext;
	}
	
	
	@SuppressWarnings("deprecation")
	public void start(){
		mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		if(mSensorManager!=null){
			//获得方向传感器
			mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		}
		if(mSensor!=null){
			mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_UI);
		}
	}
	
	public void stop(){
		mSensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {//精度改变暂时不管

	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
			float x=event.values[SensorManager.DATA_X];
			//避免特别快的进行更新 与最新的X进行比对
			if(Math.abs(x-lastX)>1.0){//大于1度
				if(mOnOrientationListener!=null){
					mOnOrientationListener.onOrientationChanged(x);
				}
			}
			lastX=x;
		}
	}
	
	private OnOrientationListener mOnOrientationListener;
	
	
	public void setmOnOrientationListener(
			OnOrientationListener mOnOrientationListener) {
		this.mOnOrientationListener = mOnOrientationListener;
	}


	public interface OnOrientationListener{
		void onOrientationChanged(float x);
	}
	

}
