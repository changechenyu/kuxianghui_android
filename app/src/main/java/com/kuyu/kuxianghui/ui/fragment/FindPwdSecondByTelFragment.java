package com.kuyu.kuxianghui.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.util.ToastUtils;

/**
 * Created by Administrator on 2015/10/12.
 */
public class FindPwdSecondByTelFragment extends Fragment implements View.OnClickListener {
    private TextView tvShowTel;
    private EditText etCode;
    private Button btShowTime;
    private Button btNext;
    private int time=60;
    private boolean result=true;
    private String serverCode="";
    private String newString="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_findpwd_second_telephone,container,false);
        tvShowTel= (TextView) view.findViewById(R.id.telephone_show_findpwd);
        etCode= (EditText) view.findViewById(R.id.input_code_findpwd_second_telephone);
        btShowTime= (Button) view.findViewById(R.id.repeat_code_button);
        btNext= (Button) view.findViewById(R.id.btn_findpwd_second_telephone_next);

        showTel();
        showTime();
        btNext.setOnClickListener(this);
        btShowTime.setOnClickListener(FindPwdSecondByTelFragment.this);
        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_findpwd_second_telephone_next:
                String code=etCode.getText().toString().trim();
                if(checkCode(code)){
                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    FindPwdThirdFragment ftf=new FindPwdThirdFragment();
                    ft.replace(R.id.id_content, ftf, "THIRD");
                    //回退的时候显示上一个Fragment
                    ft.addToBackStack(null);
                    ft.commit();
                }
                break;
            case R.id.repeat_code_button:
                sendCode();
                time=60;
                result=true;
                showTime();
                sendCode();
                break;
            default:
                break;
        }
    }

    /**
     * 显示带*****的电话号码
     */
    public void showTel(){
        String string=FindPwdFirstFragment.telephone;
        if(string!=null&&!"".equals(string)){
              newString = string.substring(0, 3) + "*****" + string.substring(8);
        }
        tvShowTel.post(new Runnable() {
            @Override
            public void run() {
                tvShowTel.setText(newString);
            }
        });
//        tvShowTel.setText(string);
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
                                       tvShowTel.post(new Runnable() {
                                           @Override
                                           public void run() {
                                               btShowTime.setText(time + "s后重新获取");
                                               btShowTime.setClickable(false);
                                           }
                                       });
                                     if (time <= 1) {
                                         result = false;
                                         tvShowTel.post(new Runnable() {
                                             @Override
                                             public void run() {
                                                 btShowTime.setText("获取验证码");
                                                 btShowTime.setClickable(true);
                                             }
                                         });
                                     }
                                 } catch (InterruptedException e) {
                                     e.printStackTrace();
                                 }

                             }
                         }

             }).start();
    }

    /**
     * 发送手机验证码
     */
    public void sendCode(){
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    /**
     * 判断验证码是否正确
     */
    public boolean checkCode(String code){
        if(TextUtils.isEmpty(code)){
            ToastUtils.showText(getActivity(), R.string.tel_code_is_not_null, ToastUtils.ONE_SECOND);
            return false;
        }
//        if(!FindPwdFirstFragment.telCode.equals(code)){
//            ToastUtils.showText(getActivity(), R.string.tel_code_errror, ToastUtils.ONE_SECOND);
//            return false;
//        }
        return true;
    }
}
