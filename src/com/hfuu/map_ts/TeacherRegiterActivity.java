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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherRegiterActivity extends Activity implements OnClickListener,OnCheckedChangeListener{
	private Context context;
	private TextView test_text;
	private EditText edit_tno;
	private EditText edit_pwd;
	private EditText edit_tname;
	private EditText edit_tel;
	private Button btn_reg;
	private RadioGroup genderGroup; 
    private RadioButton rbtn_male;
    private RadioButton rbtn_female; 
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_register);
		this.context=this;
		initView();
		test_text=(TextView) findViewById(R.id.reg_info_teacher);
		Intent intent = getIntent();
		String tno = intent.getStringExtra("sno");
		String password = intent.getStringExtra("password");
		test_text.setText(test_text.getText()+tno);
		edit_tno.setText(tno);
		edit_pwd.setText(password);
	}
	private void initView() {
		edit_tno = (EditText) findViewById(R.id.edit_tno_r);
		edit_pwd = (EditText) findViewById(R.id.edit_pwd_r_teacher);
		edit_tname = (EditText) findViewById(R.id.edit_tname);
		edit_tel = (EditText) findViewById(R.id.edit_tel_teacher);
		btn_reg = (Button) findViewById(R.id.btn_reg_r_teacher);
		btn_reg.setOnClickListener(this);
		genderGroup=(RadioGroup)findViewById(R.id.gender_teacher); 
		rbtn_male=(RadioButton)findViewById(R.id.gender_male); 
		rbtn_female=(RadioButton)findViewById(R.id.gender_female); 
		genderGroup.setOnCheckedChangeListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_reg_r_teacher){
			Toast.makeText(context, "注册成功，请返回登录!",
					Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(checkedId==rbtn_male.getId()){ 
			Toast.makeText(context, "男性",
					Toast.LENGTH_SHORT).show();
        }else if(checkedId==rbtn_female.getId()){ 
        	Toast.makeText(context, "女性",
					Toast.LENGTH_SHORT).show();
        } 
		
	}
}
