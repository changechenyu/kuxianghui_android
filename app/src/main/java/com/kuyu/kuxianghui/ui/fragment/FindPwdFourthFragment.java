package com.kuyu.kuxianghui.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.ui.LoginActivity;

/**
 * Created by Administrator on 2015/10/12.
 */
public class FindPwdFourthFragment extends Fragment implements View.OnClickListener {

    private Button btReturnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findpwd_fourth, container, false);
        btReturnLogin = (Button) view.findViewById(R.id.btn_return_login);
        btReturnLogin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_return_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
