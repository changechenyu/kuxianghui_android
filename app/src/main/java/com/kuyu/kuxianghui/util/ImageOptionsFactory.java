package com.kuyu.kuxianghui.util;

import android.graphics.Bitmap;
import com.kuyu.kuxianghui.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class ImageOptionsFactory {

	public static DisplayImageOptions newOptions(int resId){
		return new DisplayImageOptions.Builder()
		.showStubImage(resId)
		.showImageForEmptyUri(resId)
		.showImageOnFail(resId).cacheInMemory(true)
		.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();		
	}
	public static DisplayImageOptions newAppLogoOptions(){
		return new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.app_logo)
		.showImageForEmptyUri(R.drawable.app_logo)
		.showImageOnFail(R.drawable.app_logo).cacheInMemory(true)
		.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();		
	}
}
