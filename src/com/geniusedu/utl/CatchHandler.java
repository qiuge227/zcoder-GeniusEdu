package com.geniusedu.utl;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * 
 * 
 * �����ƣ�CatchHandler
 * ��������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-17 ����12:56:51
 * �޸ı�ע��
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
	 * @Description:�������ӡtoast
	 * @param @param thread
	 * @param @param ex �趨�ļ�
	 * @return void ��������
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
				Toast.makeText(mContext, "������Ϣ���ϴ������Ǿ����޸�����л����֧�֣�",
						Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
	}

	private void handleException(final Thread thread, final Throwable ex) {
		new AlertDialog.Builder(mContext).setMessage("���������").show();
	}
}
