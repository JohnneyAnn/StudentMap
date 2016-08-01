package com.hfuu.map_ts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.mapapi.SDKInitializer;

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
	@Override
	//RadioBotton监听
	public void onCheckedChanged(RadioGroup group, int checkedId) {
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
				Intent intent = new Intent(context, RegisterActivity.class);
				intent.putExtra("sno", snoEditText.getText().toString());
				intent.putExtra("password", pwdEditText.getText().toString());
				startActivity(intent);
				break;
			case R.id.login:
				Intent intent1 = new Intent(context, BaiduMapActivity.class);
				startActivity(intent1);
				break;
		}
	}
}
