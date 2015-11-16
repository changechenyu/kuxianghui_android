package com.kuyu.kuxianghui.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.network.extension.CustomJSONObjectRequest;
import com.kuyu.kuxianghui.network.extension.GsonRequest;
import com.kuyu.kuxianghui.util.NetState;
import com.kuyu.kuxianghui.util.ToastUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fish on 1/26/15. 网络请求类，单例，
 */
public class AppController {
    public static final String TAG = AppController.class.getSimpleName();
    public static final String COMMON_REQUEST_TAG = "COMMON_REQUEST_TAG";

    private static AppController ourInstance = new AppController();

    private static Context mContext;

    public static AppController getInstance(Context context) {
        mContext = context;
        return ourInstance;
    }

    private AppController() {
    }

    private RequestQueue mRequestQueue;

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * 重新设计的通用请求方法
     * @param context
     * @param requestTag
     * @param requstParams
     * @param clazz
     * @param onSuccess
     * @param onFail
     * @param <T>
     */
    public static <T> void customRequest(Context context, String requestTag,
                                         final JSONObject requstParams, Class<T> clazz,
                                         Response.Listener<T> onSuccess, Response.ErrorListener onFail) {
        mContext = context;

        GsonRequest<T> mGsonRequest = new GsonRequest<T>(Constants.SERVER_URL,
                clazz, requstParams, onSuccess, onFail);
        // 设置超时时间和重连次数
        mGsonRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.initialTimeoutMs,
                Constants.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //在进行请求前检查网络
        if (NetState.checkState(mContext) != NetState.CONNECTION_NO) {
            //有网络
            ourInstance.addToRequestQueue(mGsonRequest, requestTag);
        } else {
            //没有网络
            ToastUtils.makeText(mContext, "无网络连接，请检查或者稍后重试");
        }
    }

    public static void customRequest(Context context, String requestTag,
                                     final JSONObject requstParams,
                                     Response.Listener<JSONObject> onSuccess, Response.ErrorListener onFail){
        mContext = context;

        //一下代码是因为使用volley的post传参方式，服务器端无法接受到参数，所以这里选择折中的方案，
        //自己把酷享汇所需要的参数形式 用 拼url的方式 添加到请求的url后面
        Map<String, String> map = new HashMap<String, String>();
        map.put("jsonParam", requstParams.toString());
        String postParams = map.toString();
        String postUrl = Constants.SERVER_URL + "?" + postParams.substring(1,postParams.length()-1);
        Log.i("postUrl",postUrl);
        //------------------------------------------------------------------------------------------

        CustomJSONObjectRequest mCustomRequest = new CustomJSONObjectRequest(Request.Method.POST,postUrl,
                requstParams,onSuccess, onFail);
        //设置超时时间和重连次数
        mCustomRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.initialTimeoutMs,
                Constants.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //在进行请求前检查网络
        if (NetState.checkState(mContext) != NetState.CONNECTION_NO) {
            //有网络
            ourInstance.addToRequestQueue(mCustomRequest, requestTag);
        } else {
            //没有网络
            ToastUtils.makeText(mContext, "无网络连接，请检查或者稍后重试");
        }
    }

    private static String generateCacheKey(String url, JSONObject requstParams) {
        if (null != requstParams) {
            // 删除请求中的 timestamp 和 sgin 参数
            requstParams.remove("timestamp");
            requstParams.remove("sgin");

            return url + requstParams.toString();
        } else {
            return url;
        }
    }

    public static <T> void customRequestNoRetryInner(Context context, String requestTag,
                                         final JSONObject mRequest, Class<T> clazz,
                                         Response.Listener<T> onSuccess, Response.ErrorListener onFail) {
        mContext = context;

        GsonRequest<T> mGsonRequest = new GsonRequest<T>(Constants.SERVER_URL,
                clazz, mRequest, onSuccess, onFail) {
            // 重载 缓存的cachekey规则，默认的方法是使用 请求的url作为cacheKey，对于十分到家这样的，请求url始终相同，
            // 只是使用参数不同，来加载不同数据的方法，不能缓存数据，所以更改为使用 请求url+请求参数 的方式作为cacheKey
            // url和参数都一样的请求情况下，才认为是同一个请求。
            @Override
            public String getCacheKey() {
                return generateCacheKey(Constants.SERVER_URL, mRequest);
            }
        };
        // 设置超时时间和重连次数
        mGsonRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.longTimeoutMs,
                Constants.DEFAULT_NO_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //在进行请求前检查网络
        if (NetState.checkState(mContext) != NetState.CONNECTION_NO) {
            //有网络
            ourInstance.addToRequestQueue(mGsonRequest, requestTag);
        } else {
            //没有网络
            ToastUtils.makeText(mContext, "无网络连接，请检查或者稍后重试");
        }
    }

    //默认的 请求失败 回调函数
    public static Response.ErrorListener dErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            //意外的出错情况处理
            if (error != null){
                Log.e(TAG, "Error: " + error.getMessage());
            }
        }
    };
    //默认的 请求成功 回调函数，什么也不做
    public static Response.Listener<JSONObject> dSuccessListener = new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject rsp) {
        }
    };
}