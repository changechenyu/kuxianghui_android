package com.kuyu.kuxianghui.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.kuyu.kuxianghui.config.Constants;

/**
 * Created by Administrator on 2015/11/5.
 */
public class BaiduLocationUtil{
    public LocationClient mLocationClient=null;
    public MyLocationListener mMyLocationListener=null;
    public static String  city="惠州市";
    public static String  provice="广东省";
    public static String addStr="";
    public static Double lat;//纬度
    public static Double lon;//经度
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // Receive Location
//            appSession.setBdLocation(location);
            Log.i("baidu", "baidu");
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\ndirection : ");
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append(location.getDirection());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\noperationers:");
                sb.append(location.getOperators());
            }
            lat= location.getLatitude();
            lon=location.getLongitude();
            Log.i("province",location.getProvince()+"");
            Log.i("city",location.getCity()+"");
            city=location.getCity();
            provice=location.getProvince();
            addStr=location.getAddrStr();
            Log.i("lat", "" + lat);
            Log.i("lon",""+lon);
            Log.i("BaiduLocationApiDem", sb.toString());
            Log.i("location",location.getAddrStr());
        }
    }
    /**
     * 用这个方法的时候要注意ct是 使用这个函数的context 而不是这个类的context 方法的参数应该是this.getApplication传到这个方法里面去
     * @param ct
     */
    public void location(Context ct) {
        mLocationClient = new LocationClient(ct);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);// 中文地址
        option.setCoorType("bd09ll");// gcj02 国测局经纬度坐标系 ；bd09 百度墨卡托坐标系；bd09ll
        // 百度经纬度坐标系
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);// 设置定位模式
        option.setScanSpan(5*60000);//检查周期 小于1秒的按1秒
        mLocationClient.setLocOption(option);
        Log.i("2", "2");
        mLocationClient.registerLocationListener(new MyLocationListener());
        mLocationClient.start();
    }

 /*
 * 定位得到城市
 */
    public String  getCity(Context ct){
        location(ct);
        if(TextUtils.isEmpty(city)&&TextUtils.isEmpty(provice)&&TextUtils.isEmpty(addStr)){
            Log.i("APPCity",city);
            return Constants.RESUlTCODE_SUCCESS;
        }else{
            return Constants.ISNULL;
        }
    }
}
