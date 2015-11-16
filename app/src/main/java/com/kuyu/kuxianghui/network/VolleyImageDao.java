package com.kuyu.kuxianghui.network;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyImageDao {
	private static RequestQueue mQueue;
	private static ImageLoader imageLoader;
	private static VolleyImageDao instance=null;
	
	private VolleyImageDao(){
		
	}

	public static VolleyImageDao getInstance(Context context){
		if(instance==null){
			instance=new VolleyImageDao();
			mQueue= Volley.newRequestQueue(context);
			imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
				public void putBitmap(String url, Bitmap bitmap) {
				}

				public Bitmap getBitmap(String url) {
					return null;
				}
			});
			mQueue.start();
		}
		return instance;
	}
	
	public void setNetworkImage(NetworkImageView iv,String url){
		//iv.setDefaultImageResId(R.drawable.icon_goods);
		//iv.setErrorImageResId(R.drawable.icon_goods);
		iv.setImageUrl(url, imageLoader);
	}
}
