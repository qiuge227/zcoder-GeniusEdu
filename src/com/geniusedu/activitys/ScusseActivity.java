package com.geniusedu.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.geniusedu.fire.MyView;

/**
 * 
 * 
 * ����ƣ�ScusseActivity
 * ������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-21 ����10:42:28
 * �޸ı�ע��
 * @version 1.0.0
 *
 */
public class ScusseActivity extends Activity {
//
//	MediaPlayer player;
	static final String LOG_TAG = ScusseActivity.class.getSimpleName();
	static int SCREEN_W = 480;// ��ǰ���ڵĴ�С
	static int SCREEN_H = 800;
	MyView fireworkView; // �Զ���View
	private int[] wh = new int[2]; // ��Ļ���
	// MediaPlayer player = new MediaPlayer();; //ý�岥�Ŷ���
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ��״̬8��������ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//		player = MediaPlayer.create(this, R.raw.bgmusic);
//		try {
//			player.prepare(); // ���л���
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		player.setOnPreparedListener(new PreparedListener()); // ��ӻ�������¼�

//		fireworkView = new MyView(this, getWH(),"�����Ի������");
		
		setContentView(fireworkView);
	
		

	}

//	// ��������¼�
//	private final class PreparedListener implements OnPreparedListener {
//		@Override
//		public void onPrepared(MediaPlayer mp) {
//			player.setLooping(true);
//			player.start();
//		}
//	}

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

}