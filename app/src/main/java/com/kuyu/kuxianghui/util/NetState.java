package com.kuyu.kuxianghui.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class NetState {
	
	public final static int CONNECTION_NO      = 0; //无网络连接
	public static final int CONNECTION_HOME    = 1;//内网中wifi
	public final static int CONNECTION_OUTSIDE = 2;//外网中wifi或使用移动数据
	//检查网络状态
	public static int checkState(Context context){
		int state = CONNECTION_NO;
		ConnectivityManager connectivityManager= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager!=null){
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if(networkInfo!=null&&networkInfo.isAvailable()&&networkInfo.isConnected()){
				if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					WifiManager wifiManager =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
					WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//					wifiInfo.getBSSID().equals(wifiMac);
					state = CONNECTION_HOME;
				}else {
					state =CONNECTION_OUTSIDE;
				}
			}
		} 
		return state;
	}
	
	
	/**
	 * 判断是否有网络
	 */
	@SuppressWarnings("deprecation")
	public static boolean IfNet(Context context){
		switch (NetState.checkState(context)) {
		case NetState.CONNECTION_NO:
			Toast.makeText(context, "网络断了哦,检查一下您的网络吧", Toast.LENGTH_SHORT).show();
			return true;
		default:
			break;
		}
		return false;
	}
	
	public static String getLocalMacAddress(Context context) {  
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    }  

    public static boolean isWifi(Context context) {      
        try {  
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
            return (wm!=null && WifiManager.WIFI_STATE_ENABLED == wm.getWifiState());  
        } catch (Exception e) {           
        }  
        return false;  
    }  
}
