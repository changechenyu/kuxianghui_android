package com.kuyu.kuxianghui.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.ui.CusReflashWebViewSetting;
import com.kuyu.kuxianghui.ui.pullwebview.PullToRefreshBase;
import com.kuyu.kuxianghui.ui.pullwebview.PullToRefreshWebView;

public class CartFragment extends BaseFragment {

    private PullToRefreshWebView mPullToRefreshWebView;
    private ProgressBar bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart, container, false);

        findViews();
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected void findViews() {
        bar = (ProgressBar)mView.findViewById(R.id.myCartProgressBar);
        mPullToRefreshWebView = (PullToRefreshWebView) mView.findViewById(R.id.pull_refresh_webview);
        mPullToRefreshWebView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {
            @Override
            public void onRefresh(
                    PullToRefreshBase<WebView> refreshView) {
                mPullToRefreshWebView.onRefreshComplete();
            }
        });
        mWebView = mPullToRefreshWebView.getRefreshableView();
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        CusReflashWebViewSetting.getInstance().normalWebView(getActivity(), mWebView, Constants.URL_CART);
    }

}
