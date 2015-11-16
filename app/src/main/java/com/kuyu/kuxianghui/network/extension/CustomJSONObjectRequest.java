package com.kuyu.kuxianghui.network.extension;

import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.kuyu.kuxianghui.util.CommonLog;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fish on 15/10/21.
 */
public class CustomJSONObjectRequest extends Request<JSONObject> {
    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json;charset=%s", PROTOCOL_CHARSET);

    private final String mRequestParamsJson;

    private final Response.Listener<JSONObject> mListener;

    public CustomJSONObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        mListener = listener;
        mRequestParamsJson = jsonRequest.toString();


        Log.i(CommonLog.TAG, "url:" + url);
    }

    public CustomJSONObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener) {
        this(Method.POST, url, jsonRequest, listener, errorListener);
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
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        Log.e(CommonLog.TAG, "parseResponse error:" + volleyError.networkResponse.statusCode);// java.lang.NullPointerException

        return super.parseNetworkError(volleyError);
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }

//    /**
//     * @deprecated Use {@link #getBodyContentType()}.
//     */
//    @Override
//    public String getPostBodyContentType() {
//        return getBodyContentType();
//    }

//    @Override
//    public String getBodyContentType() {
//        return PROTOCOL_CONTENT_TYPE;
//    }

    /**
     * 酷享汇接口的需求，header内增加Content-Type 字段
     * @return
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", PROTOCOL_CONTENT_TYPE);
//        CommonLog.i("headers:"+PROTOCOL_CONTENT_TYPE);

        return headers;
    }

}
