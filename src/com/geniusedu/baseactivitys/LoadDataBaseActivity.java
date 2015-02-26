package com.geniusedu.baseactivitys;

import java.util.ArrayList;

import com.geniusedu.basebeans.BaseEntry;
import android.app.Activity;
import android.os.Bundle;
/**
 * 
 * 
 * 类名称：LoadDataBaseActivity
 * 类描述：基础activity，实现请求和加载服务器数据
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-5 下午9:55:21
 * 修改备注：
 * @version 1.0.0
 *
 */
public class LoadDataBaseActivity extends Activity {
	//实体类结合
	public ArrayList<BaseEntry> beanslist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
}
