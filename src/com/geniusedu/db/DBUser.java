package com.geniusedu.db;

import android.provider.BaseColumns;

/**
 * 
 * 
 * 类名称：DBUser
 * 类描述：
 * 创建人：卢东斌 
 * 修改人：卢东斌 
 * 修改时间：2014-9-17 上午12:56:31
 * 修改备注：
 * @version 1.0.0
 *
 */
public final class DBUser {

	public static final class User implements BaseColumns {
		public static final String USERNAME = "username";
		public static final String PASSWORD = "password";
		public static final String ISSAVED = "issaved";
		public static final String PICTURE= "picture";
		public static final String USERID = "userid";
	}

}
