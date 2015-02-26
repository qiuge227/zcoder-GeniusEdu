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
 * 类名称：DBHelper 类描述： 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-17 上午12:56:18 修改备注：
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
	public long insertOrUpdate(String userName, String userId, Bitmap bit,
			String password, int isSaved) {
		long id = -1;

		if (queryPasswordById(userId)) {
			id = update(userName, userId, bit, password, isSaved);// 跟新一条数据
			System.out.println("isupdate" + id);
		} else {
			System.out.println("新的数据");
			if (db != null) {
				System.out.println("db不空，进来了");
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
	 * 删除一条记录
	 * 
	 * @param userName
	 *            用户名
	 * @param tableName
	 *            表名
	 * @return 删除记录的id -1表示删除不成功
	 */
	public long delete(String userId) {
		long id = db.delete(USER_TABLE_NAME, User.USERID + " = '" + userId
				+ "'", null);
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
	public long update(String username, String userId, Bitmap bit,
			String password, int isSaved) {
		System.out.println("跟新一条数据");
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
	 * 根据id查询用户是否存在
	 * 
	 * @param username
	 *            用户名
	 * @param tableName
	 *            表名
	 * @return Hashmap 查询的记录
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
			System.out.println("新德");
		}
		if (isUpdate) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 记住密码选项框是否被选中
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
	 * 获取所有用户信息集合
	 * 
	 * @param tableName
	 *            表名
	 * @return 所有记录的list集合
	 */
	public ArrayList<UserRecord> queryAllUserInfo() {
		System.out.println("查询所有用户名");
		ArrayList<UserRecord> list = new ArrayList<UserRecord>();
		if (db != null) {
			UserRecord userrecord = null;
			System.out.println("进来了");
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
	// * (这里描述这个方法适用条件 – 可选)
	// * @return
	// *String[]
	// * @exception
	// * @since 1.0.0
	// */
	// public String[] queryAllUserId() {
	// System.out.println("查询所有用户名");
	//
	// if (db != null) {
	// System.out.println("进来了");
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
