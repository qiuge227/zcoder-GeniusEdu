package com.geniusedu.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusedu.app.CatchApplication;
import com.geniusedu.fire.MyView;

/**
 * 
 * 
 * 类名称：ScusseActivity 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-21 下午10:42:28 修改备注：
 * 
 * @version 1.0.0
 * 
 */
public class SuccessActivity extends Activity {
	//
	// MediaPlayer player;
	static final String LOG_TAG = SuccessActivity.class.getSimpleName();
	static int SCREEN_W = 480;// 当前窗口的大小
	static int SCREEN_H = 800;
	MyView fireworkView; // 自定义View
	private int[] wh = new int[2]; // 屏幕宽高
	private String points;
	private RelativeLayout successlayout;
	private TextView messag;
	private Boolean isthreadopen = false;//防止返回之后，线程继续执行发送广播

	// MediaPlayer player = new MediaPlayer();; //媒体播放对象
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 去掉状态栏并且设置全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// player = MediaPlayer.create(this, R.raw.bgmusic);
		// try {
		// player.prepare(); // 进行缓冲
		// } catch (IllegalStateException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// player.setOnPreparedListener(new PreparedListener()); // 添加缓冲监听事件
		fireworkView = new MyView(this, getWH());
		setContentView(R.layout.success_layout);
		messag = (TextView) findViewById(R.id.messag);
		successlayout = (RelativeLayout) findViewById(R.id.scusselayout);
		successlayout.addView(fireworkView);
		points = getIntent().getStringExtra("time");
		messag.setText(CatchApplication.stu_name + "同学获得了" + points + "积分的奖励"
				+ "\n\r" + "该祝贺页面将停留" + points + "秒");
		openThread(Integer.parseInt(points) * 1000);

	}

	// // 缓冲监听事件
	// private final class PreparedListener implements OnPreparedListener {
	// @Override
	// public void onPrepared(MediaPlayer mp) {
	// player.setLooping(true);
	// player.start();
	// }
	// }

	public int[] getWH() {
		// 获得屏幕的宽高
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		wh[0] = metrics.widthPixels;
		wh[1] = metrics.heightPixels;
		// Log.e(LOG_TAG,
		// "宽--->"+String.valueOf(wh[0])+"   高---->"+String.valueOf(wh[1]));
		return wh;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (fireworkView.isRunning()) {
			fireworkView.setRunning(false);
		}

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
	 * openThread(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void openThread(final int time) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(time);
					if (!isthreadopen) {
						Intent intent = new Intent();
						intent.setAction("add_points");
						intent.putExtra("points", Integer.parseInt(points));
						sendBroadcast(intent);
						finish();
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			isthreadopen = true;
			Intent intent = new Intent();
			intent.setAction("add_points");
			intent.putExtra("points", Integer.parseInt(points));
			sendBroadcast(intent);
			finish();
		}

		return false;
	}

}