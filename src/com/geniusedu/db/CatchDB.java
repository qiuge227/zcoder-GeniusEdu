package com.geniusedu.db;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.geniusedu.beans.StudentInfo;
import com.geniusedu.db.DBUser.User;

/**
 * 
 * 
 * �����ƣ�CatchDB �������� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-25 ����8:38:15 �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
public class CatchDB {

	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "userinfo.db";
	public static final String USER_TABLE_NAME = "userinfo_table";
	private SQLiteDatabase db;
	private DBOpenHelper dbOpenHelper;

	public CatchDB(Context context) {
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
	public long insertOrUpdate(Bitmap bit, String... param) {
		boolean isUpdate = false;
		String[] stuids = queryAllstuid();
		System.err.println("����һ������");
		for (int i = 0; i < stuids.length; i++) {
			if (param[0].equals(stuids[i])) {
				isUpdate = true;
				break;
			}
			System.out.println("�µ�");
		}
		long id = -1;
		if (isUpdate) {
			id = update(bit, param);
			System.out.println("isupdate" + id);
		} else {
			System.err.println("�µ�����");
			if (db != null) {
				System.out.println("db���գ�������");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bit.compress(Bitmap.CompressFormat.PNG, 100, baos);
				ContentValues values = new ContentValues();
				values.put("stuid", param[0]);
				values.put("stuname", param[1]);
				values.put("schoolname", param[2]);
				values.put("birthday", param[3]);
				values.put("stuclass", param[4]);
				values.put("points", param[5]);
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
	public long delete(String stuid) {
		long id = db.delete(USER_TABLE_NAME, "stuid" + " = '" + stuid + "'",
				null);
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
	public long update(Bitmap bit, String... param) {
		System.err.println("����һ������");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.PNG, 100, baos);
		ContentValues values = new ContentValues();
		values.put("stuid", param[0]);
		values.put("stuname", param[1]);
		values.put("schoolname", param[2]);
		values.put("birthday", param[3]);
		values.put("stuclass", param[4]);
		values.put("points", param[5]);
		values.put(User.PICTURE, baos.toByteArray());
		long id = db.update(USER_TABLE_NAME, values, "stuid" + " = '"
				+ param[0] + "'", null);
		return id;
	}

	/**
	 * �����û�id��ѯ��Ϣ
	 * 
	 * @param stuid
	 *            �û�id
	 * @param tableName
	 *            ����
	 * @return Hashmap ��ѯ�ļ�¼
	 */
	public StudentInfo queryinfoByid(String stuid) {
		String sql = "select * from " + USER_TABLE_NAME + " where " + "stuid"
				+ " = '" + stuid + "'";
		Cursor cursor = db.rawQuery(sql, null);
		StudentInfo stuinfo = null;
		System.err.println("����ѯ��");
		if (cursor.getCount() > 0) {

			stuinfo = new StudentInfo();
			cursor.moveToFirst();
			stuinfo.stuid = cursor.getString(cursor.getColumnIndex("stuid"));
			stuinfo.stuname = cursor
					.getString(cursor.getColumnIndex("stuname"));
			stuinfo.setStubirth(cursor.getString(cursor
					.getColumnIndex("birthday")));
			stuinfo.setStuclass(cursor.getString(cursor
					.getColumnIndex("stuclass")));
			stuinfo.setStuintegral(cursor.getString(cursor
					.getColumnIndex("points")));
			byte[] in = cursor.getBlob(cursor.getColumnIndex("stupic"));
			stuinfo.setStubitpic(BitmapFactory
					.decodeByteArray(in, 0, in.length));
			System.err.println(stuinfo.toString());
		}
		cursor.close();
		return stuinfo;
	}

	/**
	 * ��ѯ�����û���
	 * 
	 * @param tableName
	 *            ����
	 * @return ���м�¼��list����
	 */
	public String[] queryAllstuid() {
		System.err.println("��ѯ�����û���");
		if (db != null) {
			System.err.println("������");
			Cursor cursor = db.query(USER_TABLE_NAME, null, null, null, null,
					null, null);
			int count = cursor.getCount();
			String[] stuids = new String[count];
			System.out.println(count);
			if (count > 0) {
				cursor.moveToFirst();
				for (int i = 0; i < count; i++) {
					stuids[i] = cursor
							.getString(cursor.getColumnIndex("stuid"));
					cursor.moveToNext();
				}
				System.out.println(stuids.toString());
			}
			cursor.close();
			return stuids;
		} else {
			return new String[0];
		}

	}

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
			db.execSQL("create table " + USER_TABLE_NAME + " (" + "stuid"
					+ " text, " + "stuname" + " text, " + "stupic" + " image, "
					+ "schoolname" + " text, " + "birthday" + " text, "
					+ "stuclass" + " text, " + "points" + " text)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
			onCreate(db);
		}

	}

}
