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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.geniusedu.adapter.MyAdapter;
import com.geniusedu.app.CatchApplication;
import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.beans.MyListItem;
import com.geniusedu.db.DBManager;
import com.geniusedu.logical.implement.ImRequestData;
import com.geniusedu.utl.PreferenceUtils;

/**
 * 
 * 
 * �����ƣ�SchoolSelectActivity �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-17 ����12:51:58 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
public class SchoolSelectActivity extends Activity {
	private DBManager dbm;
	private SQLiteDatabase db;
	private Spinner spinner1 = null;
	private Spinner spinner2 = null;
	private Spinner spinner3 = null;
	private String province = null;
	private String city = null;
	private String cityutf;
	private String provinceutf;
	private String schoolname = null;
	private String schoolid = null;
	private Button showselect;
	private TextView school;
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schoolselecte_layout);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		showselect = (Button) findViewById(R.id.showselect);
		school = (TextView) findViewById(R.id.schoolname);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		spinner1.setPrompt("��ѡ��ʡ��");
		spinner2.setPrompt("��ѡ��������");
		spinner3.setPrompt("��ѡ��ѧУ");
		initSpinner1();
		showselect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SchoolSelectActivity.this,
						LoginActivity.class);
				intent.putExtra("schoolid", schoolid);
				intent.putExtra("schoolname", schoolname);
				setResult(2222, intent);
				finish();
			}
		});
	}

	/**
	 * 
	 * initSpinner1(��ʼ��ʡ�������б�) (����������������������� �C ��ѡ) void
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
		myListItem1.setName("��ѡ��ʡ��");
		myListItem1.setPcode("");
		list.add(0, myListItem1);
		MyAdapter myAdapter = new MyAdapter(this, list);
		spinner1.setAdapter(myAdapter);
		spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
	}

	/**
	 * 
	 * initSpinner2(��ʼ�����������б�) (����������������������� �C ��ѡ)
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
		myListItem1.setName("��ѡ�����");
		myListItem1.setPcode("");
		list.add(0, myListItem1);
		MyAdapter myAdapter = new MyAdapter(this, list);
		spinner2.setAdapter(myAdapter);
		spinner2.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
	}

	/**
	 * 
	 * initSpinner3(��ʼ��ѧУ�����б�) (����������������������� �C ��ѡ)
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

		new ImRequestData(SchoolSelectActivity.this) {

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
				myListItem1.setName("��ѡ��ѧУ");
				myListItem1.setPcode("");
				list.add(0, myListItem1);
				MyAdapter myAdapter = new MyAdapter(SchoolSelectActivity.this,
						list);
				spinner3.setAdapter(myAdapter);
				spinner3.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
			}
		}.getDataByNet(R.string.interface_download_school, cityutf, provinceutf);

	}

	/**
	 * 
	 * 
	 * �����ƣ�SpinnerOnSelectedListener1 �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-13
	 * ����10:13:44 �޸ı�ע��
	 * 
	 * @version 1.0.0
	 * 
	 */

	class SpinnerOnSelectedListener1 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			province = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();
			// ��ȡʡ���
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
	 * �����ƣ�SpinnerOnSelectedListener2 �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-13
	 * ����10:15:25 �޸ı�ע��
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
					.getItemAtPosition(position)).getPcode();
			initSpinner3();
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * 
	 * 
	 * �����ƣ�SpinnerOnSelectedListener3 �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-13
	 * ����10:15:32 �޸ı�ע��
	 * 
	 * @version 1.0.0
	 * 
	 */
	class SpinnerOnSelectedListener3 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			schoolname = ((MyListItem) adapterView.getItemAtPosition(position))
					.getName();
			schoolid = ((MyListItem) adapterView.getItemAtPosition(position))
					.getPcode();
			if (!schoolname.equals("��ѡ��ѧУ")) {
				CatchApplication.schoolid = schoolid;
				CatchApplication.schoolname = schoolname;
				PreferenceUtils.setPrefString(SchoolSelectActivity.this,
						"schoolname", schoolname);
				school.setText(schoolname);
			}
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.overridePendingTransition(R.anim.flip_vertical_in,
				R.anim.flip_vertical_out);
		super.onPause();
	}
}
