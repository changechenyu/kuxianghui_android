package com.kuyu.kuxianghui.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.network.AppController;
import com.kuyu.kuxianghui.network.VolleyRequest;
import com.kuyu.kuxianghui.ui.LoginActivity;
import com.kuyu.kuxianghui.util.DialogUtil;
import com.kuyu.kuxianghui.util.ToastUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONException;
import org.json.JSONObject;

import static com.kuyu.kuxianghui.util.StringUtils.isEmail;
import static com.kuyu.kuxianghui.util.StringUtils.isTel;

/**
 * Created by Administrator on 2015/10/12.
 */
public class FindPwdFirstFragment extends Fragment implements View.OnClickListener {
    private EditText etTelOrEmail;
    private EditText etVerficationCode;
    private TextView tvChangeCode;
    private Button btRegister;
    private ImageView ivCode;
    public static String telephone = "";
    public static String telCode = "";
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader = null;
    private String captchakey;
    private boolean boo = false;
    public static String telOrEmail;
    private boolean usernameResult = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ImageLoader配置
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)  //1.8.6包使用时候，括号里面传入参数true
                .cacheOnDisc(true)    //1.8.6包使用时候，括号里面传入参数true
                .build();
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();

        //更换验证码
        changeVerficationCode();

        View view = inflater.inflate(R.layout.fragment_findpwd_first, container, false);
        etTelOrEmail = (EditText) view.findViewById(R.id.et_email_for_findpwd);
        etVerficationCode = (EditText) view.findViewById(R.id.et_input_verification_code_findpwd);
        btRegister = (Button) view.findViewById(R.id.btn_findpwd_first_next);
        tvChangeCode = (TextView) view.findViewById(R.id.findpwd_change_one);
        ivCode = (ImageView) view.findViewById(R.id.find_pwd_code);

        //注册点击事件
        btRegister.setOnClickListener(this);
        tvChangeCode.setOnClickListener(this);

        //注册焦点事件
        etTelOrEmail.setOnFocusChangeListener(new myFocusChangeListener());
        btRegister.setOnFocusChangeListener(new myFocusChangeListener());
        return view;
    }

    /**
     * 聚焦失焦点 内部类
     */
    class myFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {  //获得焦点
                switch (view.getId()) {
                    case R.id.et_email_for_findpwd:
                        etTelOrEmail.setText("");
                        break;
                    case R.id.et_input_verification_code_findpwd:
                        etVerficationCode.setText("");
                        break;
                    default:
                        break;
                }
            } else { //失去焦点
                switch (view.getId()) {
                    case R.id.et_email_for_findpwd:
                        String result = "";
                        result = etTelOrEmail.getText().toString();
                        result = result != null ? result.trim() : result;
                        if (TextUtils.isEmpty(result)) {
                            ToastUtils.showText(getActivity(), R.string.telephone_or_email_can_not_null, ToastUtils.ONE_SECOND);
                            return;
                        } else {
                            if (result.contains("@")) {//判断是否是邮箱
                                if (!isEmail(result)) {
                                    ToastUtils.showText(getActivity(), R.string.email_is_errror, ToastUtils.ONE_SECOND);
                                }
                            } else {
                                if (!isTel(result)) {
                                    ToastUtils.showText(getActivity(), R.string.tel_is_errror, ToastUtils.ONE_SECOND);
                                }
                            }
                        }
                        break;
                    case R.id.et_input_verification_code_findpwd:
                        String code = "";
                        code = etVerficationCode.getText().toString();
                        code = code != null ? code.trim() : code;
                        if (TextUtils.isEmpty(code)) {
                            ToastUtils.showText(getActivity(), R.string.code_can_not_null, ToastUtils.ONE_SECOND);
                            return;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_findpwd_first_next:
                telOrEmail = etTelOrEmail.getText().toString();
                String code = etVerficationCode.getText().toString();
                if (!TextUtils.isEmpty(telOrEmail)) {
                    if (isEmail(telOrEmail.trim()) || isTel(telOrEmail.trim()) && !TextUtils.isEmpty(code)) {
                        boolean flag = checkCode(captchakey, code);//检验验证码
                    } else {
                        return;
                    }
                } else {
                    return;
                }
                break;
            case R.id.findpwd_change_one:
                changeVerficationCode();
                break;
            default:
                break;
        }
    }

    /**
     * 更换验证码
     */
    public void changeCode() {

    }

    /**
     * 判断验证是否输入错误
     */
    public boolean checkCode() {
        boolean result = false;
        return false;
    }

    /**
     * 找回密码第二步
     */
    public void findPwdNext(String result) {

    }

    /**
     * 发送验证码
     */
    public void sendCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送验证码
                telCode = "后台过来的值";
            }
        }).start();
    }

    /**
     * 发送邮箱
     */
    public void sendEmail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //邮箱
            }
        }).start();
    }

    /**
     * 更换验证码
     */
    public void changeVerficationCode() {
//        DialogUtil.showProgressDialog(getActivity());
        JSONObject request = VolleyRequest.getInstance(getActivity()).changeCode();
        Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("chageCode", response.toString());
//                DialogUtil.exitProgressDialog();
                if (response.toString() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        if (Constants.RESUlTCODE_SUCCESS.equals(jsonObject.get("resultCode"))) {//获取图片验证码成功
                            String url = (String) jsonObject.get("verifyimgurl");
                            captchakey = (String) jsonObject.get("verifycodetag");
                            imageLoader.displayImage(url, ivCode);
                        } else if (Constants.USENAMERORPASSWORDISERROR.equals(jsonObject.get("resultCode"))) {//获取图片验证码失败
                            ToastUtils.showText(getActivity(), R.string.get_picture_error, ToastUtils.ONE_SECOND);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        AppController.customRequest(getActivity().getApplication(), AppController.COMMON_REQUEST_TAG, request, onSuccess,
                AppController.dErrorListener);
    }

    /**
     * 检验验证码
     */
    public boolean checkCode(String key, String code) {
        JSONObject request = VolleyRequest.getInstance(getActivity()).CheckCode(key, code);
        Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (!Constants.RESUlTCODE_SUCCESS.equals(jsonObject.get("resultCode"))) {
                        Log.i("codeError",response.toString());
                        ToastUtils.showText(getActivity(), R.string.code_error, ToastUtils.ONE_SECOND);
                        return;
                    } else {
                        boo = true;
                        sendVerificationCode();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AppController.customRequest(getActivity().getApplication(), AppController.COMMON_REQUEST_TAG, request, onSuccess,
                AppController.dErrorListener);
        return boo;
    }

    /**
     * 发送验证码给手机或者邮箱
     */
    public void sendVerificationCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String code = etVerficationCode.getText().toString();
                if (TextUtils.isEmpty(telOrEmail)) {
                    ToastUtils.showText(getActivity(), R.string.phone_can_not_null, ToastUtils.ONE_SECOND);
                } else {
                    if (isEmail(telOrEmail.trim()) || isTel(telOrEmail.trim()) && !TextUtils.isEmpty(code)) { //如果是手机号码或者邮箱就发验证码
                        JSONObject request = VolleyRequest.getInstance(getActivity()).checkAccountName(telOrEmail, code, captchakey);
                        Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("findPwdFirstResponse", response.toString());
                                try {
                                    JSONObject json = new JSONObject(response.toString());
                                    if (Constants.RESUlTCODE_SUCCESS.equals(json.get("resultCode"))) {//发送成功之后的跳转
                                        if (!telOrEmail.contains("@")) {
                                            ToastUtils.showText(getActivity(), R.string.send_telephone_success, ToastUtils.ONE_SECOND);
                                        } else {
                                            ToastUtils.showText(getActivity(), R.string.send_email_success, ToastUtils.ONE_SECOND);
                                        }
                                        skipTelephoneOrEmail();
                                    } else if (Constants.USERNAMEISNULL.equals(json.get("resultCode"))) {//用户名为空的情况
                                        ToastUtils.showText(getActivity(), R.string.username_is_null, ToastUtils.ONE_SECOND);
                                        return;
                                    }else if(Constants.CODEISERROR.equals(json.get("resultCode"))){//验证码错误
                                        ToastUtils.showText(getActivity(), R.string.code_error, ToastUtils.ONE_SECOND);
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        AppController.customRequest(getActivity().getApplication(), AppController.COMMON_REQUEST_TAG, request, onSuccess,
                                AppController.dErrorListener);
                    }
                }
            }
        }).start();
    }

    /**
     * 判断手机号码是否合法
     */
    public void isMobileNum(String telNum) {
        boolean boo = false;
        boo = isTel(telNum);
        if (!boo) {
            ToastUtils.showText(getActivity(), R.string.phone_error, ToastUtils.ONE_SECOND);
            return;
        }
    }

    /**
     * 检验用户名是否存在并且发送相关的信息到手机或者邮箱，然后跳转页面
     */
    public void skipTelephoneOrEmail() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FindPwdThirdFragment fThirdByTephone = new FindPwdThirdFragment();
        ft.replace(R.id.id_content, fThirdByTephone, "TWOBYTELPHONE");
        //回退的时候显示上一个Fragment
        ft.addToBackStack(null);
        ft.commit();
    }
}
