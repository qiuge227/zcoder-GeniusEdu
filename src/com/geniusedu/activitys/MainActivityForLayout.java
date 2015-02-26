package com.geniusedu.activitys;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusedu.view.PullDoorView;

/**
 * 
 * 
 * �����ƣ�MainActivityForLayout �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-14 ����4:27:31 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
public class MainActivityForLayout extends Activity {
	private static final int ITEM1 = Menu.FIRST + 1;// �˵���1
	private static final int ITEM2 = Menu.FIRST + 2;// �˵���2
	private LocalActivityManager manager = null;
	private Button btnBelow, btnAbove;
	private TextView tvHint;
	private PullDoorView pulldoorview;
	private RelativeLayout guardian_layout;
	private RelativeLayout main_layout;
	long exitTime;// ����ʱ���ж�
	public BroadcastReceiver broadcastreceiver;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_zaker);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		initView();
		getBroadcast();
	}

	/**
	 * 
	 * getBroadcast(��������ѧУѡ�����Ĺ㲥)
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void getBroadcast() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("Guardian");
		broadcastreceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (pulldoorview.mCloseFlag) {
					pulldoorview.mCloseFlag = false;
					pulldoorview.setVisibility(View.VISIBLE);
					pulldoorview.startBounceAnim(pulldoorview.getScrollY(),
							-pulldoorview.getScrollY(), 1000);
				}

			}
		};
		registerReceiver(broadcastreceiver, intentFilter);
	}

	/**
	 * 
	 * initView(������һ�仰�����������������) (����������������������� �C ��ѡ) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void initView() {
		Intent mainIntent = new Intent(MainActivityForLayout.this,
				MainActivity.class);
		Intent guardianIntent = new Intent(MainActivityForLayout.this,
				GuardianActivity.class);
		guardian_layout = (RelativeLayout) findViewById(R.id.guardian_layout);
		main_layout = (RelativeLayout) findViewById(R.id.main_layout);
		tvHint = (TextView) this.findViewById(R.id.tv_hint);
		pulldoorview = (PullDoorView) findViewById(R.id.myImage);
		main_layout.addView(getView("MAIN", mainIntent));
		// pulldoorview.addActivityView(getView("MAIN", mainIntent));
		guardian_layout.addView(getView("GUARDIAN", guardianIntent));
		Animation ani = new AlphaAnimation(0f, 1f);
		ani.setDuration(1500);
		ani.setRepeatMode(Animation.REVERSE);
		ani.setRepeatCount(Animation.INFINITE);
		tvHint.startAnimation(ani);
	}

	/**
	 * 
	 * getView(������һ�仰�����������������) (����������������������� �C ��ѡ)
	 * 
	 * @param id
	 * @param intent
	 * @return View
	 * @exception
	 * @since 1.0.0
	 */
	@SuppressWarnings("deprecation")
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		manager.dispatchStop();
		super.onStop();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		manager.dispatchPause(isFinishing());
		super.overridePendingTransition(R.anim.flip_horizontal_in,
				R.anim.flip_horizontal_out);
		super.onPause();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		manager.destroyActivity("GUARDIAN", isFinishing());
		manager.destroyActivity("GUARDIAN", isFinishing());
		if (broadcastreceiver != null) {
			MainActivityForLayout.this.unregisterReceiver(broadcastreceiver);
		}
		super.onDestroy();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, ITEM1, 0, "�޸��ҵ���Ϣ")
				.setIcon(android.R.drawable.ic_menu_edit);
		menu.add(0, ITEM2, 0, "�޸��ҵ�����").setIcon(
				android.R.drawable.ic_lock_idle_lock);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case ITEM1:
			Intent intent = new Intent(MainActivityForLayout.this,
					ChangeStuinfoActivity.class);
			startActivity(intent);
			break;
		case ITEM2:
			Intent intent2 = new Intent(MainActivityForLayout.this,
					ChangePasswordActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
