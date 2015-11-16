package com.kuyu.kuxianghui.util;

import android.util.Log;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MD5Util {
	 // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
	public static String MD5(String strObj) {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
			Log.d("MD5Util", resultString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString;
	}
	
	 // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString().toUpperCase();
    }
    
 // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    public static void main(String[] args){
//        String a = "appid=wxd930ea5d5a258f4f&auth_code=123456&body=test&device_info=123&mch_id=1900000109&nonce_str=960f228109051b9969f76c82bde183ac&out_trade_no=1400755861 &spbill_create_ip=127.0.0.1&total_fee=1&key=8934e7d15453e97507ef794cf7b0519d";
//        System.out.println(MD5Util.MD5(a));

        //LinkedHashMap 有序
        LinkedHashMap<String, Object> maps = new LinkedHashMap<String, Object>();
        maps.put("1", "张三");
        maps.put("2", "李四");
        maps.put("3", "王五");
        maps.put("4", "赵六");
        System.out.println("LinkedHashMap(有序):");
        Iterator it = maps.entrySet().iterator();
        maps.keySet();
        while(it.hasNext())
        {
            Map.Entry entity = (Map.Entry) it.next();
            System.out.println("[ key = " + entity.getKey() +
                    ", value = " + entity.getValue() + " ]");
        }
        //HashMap 无序
        Map map = new HashMap();
        map.put("1", "张三");
        map.put("2", "李四");
        map.put("3", "王五");
        map.put("4", "赵六");
        it = null;
        System.out.println("HashMap(无序):");
        it = map.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry entity = (Map.Entry) it.next();
            System.out.println("[ key = " + entity.getKey() +
                    ", value = " + entity.getValue() + " ]");
        }
    }
}
