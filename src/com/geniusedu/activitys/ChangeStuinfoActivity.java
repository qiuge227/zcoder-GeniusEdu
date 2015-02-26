package com.geniusedu.activitys;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.geniusedu.adapter.MyAdapter;
import com.geniusedu.app.CatchApplication;
import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.beans.MyListItem;
import com.geniusedu.db.DBManager;
import com.geniusedu.logical.implement.ImRequestData;
import com.geniusedu.utl.GetPicture;
import com.geniusedu.utl.PreferenceUtils;

/**
 * 
 * 
 * 类名称：ChangeStuinfoActivity 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-21 下午2:28:19 修改备注：
 * 
 * @version 1.0.0
 * 
 */
public class ChangeStuinfoActivity extends Activity {
	private DBManager dbm;
	private SQLiteDatabase db;
	private Spinner spinner1 = null;
	private Spinner spinner2 = null;
	private Spinner spinner3 = null;
	private Spinner spinner4 = null;
	private String province = null;
	private String city = null;
	private String cityutf;
	private String provinceutf;
	private String schoolname = null;
	private String schoolid = null;// 修改后学校的id
	private String stuclass = null;
	private String Ustuclass; // 转码utf-8的班级名字
	private Button showselect;
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changestuinfo_layout);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		spinner4 = (Spinner) findViewById(R.id.spinner4);
		showselect = (Button) findViewById(R.id.showselect);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		spinner1.setPrompt("请选择省份");
		spinner2.setPrompt("请选择所在市");
		spinner3.setPrompt("请选择学校");
		spinner4.setPrompt("请选择班级");
		initSpinner1();
		showselect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!schoolname.equals("请选择学校") && !stuclass.equals("请选择班级")
						&& !province.equals("请选择省份") && !city.equals("请选择所在市")) {
					try {
						Ustuclass = URLEncoder.encode(stuclass, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setStuinfo(CatchApplication.stuid,
							CatchApplication.schoolid, schoolid, Ustuclass);
				} else {
					Toast.makeText(ChangeStuinfoActivity.this,
							"有选项卡未选择，信息修改失败", 1000).show();
				}
			}
		});

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.overridePendingTransition(R.anim.flip_horizontal_in,
				R.anim.flip_horizontal_out);
		super.onPause();
	}

	/**
	 * 
	 * setStuinfo(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param param
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void setStuinfo(String... param) {
		new ImRequestData(ChangeStuinfoActivity.this) {

			@Override
			public void getResult(String result) {
				// TODO Auto-generated method stub
				if (result != null) {
					if (result.equals("1")) {
						Intent intent = new Intent();
						intent.setAction("stuinfo_change");
						intent.putExtra("stu_class", stuclass);
						sendBroadcast(intent);
						Toast.makeText(ChangeStuinfoActivity.this, "修改成功", 1000)
								.show();
						CatchApplication.schoolid = schoolid;
						CatchApplication.schoolname = schoolname;
						PreferenceUtils.setPrefString(
								ChangeStuinfoActivity.this, "schoolname",
								schoolname);
						PreferenceUtils.setPrefString(
								ChangeStuinfoActivity.this, "schoolid",
								schoolid);
						finish();

					} else if (result.equals("2")) {
						Toast.makeText(ChangeStuinfoActivity.this, "修改失败", 1000)
								.show();
					} else {
						Toast.makeText(ChangeStuinfoActivity.this, "服务器数据连接异常",
								1000).show();
					}
				} else {
					Toast.makeText(ChangeStuinfoActivity.this, "解析异常", 1000)
							.show();
				}
			}

			@Override
			public void getBaseentry(BaseEntry baseentry) {
				// TODO Auto-generated method stub

			}
		}.getDataByNet(R.string.interface_upload_stuinfo, param);
	}

	/**
	 * 
	 * initSpinner1(初始化省份下拉列表) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void initSpinner1() {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<BaseEntry> list = new ArrayList<BaseEntry>();

		try {
			String sql = "select * from province";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				MyListItem myListItem = new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			MyListItem myListItem = new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();
		MyListItem myListItem1 = new MyListItem();
		myListItem1.setName("请选择省份");
		myListItem1.setPcode("");
		list.add(0, myListItem1);
		MyAdapter myAdapter = new MyAdapter(this, list);
		spinner1.setAdapter(myAdapter);
		spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
	}

	/**
	 * 
	 * initSpinner2(初始化城市下拉列表) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param pcode
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void initSpinner2(String pcode) {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<BaseEntry> list = new ArrayList<BaseEntry>();

		try {
			String sql = "select * from city where pcode='" + pcode + "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				MyListItem myListItem = new MyListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			MyListItem myListItem = new MyListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();

		MyListItem myListItem1 = new MyListItem();
		myListItem1.setName("请选择城市");
		myListItem1.setPcode("");
		list.add(0, myListItem1);
		MyAdapter myAdapter = new MyAdapter(this, list);
		spinner2.setAdapter(myAdapter);
		spinner2.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
	}

	/**
	 * 
	 * initSpinner3(初始化学校下拉列表) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param pcode
	 *            void
	 * @exception
	 * @since 1.0.0
	 */

	public void initSpinner3() {
		try {
			cityutf = URLEncoder.encode(city, "UTF-8");
			provinceutf = URLEncoder.encode(province, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new ImRequestData(ChangeStuinfoActivity.this) {

			@Override
			public void getResult(String result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void getBaseentry(BaseEntry baseentry) {
				// TODO Auto-generated method stub
				List<BaseEntry> list = null;
				list = baseentry.beanslist;
				System.out.println("lsit" + list.toString());
				MyListItem myListItem1 = new MyListItem();
				myListItem1.setName("请选择学校");
				myListItem1.setPcode("");
				list.add(0, myListItem1);
				MyAdapter myAdapter = new MyAdapter(ChangeStuinfoActivity.this,
						list);
				spinner3.setAdapter(myAdapter);
				spinner3.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
			}
		}.getDataByNet(R.string.interface_download_school, cityutf, provinceutf);

	}

	/**
	 * 
	 * initSpinner4(初始化班级下拉列表) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param pcode
	 *            void
	 * @exception
	 * @since 1.0.0
	 */

	public void initSpinner4() {
		try {
			cityutf = URLEncoder.encode(city, "UTF-8");
			provinceutf = URLEncoder.encode(province, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!schoolid.equals("")) {
			new ImRequestData(ChangeStuinfoActivity.this) {

				@Override
				public void getResult(String result) {
					// TODO Auto-generated method stub
				}

				@Override
				public void getBaseentry(BaseEntry baseentry) {
					// TODO Auto-generated method stub
					List<BaseEntry> list = null;
					list = baseentry.beanslist;
					System.out.println("lsit" + list.toString());
					MyListItem myListItem1 = new MyListItem();
					myListItem1.setName("请选择班级");
					myListItem1.setPcode("");
					list.add(0, myListItem1);
					MyAdapter myAdapter = new MyAdapter(
							ChangeStuinfoActivity.this, list);
					spinner4.setAdapter(myAdapter);
					spinner4.setOnItemSelectedListener(new SpinnerOnSelectedListener4());
				}
			}.getDataByNet(R.string.interface_download_class, schoolid);
		}else{
			List<BaseEntry> list = new ArrayList<BaseEntry>();
			System.out.println("lsit" + list.toString());
			MyListItem myListItem1 = new MyListItem();
			myListItem1.setName("请选择班级");
			myListItem1.setPcode("");
			list.add(0, myListItem1);
			MyAdapter myAdapter = new MyAdapter(
					ChangeStuinfoActivity.this, list);
			spinner4.setAdapter(myAdapter);
		}

	}

	/**
	 * 
	 * 
	 * 类名称：SpinnerOnSelectedListener1 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-13
	 * 下午10:13:44 修改备注：
	 * 
	 * @version 1.0.0
	 * 
	 */

	class SpinnerOnSelectedListener1 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			province = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();
			// 获取省编号
			String pcode = ((MyListItem) adapterView
					.getItemAtPosition(position)).getPcode();

			initSpinner2(pcode);
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * 
	 * 
	 * 类名称：SpinnerOnSelectedListener2 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-13
	 * 下午10:15:25 修改备注：
	 * 
	 * @version 1.0.0
	 * 
	 */

	class SpinnerOnSelectedListener2 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			city = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();
			String pcode = ((MyListItem) adapterView
					.getItemAtPosition(position)).getPcode();// 获取城市编号
			initSpinner3();
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * 
	 * 
	 * 类名称：SpinnerOnSelectedListener3 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-13
	 * 下午10:15:32 修改备注：
	 * 
	 * @version 1.0.0
	 * 
	 */
	class SpinnerOnSelectedListener3 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			schoolname = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();// 获取学校名字
			schoolid = ((MyListItem) adapterView.getItemAtPosition(position))
					.getPcode();// 获取学校id
			initSpinner4();
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * 
	 * 
	 * 类名称：SpinnerOnSelectedListener4 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-13
	 * 下午10:15:25 修改备注：
	 * 
	 * @version 1.0.0
	 * 
	 */

	class SpinnerOnSelectedListener4 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			stuclass = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();// 获取班级姓名
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

}
