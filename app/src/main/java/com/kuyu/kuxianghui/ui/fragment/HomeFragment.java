package com.kuyu.kuxianghui.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

public class HomeFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;
    private PullToRefreshWebView mPullToRefreshWebView;
    private Handler mHandler = new Handler();
    private  ProgressBar bar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        findViews();
        return mView;
    }

    @Override
    protected void findViews() {
        bar = (ProgressBar)mView.findViewById(R.id.myHomeProgressBar);
        mPullToRefreshWebView = (PullToRefreshWebView) mView.findViewById(R.id.pull_refresh_webview);
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

        mPullToRefreshWebView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {
            @Override
            public void onRefresh(PullToRefreshBase<WebView> refreshView) {
                mWebView.reload();
                mPullToRefreshWebView.onRefreshComplete();
            }
        });
        CusReflashWebViewSetting.getInstance().normalWebView(getActivity(), mWebView, Constants.URL_HOME);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
