package com.kuyu.kuxianghui.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.ui.fragment.FindPwdFirstFragment;

/**
 * Created by chenyu on 2015/10/10.
 */
public class FindPdwActivity extends  BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwd_original);

        //增加一个fragment
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.id_content,new FindPwdFirstFragment(),"findPwdFirst");
        fragmentTransaction.commit();
    }
}
