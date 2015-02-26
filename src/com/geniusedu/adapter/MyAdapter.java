package com.geniusedu.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geniusedu.activitys.R;
import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.beans.MyListItem;
/**
 * 
 * 
 * 类名称：MyAdapter
 * 类描述：
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-17 上午12:54:43
 * 修改备注：
 * @version 1.0.0
 *
 */
@SuppressLint("ResourceAsColor")
public class MyAdapter extends BaseAdapter {
	
	private Context context;
	private List<BaseEntry> myList;
	
	public MyAdapter(Context context, List<BaseEntry> myList) { 
		this.context = context; 
		this.myList = myList;
	}
	
	public int getCount() {
		return myList.size(); 
	} 
	public Object getItem(int position) {
		return myList.get(position);
	} 
	public long getItemId(int position) {
		return position;
	} 
	
	public View getView(int position, View convertView, ViewGroup parent)
	{ 
		MyListItem myListItem = (MyListItem) myList.get(position); 
		return new MyAdapterView(this.context, myListItem ); 
	}

	class MyAdapterView extends LinearLayout { 
		public static final String LOG_TAG = "MyAdapterView";
		
		public MyAdapterView(Context context, MyListItem myListItem ) { 
		super(context);
		this.setOrientation(HORIZONTAL); 
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, LayoutParams.WRAP_CONTENT); 
		params.setMargins(10,10, 10,10); 
		
		TextView name = new TextView(context); 
		name.setText( myListItem.getName() ); 
		name.setTextColor(R.color.text1);
		name.setTextSize(17.0f);
		addView( name, params); 
		
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(200, LayoutParams.WRAP_CONTENT); 
		params2.setMargins(10, 10, 10, 10); 
		
		TextView pcode = new TextView(context); 
		pcode.setText(myListItem.getPcode()); 
		addView( pcode, params2); 
		pcode.setVisibility(GONE);

		}		 

		}

}