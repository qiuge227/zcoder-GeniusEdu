package com.geniusedu.db;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.geniusedu.beans.UserRecord;
import com.geniusedu.db.DBUser.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 
 * 
 * �����ƣ�DBHelper �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-17 ����12:56:18 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
public class DBHelper {
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "user.db";
	public static final String USER_TABLE_NAME = "user_table";
	public static final String[] USER_COLS = { User.USERNAME, User.PASSWORD,
			User.ISSAVED };

	private SQLiteDatabase db;
	private DBOpenHelper dbOpenHelper;

	public DBHelper(Context context) {
		this.dbOpenHelper = new DBOpenHelper(context);
		establishDb();
	}

	/**
	 * �����ݿ�
	 */
	private void establishDb() {
		if (this.db == null) {
			this.db = this.dbOpenHelper.getWritableDatabase();
		}
	}

	/**
	 * ����һ����¼
	 * 
	 * @param map
	 *            Ҫ����ļ�¼
	 * @param tableName
	 *            ����
	 * @return �����¼��id -1��ʾ���벻�ɹ�
	 */
	public long insertOrUpdate(String userName, String userId, Bitmap bit,
			String password, int isSaved) {
		long id = -1;

		if (queryPasswordById(userId)) {
			id = update(userName, userId, bit, password, isSaved);// ����һ������
			System.out.println("isupdate" + id);
		} else {
			System.out.println("�µ�����");
			if (db != null) {
				System.out.println("db���գ�������");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bit.compress(Bitmap.CompressFormat.PNG, 100, baos);
				ContentValues values = new ContentValues();
				values.put(User.USERNAME, userName);
				values.put(User.USERID, userId);
				values.put(User.PASSWORD, password);
				values.put(User.ISSAVED, isSaved);
				values.put(User.PICTURE, baos.toByteArray());
				id = db.insert(USER_TABLE_NAME, null, values);
				System.out.println("insert" + id);
			}
		}
		return id;
	}

	/**
	 * ɾ��һ����¼
	 * 
	 * @param userName
	 *            �û���
	 * @param tableName
	 *            ����
	 * @return ɾ����¼��id -1��ʾɾ�����ɹ�
	 */
	public long delete(String userId) {
		long id = db.delete(USER_TABLE_NAME, User.USERID + " = '" + userId
				+ "'", null);
		return id;
	}

	/**
	 * ����һ����¼
	 * 
	 * @param
	 * 
	 * @param tableName
	 *            ����
	 * @return ���¹����¼��id -1��ʾ���²��ɹ�
	 */
	public long update(String username, String userId, Bitmap bit,
			String password, int isSaved) {
		System.out.println("����һ������");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.PNG, 100, baos);
		ContentValues values = new ContentValues();
		values.put(User.USERNAME, username);
		values.put(User.USERID, userId);
		values.put(User.PASSWORD, password);
		values.put(User.ISSAVED, isSaved);
		values.put(User.PICTURE, baos.toByteArray());
		long id = db.update(USER_TABLE_NAME, values, User.USERID + " = '"
				+ userId + "'", null);
		return id;
	}

	/**
	 * ����id��ѯ�û��Ƿ����
	 * 
	 * @param username
	 *            �û���
	 * @param tableName
	 *            ����
	 * @return Hashmap ��ѯ�ļ�¼
	 */
	public Boolean queryPasswordById(String userid) {
		ArrayList<UserRecord> list;
		list = queryAllUserInfo();
		Boolean isUpdate = false;
		for (int i = 0; i < list.size(); i++) {
			if (userid.equals(list.get(i).getUserId())) {
				isUpdate = true;
				break;
			}
			System.out.println("�µ�");
		}
		if (isUpdate) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��ס����ѡ����Ƿ�ѡ��
	 * 
	 * @param username
	 * @return
	 */
	public int queryIsSavedById(String userId) {
		String sql = "select * from " + USER_TABLE_NAME + " where "
				+ User.USERID + " = '" + userId + "'";
		Cursor cursor = db.rawQuery(sql, null);
		int isSaved = 0;
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			isSaved = cursor.getInt(cursor.getColumnIndex(User.ISSAVED));
		}
		cursor.close();
		return isSaved;
	}

	/**
	 * ��ȡ�����û���Ϣ����
	 * 
	 * @param tableName
	 *            ����
	 * @return ���м�¼��list����
	 */
	public ArrayList<UserRecord> queryAllUserInfo() {
		System.out.println("��ѯ�����û���");
		ArrayList<UserRecord> list = new ArrayList<UserRecord>();
		if (db != null) {
			UserRecord userrecord = null;
			System.out.println("������");
			Cursor cursor = db.query(USER_TABLE_NAME, null, null, null, null,
					null, null);
			int count = cursor.getCount();

			System.out.println(count);
			if (count > 0) {
				cursor.moveToFirst();
				for (int i = 0; i < count; i++) {
					userrecord = new UserRecord();
					userrecord.setUserId(cursor.getString(cursor
							.getColumnIndex(User.USERID)));
					userrecord.setUserName(cursor.getString(cursor
							.getColumnIndex(User.USERNAME)));
					userrecord.setPassWord(cursor.getString(cursor
							.getColumnIndex(User.PASSWORD)));
					userrecord.setIsSave(cursor.getInt(cursor
							.getColumnIndex(User.ISSAVED)));
					byte[] in = cursor.getBlob(cursor
							.getColumnIndex(User.PICTURE));
					userrecord.setUserPic(BitmapFactory.decodeByteArray(in, 0,
							in.length));
					cursor.moveToNext();
					list.add(userrecord);
				}

			}
			cursor.close();
			System.out.println("list:" + list.size());
			return list;
		} else {
			return list;
		}

	}

	// /**
	// *
	// * queryAllUserId()
	// * (����������������������� �C ��ѡ)
	// * @return
	// *String[]
	// * @exception
	// * @since 1.0.0
	// */
	// public String[] queryAllUserId() {
	// System.out.println("��ѯ�����û���");
	//
	// if (db != null) {
	// System.out.println("������");
	// Cursor cursor = db.query(USER_TABLE_NAME, null, null, null, null,
	// null, null);
	// int count = cursor.getCount();
	// String[] userid = new String[count];
	// System.out.println(count);
	// if (count > 0) {
	// cursor.moveToFirst();
	// for (int i = 0; i < count; i++) {
	// userid[i] = cursor.getString(cursor
	// .getColumnIndex(User.USERID));
	// cursor.moveToNext();
	// }
	//
	// }
	// cursor.close();
	// return userid;
	// } else {
	// return null ;
	// }
	//
	// }

	/**
	 * �ر����ݿ�
	 */
	public void cleanup() {
		if (this.db != null) {
			this.db.close();
			this.db = null;
		}
	}

	/**
	 * ���ݿ⸨����
	 */
	private static class DBOpenHelper extends SQLiteOpenHelper {

		public DBOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table " + USER_TABLE_NAME + " (" + User._ID
					+ " integer primary key," + User.USERID + " text, "
					+ User.USERNAME + " text, " + User.PASSWORD + " text, "
					+ User.PICTURE + " blob, " + User.ISSAVED + " INTEGER)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
			onCreate(db);
		}

	}

}
