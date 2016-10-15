package com.hfuu.map_ts;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.hfuu.map_ts.users.MyProtocal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StudentRegisterActivity extends Activity implements OnClickListener,OnCheckedChangeListener{
	private Context context;
	private TextView test_text;
	private EditText edit_sno;
	private EditText edit_pwd;
	private EditText edit_name;
	private EditText edit_tel;
	private Button btn_reg;
	private RadioGroup genderGroup; 
    private RadioButton rbtn_boy;
    private RadioButton rbtn_girl; 
    private Spinner classSpinner; 
    String[] classData={"13通信(1)班"}; 
    private Spinner teacherSpinner; 
    String[] teacherData={"查长军"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_register);
		this.context=this;
		//initData();//导入数据
		initView();
		test_text=(TextView) findViewById(R.id.reg_info);
		Intent intent = getIntent();
		String sno = intent.getStringExtra("sno");
		String password = intent.getStringExtra("password");
		test_text.setText(test_text.getText()+sno);
		edit_sno.setText(sno);
		edit_pwd.setText(password);
	}
//	private void initData(){
//		new Thread() {
//			public void run() {
//				String httpURL = "http://115.159.118.230:8080/map/MapServerHttpServlet";
//				URL url = null;
//				try {
//					url = new URL(httpURL);
//				} catch (MalformedURLException e1) {
//					e1.printStackTrace();
//				}
//				if (url != null) {
//					try {
//						HttpURLConnection conn = (HttpURLConnection) url
//								.openConnection();
//						conn.setRequestMethod("POST");
//						conn.setUseCaches(false);
//						conn.connect();
//						DataOutputStream dos = new DataOutputStream(
//								conn.getOutputStream());
//						dos.writeInt(MyProtocal.GET_REG_DATA);
//						dos.flush();
//						dos.close();
//
//						DataInputStream dis = new DataInputStream(
//								conn.getInputStream());
//						classData= dis.readUTF().split(",");
//						teacherData=dis.readUTF().split(",");
//						dis.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				} else {
//					Toast.makeText(context, "url is null",
//							Toast.LENGTH_SHORT).show();
//				}
//			}
//		}.start();
//	}
	private void initView() {
		edit_sno = (EditText) findViewById(R.id.edit_sno_r);
		edit_pwd = (EditText) findViewById(R.id.edit_pwd_r);
		edit_name = (EditText) findViewById(R.id.edit_name);
		edit_tel = (EditText) findViewById(R.id.edit_tel);
		btn_reg = (Button) findViewById(R.id.btn_reg_r);
		btn_reg.setOnClickListener(this);
		genderGroup=(RadioGroup)findViewById(R.id.gender); 
		rbtn_boy=(RadioButton)findViewById(R.id.gender_boy); 
		rbtn_girl=(RadioButton)findViewById(R.id.gender_girl); 
		genderGroup.setOnCheckedChangeListener(this);
		classSpinner= (Spinner) this.findViewById(R.id.class_spinner); 
		classSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,classData));
		classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
		      @Override
		      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { 
		    	  
		      } 
		      @Override
		      public void onNothingSelected(AdapterView<?> parent) { 
		    	  
		      } 
		    }); 
		teacherSpinner= (Spinner) this.findViewById(R.id.teacher_spinner); 
		teacherSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,teacherData));
		teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
		      @Override
		      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { 
		    	  
		      } 
		      @Override
		      public void onNothingSelected(AdapterView<?> parent) { 
		    	  
		      } 
		    }); 
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_reg_r){
			Toast.makeText(context, "注册成功，请返回登录!",
					Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(checkedId==rbtn_boy.getId()){ 
			Toast.makeText(context, "男生",
					Toast.LENGTH_SHORT).show();
        }else if(checkedId==rbtn_girl.getId()){ 
        	Toast.makeText(context, "女生",
					Toast.LENGTH_SHORT).show();
        } 
		
	}
}
