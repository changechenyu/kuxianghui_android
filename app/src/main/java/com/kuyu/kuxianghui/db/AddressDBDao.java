package com.kuyu.kuxianghui.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressDBDao {

	private static Context mContext ;
	private static DBHelper dbHelper;
	private static AddressDBDao instance=null;
	
	
	public static AddressDBDao getInstance(Context context){
		if(instance==null){
			instance = new AddressDBDao();
			mContext=context;
		    dbHelper= new DBHelper(mContext);
		}
		return instance ;
	}
	
	private AddressDBDao(){
		
	}
	
	
	 //插入数据
	public void add(String id,String localName,String pid) {
	     SQLiteDatabase db = dbHelper.getWritableDatabase();
	     if (db.isOpen()) {
	          db.execSQL( "INSERT INTO address_table(id,local_name,pid) VALUES(?,?,?)", new Object[] {id,localName,pid});
	          db.close();
	     }
	}
	
	//获得省份List
	public List<Map<String,String>> getProvincesList(){
	     SQLiteDatabase db = dbHelper.getReadableDatabase();
	     List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	     if(db.isOpen()) {
	    	  Cursor cursor = db.rawQuery( "SELECT * FROM address_table WHERE pid=?",new String[] { "0"});
	          while(cursor.moveToNext()) {
	        	  Map<String, String> map = new HashMap<String, String>();
	        	  map.put("id", cursor.getString(cursor.getColumnIndex("id")));
	        	  map.put("local_name", cursor.getString(cursor.getColumnIndex("local_name")));
	        	  list.add(map);
	          }
	          cursor.close();
	          db.close();
	     }
	     return list;
	}
	
	//获得城市List
	public List<Map<String,String>> getCityList(String provinceId){
	     SQLiteDatabase db = dbHelper.getReadableDatabase();
	     List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	     if(db.isOpen()) {
	    	  Cursor cursor = db.rawQuery( "SELECT * FROM address_table WHERE pid=?",new String[] { provinceId});
	          while(cursor.moveToNext()) {
	        	  Map<String, String> map = new HashMap<String, String>();
	        	  map.put("id", cursor.getString(cursor.getColumnIndex("id")));
	        	  map.put("local_name", cursor.getString(cursor.getColumnIndex("local_name")));
	        	  list.add(map);
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
	          Cursor cursor = db.rawQuery( "SELECT version FROM address_table",new String[] {});
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
			  db.execSQL( "DELETE FROM address_table");
	          db.close();
	     }
	}

}
