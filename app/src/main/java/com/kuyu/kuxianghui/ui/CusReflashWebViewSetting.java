package com.kuyu.kuxianghui.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.kuyu.kuxianghui.BuildConfig;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.util.CommonLog;
import com.kuyu.kuxianghui.util.IntentFactory;

/**
 * 这个WebView设置是让浏览器加载数据时，在顶端有ProgressBar显示加载进度。
 *
 * @author fish
 */
public class CusReflashWebViewSetting {

    private static CusReflashWebViewSetting instance = new CusReflashWebViewSetting();

    public static CusReflashWebViewSetting getInstance() {
        return instance;
    }

    public CusReflashWebViewSetting() {
    }

    private String HTML = "html";
    private WebSettings setting;

    public void normalWebView(Context context, final WebView webView, String loadUrl){
        normalWebView(context,webView,loadUrl,null);
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    public void normalWebView(final Context context, final WebView webView, String loadUrl, ProgressBar pb) {

        setting = webView.getSettings();

        //debug模式下增加time参数
        if (BuildConfig.DEBUG_MODE) {
            String time = "?time=" + String.valueOf(System.currentTimeMillis());
            StringBuilder sb = new StringBuilder(loadUrl);
            if (sb.indexOf(HTML) != -1) {
                sb.insert(sb.indexOf(HTML) + HTML.length(), time);
            }
            loadUrl = sb.toString();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        if (pb != null)  webView.setWebViewClient(new SampleWebViewClient(pb));

        setting.setJavaScriptEnabled(true);  //支持js
        setting.setBuiltInZoomControls(false);// 支持缩放
        setting.setSaveFormData(false);// 支持保存数据
        // db操作 start
        setting.setDomStorageEnabled(true);
        String dir = webView.getContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webView.getSettings().setDatabasePath(dir);
        // db操作 end
        setting.setAllowFileAccess(true);
        webView.clearHistory(); // 清除历史记录
        webView.addJavascriptInterface(context, "js_invoke");//添加js调用
        webView.loadUrl(loadUrl);//载入地址
        CommonLog.i(CommonLog.TAG, "loadUrl:" + loadUrl);

        webView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    webView.scrollTo(webView.getScrollX(), webView.getScrollY() + 1);
                }
                return false;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //进行url拦击过滤
                if (overrideUrls(context,url)){
                    Log.d("filter:",url);
                    return true;
                }
                if (isPay(context,url)){
                    pay(context,url);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
    }
    private void pay(Context ctx,String url){
        //zhifu://#TCLPAY;channel_code:UPOP;bankTN:201511111743566247538;flag:5;out_trade_no:INDENT000003546686
        String[] infos = url.split(";");
        String flag = infos[3].split(":")[1];
        String pz = infos[2].split(":")[1];
        String type = infos[1].split(":")[1];
        BaseActivity act = (BaseActivity)ctx;
        act.hytPay(type,pz,flag);
    }
    private boolean isPay(Context ctx,String url){
        Log.d("PAY:",url);
        //BaseActivity.this.upopPay();
        return url.indexOf("zhifu")>=0;
    }

    /**
     * 对酷享汇内的 webView 跳转进行url拦截，不能出现跟酷享汇逻辑不相干的页面
     * url 的拦截规则提现在这个方法里
     * @param context
     * @param url
     * @return true 进行了拦截；false 没有进行拦截
     */
    private boolean overrideUrls(Context context, String url){
        if (url.startsWith(Constants.URL_WAP_TCL_INDEX_SUFF) ||
                url.equals(Constants.URL_WAP_TCL_INDEX) ||
                url.equals(Constants.URL_WAP_TCL_INDEX_2)){
            //拦截垂直电商移动端的首页，重写到酷享汇首页
            context.startActivity(IntentFactory.goHomeFragment(context));
            return true;
        }
        return false;
    }

    private static class SampleWebViewClient extends WebViewClient {
        private ProgressBar mProBar;

        public SampleWebViewClient(ProgressBar progressBar) {
            mProBar = progressBar;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.indexOf("tel:") < 0) {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            mProBar.setProgress(view.getProgress());
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mProBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

    }

}
