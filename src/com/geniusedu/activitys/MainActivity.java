package com.geniusedu.activitys;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusedu.app.CatchApplication;
import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.beans.StudentInfo;
import com.geniusedu.db.CatchDB;
import com.geniusedu.db.DBHelper;
import com.geniusedu.logical.implement.ImRequestData;
import com.geniusedu.utl.Base64Coder;
import com.geniusedu.utl.GetNetPicture;
import com.geniusedu.utl.NetWorkUtil;
import com.geniusedu.utl.NetworkUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 
 * 
 * �����ƣ�MainActivity ��������Ӧ�������棨��½�ɹ�����������棩 �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-5
 * ����9:33:02 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button relogin;
	private ImageView scanningpoint, student_picture;
	private TextView student_name, student_number, student_birth,
			student_class, student_points, student_school, changepassword;
	private LinearLayout btn_mapnetwork;// networkerr
	public String stu_password;// ѧ������
	public String stuid;// ѧ��ѧ��
	public String schoolid;// ѧУ���
	public DisplayImageOptions options;// ͼ�񻺴�
	public String picurl;// ����ͼ������
	public static final int SELECTEPIC = 1;
	public int addpoints = 0;// Ҫ���ӵķ���
	public int points;// ԭ���Ļ���
	public Boolean isFlash = false;// �Ƿ���Ҫˢ��
	public Boolean isClose = false;// �˳�
	public BroadcastReceiver broadcastreceiver;
	private CatchDB catchdb;
	private DBHelper dbhelper;
	long exitTime;// ����ʱ���ж�
	public StudentInfo studentinfo;
	/**
	 * ����ͼ��
	 */
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				student_points.setText(String.valueOf(++points));
			} else if (msg.what == 1) {
				student_picture.setBackgroundDrawable(new BitmapDrawable(
						(Bitmap) msg.obj));
				catchdb.insertOrUpdate((Bitmap) msg.obj, studentinfo.stuid,
						studentinfo.stuname, CatchApplication.schoolname,
						studentinfo.getStubirth(), studentinfo.getStuclass(),
						studentinfo.getStuintegral());
				if (CatchApplication.isSave == 1) {// ��ס����
					dbhelper.insertOrUpdate(studentinfo.stuname,
							studentinfo.stuid, (Bitmap) msg.obj,
							CatchApplication.stu_password, 1);
					System.out.println("aaaaaaaaaaaaaaaaaaa");
				} else {// ����ס����
					dbhelper.insertOrUpdate(studentinfo.stuname,
							studentinfo.stuid, (Bitmap) msg.obj,
							CatchApplication.stu_password, 0);
					System.out.println("bbbbbbbbbbbbbbbbbb");
				}

			} else if (msg.what == 22) {
				btn_mapnetwork.setVisibility(View.GONE);
				if (!isFlash) {
					if (stuid != null) {
						getStuinfo(stuid);
					}
					isFlash = true;
				}
			} else if (msg.what == 33) {
				isFlash = false;
				btn_mapnetwork.setVisibility(View.VISIBLE);
			} else {

			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		findView();
		setlistener();
		stuid = CatchApplication.stuid;
		stu_password = CatchApplication.stu_password;
		schoolid = CatchApplication.schoolid;
		getCatchinfo();// ��ȡ�������ݿ��������
		getBroadcast();

		/**
		 * ��������״̬
		 */
		new Thread() {
			public void run() {
				while (!isClose) {

					if (NetworkUtils.isNetworkAvailable(MainActivity.this)) {
						Message msg = Message.obtain();
						msg.what = 22;
						handler.sendMessage(msg);

					} else {
						Message msg = Message.obtain();
						msg.what = 33;
						handler.sendMessage(msg);
					}
					try {
						sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};
		}.start();

	}

	/**
	 * 
	 * findView(�󶨿ؼ�)
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void findView() {
		catchdb = new CatchDB(MainActivity.this);
		dbhelper = new DBHelper(MainActivity.this);
		student_name = (TextView) findViewById(R.id.student_name);
		student_number = (TextView) findViewById(R.id.student_number);
		student_birth = (TextView) findViewById(R.id.student_birth);
		student_class = (TextView) findViewById(R.id.student_class);
		student_points = (TextView) findViewById(R.id.student_points);
		student_school = (TextView) findViewById(R.id.schoolname);
		changepassword = (TextView) findViewById(R.id.changepassword);
		relogin = (Button) findViewById(R.id.relogin);
		scanningpoint = (ImageView) findViewById(R.id.scanningpoint);
		student_picture = (ImageView) findViewById(R.id.student_picture);
		btn_mapnetwork = (LinearLayout) findViewById(R.id.btn_mapnetwork);
		btn_mapnetwork.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
				startActivity(intent);
			}
		});
		// Animation ani = new AlphaAnimation(0f, 1f);
		// ani.setDuration(1500);sd
		// ani.setRepeatMode(Animation.REVERSE);
		// ani.setRepeatCount(Animation.INFINITE);
		// scanningpoint.startAnimation(ani);
		/**
		 * url��ȡͼƬ�������
		 */
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.tutu)
				.showImageForEmptyUri(R.drawable.tutu)
				.showImageOnFail(R.drawable.tutu).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
	}

	/**
	 * 
	 * getCatchinfo(������һ�仰�����������������) (����������������������� �C ��ѡ) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void getCatchinfo() {
		StudentInfo studentinfo = catchdb.queryinfoByid(stuid);
		System.err.println("��Ľ�����,����û���ݰ�");
		if (studentinfo != null) {
			System.err.println("��������Ľ�����");
			student_name.setText(studentinfo.stuname);
			student_number.setText(studentinfo.stuid);
			student_birth.setText(studentinfo.getStubirth());
			student_school.setText(CatchApplication.schoolname);
			student_class.setText(studentinfo.getStuclass());
			student_points.setText(studentinfo.getStuintegral());
			student_picture.setBackgroundDrawable(new BitmapDrawable(
					studentinfo.getStubitpic()));
		}

	}

	/**
	 * 
	 * setlistener���ü���
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void setlistener() {
		changepassword.setOnClickListener(this);
		relogin.setOnClickListener(this);
		scanningpoint.setOnClickListener(this);
		student_picture.setOnClickListener(this);
	}

	/**
	 * 
	 * getBroadcast(��������ѧУѡ�����Ĺ㲥) (����������������������� �C ��ѡ) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void getBroadcast() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("pictureselect");
		intentFilter.addAction("stuinfo_change");
		intentFilter.addAction("add_points");
		broadcastreceiver = new BroadcastReceiver() {
			@SuppressWarnings("deprecation")
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals("pictureselect")) {
					if (intent.getStringExtra("flag").equals("student")) {
						Bitmap bit = intent.getParcelableExtra("bitmap");
						student_picture
								.setBackgroundDrawable(new BitmapDrawable(bit));
						setPhoto(bit);
					}
				}
				if (intent.getAction().equals("stuinfo_change")) {
					student_school.setText(CatchApplication.schoolname);
					student_class.setText(intent.getStringExtra("stu_class"));
				}
				if (intent.getAction().equals("add_points")) {
					addpoints = intent.getIntExtra("points", 0);
					changepoints();
				}
			}
		};
		registerReceiver(broadcastreceiver, intentFilter);
	}

	/**
	 * 
	 * openThread(������һ�仰�����������������) (����������������������� �C ��ѡ) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void changepoints() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				while (addpoints > 0) {
					try {
						Thread.sleep(200);
						Message msg = Message.obtain();
						msg.what = 0;
						handler.sendMessage(msg);
						addpoints--;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	/**
	 * 
	 * setPhoto(����ͼ���ϴ�ͼ��)
	 * 
	 * @param v
	 * @exception
	 * @since 1.0.0
	 */
	private void setPhoto(Bitmap bit) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.JPEG, 80, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer = out.toByteArray();
		final String picStr = new String(Base64Coder.encodeLines(buffer));
		new Thread(new Runnable() {
			@SuppressWarnings("unused")
			@Override
			public void run() {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("stu_number",
						CatchApplication.stuid));
				params.add(new BasicNameValuePair("photo", picStr));
				params.add(new BasicNameValuePair("dataBaseName", schoolid));
				final String result = NetWorkUtil
						.httpPost(
								getString(R.string.interface_upload_stupicture),
								params);
				runOnUiThread(new Runnable() {
					public void run() {
					}
				});
			}
		}).start();
	}

	/**
	 * 
	 * getStuinfo(��ȡѧ����Ϣ)
	 * 
	 * @param stuid
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void getStuinfo(String stuid) {
		new ImRequestData(MainActivity.this) {

			@Override
			public void getResult(String result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void getBaseentry(BaseEntry baseentry) {
				// TODO Auto-generated method stub
				if (baseentry != null) {
					studentinfo = (StudentInfo) baseentry;
					System.err.println(studentinfo.toString());
					student_name.setText(studentinfo.stuname);
					student_number.setText(studentinfo.stuid);
					student_birth.setText(studentinfo.getStubirth());
					student_school.setText(CatchApplication.schoolname);
					student_class.setText(studentinfo.getStuclass());
					student_points.setText(studentinfo.getStuintegral());
					picurl = studentinfo.getStupicture();
					points = Integer.parseInt(studentinfo.getStuintegral());
					new Thread() {
						@SuppressWarnings({ "deprecation", "unused" })
						public void run() {
							Bitmap bit;
							try {
								bit = GetNetPicture.getBitmap(picurl);
								if (bit == null) {
									student_picture
											.setBackgroundResource(R.drawable.tutu);
									bit = BitmapFactory.decodeResource(
											getResources(), R.drawable.tutu);
								}
								Message msg = Message.obtain();
								msg.obj = bit;
								msg.what = 1;
								handler.sendMessage(msg);

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						};
					}.start();
					Intent intent = new Intent();
					intent.setAction("mainactivity");
					intent.putExtra("studname", studentinfo.stuname);
					sendBroadcast(intent);
					CatchApplication.stu_name = student_name.getText()
							.toString();
				}
			}
		}.getDataByNet(R.string.interface_download_stuinfo, stuid, schoolid);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.changepassword:
			// Intent intent2 = new Intent(getApplicationContext(),
			// ChangePasswordActivity.class);
			// startActivity(intent2);

			break;
		case R.id.relogin:
			Intent intent3 = new Intent(MainActivity.this, LoginActivity.class);
			intent3.putExtra("flag", true);
			startActivity(intent3);
			finish();
			break;
		case R.id.scanningpoint:
			Intent intent4 = new Intent(MainActivity.this,
					CaptureActivity.class);
			intent4.putExtra("stuid", student_number.getText().toString());
			startActivity(intent4);
			break;
		case R.id.student_picture:
			Intent intent5 = new Intent(MainActivity.this,
					SelectPicActivity.class);
			intent5.putExtra("flag", "student");
			startActivityForResult(intent5, SELECTEPIC);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		isClose = true;
		if (broadcastreceiver != null) {
			unregisterReceiver(broadcastreceiver);
		}
		catchdb.cleanup();
		dbhelper.cleanup();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub

		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						System.exit(0);
					}
				}, 1000);
			}
		}

		return false;
	}
}
