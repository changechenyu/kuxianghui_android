package com.kuyu.kuxianghui.util;

import android.content.Context;
import com.kuyu.kuxianghui.ui.dialog.CustomProgressDialog;

public class DialogUtil {
	private static CustomProgressDialog progressDialog = null;

	public static void showProgressDialog(Context mContext){
		if (progressDialog == null){
			progressDialog = CustomProgressDialog.createDialog(mContext);
	    	//progressDialog.setMessage(mContext.getString(R.string.is_loading));
		}
		if(progressDialog.isShowing()) return;
		progressDialog.show();
	}
	
	public static void exitProgressDialog() {
		if (progressDialog != null){
			if(progressDialog.isShowing()){
				progressDialog.dismiss();
				progressDialog = null;
			}
		}
	}
}
