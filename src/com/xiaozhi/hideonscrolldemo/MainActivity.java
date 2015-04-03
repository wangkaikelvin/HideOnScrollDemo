package com.xiaozhi.hideonscrolldemo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaozhi.hideonscrolldemo.listener.HidingScrollListener;
import com.xiaozhi.hideonscrolldemo.view.HidingListView;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity";

	private Toolbar mToolbar;
	private HidingListView mListView;
	private MyAdapter mMyAdapter;
	private List<String> mList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
	}

	private void initViews(){
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setTitle("Xiao");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mListView = (HidingListView) findViewById(R.id.listView);
		getData();
		mMyAdapter = new MyAdapter();
		mListView.setAdapter(mMyAdapter);
		mListView.setScrollListener(new HidingScrollListener() {

			@Override
			public void onShow() {
				showToolbar();
			}

			@Override
			public void onHide() {
				hideToolbar();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void hideToolbar(){
		mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
	}

	private void showToolbar(){
		mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
	}

	private void getData(){
		for (int i = 0; i < 10; i++) {
			mList.add("Item " + i);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class MyAdapter extends BaseAdapter{

		private static final int TYPE_HEAD = 0;
		private static final int TYPE_ITEM = 1;

		@Override
		public int getCount() {
			return mList.size() + 1;
		}

		@Override
		public String getItem(int position) {
			return mList.get(position - 1);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			if (position == 0) {
				return TYPE_HEAD;
			}
			return TYPE_ITEM;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (getItemViewType(position) == TYPE_HEAD) {
				convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_header, parent,false);
			}else {

				ViewHolder holder = new ViewHolder();
				convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.listview_item, parent,false);
				holder.textView = (TextView) convertView.findViewById(R.id.itemTextView);
				convertView.setTag(holder);

				holder.textView.setText(getItem(position));
			}

			return convertView;
		}
	}

	private class ViewHolder {
		TextView textView;
	}
}
