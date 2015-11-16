package com.kuyu.kuxianghui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.network.AppController;
import com.kuyu.kuxianghui.network.VolleyRequest;
import com.kuyu.kuxianghui.ui.app.AppSharedPreferences;
import com.kuyu.kuxianghui.util.DialogUtil;
import com.kuyu.kuxianghui.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenyu on 2015/10/10.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText etAccount;
    private EditText etPassword;
    private EditText etVerificationCode;
    private Button btLogin;
    private TextView tvChangeVerficationCode;
    private TextView tvRegister;
    private TextView tvForgetPassword;
    private ImageView ivVerificationCode;
    private LinearLayout linearLayout;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader = null;
    //图片验证码的key
    private String captchakey;
    private boolean boo = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
    }

    /**
     * 得到视图并且设置监听
     */
    public void findViews() {
        etAccount = (EditText) findViewById(R.id.et_email_for_login);
        etPassword = (EditText) findViewById(R.id.password_for_login);
        etVerificationCode = (EditText) findViewById(R.id.et_input_verification_code);
        btLogin = (Button) findViewById(R.id.btn_login);
        tvChangeVerficationCode = (TextView) findViewById(R.id.login_change_one);
        tvRegister = (TextView) findViewById(R.id.register);
        tvForgetPassword = (TextView) findViewById(R.id.tv_forgot_psw);
        linearLayout = (LinearLayout) findViewById(R.id.verification_layout);
        ivVerificationCode = (ImageView) findViewById(R.id.login_image_code);

        btLogin.setOnClickListener(this);
        tvChangeVerficationCode.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_forgot_psw:
                startActivity(new Intent(this, FindPdwActivity.class));
                finish();
                break;
            case R.id.login_change_one:
                changeVerficationCode();
                break;
            case R.id.btn_login:
                String account = etAccount.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                boolean result = checkAccountAndPassword(account, password);
                if (result) {
                    if (linearLayout.getVisibility() == View.VISIBLE) {
                        String verifycode = etVerificationCode.getText().toString();
                        DialogUtil.showProgressDialog(this);
                        boolean result1 = checkCode(account, password, captchakey, verifycode);
                          if(result1) {//如果验证码正确的话才能登陆
                              loginByCode(account, password, verifycode, captchakey);
                          }
                    } else {
                        DialogUtil.showProgressDialog(this);
                        logout(account, password);
                    }
                    return;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 更换验证码
     */
    public void changeVerficationCode() {
        JSONObject request = VolleyRequest.getInstance(this).changeCode();
        Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("chageCode", response.toString());
                if (response.toString() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        if (Constants.RESUlTCODE_SUCCESS.equals(jsonObject.get("resultCode"))) {
                            String url = (String) jsonObject.get("verifyimgurl");
                            captchakey = (String) jsonObject.get("verifycodetag");
                            imageLoader.displayImage(url, ivVerificationCode);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        AppController.customRequest(getApplication(), AppController.COMMON_REQUEST_TAG, request, onSuccess,
                AppController.dErrorListener);
    }

    /**
     * 检验验证码
     */
    public boolean checkCode(String account, String password, String key, String code) {
        final JSONObject request = VolleyRequest.getInstance(this).CheckCode(key, code);
        Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    DialogUtil.exitProgressDialog();
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (!Constants.RESUlTCODE_SUCCESS.equals(jsonObject.get("resultCode"))) {
                        ToastUtils.showText(LoginActivity.this, R.string.code_error, ToastUtils.ONE_SECOND);
                    } else {
//                        boo=true;
                        String account = etAccount.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();
                        String verifycode = etVerificationCode.getText().toString();
                        loginByCode(account, password, verifycode, captchakey);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AppController.customRequest(getApplication(), AppController.COMMON_REQUEST_TAG, request, onSuccess,
                AppController.dErrorListener);
        return boo;
    }

    /**
     * 验证用户名和密码
     */
    public boolean checkAccountAndPassword(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showText(this, R.string.accoutn_error,
                    ToastUtils.ONE_SECOND);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showText(this, R.string.password_error, ToastUtils.ONE_SECOND);
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    /**
     * 登陆
     */
    private void logout(final String username, final String password) {
        JSONObject request = VolleyRequest.getInstance(this).newLoginRequest(username, password);
        Response.Listener<JSONObject> onSuccess;
        onSuccess = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("result", response.toString());
                try {
                    DialogUtil.exitProgressDialog();
                    JSONObject json = new JSONObject(response.toString());
                    String resultCode = (String) json.get("resultCode");
                    final String resultMsg = (String) json.get("resultMsg");
                    if (Constants.RESUlTCODE_SUCCESS.equals(resultCode)) {//用户登陆成功 跳到其它页面
                        String token=(String)json.get("token");
                        Log.i("login1token",token);
                        new AppSharedPreferences(getApplicationContext()).save_token(token);
                       String result= new AppSharedPreferences(getApplicationContext()).get_token();
                       Log.i("getToken",result);
                    } else if (Constants.RESULTCODE_NO_REGISTER.equals(resultCode)) {//未注册
                        String result = (String) json.get("resultMsg");
                        ToastUtils.showText(LoginActivity.this, R.string.user_id_not_register, ToastUtils.ONE_SECOND);
                        return;
                    } else if (Constants.USENAMERORPASSWORDISERROR.equals(resultCode)) {//用户名或密码不正确
                        ToastUtils.showText(getApplicationContext(), R.string.username_or_password_is_error,
                                ToastUtils.ONE_SECOND);
                        linearLayout.setVisibility(View.VISIBLE);
                        String url = (String) json.get("url");
                        captchakey = (String) json.get("key");
                        imageLoader.displayImage(url, ivVerificationCode);
                    } else if (Constants.NEEDCODE.equals(resultCode)) {//需要验证码
                        linearLayout.setVisibility(View.VISIBLE);
                        String url = (String) json.get("url");
                        captchakey = (String) json.get("key");
                        imageLoader.displayImage(url, ivVerificationCode);
                        Log.i("url", url);
                        Log.i("key", captchakey);
                    } else if (Constants.LOCKUSERNAME.equals(resultCode)) {//行号被锁
                        ToastUtils.showText(LoginActivity.this, R.string.lack_username, ToastUtils.ONE_SECOND);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AppController.customRequest(getApplication(), AppController.COMMON_REQUEST_TAG, request, onSuccess,
                AppController.dErrorListener);
    }

    /**
     * 得到验证码
     */
    public void loginByCode(String username, String password, String verifycode, String key) {
        if (!TextUtils.isEmpty(verifycode)) {//验证码不为空的时候去登陆
            if (key != null) {
                DialogUtil.showProgressDialog(this);
                JSONObject request = VolleyRequest.getInstance(LoginActivity.this).newLoginRequest(username, password, verifycode, key);
                Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
                    @Override    //返回成功
                    public void onResponse(JSONObject response) {
                        Log.i("codeReponse", response.toString());
                        //{"resultMsg":"验证码错误","resultCode":"-5"}
                        try {
                            DialogUtil.exitProgressDialog();
                            JSONObject json = new JSONObject(response.toString());
//                          if("验证码错误".equals(json.get("resultMsg")){}
                            String resultCode = (String) json.get("resultCode");
                            if (Constants.USENAMERORPASSWORDISERROR.equals(resultCode)) {//用户名或密码不正确
                                ToastUtils.showText(getApplicationContext(), R.string.username_or_password_is_error,
                                        ToastUtils.ONE_SECOND);
                                String url = (String) json.get("url");
                                captchakey = (String) json.get("key");
                                imageLoader.displayImage(url, ivVerificationCode);
                            }
                            if (Constants.RESULTCODE_NO_REGISTER.equals(resultCode)) {//未注册
                                ToastUtils.showText(getApplicationContext(), R.string.user_id_not_register,
                                        ToastUtils.ONE_SECOND);
                            } else if (Constants.RESUlTCODE_SUCCESS.equals(resultCode)) { //登陆成功
                               String token=(String)json.get("token");
                                Log.i("login2token",token);
                                new AppSharedPreferences(getApplicationContext()).save_token(token);

                            } else if (Constants.LOCKUSERNAME.equals(resultCode)) {//用户被锁住
                                ToastUtils.showText(LoginActivity.this, R.string.lack_username, ToastUtils.ONE_SECOND);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AppController.customRequest(getApplication(), AppController.COMMON_REQUEST_TAG, request, onSuccess,
                        AppController.dErrorListener);
            }
        }
    }

    /**
     * 检验用户名是否存在
     */
    public void checkAccountName(String username, String key, String code) {
        JSONObject request = VolleyRequest.getInstance(LoginActivity.this).checkAccountName(username, key, code);
        Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
            @Override    //返回成功
            public void onResponse(JSONObject response) {
                Log.i("codeReponse", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String resultCode = (String) jsonObject.get("resultCode");
                    String resultMsg = (String) jsonObject.get("resultMsg");
                    if (Constants.RESUlTCODE_SUCCESS.equals(resultCode)) {
                        if ("success".equals(resultMsg)) {
                            //这个地方不确定 还需要测试
                            ToastUtils.showText(LoginActivity.this, R.string.username_is_null, ToastUtils.ONE_SECOND);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AppController.customRequest(getApplication(), AppController.COMMON_REQUEST_TAG, request, onSuccess,
                AppController.dErrorListener);
    }

}
