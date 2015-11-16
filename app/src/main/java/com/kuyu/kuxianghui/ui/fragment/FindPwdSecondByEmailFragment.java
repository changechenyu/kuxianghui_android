package com.kuyu.kuxianghui.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.ui.LoginActivity;

/**
 * Created by Administrator on 2015/10/12.
 */
public class FindPwdSecondByEmailFragment extends Fragment implements View.OnClickListener {

    private Button btReturnLogin;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_return_third_login:
                Intent intent =new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_findpwd_second_email,container,false);
        btReturnLogin=(Button)view.findViewById(R.id.btn_return_third_login);
        btReturnLogin.setOnClickListener(this);
        return view;
    }
}