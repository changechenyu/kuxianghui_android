package com.kuyu.kuxianghui.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.ui.pullwebview.PullToRefreshBase;
import com.kuyu.kuxianghui.ui.pullwebview.PullToRefreshWebView;
import com.kuyu.kuxianghui.util.ActionbarUtil;
import com.kuyu.kuxianghui.util.IntentFactory;

/**
 * Created by fish on 2015/10/10.
 */
public class LocateActivity extends  BaseActivity {
    private PullToRefreshWebView mPullToRefreshWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);

        ActionbarUtil.makeCustomActionBar(this, getActionBar(), getString(R.string.select_place), ActionbarUtil
                .ACTIONBAR_RETURN);

        initeView();
    }

    protected void initeView(){
        mPullToRefreshWebView = (PullToRefreshWebView)findViewById(R.id.pull_refresh_webview);
        mWebView = mPullToRefreshWebView.getRefreshableView();

        mPullToRefreshWebView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {
                    @Override
                    public void onRefresh(
                            PullToRefreshBase<WebView> refreshView) {
                        mWebView.reload();
                        mPullToRefreshWebView.onRefreshComplete();
                    }
                });
        CusReflashWebViewSetting.getInstance().normalWebView(this, mWebView, Constants.URL_LOCATE);

    }


}
