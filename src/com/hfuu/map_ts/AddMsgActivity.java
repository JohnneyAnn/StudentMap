package com.hfuu.map_ts;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.hfuu.map_ts.users.MyProtocal;

public class AddMsgActivity extends Activity implements OnClickListener{
	private Context context;
	private EditText edit_title;
	private EditText edit_ctx;
	private Button btd_add;
	private String tno;
	private MyHandler myHandler = new MyHandler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.add_msg);
		this.context=this;
		Intent intent = getIntent();
        tno = intent.getStringExtra("tno");
		initView();
	}
	private void initView() {
		edit_title = (EditText) findViewById(R.id.edit_msg_title);
		edit_ctx = (EditText) findViewById(R.id.edit_msg_ctx);
		btd_add = (Button) findViewById(R.id.btn_add_msg);
		btd_add.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_add_msg){
			new Thread() {
				public void run() {
					String httpURL = MyProtocal.URL;
					URL url = null;
					try {
						url = new URL(httpURL);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
					if (url != null) {
						try {
							HttpURLConnection conn = (HttpURLConnection) url
									.openConnection();
							conn.setRequestMethod("POST");
							conn.setUseCaches(false);
							conn.connect();
							Log.i("log","connect");
							DataOutputStream dos = new DataOutputStream(
									conn.getOutputStream());
							dos.writeInt(MyProtocal.ADD_MSG);
							dos.writeUTF(tno);
							dos.writeUTF(edit_title.getText().toString());
							dos.writeUTF(edit_ctx.getText().toString());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
							String time = sdf.format(new Date());
							Log.i("time:",time);
							dos.writeUTF(time);
							long createTime = System.currentTimeMillis();
							Log.i("createTime:",createTime+"");
							dos.writeLong(createTime);
							dos.flush();
							dos.close();
							DataInputStream dis = new DataInputStream(
									conn.getInputStream());
							int b= dis.readInt();
							if(b==MyProtocal.OK){
								Message msg = Message.obtain();
								msg.obj = "发布成功！(^＿^)";
								myHandler.sendMessage(msg);
							}else{
								Message msg = Message.obtain();
								msg.obj = "发布失败！(┬＿┬)";
								myHandler.sendMessage(msg);
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
		}
	}
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			System.out.println(msg.obj.toString());
			Builder builder = new Builder(context);
			builder.setTitle("最近通知");
			builder.setMessage((String)msg.obj);
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					edit_title.setText("");
					edit_ctx.setText("");
				}
			});
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
			super.handleMessage(msg);
		}
		
	}
}
