package com.hfuu.map_ts;

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

public class RegisterActivity extends Activity implements OnClickListener,OnCheckedChangeListener{
	private Context context;
	private TextView test_text;
	private EditText edit_sno;
	private EditText edit_pwd;
	private EditText edit_name;
	private Button btn_reg;
	private RadioGroup genderGroup; 
    private RadioButton rbtn_boy;
    private RadioButton rbtn_girl; 
    private Spinner spinner; 
    String[] data=new String[]{"13通信(1)班","13通信(2)班","13电子(1)班"}; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		this.context=this;
		initView();
		test_text=(TextView) findViewById(R.id.reg_info);
		Intent intent = getIntent();
		String sno = intent.getStringExtra("sno");
		String password = intent.getStringExtra("password");
		test_text.setText(test_text.getText()+sno);
		edit_sno.setText(sno);
		edit_pwd.setText(password);
	}
	private void initView() {
		edit_sno = (EditText) findViewById(R.id.edit_sno_r);
		edit_pwd = (EditText) findViewById(R.id.edit_pwd_r);
		edit_name = (EditText) findViewById(R.id.edit_name);
		btn_reg = (Button) findViewById(R.id.btn_reg_r);
		btn_reg.setOnClickListener(this);
		genderGroup=(RadioGroup)findViewById(R.id.gender); 
		rbtn_boy=(RadioButton)findViewById(R.id.gender_boy); 
		rbtn_girl=(RadioButton)findViewById(R.id.gender_girl); 
		genderGroup.setOnCheckedChangeListener(this);
		spinner= (Spinner) this.findViewById(R.id.spinner); 
		spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));
		
		//new ArrayAdapter<String>(this,R.layout.spinne_item,data)
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
		      @Override
		      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { 
		    	  edit_name.setText(data[position]); 
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
