package com.kuyu.kuxianghui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by chenyu on 2015/10/10.
 */
public class ProtocalActivity extends BaseActivity implements View.OnClickListener{
    private WebView wvProtocal;
    private Button btAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_protocl);
        findViews();
    }
    /**
     *得到视图并且设置监听
     */
    public void  findViews(){
        wvProtocal= (WebView) findViewById(R.id.webview_register_protocl);
        btAgree=(Button)findViewById(R.id.btn_register_protocl_agree);
        wvProtocal.loadUrl("www.baidu.com");
        btAgree.setOnClickListener(this);
    }
    /**
     *点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register_protocl_agree:
                Intent intent =new Intent(this,RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
