package com.kuyu.kuxianghui.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kuyu.kuxianghui.util.MD5Util;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class VolleyRequest {

    private final static String v = "2.0";
    private final static String format = "json";
    private final static String appkey = "199cf7bdf2dd4ea0da93cb53558e79a7";
    private JSONObject request;
    private static VolleyRequest instance = null;

    private static Gson mGson;

    public static VolleyRequest getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyRequest();
            mGson = new Gson();
        }
        return instance;
    }

    private VolleyRequest() {

    }

    /**
     * Make a sign
     *
     * @param content the content you want to sign
     */
    private static String generateSign(String content) {
        StringBuilder requestParametersBuilder = new StringBuilder(content);
        requestParametersBuilder.append(appkey);
        String requestParameters = requestParametersBuilder.toString();
        if (!"".equals(requestParameters)) {
            return MD5Util.MD5(requestParameters).toUpperCase();
        } else {
            return "";
        }
    }

    /**
     * Make a sign
     *
     * @param params the content you want to sign
     */
    private static String generateSign(JSONObject params) {
        TreeMap mTreeMap = new TreeMap();
        try {
            Iterator it = params.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                mTreeMap.put(key, params.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringBuilder requestParametersBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> treeIt = mTreeMap.entrySet().iterator();
        while (treeIt.hasNext()) {
            Map.Entry<String, String> entry = treeIt.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            requestParametersBuilder.append(entry.getKey()).append(entry.getValue());
        }
        requestParametersBuilder.append(appkey);
        Log.i("treeMapToString", requestParametersBuilder.toString());
        String requestParameters = requestParametersBuilder.toString();
        if (!"".equals(requestParameters)) {
            return MD5Util.MD5(requestParameters).toUpperCase();
        } else {
            return "";
        }
    }

    private void newRequest() {
        request = new JSONObject();
    }

    // login no code
    public JSONObject newLoginRequest(String username, String password) {
        newRequest();
        try {
            request.put("opeType", "quickLogin");
            JSONObject tParams = new JSONObject();
            tParams.put("username",username);
            tParams.put("password", password);
            request.put("map",tParams);
            request.put("sign", generateSign(tParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }
    // Login has code
    public JSONObject newLoginRequest(String username, String password,String verifycode,
                                      String verifycodetag) {
        newRequest();
        try {
            request.put("opeType", "quickLogin");
            JSONObject tParams = new JSONObject();
            tParams.put("password", password);
            tParams.put("username", username);
            tParams.put("verifycode",verifycode);
            tParams.put("verifycodetag",verifycodetag);
            request.put("map",tParams);
            request.put("sign", generateSign(tParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }
    //修改密码
    public JSONObject changePasswordRequest(String token,String oldPassword,String newPassword,String confirmpwd){
        newRequest();
        try {
            request.put("opeType", "changePassword");
            JSONObject tParams = new JSONObject();
            tParams.put("token", token);
            tParams.put("oldPassword", oldPassword);
            tParams.put("newPassword", newPassword);
            tParams.put("confirmpwd", confirmpwd);
            request.put("map",tParams);
            request.put("sign", generateSign(tParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }
    //check token
    public JSONObject checkTokenRequest(String token){
        newRequest();
        try {
            request.put("opeType", "checkToken");
            JSONObject tParams = new JSONObject();
            tParams.put("token", token);
            request.put("map",tParams);
            request.put("sign", generateSign(tParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }
    //getUserInfo
    public JSONObject getUserInfoRequest(String token){
        newRequest();
        try {
            request.put("opeType", "getUserInfo");
            JSONObject tParams = new JSONObject();
            tParams.put("token", token);
            request.put("map",tParams);
            request.put("sign", generateSign(tParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }
    /**
     * 获取短信验证码
     *
     * @param mobile_phone
     * @return
     */
    public JSONObject newGetMobileCodeRequest(String mobile_phone) {
        newRequest();
        try {
            request.put("opeType", "getMobileCode");
            JSONObject tParams = new JSONObject();
            tParams.put("mobile_phone", mobile_phone);
            request.put("sign", generateSign(tParams));
            request.put("map", tParams);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    //change code
    public JSONObject changeCode(){
        newRequest();
        try {
            request.put("opeType","getCaptcha");
            JSONObject tParams = new JSONObject();
            request.put("map",tParams);
            request.put("sign",generateSign(tParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    //check code
    public  JSONObject CheckCode(String key,String code){
        newRequest();
        try {
            request.put("opeType", "checkCaptcha");
            JSONObject tParams = new JSONObject();
            tParams.put("captchakey",key);
            tParams.put("captchadata",code);
            request.put("map",tParams);
            request.put("sign",generateSign(tParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;

    }
    // Send Validate number
    public JSONObject getMobileCode(String mobile) {
        newRequest();
        try {
            JSONObject tParams = new JSONObject();
            request.put("opeType", "getMobileCode");
            tParams.put("mobile",mobile);
            request.put("map",tParams);
            request.put("sign",generateSign(tParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }
    //check username
    public JSONObject checkAccountName(String username,String key,String code){
        newRequest();
        JSONObject tParams = new JSONObject();
        try {
            request.put("opeType", "checkAccountName");
            tParams.put("username",username);
            tParams.put("captchadata",code);
            tParams.put("captchakey",key);
            request.put("map",tParams);
            request.put("sign",generateSign(tParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }
    // Register
    public JSONObject newRegisterRequest(String mobile, String password,String comfirmpwd,String code) {
        newRequest();
        try {
            request.put("opeType","register");
            JSONObject tParams = new JSONObject();
            tParams.put("mobile", mobile);
            tParams.put("password", password);
            tParams.put("comfirmpwd", comfirmpwd);
            tParams.put("code",code);
            request.put("map",tParams);
            request.put("sign", generateSign(tParams));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }
    //findPwd
    public JSONObject updatePassWord(String username,String password,String comfirmPwd,String safecode){
        newRequest();
        try {
            request.put("opeType", "updatePassWord");
            JSONObject tParams = new JSONObject();
            tParams.put("username",username);
            tParams.put("password",password);
            tParams.put("confirmpwd",comfirmPwd);
            tParams.put("safecode",safecode);
            request.put("map",tParams);
            request.put("sign", generateSign(tParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // Reset Password
    public JSONObject newResetPswRequest(String token, String old_password,
                                         String new_password) {
        newRequest();
        try {
            request.put("opeType", "user.password.reset");
            request.put("token", token);
            request.put("old_password", old_password);
            request.put("new_password", new_password);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // Find Password
    public JSONObject newFindPswRequest(String input) {
        newRequest();
        try {
            request.put("opeType", "user.password.find");
            request.put("user", input);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // Update User
    public JSONObject newUserUpGradeRequest(String realName, String phoneNum,
                                            String verifycode, String token) {
        newRequest();
        try {
            request.put("opeType", "user.upgrade");
            request.put("token", token);
            request.put("real_name", realName);
            request.put("mobilephone", phoneNum);
            request.put("verifycode", verifycode);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    //1、Email ,2、UserName,3、PhoneNum
    public JSONObject newIsCorrectRequeset(String token, String username, int type) {
        newRequest();
        try {
            request.put("opeType", "user.validate");
            request.put("username", username);
            request.put("token", token);
            request.put("type", type);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // Check vercode Validate
    public JSONObject newVerCodeIsCorrectRequeset(String phoneNum, String verCode) {
        newRequest();
        try {
            request.put("opeType", "user.check.verify.code");
            request.put("mobilephone", phoneNum);
            request.put("verifycode", verCode);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // Upload Share Message
    public JSONObject newShareMsgRequest(String token, String url, String content,
                                         String platName, String comefrom) {
        newRequest();
        try {
            request.put("opeType", "share.add");
            request.put("token", token);
            request.put("url", url);
            request.put("content", content);
            request.put("platName", platName);
            request.put("comefrom", comefrom);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // My Income
    public JSONObject newMyIcomeRequest(String token, int page, int limit, String showType) {
        newRequest();
        try {
            request.put("opeType", "income.list");
            request.put("page", page);
            request.put("limit", limit);
            request.put("showType", showType);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // Upload Share Message
    public JSONObject newMyCustomerRequest(String token, int page, int limit) {
        newRequest();
        try {
            request.put("opeType", "custom.list");
            request.put("token", token);
            request.put("page", page);
            request.put("limit", limit);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // Upload Suggest
    public JSONObject newUploadSuggestRequest(String suggest, String contact, String token) {
        newRequest();
        try {
            request.put("opeType", "add.feedback");
            request.put("token", token);
            request.put("content", suggest);
            request.put("link", contact);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // gather info
    public JSONObject newGatherInfoRequest(String token) {
        newRequest();
        try {
            request.put("opeType", "gather.info");
            request.put("token", token);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // get new order num
    public JSONObject newNewOrderRequest(String token) {
        newRequest();
        try {
            request.put("opeType", "new.order.num");
            request.put("token", token);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // get new invite request
    public JSONObject newInviteRequest(String token, String email, String realName, int inviteType) {
        newRequest();
        try {
            request.put("opeType", "user.invite");
            request.put("token", token);
            request.put("receiver_email", email);
            request.put("receiver", realName);
            request.put("invite_type", inviteType);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // sent pay result to service
    public JSONObject newUpdateOrderStateRequest(String token, String orderNum, String tradeNum) {
        newRequest();
        try {
            request.put("opeType", "edit.pay.status");
            request.put("token", token);
            request.put("order_sn", orderNum);
            request.put("trade_no", tradeNum);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // get invite image
    public JSONObject newGetInviteImage(String token) {
        newRequest();
        try {
            request.put("opeType", "user.rqcode");
            request.put("token", token);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // get fund info
    public JSONObject newGetFundInfoRequest(String token) {
        newRequest();
        try {
            request.put("opeType", "withdraw.info");
            request.put("token", token);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // get local info
    public JSONObject newGetLocalInfoRequest(String version) {
        newRequest();
        try {
            request.put("opeType", "district.all");
            request.put("version", version);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // apply draw
    public JSONObject newTakeCashRequest(String token, String moneyTake, JSONObject bankInfo) {
        newRequest();
        try {
            request.put("opeType", "apply.draw");
            request.put("token", token);
            request.put("amount", moneyTake);
            request.put("bankInfo", bankInfo);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // get new token
    public JSONObject newGetTokenRequest(String token) {
        newRequest();
        try {
            request.put("opeType", "user.get.new.token");
            request.put("token", token);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // get unionpay tn
    public JSONObject newGetTNRequest(String token, String orderId) {
        newRequest();
        try {
            request.put("opeType", "get.union.tn");
            request.put("token", token);
            request.put("order_id", orderId);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // get bank info
    public JSONObject newGetBankInfoRequeset(String token) {
        newRequest();
        try {
            request.put("opeType", "bank.code");
            request.put("token", token);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    //get good cate list
    public JSONObject newGetCatRequest(boolean isGetChild) {
        newRequest();
        try {
            request.put("opeType", "mall.goods.cat");
            request.put("get_child", isGetChild);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // Alipay
    public JSONObject newAliPayRequest(String orderId, String token) {
        newRequest();
        try {
            request.put("opeType", "alipay.pay");
            request.put("order_id", orderId);
            request.put("token", token);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // weChat pay
    public JSONObject newWeChatRequest(String orderId, String token) {
        newRequest();
        try {
            request.put("opeType", "wechat.pay");
            request.put("order_id", orderId);
            request.put("token", token);
            request.put("sign", generateSign(request.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }
}
