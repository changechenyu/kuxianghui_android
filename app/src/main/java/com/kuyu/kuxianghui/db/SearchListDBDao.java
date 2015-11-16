package com.kuyu.kuxianghui.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchListDBDao {

	private Context mContext ;
	private DBHelper dbHelper;
	
	public SearchListDBDao(Context context){
	    mContext=context;
	    dbHelper= new DBHelper(mContext);
	}
	
	 //插入数据
	public void add(String record) {
	     SQLiteDatabase db = dbHelper.getWritableDatabase();
	     if (db.isOpen()) {
	          db.execSQL( "INSERT INTO record_table(record) VALUES(?)", new Object[] {record});
	          db.close();
	     }
	}
	
	//获得搜索记录List
	public List<String> getRecordList(){
	     SQLiteDatabase db = dbHelper.getReadableDatabase();
	     List<String> recordList = new ArrayList<String>();
	     if(db.isOpen()) {
	          Cursor cursor = db.rawQuery( "SELECT record FROM record_table GROUP BY record ORDER BY _id DESC LIMIT ?,?",new String[] { "0","6" });
	          while(cursor.moveToNext()) {
	              recordList.add(cursor.getString(cursor.getColumnIndex("record")));
	          }
	          cursor.close();
	          db.close();
	     }
	     return recordList;
	}
	
	//清空记录用的
	public void clearAllRecord(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
	          db.execSQL( "DELETE FROM record_table");
	          db.close();
	     }
	}

}
