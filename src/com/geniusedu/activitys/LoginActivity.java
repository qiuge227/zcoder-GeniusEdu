package com.geniusedu.activitys;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusedu.app.CatchApplication;
import com.geniusedu.baseactivitys.LoadDataBaseActivity;
import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.beans.UserRecord;
import com.geniusedu.db.DBHelper;
import com.geniusedu.logical.implement.ImRequestData;
import com.geniusedu.utl.CustomProgressDialog;
import com.geniusedu.utl.NetworkUtils;
import com.geniusedu.utl.PreferenceUtils;

/**
 * 
 * 
 * �����ƣ�LoginActivity �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-17 ����12:47:49 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
@SuppressLint("ShowToast")
public class LoginActivity extends LoadDataBaseActivity implements
		OnClickListener {
	private EditText idname;
	private EditText password;
	private Button login;
	private Button mDropDown;
	private ImageView clearpd;
	private TextView selecteschool;
	private ImageView img_logpic;

	private DBHelper dbHelper;
	private PopupWindow popView;
	private MyAdapter dropDownAdapter;
	private CheckBox mCheckBox, autoCheckBox;
	private int urlid;
	public static final String LOGIN_SUCCESS = "1";
	public static final String LOGIN_FAIL = "2";
	public String loginstate;
	private String schoolid = null;
	private String schoolname;
	private CustomProgressDialog progressDialog; // ������
	private Context context;
	private ArrayList<UserRecord> userlist;
	long exitTime;// ����ʱ���ж�

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout2);
		findview();
		setlistener();
		// getBroadcast();
		if (!getIntent().getBooleanExtra("flag", false)) {
			if (PreferenceUtils.getPrefBoolean(LoginActivity.this, "autologin",
					false)) {
				login();
			}
		}

	}

	/**
	 * 
	 * findview(������һ�仰�����������������) (����������������������� �C ��ѡ) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void findview() {
		img_logpic = (ImageView) findViewById(R.id.img_logpic);
		idname = (EditText) findViewById(R.id.idname);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		mDropDown = (Button) findViewById(R.id.dropdown_button);
		clearpd = (ImageView) findViewById(R.id.clearpd);
		selecteschool = (TextView) findViewById(R.id.selecteschool);
		mCheckBox = (CheckBox) findViewById(R.id.remember);
		autoCheckBox = (CheckBox) findViewById(R.id.automaticlogin);
		selecteschool.setText(PreferenceUtils.getPrefString(LoginActivity.this,
				"schoolname", "��ѡ��ѧУ"));
		if (PreferenceUtils.getPrefBoolean(LoginActivity.this, "autologin",
				false)) {
			autoCheckBox.setChecked(true);
		} else {
			autoCheckBox.setChecked(false);
		}
		context = getParent();
		schoolid = PreferenceUtils.getPrefString(LoginActivity.this,
				"schoolid", null);
		schoolname = selecteschool.getText().toString();
	}

	/**
	 * 
	 * setlistener(������һ�仰�����������������) (����������������������� �C ��ѡ) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void setlistener() {
		login.setOnClickListener(this);
		mDropDown.setOnClickListener(this);
		clearpd.setOnClickListener(this);
		selecteschool.setOnClickListener(this);
		dbHelper = new DBHelper(LoginActivity.this);

		initLoginUserName();
		// �Զ���¼
		autoCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					PreferenceUtils.setPrefBoolean(LoginActivity.this,
							"autologin", true);
				} else {
					PreferenceUtils.setPrefBoolean(LoginActivity.this,
							"autologin", false);
				}
			}
		});
	}

	// /**
	// *
	// * getBroadcast(��������ѧУѡ�����Ĺ㲥,����ѧУid)
	// *
	// * @exception
	// * @since 1.0.0
	// */
	// public void getBroadcast() {
	// IntentFilter intentFilter = new IntentFilter();
	// intentFilter.addAction("city_school");
	// context.registerReceiver(new BroadcastReceiver() {
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// System.out.println("registerreveiver1111111111111111111");
	// if (intent.getAction().equals("city_school")) {
	// if (intent != null) {
	// schoolid = intent.getStringExtra("schoolid");
	// }
	// }
	//
	// }
	// }, intentFilter);
	// }

	/**
	 * 
	 * isNumeric(������һ�仰�����������������) (����������������������� �C ��ѡ)
	 * 
	 * @param content
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean isNumeric(String content) {
		boolean flag = false;
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		if (m.matches())
			flag = true;
		return flag;

	}

	/**
	 * 
	 * islogin(������һ�仰�����������������)
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void islogin(boolean isid, final String id, final String password) {
		startProgressDialog("���ڵ�½���Ժ�...");
		if (!id.trim().equals("") && !password.trim().equals("")) {
			if (isid) {
				urlid = R.string.interface_upload_logininfo;
			} else {
				urlid = R.string.interface_upload_loginbynameinfo;
			}
			new ImRequestData(LoginActivity.this) {

				@Override
				public void getResult(String result) {
					if (result != null) {
						if (result.equals("1")) {
							stopProgressDialog();
							PreferenceUtils.setPrefString(LoginActivity.this,
									"schoolid", schoolid);
							Intent intent = new Intent(LoginActivity.this,
									MainActivity.class);
							CatchApplication.stuid = id;
							CatchApplication.stu_password = password;
							CatchApplication.schoolid = schoolid;
							CatchApplication.schoolname = selecteschool
									.getText().toString();
							if (mCheckBox.isChecked()) {
								CatchApplication.isSave = 1;
							} else {
								CatchApplication.isSave = 0;
							}
							startActivity(intent);
							overridePendingTransition(R.anim.in_translate_top,
									R.anim.out_translate_top);
							((Activity) context).finish();

						} else if (result.equals("2")) {
							Toast.makeText(LoginActivity.this, "�������", 1000)
									.show();
						} else {
							Toast.makeText(LoginActivity.this, "���������������쳣",
									1000).show();
						}
					} else {
						Toast.makeText(LoginActivity.this, "�����쳣", 1000).show();
					}
					stopProgressDialog();
				}

				@Override
				public void getBaseentry(BaseEntry baseentry) {
					// TODO Auto-generated method stub
					if (baseentry != null) {
						if (baseentry.beanslist.size() == 1) {
							islogin(true, baseentry.beanslist.get(0).stuid,
									password);
						} else if (baseentry.beanslist.size() > 1) {
							Intent intent = new Intent(LoginActivity.this,
									LoginByNameListActivity.class);
							intent.putExtra("baseentry", baseentry);
							startActivityForResult(intent, 1111);
						} else {
							Toast.makeText(LoginActivity.this, "��֤�û��ٵ�½", 1000)
									.show();
						}
					} else {
						Toast.makeText(LoginActivity.this, "��֤�û��ٵ�½", 1000)
								.show();
					}
					stopProgressDialog();

				}

			}.getDataByNet(urlid, id, password, schoolid);

		} else {
			Toast.makeText(LoginActivity.this, "�û������벻��Ϊ��", 1000).show();
			stopProgressDialog();
		}

	}

	/**
	 * 
	 * login(��¼��ʽѡ���߼��ж�) (����������������������� �C ��ѡ) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void login() {
		if (schoolid != null
				&& !selecteschool.getText().toString().equals("��ѡ��ѧУ")) {

			if (isNumeric(idname.getText().toString())) {
				islogin(true, idname.getText().toString(), password.getText()
						.toString());// id��½����

			} else {
				try {
					islogin(false, URLEncoder.encode(idname.getText()
							.toString(), "utf-8"), password.getText()
							.toString());// ���ֵ�½����
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		} else {
			Animation anim = AnimationUtils.loadAnimation(LoginActivity.this,
					R.anim.myanim);
			selecteschool.startAnimation(anim);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == 1111) {
			islogin(true, data.getStringExtra("id"), password.getText()
					.toString());
		}
		if (resultCode == 2222) {
			schoolid = data.getStringExtra("schoolid");
			selecteschool.setText(data.getStringExtra("schoolname"));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// ����չ
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			if (NetworkUtils.isNetworkAvailable(LoginActivity.this)) {
				login();
			} else {
				Toast.makeText(LoginActivity.this, "��½ʧ�ܣ���������״̬", 1000).show();
			}

			break;
		case R.id.dropdown_button:
			if (popView != null) {
				if (!popView.isShowing()) {
					popView.showAsDropDown(idname);
				} else {
					popView.dismiss();
				}
			} else {
				// ������Ѿ���¼���˺�
				if (userlist.size() > 0) {
					initPopView();
					if (!popView.isShowing()) {
						popView.showAsDropDown(idname);
					} else {
						popView.dismiss();
					}
				} else {
					Toast.makeText(this, "�޼�¼", Toast.LENGTH_LONG).show();
				}

			}
			break;
		case R.id.selecteschool:
			if (NetworkUtils.isNetworkAvailable(LoginActivity.this)) {
				Intent intent = new Intent(LoginActivity.this,
						SchoolSelectActivity.class);
				startActivityForResult(intent, 2222);
				overridePendingTransition(R.anim.flip_vertical_in,
						R.anim.flip_vertical_out);
			} else {
				Toast.makeText(LoginActivity.this, "��ǰ�����ӣ���������״̬", 1000)
						.show();
			}

			break;
		case R.id.clearpd:
			password.setText("");
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * 
	 * �����ƣ�MyAdapter ����������¼���������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-8 ����9:25:51 �޸ı�ע��
	 * 
	 * @version 1.0.0
	 * 
	 */
	class MyAdapter extends BaseAdapter {

		public Context context;

		public MyAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return userlist.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			System.out.println(position);
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.dropdown_item, null);
				holder.btn =  (ImageView) convertView
						.findViewById(R.id.delete);
				holder.tvname = (TextView) convertView
						.findViewById(R.id.username);
				holder.tvid = (TextView) convertView.findViewById(R.id.userid);
				holder.img_userpicture = (ImageView) convertView
						.findViewById(R.id.img_userpicture);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tvname.setText(userlist.get(position).getUserName());
			holder.tvid.setText(userlist.get(position).getUserId());
			holder.img_userpicture.setBackgroundDrawable(new BitmapDrawable(
					userlist.get(position).getUserPic()));
			holder.btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (userlist.size() > 0) {
						dbHelper.delete(userlist.get(position).getUserId());
					}
				   userlist = dbHelper.queryAllUserInfo();
					if (userlist.size() > 0) {
						initPopView();
						popView.showAsDropDown(idname);
					} else {
						popView.dismiss();
						popView = null;
					}
				}
			});
			return convertView;
		}
	}

	// �������
	class ViewHolder {
		private TextView tvname;
		private TextView tvid;
		private ImageView img_userpicture;
		private ImageView btn;
	}

	/**
	 * 
	 * initPopView(���ر������û�����¼)
	 * 
	 * @param usernames
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void initPopView() {
		dropDownAdapter = new MyAdapter(LoginActivity.this);
		ListView listView = new ListView(LoginActivity.this);
		listView.setAdapter(dropDownAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				UserRecord user = userlist.get(arg2);
				img_logpic.setBackgroundDrawable(new BitmapDrawable(user.getUserPic()));
				idname.setText(user.getUserName());
				if(user.getIsSave()==1){
					password.setText(user.getPassWord());
				}else{
					password.setText("");
				}
				popView.dismiss();
			}
		});
		popView = new PopupWindow(listView, idname.getWidth(),
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		popView.setFocusable(true);
		popView.setOutsideTouchable(true);
		popView.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.white));
	}

	/**
	 * 
	 * initLoginUserName(������һ�仰�����������������) (����������������������� �C ��ѡ) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void initLoginUserName() {
		userlist = dbHelper.queryAllUserInfo();
	System.out.println(userlist.toString());
		if (userlist.size() > 0) {
			UserRecord userinfo = userlist.get(userlist.size() - 1);
			idname.setText(userinfo.getUserName());
			idname.setSelection(userlist.size());
			int checkFlag = userinfo.getIsSave();
			if (checkFlag == 0) {
				mCheckBox.setChecked(false);
				password.setText("");
			} else if (checkFlag == 1) {
				mCheckBox.setChecked(true);
				password.setText(userinfo.getPassWord());
			}

		}

		// ���������ļ���
		idname.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				password.setText("");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		dbHelper.cleanup();
		super.onDestroy();
	}

	/**
	 * 
	 * startProgressDialog(�����ȴ��Ի���)
	 * 
	 * @param str
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void startProgressDialog(String str) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(this);
			progressDialog.setMessage(str);
		}
		progressDialog.show();
	}

	/**
	 * 
	 * stopProgressDialog(�رյȴ��Ի���)
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
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
