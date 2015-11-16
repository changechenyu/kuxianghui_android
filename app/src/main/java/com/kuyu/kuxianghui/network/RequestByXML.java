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

    public String result = "";

    //注册请求
    public static String postByRegister(String urlStr, String username, String pwd, String mobile, String email, String sex, String birthday, String address) {
        OutputStreamWriter out = null;
        String line = "";
        BufferedReader in = null;
        try {
            HttpURLConnection con = setRequest(urlStr);
            out = new OutputStreamWriter(con.getOutputStream());
            String xmlInfo = getXmlRegister(username, pwd, mobile, email, sex, birthday, address);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    //注册参数
    public static String getXmlRegister(String username, String userpwd, String mobile, String email, String sex, String birthday, String address) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<username>" + username + "</username>");
        sb.append("<userpwd>" + userpwd + "</userpwd>");
        sb.append("<mobile>" + mobile + "</mobile>");
        sb.append("<email>" + username + "</email>");
        if (sex != null) {
            sb.append("<sex>" + sex + "</sex>");//是否必填 否
        }
        if (birthday != null) {
            sb.append("<birthday>" + birthday + "</birthday>");//是否必填 否
        }
        if (address != null) {
            sb.append("<address>" + address + "</address>");//是否必填 否
        }
        sb.append("</xml>");
        return sb.toString();
    }

    //登陆参数
    //logintype	string	是	登录类型：app、web，手机端使用app
    public static String getXmlLogin(String useraccount, String userpwd, String logintype) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<useraccount>" + useraccount + "</useraccount>");
        sb.append("<userpwd>" + useraccount + "</userpwd>");
        sb.append("<logintype>" + useraccount + "</logintype>");
        sb.append("</xml>");
        return sb.toString();
    }

    //注销参数
    public static String getXmlGoOut(String userid, String logintype) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<userid>" + userid + "</userid>");
        sb.append("<logintype>" + logintype + "</logintype>");
        sb.append("</xml>");
        return sb.toString();
    }

    //修改密码参数
    public static String getXmlUpdatePwd(String userid, String oldpwd, String newpwd) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<userid>" + userid + "</userid>");
        sb.append("<oldpwd>" + oldpwd + "</oldpwd>");
        sb.append("<newpwd>" + newpwd + "</newpwd>");
        sb.append("</xml>");
        return sb.toString();
    }

    // 用户信息修改参数
    public static String getXmlUpdateUserInfo(String userid, String sex, String birthday, String address) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<userid>" + userid + "</userid>");
        if (sex != null) {
            sb.append("<sex>" + sex + "</sex>");//是否必填 否
        }
        if (birthday != null) {
            sb.append("<birthday>" + birthday + "</birthday>");//是否必填 否
        }
        if (address != null) {
            sb.append("<address>" + address + "</address>");//是否必填 否
        }
        sb.append("</xml>");
        return sb.toString();
    }

    //订购漫画参数
    public static String getXmlBuyCartoon(String userid, String appid, String paycode, String productcode, String partscode, String imei, String Imsi, int amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<userid>" + userid + "</userid>");
        sb.append("<appid>" + appid + "</appid>");
        sb.append("<paycode>" + paycode + "</paycode>");
        sb.append("<productcode>" + productcode + "</productcode>");
        sb.append("<partscode>" + partscode + "</partscode>");
        sb.append("<imei>" + imei + "</imei>");
        sb.append("<Imsi>" + Imsi + "</Imsi>");
        sb.append("<amount>" + amount + "</amount>");
        sb.append("</xml>");
        return sb.toString();
    }

    //订购记录查询参数
//    userid	string	是	用户id
//    searchtype	string	是	查询类型：3：三个月内订单:4：半年内订单:5：一年内订单:6：自定义查询
//    begintime	string	否	当查询类型（searchtype）为自定义时必填
//    endtime	string	否	当查询类型（searchtype）为自定义时必填
    public static String getXmlOrderSearch(String userid, String searchtype, String begintime, String endtime) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<userid>" + userid + "</userid>");
        sb.append("<searchtype>" + searchtype + "</searchtype>");
        if (begintime != null) {
            sb.append("<begintime>" + begintime + "</begintime>");
        }
        if (endtime != null) {
            sb.append("<endtime>" + endtime + "</endtime>");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    //    获取首页作品列表
//userid	string	否	用户id，若未登录则为空
//    searchtype	string	是	查询类型：3:首页推荐、4:热门排行、5:最新上架
    public static String getXmlHomePage(String userid, String searchtype) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        if (userid != null) {
            sb.append("<userid>" + userid + "</userid>");
        }
        sb.append("<searchtype>" + searchtype + "</searchtype>");
        sb.append("</xml>");
        return sb.toString();

    }
//    userid	string	否	用户id，若未登录则为空
//    searchvalue	string	是	查询值，作品名、代码、作者
//    currentpagecount	int		当前页序号（分页）
//    eachpagecount	int		每页显示数量（分页）
    //获取全部列表
    public static String getXmlProductList(String userid,String searchvalue,int currentpagecount,int eachpagecount){
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        if (userid != null) {
            sb.append("<userid>" + userid + "</userid>");
        }
        sb.append("<searchvalue>" + searchvalue + "</searchvalue>");
        if (currentpagecount != 0) {
            sb.append("<currentpagecount>" + currentpagecount + "</currentpagecount>");
        }
        if (eachpagecount != 0) {
            sb.append("<eachpagecount>" + eachpagecount + "</eachpagecount>");
        }
        sb.append("</xml>");
        return sb.toString();
    }
    //获取单部作品详情
//    userid	string	否	用户id，若未登录则为空
//    productcode	string	是	作品代码
    public static String getXmlProductDetail(String  userid,String productcode){
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        if (userid != null) {
            sb.append("<userid>" + userid + "</userid>");
        }
        sb.append("<productcode>" + productcode + "</productcode>");
        sb.append("</xml>");
        return sb.toString();
    }
    //获取单部作品单话列表
//    userid	string	否	用户id，若未登录则为空
//    productcode	string	是	查询值，作品名、代码、作者
    public static String getXmlProductPartsList(String userid,String productcode){
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        if (userid != null) {
            sb.append("<userid>" + userid + "</userid>");
        }
        sb.append("<productcode>" + productcode + "</productcode>");
        sb.append("</xml>");
        return sb.toString();
    }
    //获取单话作品详情
//    userid	string	否	用户id，若未登录则为空
//    productcode	string	是	作品代码
//    partscode	string	是	话数代码
    public static String getXmlProductPartsDetail(String userid,String productcode,String partscode){
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        if (userid != null) {
            sb.append("<userid>" + userid + "</userid>");
        }
        sb.append("<productcode>" + productcode + "</productcode>");
        sb.append("<partscode>" + partscode + "</partscode>");
        sb.append("</xml>");
        return sb.toString();
    }
    //得到连接
    public static HttpURLConnection setRequest(String urlStr) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlStr);
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

}
