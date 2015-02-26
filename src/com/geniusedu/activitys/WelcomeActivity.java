package com.geniusedu.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.geniusedu.utl.NetworkUtils;
import com.loopj.android.image.SmartImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * 
 * 
 * 类名称：WelcomeActivity 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-17 上午12:51:38 修改备注：
 * 
 * @version 1.0.0
 * 
 */
@SuppressLint("ShowToast")
public class WelcomeActivity extends Activity {
	private boolean isFirstUse;// 判断是否是第一次登陆
	private SmartImageView welcomeimg;
	DisplayImageOptions options;// 获得缓存的图片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_layout);
		// 判断网络状态
		if (NetworkUtils.isNetworkAvailable(WelcomeActivity.this)) {

			if (NetworkUtils.isWifi(WelcomeActivity.this)) {
				Toast.makeText(WelcomeActivity.this, "当前通过wifi连接", 1000).show();
			} else if (NetworkUtils.is3G(WelcomeActivity.this)) {
				Toast.makeText(WelcomeActivity.this, "当前通过3G网连接", 1000).show();
			}

		} else {
			Toast.makeText(WelcomeActivity.this, "网络未连接，请检查当前网络连接状态", 1000)
					.show();
		}
		// welcomeimg = (SmartImageView) findViewById(R.id.welcomeimg);
		// welcomeimg.setImageUrl(constants.BaseUrl + LOGOURL);
		openThread();// 打开一个线程
	}

	/**
	 * 
	 * openThread(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void openThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				/**
				 * 延迟两秒时间
				 */
				try {
					Thread.sleep(2000);
					// 读取SharePreferences中需要的数据

					// isFirstUse = PreferenceUtils.getPrefBoolean(
					// WelcomeActivity.this, "isFirstUse", true);
					// /**
					// * 如果用户不是第一次使用则直接调转到显示界面，否则调转到引导界面
					// */
					// if (isFirstUse) {
					// startActivity(new Intent(WelcomeActivity.this,
					// GuideActivity.class));
					// isFirstUse = false;
					// } else {
					startActivity(new Intent(WelcomeActivity.this,
							LoginActivity.class));
					
					finish();
//					overridePendingTransition(R.anim.flip_horizontal_in, R.anim.flip_horizontal_out);
					// }
//					finish();
					//
					// // 存入数据
					// PreferenceUtils.setPrefBoolean(WelcomeActivity.this,
					// "isFirstUse", false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

}
