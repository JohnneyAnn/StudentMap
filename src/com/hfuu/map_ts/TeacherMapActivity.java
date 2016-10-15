package com.hfuu.map_ts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hfuu.map_ts.MyOrientionListener.OnOrientationListener;
import com.hfuu.map_ts.users.Student;


public class TeacherMapActivity extends Activity {
	//private String tbianhao=null;
	
	private MapView mMapView; 
	private BaiduMap mBaiduMap;
	private Context context;
	private TextView mTextView;
	//定位相关
	private LocationClient mLocationClient;
	private MyLocationListener myLocationListener;
	private boolean isFirstIn = true;
	private double mLatitude;
	private double mLongitude;
	//自定义定位图标
	private BitmapDescriptor mIconLocation;
	private MyOrientionListener mOrientionListener;
	private float mCurrentX;
	private LocationMode mLocationMode;
	//跳转到列表
	private Button btnToList;
	//获取学生位置并定位
	private BitmapDescriptor mMarker;
	private List<Student> students=new ArrayList<Student>();
	//将学生位置插入到地图
	private void initStudentLocation(){
		students.add(new Student("安阳",31.768989,117.246873));//纬度，经度
		students.add(new Student("陶华旭",31.763832,117.262648));
		students.add(new Student("程思铭",31.762051,117.254168));
		//以上的以后用服务器获取
		mMarker=BitmapDescriptorFactory.fromResource(R.drawable.maker);
		mBaiduMap.clear();//清除所有的Overlay
		LatLng latLng=null;
		Marker marker = null;
		OverlayOptions options;
		
		for(Student student:students){
			//经纬度
			latLng = new LatLng(student.getLatitude(), student.getLongitude());
			//图标
			options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
			marker = (Marker) mBaiduMap.addOverlay(options);
			
			
			Bundle bundle = new Bundle();
			bundle.putSerializable("student", student);
			marker.setExtraInfo(bundle);
		}
		//把地图移到最后一个marker的地址
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(msu);
		
		Toast.makeText(context, "学生位置导入成功！", Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext()); 
        setContentView(R.layout.teacher_map); 
        this.context=this;
        initView();
        //初始化定位
      	initLocation();
      //调用加载学生位置图片
      	initStudentLocation();
      	mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				Bundle extraInfo = marker.getExtraInfo();
				Student student = (Student) extraInfo.getSerializable("student");
				//图标上的文字
				InfoWindow infoWindow;
				TextView tv = new TextView(context);
				tv.setBackgroundResource(R.drawable.location_tips);
				tv.setPadding(30, 20, 30, 50);
				tv.setText(student.getName());
				tv.setTextColor(Color.parseColor("#ffffff"));
				final LatLng latLng = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(latLng);//将经纬度转化成屏幕地图上的点
				
				LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);//在将点转化为经纬度
				BitmapDescriptor tvBD = BitmapDescriptorFactory.fromView(tv);
				infoWindow = new InfoWindow(tvBD, ll, -47, new InfoWindow.OnInfoWindowClickListener(){
					@Override
					public void onInfoWindowClick(){
						mBaiduMap.hideInfoWindow();
					}
				});
					mBaiduMap.showInfoWindow(infoWindow);
					return false;
			}
		});
      	
	}
	private void initLocation() {
		mLocationMode = LocationMode.NORMAL;
		mLocationClient = new LocationClient(this);
		myLocationListener = new MyLocationListener();
		//对监听器进行注册
		mLocationClient.registerLocationListener(myLocationListener);
		//对LocationClient进行一些配置
		LocationClientOption option = new LocationClientOption();
		//坐标类型
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);//获得地址必做的事
		option.setOpenGps(true);//打开GPS
		//隔多少秒进行一次请求
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		//初始化定位图标
		mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
		                                                                                                                                            
		mOrientionListener = new MyOrientionListener(context);
		mOrientionListener.setmOnOrientationListener(new OnOrientationListener() {
			
			@Override
			public void onOrientationChanged(float x) {
				mCurrentX=x;
			}
		});
	}
	private void initView() {
		//Intent intent = getIntent();
		//tbianhao = intent.getStringExtra("sno");
		mMapView=(MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		//设置刚进去的地图放大比例
		MapStatusUpdate msu = 
				MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
		mTextView = (TextView) findViewById(R.id.id_textView);
		btnToList = (Button) findViewById(R.id.btn_list);
		//跳转到列表
		btnToList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, Location_list.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.id_map_common:
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				break;
			case R.id.id_map_site:
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				break;
			case R.id.id_map_traffic:
				//检测实时交通是否打开 默认为off
				if(mBaiduMap.isTrafficEnabled()){
					mBaiduMap.setTrafficEnabled(false);
					item.setTitle("实时交通(off)");
				}else{
					mBaiduMap.setTrafficEnabled(true);
					item.setTitle("实时交通(on)");
				}
				break;
			case R.id.id_map_location:
				centerToMyLocation();
				break;
			//模式切换
			case R.id.id_map_mode_common:
				mLocationMode=LocationMode.NORMAL;
				break;
			case R.id.id_map_mode_following:
				mLocationMode=LocationMode.FOLLOWING;
				break;
			case R.id.id_map_mode_compass:
				mLocationMode=LocationMode.COMPASS;
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onResume() {
		super.onResume();
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
	}
	@Override
	protected void onStart() {
		super.onStart();
		//开启定位
		mBaiduMap.setMyLocationEnabled(true);
		if(!mLocationClient.isStarted()){//判断定位是否启动
			mLocationClient.start();
			//开启方向传感器
			mOrientionListener.start();
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		//停止定位
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		//停止方向传感器
		mOrientionListener.stop();
	}
	@Override
	protected void onPause() {
		super.onPause();
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		 //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
	}//定位到我的位置
	private void centerToMyLocation() {
		LatLng latLng = new LatLng(mLatitude,mLongitude);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.animateMapStatus(msu);//地图的位置使用动画的效果
	}
	
	private class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			MyLocationData data = new MyLocationData.Builder()//build模式 建一个Builder内部类
				.direction(mCurrentX)//方向
				.accuracy(location.getRadius())//精度
				.latitude(location.getLatitude())//
				.longitude(location.getLongitude())//
				.build();
			mBaiduMap.setMyLocationData(data);
			//设置自定义图标
			MyLocationConfiguration config = new MyLocationConfiguration(
					mLocationMode,true,mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);
			//更新经纬度
			mLatitude = location.getLatitude();
			mLongitude = location.getLongitude();
			if (isFirstIn)
			{
				centerToMyLocation();
				isFirstIn = false;
				// 弹出对话框显示定位信息；
				Builder builder = new Builder(context);
				builder.setTitle("为您获得的定位信息：");
				builder.setMessage("当前位置：" + location.getAddrStr() + "\n"
						+ "城市编号：" + location.getCityCode() + "\n" + "定位时间："
						+ location.getTime() + "\n" + "当前纬度："
						+ mLatitude + "\n" + "当前经度："
						+ mLongitude);
				builder.setPositiveButton("确定", null);
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
				mTextView.setText(location.getAddrStr());
			}
		}
		
	}
	
}
