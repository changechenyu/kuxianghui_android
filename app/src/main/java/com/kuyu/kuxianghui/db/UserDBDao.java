package com.kuyu.kuxianghui.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDBDao {

	private static Context mContext;
	private static DBHelper dbHelper;
	private static UserDBDao instance=null;


	public static UserDBDao getInstance(Context context){
		if(instance==null){
			instance = new UserDBDao();
			mContext = context;
			dbHelper = new DBHelper(mContext);
		}
		return instance ;
	}
	
	private UserDBDao(){
		
	}

	// 记录登录账号和密码
	public void addUserRecord(String account, String token, String pid,
			String time, String issale,String user_name) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("DELETE FROM user_table");
			db.execSQL(
					"INSERT INTO user_table(account,token,pid,time,is_sale,user_name) VALUES(?,?,?,?,?,?)",
					new Object[] { account, token, pid, time ,issale,user_name});
			db.close();
		}
	}

	// 返回账号
	public String getAcount() {
		String account = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("SELECT * FROM user_table LIMIT 0,1",
					null);
			while (cursor.moveToNext()) {
				account = cursor.getString(cursor.getColumnIndex("account"));
			}
			db.close();
		}
		return account;

	}
	
	// 返回账号
	public String getUserName() {
		String userName = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("SELECT * FROM user_table LIMIT 0,1",
					null);
			while (cursor.moveToNext()) {
				userName = cursor.getString(cursor.getColumnIndex("user_name"));
			}
			db.close();
		}
		return userName;

	}

	// 返回上次登录时间
	public String getTime() {
		String account = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("SELECT * FROM user_table LIMIT 0,1",
					null);
			while (cursor.moveToNext()) {
				account = cursor.getString(cursor.getColumnIndex("time"));
			}
			db.close();
		}
		return account;

	}

	// 写入此次时间
	public void setTime(String time, String pid) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("UPDATE user_table SET time=? WHERE pid=?",
					new Object[] { time, pid });
		}
		db.close();
	}

	// 返回Token
	public String getToken() {
		String token = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("SELECT * FROM user_table LIMIT 0,1",
					null);
			while (cursor.moveToNext()) {
				token = cursor.getString(cursor.getColumnIndex("token"));
			}
			db.close();
		}
		return token;
	}

	// 返回PID
	public String getPid() {
		String pid = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("SELECT * FROM user_table LIMIT 0,1",
					null);
			while (cursor.moveToNext()) {
				pid = cursor.getString(cursor.getColumnIndex("pid"));
			}
			db.close();
		}
		return pid;
	}

	// 返回isSale
	public String getIsSale() {
		String issale = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("SELECT * FROM user_table LIMIT 0,1",
					null);
			while (cursor.moveToNext()) {
				issale = cursor.getString(cursor.getColumnIndex("is_sale"));
			}
			db.close();
		}
		return issale;
	}

	// 注销时，password,pid,token
	public void deletePsw(String pid) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("DELETE FROM user_table WHERE pid=?",
					new Object[] {pid });
			db.close();
		}
	}

	// 更新token
	public void updateToken(String token, String pid) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("UPDATE user_table SET token=? WHERE pid=?",
					new Object[] { token, pid });
			db.close();
		}
	}

	// 更新token和时间
	public void updataToken(String token, String time, String pid) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("UPDATE user_table SET token=?,time=? WHERE pid=?",
					new Object[] { token, time, pid });
			db.close();
		}
	}

	// 设置是否推送，1表示推送
	public void setPush(boolean isPush, String pid) {
		int push = 1;
		if (isPush) {
			push = 1;
		} else {
			push = 0;
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("UPDATE user_table SET push=? WHERE pid=?",
					new Object[] { push, pid });
			db.close();
		}
	}

	//
	public boolean getPush() {
		int push = 1;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("SELECT * FROM user_table LIMIT 0,1",
					null);
			while (cursor.moveToNext()) {
				push = cursor.getInt(cursor.getColumnIndex("push"));
			}
			db.close();
		}
		if (push == 1) {
			return true;
		} else {
			return false;
		}
	}

	// 记录邀请客户、分销员二维码
	public void addInviteCode(String cus_url, String sale_url, String pid) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("UPDATE user_table SET cus_url=?,sale_url=? WHERE pid=?",
					new Object[] { cus_url, sale_url, pid });
			db.close();
		}
	}

	// get cus_url
	public String getCusImageUrl(String pid) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String cusUrl = null;
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("SELECT * FROM user_table LIMIT 0,1",
					null);
			while (cursor.moveToNext()) {
				cusUrl = cursor.getString(cursor.getColumnIndex("cus_url"));
			}
			db.close();
		}
		if(cusUrl==null)return "";
		return cusUrl;
	}

	// get sale_url
	public String getSalerImageUrl(String pid) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String saleUrl = null;
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("SELECT * FROM user_table LIMIT 0,1",
					null);
			while (cursor.moveToNext()) {
				saleUrl = cursor.getString(cursor.getColumnIndex("sale_url"));
			}
			db.close();
		}
		if(saleUrl==null)return "";
		return saleUrl;
	}
	
}
