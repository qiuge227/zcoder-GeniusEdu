package com.geniusedu.utl;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * 
 * 
 * 类名称：CatchHandler
 * 类描述：
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-17 上午12:56:51
 * 修改备注：
 * @version 1.0.0
 *
 */
public class CatchHandler implements UncaughtExceptionHandler {

	private CatchHandler() {
	}

	public static CatchHandler getInstance() {

		return mCatchHandler;
	}

	private static CatchHandler mCatchHandler = new CatchHandler();

	private Context mContext;

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (thread.getName().equals("main")) {
			ToastException(thread, ex);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		} else {
			handleException(thread, ex);
		}

	}

	public void init(Context context) {
		mContext = context;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 
	 * @Title: ToastException
	 * @Description:崩溃后打印toast
	 * @param @param thread
	 * @param @param ex 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void ToastException(final Thread thread, final Throwable ex) {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				StringBuilder builder = new StringBuilder();
				builder.append("At thread: ").append(thread.getName())
						.append("\n");
				builder.append("Exception is :\n").append(ex.getMessage());
				//
				Toast.makeText(mContext, "错误信息已上传，我们尽快修复，感谢您的支持！",
						Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
	}

	private void handleException(final Thread thread, final Throwable ex) {
		new AlertDialog.Builder(mContext).setMessage("程序出错了").show();
	}
}
