package com.kuyu.kuxianghui.ui.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {

	SharedPreferences mSharedPreferences ;
	
	public AppSharedPreferences(Context context) {
		mSharedPreferences = context.getSharedPreferences("AppPrefs", Activity.MODE_PRIVATE);
    }
	
	public Boolean get_first(){
		return get("first", true);
	}
	public void save_first(Boolean value){
		save("first", value);
	}
	
	public String get_token(){
		return get("token", "");
	}
	public void save_token(String value){
		save("token", value);
	}

	public void remove_token(){
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.remove("token");
		editor.commit();
	}

	
	public void save(String key, String value) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public void save(String key, int value) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	public void save(String key, Boolean value) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	public void save(String key, Float value) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	public void save(String key, Long value) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public String get(String key, String defaultValue) {
		return mSharedPreferences.getString(key, defaultValue);
	}
	public int get(String key, int defaultValue) {
		return mSharedPreferences.getInt(key, defaultValue);
	}
	public Boolean get(String key, Boolean defaultValue) {
		return mSharedPreferences.getBoolean(key, defaultValue);
	}
	public Float get(String key, Float defaultValue) {
		return mSharedPreferences.getFloat(key, defaultValue);
	}
	public Long get(String key, Long defaultValue) {
		return mSharedPreferences.getLong(key, defaultValue);
	}


}
