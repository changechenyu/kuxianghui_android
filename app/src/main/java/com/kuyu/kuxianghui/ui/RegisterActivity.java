package com.kuyu.kuxianghui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.network.AppController;
import com.kuyu.kuxianghui.network.RequestByXML;
import com.kuyu.kuxianghui.network.VolleyRequest;
import com.kuyu.kuxianghui.util.DialogUtil;
import com.kuyu.kuxianghui.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kuyu.kuxianghui.util.StringUtils.isEmail;
import static com.kuyu.kuxianghui.util.StringUtils.isTel;

/**
 * Created by chenyu on 2015/10/10.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText etUsername;
    private EditText etPwd;
    private EditText etConfirmPwd;
    private EditText etTelephone;
    private EditText etVerificationCode;
    private CheckBox cbAgree;
    private TextView agreePtotocl;
    private Button btGetVerificationCode;
    private Button btRegister;
    private boolean result=true;
    private int time=60;
    private Handler handler=  handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(1000);
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
    }
    /**
     *得到视图并且设置监听
     */
    private void findViews() {
        etUsername= (EditText) findViewById(R.id.name_for_register);
        etPwd= (EditText) findViewById(R.id.password_for_register);
        etConfirmPwd= (EditText) findViewById(R.id.comfirm_password);
        etTelephone= (EditText) findViewById(R.id.telephone_register);
//        etVerificationCode=(EditText)findViewById(R.id.et_input_verification_code_register);
        cbAgree= (CheckBox) findViewById(R.id.cb_agree_register);
//        btGetVerificationCode= (Button) findViewById(R.id.verification_code_image_register);
        btRegister= (Button) findViewById(R.id.btn_login_register);
        agreePtotocl=(TextView)findViewById(R.id.tv_register_agreement);

        //设置监听事件
        etUsername.setOnFocusChangeListener(new myOnFocusChangeListener());
        etPwd.setOnFocusChangeListener(new myOnFocusChangeListener());
        etConfirmPwd.setOnFocusChangeListener(new myOnFocusChangeListener());
        etTelephone.setOnFocusChangeListener(new myOnFocusChangeListener());
//        etVerificationCode.setOnFocusChangeListener(new myOnFocusChangeListener());

//        btGetVerificationCode.setOnClickListener(this);
        agreePtotocl.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.verification_code_image_register:
//                sendVerificationCode();
//                break;
            case R.id.btn_login_register:
//                register();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                      String result=RequestByXML.postByRegister("http://61.166.155.44:9012/AnimeApp/User/Registe","chenyu","123456","18676081261","2657607916@qq.com","男","1990-10-10","湖南");
                        String result=RequestByXML.postByRegister("http://61.166.155.44:9012/AnimeApp/User/Registe","chenyu","123456","18676081261","2657607916@qq.com",null,null,null);

                        System.out.print("result"+result);
                        Log.i("result",result);
                        try {
                            JSONObject jsonObject=new JSONObject(result.toString());
                            String  errorcode= (String) jsonObject.get("errorcode");
                            Message message=handler.obtainMessage();
                            if(errorcode.equals("0")){
                                message.what=0;
                                handler.sendMessage(message);
                            }else{
                                message.what=1;
                                handler.sendMessage(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
//                username	string	是	用户昵称，非用户账号，用户账号可用手机或者邮箱登录
//                userpwd	string	是	用户密码
//                mobile	string	是	用户手机
//                email	string	是	用户邮箱
//                sex	string	否	性别，如：男，女
//                birthday	string	否	生日，如：1990-10-10
//                address	string	否	地址

                break;
            case R.id.tv_register_agreement:
                Intent intent=new Intent(this,ProtocalActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    /**
     * 判断是否为空  除了checkbox
     */
    public void textIsCorrect(String value,String type){
        switch (type){
            case "name":
                if(TextUtils.isEmpty(value)){
                    ToastUtils.showText(this,R.string.name_can_not_null,ToastUtils.ONE_SECOND);
                    return ;
                }
                break;
            case "password":
                if(TextUtils.isEmpty(value)){
                    ToastUtils.showText(this,R.string.password_error,ToastUtils.ONE_SECOND);
                    return ;
                }
                break;
            case "confirm_password":
                if(TextUtils.isEmpty(value)){
                    ToastUtils.showText(this,R.string.confirm_password_error,ToastUtils.ONE_SECOND);
                    return ;
                }
                break;
            case "telephone":
                if(TextUtils.isEmpty(value)){
                    ToastUtils.showText(this,R.string.phone_can_not_null,ToastUtils.ONE_SECOND);
                    return ;
                }
                break;
            case "verificationCode":
                if(TextUtils.isEmpty(value)){
                    ToastUtils.showText(this,R.string.code_can_not_null,ToastUtils.ONE_SECOND);
                    return ;
                }
                break;
        }
    }
    /**
     * 判断密码输入是否合乎规范
     */
    public void getIsPasswordCorrect(String password){
        if(password.length() < Constants.USER_NAME_PASSWORD_MIN
                || password.length() > Constants.USER_NAME_PASSWORD_MAX){
            ToastUtils.showText(this, R.string.pwd_length_error, ToastUtils.ONE_SECOND);
            return;
        }
    }
    /**
     * 判断2次密码是否一致
     */
    public void pwdAndConfrimPwdIsSame(){
        String password = etPwd.getText().toString().trim();
        String rePassword = etConfirmPwd.getText().toString().trim();
        if(!rePassword.equals(password)){
            ToastUtils.showText(this, R.string.password_not_same, ToastUtils.ONE_SECOND);
            return;
        }
    }
    /**
     * 判断验证码格式输入是否错误
     */
    public void  checkverificationCode(String code){

    }

    /**
     * 判断邮箱是否合法
     */
    public boolean  checkEmail(String email){
        boolean tag = true;
        tag=isEmail(email);
        return tag;
    }

    /**
     * 判断手机号码是否合法
     */
    public void isMobileNum(String telNum){
        boolean boo=false;
        boo=isTel(telNum);
        if(!boo){
            ToastUtils.showText(this, R.string.phone_error, ToastUtils.ONE_SECOND);
            return;
        }
    }

    /**
     * 判断验证码输入是否和后台发不过的是否一样
     */
    public  boolean checkSendVerificationCode(){
        return false;
    }
    /**
     * 发送验证码给手机
     */
    public void sendVerificationCode(){
//        getMobileCode
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mobile=etTelephone.getText().toString();
                if(TextUtils.isEmpty(mobile)){
                    ToastUtils.showText(RegisterActivity.this,R.string.phone_can_not_null,ToastUtils.ONE_SECOND);
                }else {
                    isMobileNum(mobile);
                    showTime();
                    JSONObject request = VolleyRequest.getInstance(RegisterActivity.this).getMobileCode(mobile);
                    Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("registerResponse", response.toString());
                            try {
                                JSONObject json=new JSONObject(response.toString());
                                if(Constants.RESUlTCODE_SUCCESS.equals(json.get("resultCode"))){
                                    ToastUtils.showText(RegisterActivity.this,R.string.send_success,ToastUtils.ONE_SECOND);
                                }else{
                                    ToastUtils.showText(RegisterActivity.this,R.string.send_error,ToastUtils.ONE_SECOND);
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
        }).start();
    }

    /**
     * 注册
     */
    public void register(){
        String name=etUsername.getText().toString().trim();
        String password=etPwd.getText().toString().trim();
        String confirmPwd=etConfirmPwd.getText().toString();
        String telephone=etTelephone.getText().toString().trim();
        String verificationCode=etVerificationCode.getText().toString().trim();
        boolean result=cbAgree.isChecked();
        if(result){
            if(!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(telephone)&&!TextUtils.isEmpty(confirmPwd)&&!TextUtils.isEmpty(verificationCode)){
                isMobileNum(telephone);
                register(telephone,password,confirmPwd,verificationCode);
            }
        }else{
            ToastUtils.showText(this, R.string.agree_protocol, ToastUtils.ONE_SECOND);
        }
    }
    /**
     * 焦点监听事件内部类
     */
    class myOnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(b){  //获得焦点
                switch (view.getId()){
                    case R.id.name_for_register:
                        etUsername.setText("");
                        break;
                    case R.id.telephone_register:
                        etTelephone.setText("");
                        break;
//                    case R.id.et_input_verification_code_register:
//                        etVerificationCode.setText("");
//                        break;
                    default:
                        break;
                }
            }else{  //失去焦点
                switch (view.getId()){
                    case R.id.name_for_register:
                        textIsCorrect(etUsername.getText().toString(), "name");
                        break;
                    case R.id.password_for_register:
                        textIsCorrect(etPwd.getText().toString(), "password");
                        break;
                    case R.id.comfirm_password:
                        textIsCorrect(etConfirmPwd.getText().toString(), "confirm_password");
                        pwdAndConfrimPwdIsSame();
                        break;
                    case R.id.telephone_register:
                        textIsCorrect(etTelephone.getText().toString(), "telephone");
                        isMobileNum(etTelephone.getText().toString());
                        break;
//                    case R.id.et_input_verification_code_register:
//                        textIsCorrect(etVerificationCode.getText().toString(), "verificationCode");
//                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 注册
     * @param mobile
     * @param password
     * @param comfirmpwd
     * @param code
     */
    public  void register(String mobile,String password,String comfirmpwd,String code){
        DialogUtil.showProgressDialog(this);
        final JSONObject request = VolleyRequest.getInstance(this).newRegisterRequest(mobile, password,comfirmpwd,code);
        Response.Listener<JSONObject> onSuccess=new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                DialogUtil.exitProgressDialog();
                Log.i("registerResponse",response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    if(Constants.RESUlTCODE_SUCCESS.equals(jsonObject.get("resultCode"))){//注册成功
                        ToastUtils.showText(RegisterActivity.this, R.string.register_success, ToastUtils.ONE_SECOND);
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else{
                        String resultMsg=(String)jsonObject.get("resultMsg");
                        Toast.makeText(RegisterActivity.this,resultMsg,Toast.LENGTH_SHORT);
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
     * 显示时间在梯减的文本框
     */
    public void showTime(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (result) {
                    time--;
                    try {
                        Thread.sleep(1000);
//                                     tvShowTime.setText(time + "s后从新获取");
                        btGetVerificationCode.post(new Runnable() {
                            @Override
                            public void run() {
                                btGetVerificationCode.setText(time + "s后重新获取");
                                btGetVerificationCode.setClickable(false);
                            }
                        });
                        if (time <= 1) {
                            result = false;
                            btGetVerificationCode.post(new Runnable() {
                                @Override
                                public void run() {
                                    btGetVerificationCode.setText("获取验证码");
                                    btGetVerificationCode.setClickable(true);
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                result=true;
                time=60;
            }
        }).start();
    }
}
