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
 * �����ƣ�ScusseActivity �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-21 ����10:42:28 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
public class SuccessActivity extends Activity {
	//
	// MediaPlayer player;
	static final String LOG_TAG = SuccessActivity.class.getSimpleName();
	static int SCREEN_W = 480;// ��ǰ���ڵĴ�С
	static int SCREEN_H = 800;
	MyView fireworkView; // �Զ���View
	private int[] wh = new int[2]; // ��Ļ���
	private String points;
	private RelativeLayout successlayout;
	private TextView messag;
	private Boolean isthreadopen = false;//��ֹ����֮���̼߳���ִ�з��͹㲥

	// MediaPlayer player = new MediaPlayer();; //ý�岥�Ŷ���
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ȥ��״̬����������ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// player = MediaPlayer.create(this, R.raw.bgmusic);
		// try {
		// player.prepare(); // ���л���
		// } catch (IllegalStateException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// player.setOnPreparedListener(new PreparedListener()); // ��ӻ�������¼�
		fireworkView = new MyView(this, getWH());
		setContentView(R.layout.success_layout);
		messag = (TextView) findViewById(R.id.messag);
		successlayout = (RelativeLayout) findViewById(R.id.scusselayout);
		successlayout.addView(fireworkView);
		points = getIntent().getStringExtra("time");
		messag.setText(CatchApplication.stu_name + "ͬѧ�����" + points + "���ֵĽ���"
				+ "\n\r" + "��ף��ҳ�潫ͣ��" + points + "��");
		openThread(Integer.parseInt(points) * 1000);

	}

	// // ��������¼�
	// private final class PreparedListener implements OnPreparedListener {
	// @Override
	// public void onPrepared(MediaPlayer mp) {
	// player.setLooping(true);
	// player.start();
	// }
	// }

	public int[] getWH() {
		// �����Ļ�Ŀ��
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		wh[0] = metrics.widthPixels;
		wh[1] = metrics.heightPixels;
		// Log.e(LOG_TAG,
		// "��--->"+String.valueOf(wh[0])+"   ��---->"+String.valueOf(wh[1]));
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
	 * openThread(������һ�仰�����������������) (����������������������� �C ��ѡ) void
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