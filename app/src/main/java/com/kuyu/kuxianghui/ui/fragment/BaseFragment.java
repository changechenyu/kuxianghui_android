package com.kuyu.kuxianghui.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by fish on 15/9/22.
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;
    protected WebView mWebView;

    protected abstract void findViews();
}
