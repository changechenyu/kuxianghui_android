package com.kuyu.kuxianghui.ui.fragment;

import android.app.Fragment;
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

import com.android.volley.Response;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.network.AppController;
import com.kuyu.kuxianghui.network.VolleyRequest;
import com.kuyu.kuxianghui.ui.LoginActivity;
import com.kuyu.kuxianghui.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/10/12.
 */
public class FindPwdThirdFragment extends Fragment implements View.OnClickListener{
    private EditText etPwd;
    private EditText etCode;
    private EditText etConfrimPwd;
    private Button btNext;
    private boolean result=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_findpwd_third,container,false);
        etPwd= (EditText) view.findViewById(R.id.et_newpassword_for_findpwd);
        etConfrimPwd= (EditText) view.findViewById(R.id.confirm_password_for_findpwd);
        btNext=(Button)view.findViewById(R.id.btn_commit_findpwd_third);
        etCode= (EditText) view.findViewById(R.id.confirm_code_for_findpwd);
        btNext.setOnClickListener(this);
        etPwd.setOnFocusChangeListener(new myOnFocusChangeListener());
        etConfrimPwd.setOnFocusChangeListener(new myOnFocusChangeListener());
        return view;
    }

    /**
     * 判断密码输入是否合乎规范
     */
    public void getIsPasswordCorrect(String password){
        if(password.length() < Constants.USER_NAME_PASSWORD_MIN
                || password.length() > Constants.USER_NAME_PASSWORD_MAX){
            ToastUtils.showText(getActivity(), R.string.pwd_length_error, ToastUtils.ONE_SECOND);
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
                ToastUtils.showText(getActivity(), R.string.password_not_same, ToastUtils.ONE_SECOND);
                return;
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_commit_findpwd_third:
                String pwd=etPwd.getText().toString();
                String confirmPwd=etConfrimPwd.getText().toString();
                String code=etCode.getText().toString();
                getIsPasswordCorrect(pwd);
                getIsPasswordCorrect(confirmPwd);
                pwdAndConfrimPwdIsSame();
                if(!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(confirmPwd)&&!TextUtils.isEmpty(code)){
                    if(FindPwdFirstFragment.telOrEmail!=null){
                        updatePassWord(FindPwdFirstFragment.telOrEmail,pwd,confirmPwd,code); //去后天验证
                        if(result){//如果找回密码成功
                            FragmentManager fm=getFragmentManager();
                            FragmentTransaction ft=fm.beginTransaction();
                            FindPwdFourthFragment fff=new FindPwdFourthFragment();
                            ft.replace(R.id.id_content, fff, "fouth");
                            //回退的时候显示上一个Fragment
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    }
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
                    case R.id.et_newpassword_for_findpwd:
                        String pwd=etPwd.getText().toString();
                        if(TextUtils.isEmpty(etPwd.getText().toString())){
                            ToastUtils.showText(getActivity(), R.string.new_password_error, ToastUtils.ONE_SECOND);
                            return;
                        }
                        getIsPasswordCorrect(pwd);
                        break;
                    case R.id.confirm_password_for_findpwd:
                        String confrimPwd=etConfrimPwd.getText().toString();
                        if(TextUtils.isEmpty(etPwd.getText().toString())){
                            ToastUtils.showText(getActivity(), R.string.new_confirm_password_error, ToastUtils.ONE_SECOND);
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
    public void updatePassWord(String username,String password,String confirmpwd,String safecode){
        JSONObject request = VolleyRequest.getInstance(getActivity()).updatePassWord(username,password,confirmpwd,safecode);
        Response.Listener<JSONObject> onSuccess=new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    if(!Constants.RESUlTCODE_SUCCESS.equals(jsonObject.get("resultCode"))){
                        ToastUtils.showText(getActivity(),R.string.find_pwd_error,ToastUtils.ONE_SECOND);
                        return;
                    }else{
                        result=true;
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
