package com.kuyu.kuxianghui.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Response;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.network.AppController;
import com.kuyu.kuxianghui.network.VolleyRequest;
import com.kuyu.kuxianghui.ui.fragment.FindPwdFourthFragment;
import com.kuyu.kuxianghui.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenyu on 2015/10/10.
 */
public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener{
    private EditText etOldPwd;
    private EditText etPwd;
    private EditText etConfrimPwd;
    private Button btNext;
    private String username;//登陆后的用户名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_modify_password);
    }
    public void findView(){
        etOldPwd= (EditText) findViewById(R.id.et_oldpassword_for_findpwd);
        etPwd= (EditText) findViewById(R.id.et_newpassword_for_modify);
        etConfrimPwd= (EditText) findViewById(R.id.confirm_password_for_modify);
        btNext= (Button) findViewById(R.id.btn_commit_modify_password);

        btNext.setOnClickListener(this);
        etOldPwd.setOnFocusChangeListener(new myOnFocusChangeListener());
        etPwd.setOnFocusChangeListener(new myOnFocusChangeListener());
        etConfrimPwd.setOnFocusChangeListener(new myOnFocusChangeListener());
    }
    /**
     * 提交
     */
    public boolean commit(String oldPwd,String newPwd,String confirmPwd){
        boolean boo=true;
        //把数据给后台服务器
        return true;
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
        String password = etPwd.getText().toString();
        String rePassword = etConfrimPwd.getText().toString();
        if(!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(rePassword)) {
            if (!rePassword.equals(password)) {
                ToastUtils.showText(this, R.string.password_not_same, ToastUtils.ONE_SECOND);
                return;
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_commit_findpwd_third:
                String oldPwd=etOldPwd.getText().toString();
                String newPwd=etPwd.getText().toString();
                String confrimPwd=etConfrimPwd.getText().toString();
                pwdAndConfrimPwdIsSame();
                if(!TextUtils.isEmpty(oldPwd)&&!TextUtils.isEmpty(newPwd)&&!TextUtils.isEmpty(confrimPwd)){
                    updatePassWord(username,newPwd,oldPwd,confrimPwd);
                }
                break;
            default:
                break;
        }
    }
    /**
     * 内部失焦类
     */
    class  myOnFocusChangeListener implements View.OnFocusChangeListener{
        @Override
        public void onFocusChange(View view, boolean b) {
            if(b){//获得焦点
            }else{//失去焦点
                switch (view.getId()){
                    case R.id.et_oldpassword_for_findpwd:
                        String odlPwd=etOldPwd.getText().toString();
                        if(TextUtils.isEmpty(etOldPwd.getText().toString())){
                            ToastUtils.showText(getApplicationContext(), R.string.old_password_error, ToastUtils.ONE_SECOND);
                            return;
                        }
                        break;
                    case R.id.et_newpassword_for_findpwd:
                        String pwd=etPwd.getText().toString();
                        if(TextUtils.isEmpty(etPwd.getText().toString())){
                            ToastUtils.showText(getApplicationContext(), R.string.new_password_error_mopdify, ToastUtils.ONE_SECOND);
                            return;
                        }
                        getIsPasswordCorrect(pwd);
                        break;
                    case R.id.confirm_password_for_findpwd:
                        String confrimPwd=etConfrimPwd.getText().toString();
                        if(TextUtils.isEmpty(etPwd.getText().toString())){
                            ToastUtils.showText(getApplicationContext(), R.string.new_confirm_password_error, ToastUtils.ONE_SECOND);
                            return;
                        }
                        pwdAndConfrimPwdIsSame();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    /**
     * 修改密码
     */
    public void updatePassWord(String token,String oldPassword ,String password,String confirmpwd){
        JSONObject request = VolleyRequest.getInstance(this).changePasswordRequest(token,oldPassword, password,confirmpwd);
        Response.Listener<JSONObject> onSuccess=new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    if(!Constants.RESUlTCODE_SUCCESS.equals(jsonObject.get("resultCode"))){
                        ToastUtils.showText(getApplicationContext(),R.string.update_pws_error,ToastUtils.ONE_SECOND);
                        return;
                    }else {
                        ToastUtils.showText(getApplicationContext(),R.string.update_pws_success,ToastUtils.ONE_SECOND);
                        Intent intent=new Intent(ModifyPasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AppController.customRequest(this, AppController.COMMON_REQUEST_TAG, request, onSuccess,
                AppController.dErrorListener);
    }
}
