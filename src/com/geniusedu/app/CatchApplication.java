package com.geniusedu.app;

import com.geniusedu.utl.CatchHandler;

import android.app.Application;

/**
 * 
 * 
 * �����ƣ�CatchApplication �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-17 ����12:54:53 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
public class CatchApplication extends Application {
	public static String schoolid;
	public static String schoolname;
	public static String stuid;
	public static String stu_password;
	public static String stu_name;
    public static int isSave;

	@Override
	public void onCreate() {
		super.onCreate();
		CatchHandler.getInstance().init(getApplicationContext());//
		// �������������ڲ��߳�
	}

}