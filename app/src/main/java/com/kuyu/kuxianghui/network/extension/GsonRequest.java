package com.kuyu.kuxianghui.network.extension;

import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.kuyu.kuxianghui.util.CommonLog;
import com.kuyu.kuxianghui.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 酷享汇专用
 * 扩展，将请求返回的json解析成指定的POJO
 * Created by fish on 1/26/15.
 */
public class GsonRequest<T> extends Request<T> {

    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json;charset=%s", PROTOCOL_CHARSET);

//    private final String mRequestBody ;
    private final String mRequestParamsJson;

    private final Response.Listener<T> mListener;

    private Gson mGson;

    private Class<T> mClass;

    public GsonRequest(int method, String url, Class<T> clazz, JSONObject jsonRequest, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        mGson = new Gson();
        mClass = clazz;
        mListener = listener;
        mRequestParamsJson = jsonRequest.toString();

        Log.i(CommonLog.TAG,"url:"+url);
    }

    public GsonRequest(String url, Class<T> clazz, JSONObject jsonRequest, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
//        this(Method.GET, url, clazz,jsonRequest, listener, errorListener);
        this(Method.POST, url, clazz, jsonRequest, listener, errorListener);
    }

    /**
     * 存放post的参数
     * @return
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        //在这里设置需要post的参数
        Map<String, String> map = new HashMap<String, String>();
        map.put("jsonParam", mRequestParamsJson);

        Log.i(CommonLog.TAG, "post Params:" + map.toString());

        return map;
    }

    /**
     * 自定义函数，将返回的json数据转换成 实体对象
     * */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.i(CommonLog.TAG, "response:" + jsonString);
            //进行出错判断，判断返回的字符串是不是json
            if(StringUtils.isJson(jsonString)){
                T temp = null;
                try {
                    temp = mGson.fromJson(jsonString, mClass);
                }catch (Exception e){
                    Log.e(CommonLog.TAG,"parseNetworkResponse:"+jsonString);
                    return Response.error(new VolleyError(jsonString));
                }
                return Response.success(temp,
                        HttpHeaderParser.parseCacheHeaders(response));
            }else{
                return Response.error(new VolleyError("服务器返回数据出错,返回数据:"+jsonString));
            }
        } catch (UnsupportedEncodingException e) {
            Log.e(CommonLog.TAG, "parseResponse error:" + response.toString());
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        Log.e(CommonLog.TAG, "parseResponse error:" + volleyError.networkResponse.statusCode);

        return super.parseNetworkError(volleyError);
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    /**
     * @deprecated Use {@link #getBodyContentType()}.
     */
    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

//    /**
//     * @deprecated Use {@link #getBody()}.
//     */
//    @Override
//    public byte[] getPostBody() {
//        return getBody();
//    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    /**
     * 酷享汇接口的需求，header内增加Content-Type 字段
     * @return
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", PROTOCOL_CONTENT_TYPE);
        CommonLog.i("headers:" + headers.toString());

        return headers;
    }

//    @Override
//    public byte[] getBody() {
//        try {
//            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
//        } catch (UnsupportedEncodingException uee) {
//            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
//                    mRequestBody, PROTOCOL_CHARSET);
//            return null;
//        }
//    }
}
