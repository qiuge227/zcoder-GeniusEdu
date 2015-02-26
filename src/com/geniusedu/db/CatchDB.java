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
 * 类名称：CatchDB 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-25 上午8:38:15 修改备注：
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
	 * 打开数据库
	 */
	private void establishDb() {
		if (this.db == null) {
			this.db = this.dbOpenHelper.getWritableDatabase();
		}
	}

	/**
	 * 插入一条记录
	 * 
	 * @param map
	 *            要插入的记录
	 * @param tableName
	 *            表名
	 * @return 插入记录的id -1表示插入不成功
	 */
	public long insertOrUpdate(Bitmap bit, String... param) {
		boolean isUpdate = false;
		String[] stuids = queryAllstuid();
		System.err.println("插入一条数据");
		for (int i = 0; i < stuids.length; i++) {
			if (param[0].equals(stuids[i])) {
				isUpdate = true;
				break;
			}
			System.out.println("新德");
		}
		long id = -1;
		if (isUpdate) {
			id = update(bit, param);
			System.out.println("isupdate" + id);
		} else {
			System.err.println("新的数据");
			if (db != null) {
				System.out.println("db不空，进来了");
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
	 * 删除一条记录
	 * 
	 * @param userName
	 *            用户名
	 * @param tableName
	 *            表名
	 * @return 删除记录的id -1表示删除不成功
	 */
	public long delete(String stuid) {
		long id = db.delete(USER_TABLE_NAME, "stuid" + " = '" + stuid + "'",
				null);
		return id;
	}

	/**
	 * 更新一条记录
	 * 
	 * @param
	 * 
	 * @param tableName
	 *            表名
	 * @return 更新过后记录的id -1表示更新不成功
	 */
	public long update(Bitmap bit, String... param) {
		System.err.println("跟新一条数据");
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
	 * 根据用户id查询信息
	 * 
	 * @param stuid
	 *            用户id
	 * @param tableName
	 *            表名
	 * @return Hashmap 查询的记录
	 */
	public StudentInfo queryinfoByid(String stuid) {
		String sql = "select * from " + USER_TABLE_NAME + " where " + "stuid"
				+ " = '" + stuid + "'";
		Cursor cursor = db.rawQuery(sql, null);
		StudentInfo stuinfo = null;
		System.err.println("来查询了");
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
	 * 查询所有用户名
	 * 
	 * @param tableName
	 *            表名
	 * @return 所有记录的list集合
	 */
	public String[] queryAllstuid() {
		System.err.println("查询所有用户名");
		if (db != null) {
			System.err.println("进来了");
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
	 * 关闭数据库
	 */
	public void cleanup() {
		if (this.db != null) {
			this.db.close();
			this.db = null;
		}
	}

	/**
	 * 数据库辅助类
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
