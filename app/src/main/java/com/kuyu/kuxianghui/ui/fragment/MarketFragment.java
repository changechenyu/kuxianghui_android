package com.kuyu.kuxianghui.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.ui.CusReflashWebViewSetting;
import com.kuyu.kuxianghui.ui.SplashActivity;
import com.kuyu.kuxianghui.ui.pullwebview.PullToRefreshBase;
import com.kuyu.kuxianghui.ui.pullwebview.PullToRefreshWebView;
import com.kuyu.kuxianghui.util.BaiduLocationUtil;
import com.kuyu.kuxianghui.util.IntentFactory;
import android.os.Handler;

public class MarketFragment extends BaseFragment {

    private PullToRefreshWebView mPullToRefreshWebView;

    RelativeLayout planGoodsBtn;
    RelativeLayout locateBtn;
    RelativeLayout channelView;
    TextView channelStringView;
    TextView slectedChannelView;
    TextView tvShowCity;
    String city;
    private Handler mHandler = new Handler();
    View bgView;
    private ProgressBar bar;

    private String planString = "";
    private String goodsString = "";
    private String slectedBtnString = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        planString = getString(R.string.market_plan);
        goodsString = getString(R.string.market_goods);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_market, container, false);

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
        bar= (ProgressBar) mView.findViewById(R.id.myMarketProgressBar);
        mPullToRefreshWebView = (PullToRefreshWebView) mView.findViewById(R.id.pull_refresh_webview);
        channelView = (RelativeLayout) mView.findViewById(R.id.rl_channel);
        channelStringView = (TextView) mView.findViewById(R.id.tv_tochange);
        bgView = mView.findViewById(R.id.v_bg);
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
        mPullToRefreshWebView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {
                    @Override
                    public void onRefresh(PullToRefreshBase<WebView> refreshView) {
                        mWebView.reload();
                        mPullToRefreshWebView.onRefreshComplete();
                        // Log.i("city--------", getCity());
                    }
                });
//        mWebView.addJavascriptInterface(new JavaScriptInterface(), "showCityInterface");
        //      String city=getCity();
        mWebView.loadUrl("javascript:js_showCity('" + city + "')");

        CusReflashWebViewSetting.getInstance().normalWebView(getActivity(), mWebView, Constants.URL_MARKET);
        //“分销计划”和“分销商品”的切换
        channelStringView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (planString.equals(slectedBtnString)) {
                    slectedChannelView.setText(goodsString);
                    slectedBtnString = goodsString;
                    channelStringView.setText(planString);
                    mWebView.loadUrl(Constants.URL_MARKET_GOODS);
//                    mWebView.loadUrl("javascript:getCity("+MyApplication.city+")");
                } else {
                    slectedChannelView.setText(planString);
                    slectedBtnString = planString;
                    channelStringView.setText(goodsString);
//                    mWebView.addJavascriptInterface(new JavaScriptInterface(), "showCityInterface");
                    mWebView.loadUrl(Constants.URL_MARKET);
                }
                channelView.setVisibility(View.INVISIBLE);
            }
        });
        bgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (channelView.getVisibility() == View.VISIBLE)
                    channelView.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void initActionBar(View customerView) {
        if (customerView == null) return;
        planGoodsBtn = (RelativeLayout) customerView.findViewById(R.id.rl_plan_goods);
        locateBtn = (RelativeLayout) customerView.findViewById(R.id.rl_locate);
        tvShowCity=(TextView)customerView.findViewById(R.id.tv_locate);
        if(SplashActivity.result.equals("0")){
            tvShowCity.setText(BaiduLocationUtil.city);
        }else{
            tvShowCity.setText(R.string.general_city);
        }
        slectedChannelView = (TextView) customerView.findViewById(R.id.action_bar_text);
        if(!"".equals(slectedBtnString)){
            slectedChannelView.setText(slectedBtnString);
        }
        //“分销计划”和“分销商品”的切换
        planGoodsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slectedBtnString = slectedChannelView.getText().toString();
                if (channelView.getVisibility() == View.INVISIBLE) {
                    channelView.setVisibility(View.VISIBLE);
                } else {
                    if (planString.equals(slectedBtnString)){
                        mWebView.loadUrl(Constants.URL_MARKET);
                    }else{
                        mWebView.loadUrl(Constants.URL_MARKET_GOODS);
                    }
                    channelView.setVisibility(View.INVISIBLE);
                }
            }
        });

        locateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IntentFactory.goLocateActivity(getActivity()));
            }
        });
    }

}

