package com.geniusedu.db;

import android.provider.BaseColumns;

/**
 * 
 * 
 * �����ƣ�DBUser
 * ��������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-17 ����12:56:31
 * �޸ı�ע��
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
