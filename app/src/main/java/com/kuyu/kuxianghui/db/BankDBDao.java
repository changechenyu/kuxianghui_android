package com.kuyu.kuxianghui.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BankDBDao {

	private static Context mContext ;
	private static DBHelper dbHelper;
	private static BankDBDao instance=null;
	
	public BankDBDao(Context context){
	    mContext=context;
	    dbHelper= new DBHelper(mContext);
	}
	
	public static BankDBDao getInstance(Context context){
		if(instance==null){
			instance = new BankDBDao();
			mContext=context;
		    dbHelper= new DBHelper(mContext);
		}
		return instance ;
	}
	
	private BankDBDao(){
		
	}
	
	//插入数据
	public void add(String name,String code) {
	     SQLiteDatabase db = dbHelper.getWritableDatabase();
	     if (db.isOpen()) {
	          db.execSQL( "INSERT INTO bank_table(bank_name,bank_code) VALUES(?,?)", new Object[] {name,code});
	          db.close();
	     }
	}
	
	//获得银行名的List
	public List<String> getProvincesList(){
	     SQLiteDatabase db = dbHelper.getReadableDatabase();
	     List<String> list = new ArrayList<String>();
	     if(db.isOpen()) {
	    	  Cursor cursor = db.rawQuery( "SELECT bank_name FROM bank_table",new String[] {});
	          while(cursor.moveToNext()) {
	        	  String name=cursor.getString(cursor.getColumnIndex("id"));
	        	  list.add(name);
	          }
	          cursor.close();
	          db.close();
	     }
	     return list;
	}
	
	//获取版本
	public String getVersion(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String version="";
		if(db.isOpen()) {
	          Cursor cursor = db.rawQuery( "SELECT version FROM bank_table",new String[] {});
	          while(cursor.moveToNext()) {
	              version=cursor.getString(cursor.getColumnIndex("version"));
	          }
	          cursor.close();
	          db.close();
	     }
	     return version;
	}
	
	//清空记录用的
	public void clearAllRecord(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			  db.execSQL( "DELETE FROM bank_table");
	          db.close();
	     }
	}

}
