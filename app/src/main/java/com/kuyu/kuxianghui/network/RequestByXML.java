package com.kuyu.kuxianghui.network;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/14.
 */
public class RequestByXML {

       public String result="";

       public  static String  postByRegister(String urlStr,String username,String pwd,String mobile,String email,String sex,String birthday,String address) {
            OutputStreamWriter out=null;
            String line = "";
            BufferedReader in=null;
            try {
                HttpURLConnection con=setRequest(urlStr);
                out = new OutputStreamWriter(con.getOutputStream());
                String xmlInfo = getXmlRegister(username, pwd, mobile,email, sex, birthday, address);
                System.out.println("urlStr=" + urlStr);
                System.out.println("xmlInfo=" + xmlInfo);
                out.write(new String(xmlInfo.getBytes("ISO-8859-1")));
                out.flush();
                out.close();
                // 使用输出流来输出字符(可选)
                if (con.getResponseCode() != 200) throw new RuntimeException("请求url失败");
                InputStream is = con.getInputStream();// 获取返回数据
                ByteArrayOutputStream baout = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) != -1) {
                    baout.write(buf, 0, len);
                }
                line = new String(baout.toByteArray());
                System.out.println(line);
                out.close();
                return line;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
             return line;
        }

      public static String getXmlRegister(String username,String userpwd,String mobile,String email,String sex,String birthday,String address) {
          StringBuilder sb=new StringBuilder();
          sb.append("<xml>");
          sb.append("<username>"+username+"</username>");
          sb.append("<userpwd>"+userpwd+"</userpwd>");
          sb.append("<mobile>"+mobile+"</mobile>");
          sb.append("<email>"+username+"</email>");
          sb.append("<sex>"+sex+"</sex>");
          sb.append("<birthday>"+birthday+"</birthday>");
          sb.append("<address>"+address+"</address>");
          sb.append("</xml>");
          return sb.toString();
        }

//       public static String

        public static HttpURLConnection  setRequest(String urlStr){
            HttpURLConnection con = null;
            try {
                URL  url = new URL(urlStr);
                try {
                    con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Pragma:", "no-cache");
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "no-cache");
                    con.setRequestProperty("Content-Type", "text/xml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return con;
        }
    public static String setXml(ArrayList<String> datas){
        StringBuilder sb=new StringBuilder();
        sb.append("<xml>");
        for(int i=0;i<datas.size();i++){
            sb.append("<"+datas.get(i)+">"+datas.get(i)+"</"+datas.get(i)+">");
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
