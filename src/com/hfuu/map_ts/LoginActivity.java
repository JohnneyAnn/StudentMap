package com.hfuu.map_ts;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.hfuu.map_ts.users.MyProtocal;


public class LoginActivity extends Activity implements OnClickListener,OnCheckedChangeListener{
	private Context context;
	private EditText pwdEditText ;
	private EditText snoEditText ;
	private Button bt_register;
	private Button bt_login;
	private RadioGroup idGroup; 
    private RadioButton rbtn_teacher;
    private RadioButton rbtn_student; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_login);
		this.context=this;
		initView();
		
	}
	private void initView() {
		Log.i("tag", "init");
		snoEditText=(EditText) findViewById(R.id.edit_sno);
		pwdEditText=(EditText) findViewById(R.id.text_password);
		bt_register=(Button) findViewById(R.id.register);
		bt_login=(Button) findViewById(R.id.login);
		bt_register.setOnClickListener(this);
		bt_login.setOnClickListener(this);
		idGroup=(RadioGroup)findViewById(R.id.identification); 
		rbtn_teacher=(RadioButton)findViewById(R.id.teacher); 
		rbtn_student=(RadioButton)findViewById(R.id.student); 
		idGroup.setOnCheckedChangeListener(this);
	}
	//测试用 用于选择了什么身份注册
	int id;
	@Override
	//RadioBotton监听
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		id=checkedId;
		if(checkedId==rbtn_teacher.getId()){ 
			Toast.makeText(context, "老师",
					Toast.LENGTH_SHORT).show();
        }else if(checkedId==rbtn_student.getId()){ 
        	Toast.makeText(context, "学生",
					Toast.LENGTH_SHORT).show();
        } 
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.register:
				if(id==rbtn_teacher.getId()){ 
					Intent intent = new Intent(context, TeacherRegiterActivity.class);
					intent.putExtra("sno", snoEditText.getText().toString());
					intent.putExtra("password", pwdEditText.getText().toString());
					startActivity(intent);
		        }else if(id==rbtn_student.getId()){ 
		        	Intent intent = new Intent(context, StudentRegisterActivity.class);
					intent.putExtra("sno", snoEditText.getText().toString());
					intent.putExtra("password", pwdEditText.getText().toString());
					startActivity(intent);
		        } else {
		        	Toast.makeText(context, "请选择你的身份！",
							Toast.LENGTH_SHORT).show();
		        }
				break;
			case R.id.login:
				if(id==rbtn_teacher.getId()){ 
					new Thread() {
						public void run() {
							String httpURL = "http://115.159.118.230:8080/map/map";
							URL url = null;
							try {
								url = new URL(httpURL);
							} catch (MalformedURLException e1) {
								e1.printStackTrace();//
							}
							if (url != null) {
								try {
									HttpURLConnection conn = (HttpURLConnection) url
											.openConnection();
									conn.setRequestMethod("POST");
									conn.setUseCaches(false);
									conn.connect();
									DataOutputStream dos = new DataOutputStream(
											conn.getOutputStream());
									dos.writeInt(MyProtocal.LOGIN);
									dos.writeInt(MyProtocal.ID_TEACHER);
									dos.writeInt(Integer.parseInt(snoEditText.getText().toString()));
									dos.writeUTF(pwdEditText.getText().toString());
									dos.flush();
									dos.close();
									DataInputStream dis = new DataInputStream(
											conn.getInputStream());
									int b= dis.readInt();
									if(b==MyProtocal.OK){
										Intent intent1 = new Intent(context, TeacherMapActivity.class);
										startActivity(intent1);
									}
									dis.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								Toast.makeText(context, "url is null",
										Toast.LENGTH_SHORT).show();
							}
						}
					}.start();	
		        }else if(id==rbtn_student.getId()){ 
		        	new Thread() {
		    			public void run() {
		    				Log.i("tag", "url");
		    				String httpURL = "http://115.159.118.230:8080/map/map";
		    				URL url = null;
		    				try {
		    					url = new URL(httpURL);
		    				} catch (MalformedURLException e1) {
		    					e1.printStackTrace();
		    				}
		    				if (url != null) {
		    					try {
		    						HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		    						conn.setRequestMethod("POST");
		    						conn.setUseCaches(false);
		    						conn.connect();
		    						Log.i("tag", "conn");
		    						DataOutputStream dos = new DataOutputStream(
		    								conn.getOutputStream());
		    						Log.i("tag", "dos");
		    						dos.writeInt(MyProtocal.LOGIN);
		    						dos.writeInt(MyProtocal.ID_STUDENT);
		    						dos.writeInt(Integer.parseInt(snoEditText.getText().toString()));
		    						dos.writeUTF(pwdEditText.getText().toString());
		    						dos.flush();
		    						dos.close();
		    						Log.i("tag", "vloser");
		    						DataInputStream dis = new DataInputStream(conn.getInputStream());
		    						Log.i("tag", "dis");
		    						int b= dis.readInt();
		    						Log.i("tag", "read");
		    						if(b==MyProtocal.OK){
		    							Log.i("tag", "readed");
		    							Intent intent1 = new Intent(context, StudentMapActivity.class);
		    							startActivity(intent1);
		    						}
		    						dis.close();
		    					} catch (IOException e) {
		    						e.printStackTrace();
		    					}
		    				} else {
		    					Toast.makeText(context, "url is null",
		    							Toast.LENGTH_SHORT).show();
		    				}
		    			}
		    		}.start();	
		        }else {
		        	Toast.makeText(context, "请选择你的身份！",
							Toast.LENGTH_SHORT).show();
		        }
				break;
		}
	}
}
