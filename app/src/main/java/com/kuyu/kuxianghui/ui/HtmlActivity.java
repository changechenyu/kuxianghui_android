package com.kuyu.kuxianghui.ui;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.ui.pullwebview.PullToRefreshWebView;
import com.kuyu.kuxianghui.util.ActionbarUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by fish on 15/9/21.
 */
public class HtmlActivity extends BaseActivity{


    private PullToRefreshWebView webView;
    private ProgressBar pb;

    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_flash);
        url = this.getIntent().getStringExtra("url");
        title = this.getIntent().getStringExtra("title");

        ActionBar bar=getActionBar();
        //bar.setTitle(title);
        //bar.setDisplayHomeAsUpEnabled(true);
        //bar.setDisplayShowHomeEnabled(true);
        ActionbarUtil.makeCustomActionBar(this, bar, title, ActionbarUtil.ACTIONBAR_RETURN);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_GO_TO_CART);
        this.registerReceiver(goCartReceiver, filter);
        webView=(PullToRefreshWebView)findViewById(R.id.pull_refresh_webview);
        pb=(ProgressBar)findViewById(R.id.pb);
        mWebView = webView.getRefreshableView();
        new CusReflashWebViewSetting().normalWebView(this, mWebView, url, pb);
        //mWebView.setWebViewClient(new WebClient());
    }

    private BroadcastReceiver goCartReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==Constants.ACTION_GO_TO_CART){
                finish();
            }
        }
    };

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(goCartReceiver);
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

    private void pay(String url){
        //zhifu://#TCLPAY;channel_code:UPOP;bankTN:201511111743566247538;flag:5;out_trade_no:INDENT000003546686
        String[] infos = url.split(";");
        String flag = infos[3].split(":")[1];
        String pz = infos[2].split(":")[1];
        String type = infos[1].split(":")[1];
        this.hytPay(type, pz, flag);
    }
    private boolean isPay(String url){
        Log.d("PAY:", url);
        //BaseActivity.this.upopPay();
        return url.indexOf("zhifu")>=0;
    }

    private class WebClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (isPay(url)){
                pay(url);
                return true;
            }
            view.loadUrl(url);
            return true;
        }
    }
}
