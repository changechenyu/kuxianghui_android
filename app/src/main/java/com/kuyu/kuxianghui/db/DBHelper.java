package com.kuyu.kuxianghui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private final static int DB_VERSION = 5; // change this version when the db
	private final static String DB_NAME = "columbus.db";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE record_table(_id INTEGER PRIMARY KEY AUTOINCREMENT,record VARCHAR(20))");
		db.execSQL("CREATE TABLE user_table(_id INTEGER PRIMARY KEY AUTOINCREMENT,account VARCHAR(20),password VARCHAR(20),token VARCHAR(20),pid VARCHAR(20),push INTEGER DEFAULT 1,cus_url VARCHAR(20),sale_url VARCHAR(20),time VARCHAR(20),is_sale VARCHAR(20),user_name VARCHAR(20),cus_bitmap BLOB,sale_bitmap BLOB)");
		db.execSQL("CREATE TABLE address_table(_id INTEGER PRIMARY KEY ,id VARCHAR(20),local_name VARCHAR(20),pid VARCHAR(20),version VARCHAR(20))");
		db.execSQL("CREATE TABLE bank_table(_id INTEGER PRIMARY KEY ,bank_name VARCHAR(20),bank_code VARCHAR(20),version VARCHAR(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// do something upgrade
		int upgradeVersion  = oldVersion; 
		if(1==upgradeVersion){
			db.execSQL("CREATE TABLE address_table(_id INTEGER PRIMARY KEY AUTOINCREMENT,id VARCHAR(20),local_name VARCHAR(20),pid VARCHAR(20),version VARCHAR(20))");
			db.execSQL("ALTER TABLE user_table ADD COLUMN push INTEGER DEFAULT 1");
			db.execSQL("ALTER TABLE user_table ADD COLUMN cus_url VARCHAR(20)");
			db.execSQL("ALTER TABLE user_table ADD COLUMN sale_url VARCHAR(20)");
			upgradeVersion=2;
		}
		if(2==upgradeVersion){
			db.execSQL("ALTER TABLE user_table ADD COLUMN time VARCHAR(20)");
			upgradeVersion=3;
		}
		if(3==upgradeVersion){
			db.execSQL("ALTER TABLE user_table ADD COLUMN is_sale VARCHAR(20)");
			db.execSQL("ALTER TABLE user_table ADD COLUMN user_name VARCHAR(20)");
			upgradeVersion=4;
		}
		if(4==upgradeVersion){
			db.execSQL("ALTER TABLE user_table ADD COLUMN cus_bitmap BLOB");
			db.execSQL("ALTER TABLE user_table ADD COLUMN sale_bitmap BLOB");
			upgradeVersion=5;
		}
		if(5==upgradeVersion){
			db.execSQL("CREATE TABLE bank_table(_id INTEGER PRIMARY KEY ,bank_name VARCHAR(20),bank_code VARCHAR(20),version VARCHAR(20))");
		}
		
		if (upgradeVersion != newVersion) {  
		    db.execSQL("DROP TABLE IF EXISTS " + "record_table"); 
		    db.execSQL("DROP TABLE IF EXISTS " + "user_table"); 
		    db.execSQL("DROP TABLE IF EXISTS " + "address_table"); 
		    onCreate(db);  
		 }  
		
	}

}
