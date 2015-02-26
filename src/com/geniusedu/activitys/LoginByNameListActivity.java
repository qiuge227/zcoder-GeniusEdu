package com.geniusedu.activitys;

import java.util.ArrayList;
import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.beans.LoginByName;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LoginByNameListActivity extends Activity {
	private ArrayList<BaseEntry> stulist;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.longinbyname_layout);
		BaseEntry baseentry = (BaseEntry) getIntent().getSerializableExtra("baseentry");
		stulist = baseentry.beanslist;
		list = (ListView) findViewById(R.id.studentlist);
		list.setAdapter(new MyAdapter());
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginByNameListActivity.this,LoginActivity.class);
				intent.putExtra("id", ((LoginByName)stulist.get(arg2)).stuid);
				setResult(1111, intent);
				finish();
			}
		});
	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return stulist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return stulist.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = LayoutInflater.from(LoginByNameListActivity.this)
					.inflate(R.layout.studentlist_item, null);
			TextView stunumber = (TextView) convertView
					.findViewById(R.id.stu_number);
			TextView stuname = (TextView) convertView
					.findViewById(R.id.stu_name);
			TextView stuclass = (TextView) convertView
					.findViewById(R.id.stu_class);
			stunumber.setText("Ñ§ºÅ:"+stulist.get(position).stuid);
			stuname.setText("ÐÕÃû:"+stulist.get(position).stuname);
			stuclass.setText("°à¼¶:"+((LoginByName) stulist.get(position))
					.getStuclass());
			return convertView;
		}

	}
}
