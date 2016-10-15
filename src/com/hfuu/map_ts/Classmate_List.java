package com.hfuu.map_ts;

import com.hfuu.map_ts.view.ArcMenu;
import com.hfuu.map_ts.view.ArcMenu.OnMenuItemClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class Classmate_List extends Activity {
	private ListView classmate_list;
	private ArcMenu mArcMenu;
	private ArrayAdapter<String> arrayAdapter;
	private String[] test_data = {
			"安阳","安阳","安阳","安阳","安阳","安阳","安阳","安阳"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classmate_list_frame);
		classmate_list = (ListView) findViewById(R.id.classmate_listview);
		mArcMenu = (ArcMenu) findViewById(R.id.id_siteMenu);
		//设置adapter 加载Listview
		arrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,test_data);
		classmate_list.setAdapter(arrayAdapter);
		initEvent();
	}
	private void initEvent()
	{
		classmate_list.setOnScrollListener(new OnScrollListener()
		{

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount)
			{
				if (mArcMenu.isOpen())
					mArcMenu.toggleMenu(600);
			}
		});
		
		mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public void onClick(View view, int pos)
			{
				Toast.makeText(Classmate_List.this, (CharSequence)view.getTag(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
