package com.hfuu.map_ts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hfuu.map_ts.view.ArcMenu;
import com.hfuu.map_ts.view.ArcMenu.OnMenuItemClickListener;

public class Location_list extends Activity {
	private ListView loc_list;
	private ArrayAdapter<String> arrayAdapter;
	private ArcMenu mArcMenu;
	private String[] test_data = {
			"安徽合肥","安徽合肥","安徽合肥","安徽合肥","安徽合肥",
			"安徽合肥","安徽合肥","安徽合肥","安徽合肥","安徽合肥","安徽合肥","安徽合肥","安徽合肥","安徽合肥"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_list_frame);
		loc_list = (ListView) findViewById(R.id.loc_listview);
		mArcMenu = (ArcMenu) findViewById(R.id.id_siteMenu);
		//设置adapter 加载Listview
		arrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,test_data);
		loc_list.setAdapter(arrayAdapter);
		initEvent();
	}
	private void initEvent()
	{
		loc_list.setOnScrollListener(new OnScrollListener()
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
				Toast.makeText(Location_list.this, (CharSequence) view.getTag(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
