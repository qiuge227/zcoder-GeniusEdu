package com.geniusedu.app;

import com.geniusedu.utl.CatchHandler;

import android.app.Application;

/**
 * 
 * 
 * 类名称：CatchApplication 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-17 上午12:54:53 修改备注：
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
		// 程序崩溃后调用内部线程
	}

}