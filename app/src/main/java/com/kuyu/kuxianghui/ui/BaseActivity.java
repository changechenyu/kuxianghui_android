package com.kuyu.kuxianghui.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.bright.RemoteInvokeService;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.config.MyApplication;
import com.kuyu.kuxianghui.db.AddressDBDao;
import com.kuyu.kuxianghui.db.BankDBDao;
import com.kuyu.kuxianghui.db.UserDBDao;
import com.kuyu.kuxianghui.ui.dialog.Effectstype;
import com.kuyu.kuxianghui.ui.dialog.NiftyDialogBuilder;
import com.kuyu.kuxianghui.util.CommonLog;
import com.kuyu.kuxianghui.util.DialogUtil;
import com.kuyu.kuxianghui.util.IntentFactory;
import com.kuyu.kuxianghui.util.ToastUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tcl.pay.sdk.SDK_PayJarActivity;
import com.tcl.pay.sdk.entity.PayResult;

import java.util.Map;

/**
 * Created by fish on 15/9/19.
 */
public class BaseActivity extends FragmentActivity implements RemoteInvokeService {

    protected AddressDBDao addressDBDao;
    protected UserDBDao userDBDao;
    protected BankDBDao bankDBDao;

    protected WebView mWebView;
    protected MyApplication mMyApplication = MyApplication.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDBDao = UserDBDao.getInstance(getApplicationContext());
        addressDBDao = AddressDBDao.getInstance(getApplicationContext());
        bankDBDao = BankDBDao.getInstance(getApplicationContext());
        //ImageLoader配置
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)  //1.8.6包使用时候，括号里面传入参数true
                .cacheOnDisc(true)    //1.8.6包使用时候，括号里面传入参数true
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .defaultDisplayImageOptions(defaultOptions)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .enableLogging() // Not necessary in common
                .build();
        ImageLoader.getInstance().init(config);
        addressDBDao = AddressDBDao.getInstance(getApplicationContext());
        bankDBDao = BankDBDao.getInstance(getApplicationContext());
    }

    /**
     * 汇银通统一的支付接口
     *
     * @param chace_codeString 支付渠道号,后台返回支付凭证中的bank_code字段
     * @param payPZ            支付凭证，后台返回支付凭证中的bnk_inf字段
     * @param flag             标志位，后台返回支付凭证中的flag字段
     */
    @Override
    @JavascriptInterface
    public void hytPay(String chace_codeString, String payPZ, String flag) {
        //String chace_codeString = "UPOP";//UPOP-银联    ALIPAY -支付宝
        //String payPZ = "";
        //String flag = "5";
        //payPZ = "201508031147335802299";
        //目前先采用这种调用方式
        new SDK_PayJarActivity().doInbokeJar(this, chace_codeString, payPZ, flag, activityHandler);
        //此方式暂时不用
        //new SDK_PayClass().start(this, payPZ, chace_codeString, classHandler);
    }

    /**
     * 银联支付接口调用
     *
     * @param payPZ 支付凭证，后台返回支付凭证中的bnk_inf字段
     * @param flag  标志位，后台返回支付凭证中的flag字段
     */
    @Override
    @JavascriptInterface
    public void upopPay(String payPZ, String flag) {
        String chace_codeString = "UPOP";//UPOP-银联    ALIPAY -支付宝
        //String payPZ = "";
        //String flag = "5";
        //payPZ = "201508031147335802299";
        //目前先采用这种调用方式
        new SDK_PayJarActivity().doInbokeJar(this, chace_codeString, payPZ, flag, activityHandler);
        //此方式暂时不用
        //new SDK_PayClass().start(this, payPZ, chace_codeString, classHandler);

    }

    @Override
    @JavascriptInterface
    public void aliPay(String payPZ, String flag) {
        String chace_codeString = "ALIPAY";
        //payPZ = "_input_charset=\"utf-8\"%26body=\"Test_one\"%26notify_url=\"https%3A%2F%2Fgw.tclpay.cn%2Fbkg%2FOBKGPUB1%2F4410024.dow\"%26out_trade_no=\"20150803000000003517\"%26partner=\"2088021214624556\"%26payment_type=\"1\"%26seller_id=\"jinrong.cai@tcl.com\"%26service=\"mobile.securitypay.pay\"%26sign=\"vTGs4q70DCBUmejxyh4ANEx8gr%2ByAGAJ9wazqyMAC090uNOetsJnhvHD1yJG4exULetUgp4Sn5LV%2FMSGdoPpGkBKJb6Gv3eyXshXClLm4sa3aNvML1x8hVRSA94tkTAYVA%2Fwr1%2FsY4oKXgI3rCX57U4rZTrsK6yk0nrGyLHTjhI%3D\"%26sign_type=\"RSA\"%26subject=\"1920-20150803000000003517\"%26total_fee=\"0.01\"";
        //目前先采用这种调用方式
        new SDK_PayJarActivity().doInbokeJar(this, chace_codeString, payPZ, flag, activityHandler);
        //此方式暂时不用
        //new SDK_PayClass().start(this, payPZ, chace_codeString, classHandler);
    }

//    public void onClick(View view){
//        String chace_codeString = "";//UPOP-银联    ALIPAY -支付宝
//        String payPZ = "";
//        String flag = "5";
//        switch (view.getId()) {
//            case R.id.btn_yinlian://银联支付
//                chace_codeString = "UPOP";
//                payPZ = "201508031147335802268";
//                new SDK_PayJarActivity().doInbokeJar(this ,chace_codeString, payPZ, flag,activityHandler);
//                break;
//            case R.id.btn_zhifubao://支付宝支付
//                chace_codeString = "ALIPAY";
//                payPZ = "_input_charset=\"utf-8\"%26body=\"Test_one\"%26notify_url=\"https%3A%2F%2Fgw.tclpay.cn%2Fbkg%2FOBKGPUB1%2F4410024.dow\"%26out_trade_no=\"20150803000000003517\"%26partner=\"2088021214624556\"%26payment_type=\"1\"%26seller_id=\"jinrong.cai@tcl.com\"%26service=\"mobile.securitypay.pay\"%26sign=\"vTGs4q70DCBUmejxyh4ANEx8gr%2ByAGAJ9wazqyMAC090uNOetsJnhvHD1yJG4exULetUgp4Sn5LV%2FMSGdoPpGkBKJb6Gv3eyXshXClLm4sa3aNvML1x8hVRSA94tkTAYVA%2Fwr1%2FsY4oKXgI3rCX57U4rZTrsK6yk0nrGyLHTjhI%3D\"%26sign_type=\"RSA\"%26subject=\"1920-20150803000000003517\"%26total_fee=\"0.01\"";
//                new SDK_PayJarActivity().doInbokeJar(this ,chace_codeString, payPZ, flag,activityHandler);
//
//                break;
//            case R.id.btn_yinlian_hanlder://handler方式调用银联
//                chace_codeString = "UPOP";
//                payPZ = "201508031147335802268";
//                new SDK_PayClass().start(Info.this, payPZ, chace_codeString, classHandler);
//                break;
//            case R.id.btn_zhifubao_hanlder://handler方式调用支付宝
//                chace_codeString = "ALIPAY";
//                payPZ = "_input_charset=\"utf-8\"%26body=\"Test_one\"%26notify_url=\"https%3A%2F%2Fgw.tclpay.cn%2Fbkg%2FOBKGPUB1%2F4410024.dow\"%26out_trade_no=\"20150803000000003517\"%26partner=\"2088021214624556\"%26payment_type=\"1\"%26seller_id=\"jinrong.cai@tcl.com\"%26service=\"mobile.securitypay.pay\"%26sign=\"vTGs4q70DCBUmejxyh4ANEx8gr%2ByAGAJ9wazqyMAC090uNOetsJnhvHD1yJG4exULetUgp4Sn5LV%2FMSGdoPpGkBKJb6Gv3eyXshXClLm4sa3aNvML1x8hVRSA94tkTAYVA%2Fwr1%2FsY4oKXgI3rCX57U4rZTrsK6yk0nrGyLHTjhI%3D\"%26sign_type=\"RSA\"%26subject=\"1920-20150803000000003517\"%26total_fee=\"0.01\"";
//                new SDK_PayClass().start(Info.this, payPZ, chace_codeString, classHandler);
//                break;
//
//            default:
//                break;
//        }
//    }

    private Handler classHandler = new Handler() {
        public void handleMessage(Message msg) {
            String resultString = "";
            //返回字段名称：PAYSTATE对应三个值0：支付成功；1：支付失败；2：支付处理中；
            Map<String, Object> map = (Map<String, Object>) msg.obj;
            String string = map.get("PAYSTATE").toString() + "";
            if (TextUtils.equals(string, "0")) {
                resultString = "支付成功";
            } else if (TextUtils.equals(string, "2")) {
                resultString = "支付处理中";
            } else if (TextUtils.equals(string, "1")) {
                resultString = "支付失败";
            }
            System.out.println("result:" + resultString);
        }

        ;
    };

    /**
     * 处理支付宝支付结果
     */
    @SuppressLint("HandlerLeak")
    private Handler activityHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 7) {//处理支付宝支付结果
                PayResult payResult = new PayResult((String) msg.obj);

                // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                String resultInfo = payResult.getResult();
                String resultStatus = payResult.getResultStatus();
                String msgResult = "";
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    msgResult = "支付成功";
                } else {
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        msgResult = "支付结果确认中";
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        msgResult = "支付失败";
                    }
                }
                Toast.makeText(BaseActivity.this, msgResult, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * TODO 接收返回值.
     *
     * @see android.app.Activity#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (17 == resultCode) {

        }
        /*************************************************
         *
         * 处理银联手机支付控件返回的支付结果
         *
         ************************************************/
        if (data == null) {
            return;
        }

        if (data.getExtras() == null) {
            return;
        }
        String msg = "";
            /*
             * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
			 */
        String str = data.getExtras().getString("pay_result");
        Bundle extras = data.getExtras();
        Log.e("123", extras.toString());
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消支付";
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 分享功能
     *
     * @param picUrl    该商品图片的Url
     * @param describe  该商品大概描述
     * @param appraise  该商品价格、分享、评价，如：￥4999|分享 20|评价 105
     * @param targetUrl 链接该商品的Url
     */
    @Override
    @JavascriptInterface
    public void share(String picUrl, String title, String describe, String appraise, String targetUrl) {
        Intent intent = new Intent(this, ShareActivity.class);
        intent.putExtra("picUrl", picUrl);
        intent.putExtra("title", title);
        intent.putExtra("describe", describe);
        intent.putExtra("appraise", appraise);
        intent.putExtra("targetUrl", targetUrl);
        this.startActivity(intent);
    }

    /**
     * 打开商品详细页
     */
    @Override
    @JavascriptInterface
    public void openDetailActivity(String title, String commodity) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("page", Constants.PAGE_DETAIL);
        intent.putExtra("title", title);
        intent.putExtra("commodity", commodity);
        this.startActivity(intent);
    }

    /**
     * 打开商品列表页
     */
    @Override
    @JavascriptInterface
    public void openCommodityListActivity(String title, String category) {
        Intent intent = new Intent(this, ReturnActivity.class);
        intent.putExtra("page", Constants.PAGE_LIST);
        intent.putExtra("title", title);
        intent.putExtra("category", category);
        this.startActivity(intent);
    }

    /**
     * 跳转至活动页，此活动页标题栏有返回按钮和标题
     */
    @Override
    @JavascriptInterface
    public void openActivityPage(String url, String title) {
        CommonLog.i(CommonLog.TAG, "OpenActivityPage" + url);
        Intent intent = new Intent(this, ReturnActivity.class);
        intent.putExtra("page", Constants.PAGE_ACTIVITY);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        this.startActivity(intent);
    }

    @Override
    public void pay(float money) {

    }

    @Override
    @JavascriptInterface
    public void showProgressDialog() {
        DialogUtil.showProgressDialog(this);
    }

    @Override
    @JavascriptInterface
    public void goHotPlan(String uuids) {
        //热门计划
        startActivity(goHtmlActivity(this, getString(R.string.market_distribution_hotplan), Constants.URL_MARKET_HOT_PLNA)); //布局distribution_define_goods.html
    }

    @Override
    @JavascriptInterface
    public void exitProgressDialog() {
        DialogUtil.exitProgressDialog();
    }

    @Override
    @JavascriptInterface
    public String getToken() {
        String token = userDBDao.getToken();
        return token;
    }

    @Override
    @JavascriptInterface
    public void showLog(String str) {
        CommonLog.i(CommonLog.TAG, str);
    }

    @Override
    @JavascriptInterface
    public void openOrderConfirmActivity(String title, String date) {
        Intent intent = new Intent(this, OrderConfirmActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("date", date);
        this.startActivity(intent);
    }

    @Override
    @JavascriptInterface
    public void showWarmingDialog(String title, String content) {
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withPPMaoDef()
                .withMessage(content)
                .isCancelableOnTouchOutside(false)
                .withEffect(Effectstype.Slidetop)
                .withButton1Text(R.string.ok)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    @JavascriptInterface
    public void showPageErrorDialog(String title, String content,
                                    final String resultCode, String comfrom) {

        if (40013 == Integer.valueOf(resultCode)) {
            userDBDao.updataToken(null, null, userDBDao.getPid());
            //ToastUtils.showText(this, R.string.login_time_out,ToastUtils.TWO_SECOND);
            Toast.makeText(this, comfrom + "", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withPPMaoDef()
                .withMessage(content)
                .isCancelableOnTouchOutside(false)
                .withEffect(Effectstype.Slidetop)
                .withButton1Text(R.string.close)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    @JavascriptInterface
    public void goIndex() {
        startActivity(IntentFactory.goHomeFragment(getApplication()));
    }

    @Override
    @JavascriptInterface
    public void goMarket() {
        startActivity(IntentFactory.goMarketFragment(getApplication()));
    }

    @Override
    @JavascriptInterface
    public void goCart() {
        startActivity(IntentFactory.goCartFragment(getApplication()));
    }

    @Override
    @JavascriptInterface
    public void goMine() {
        startActivity(IntentFactory.goMineFragment(getApplication()));
    }

    @Override
    @JavascriptInterface
    public void showToast(String content) {
        ToastUtils.makeText(this, content, ToastUtils.TWO_SECOND);
    }

    /**
     * 打开提现申请页
     */
    @Override
    @JavascriptInterface
    public void openTakeCashActivity() {
        Intent intent = new Intent(this, TakeCashActivity.class);
        startActivity(intent);
    }

    /**
     * 替换号码传来String中的tell://,然后拨号
     */
    @Override
    @JavascriptInterface
    public void makeCall(String number) {
        String tel = number.replace("//", "");
        Uri uri = Uri.parse(tel);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(it);
    }

    @Override
    @JavascriptInterface
    public void openOrderDetailActivity(String title, String data) {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("date", data);
        this.startActivity(intent);
    }

    @Override
    @JavascriptInterface
    public void openCommonActivity(String template, String title, String param) {
        Intent intent = new Intent(this, ReturnActivity.class);
        intent.putExtra("template", template);
        intent.putExtra("title", title);
        intent.putExtra("param", param);
        intent.putExtra("page", Constants.PAGE_COMMON);
        this.startActivity(intent);

    }

    @Override
    @JavascriptInterface
    public void deleteConfirm(String title, String content, final String js) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);

        dialogBuilder
                .withPPMaoDef()
                .withMessage(content)
                .isCancelableOnTouchOutside(false)
                .withEffect(Effectstype.Shake)
                .withButton1Text(R.string.confirm)
                .withButton2Text(R.string.cancel)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mWebView.loadUrl("javascript:" + js);
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }

    @Override
    @JavascriptInterface
    public void goCategorys() {
        //跳转到类目列表
        startActivity(goHtmlActivity(this, getString(R.string.market_category_select), Constants.URL_HOME_CATEGORY));
    }

    @Override
    @JavascriptInterface
    public void goDefineGoods(String uuids, String pageName) {
        //跳到热门商品/新品惠
        if (pageName.equals(Constants.URL_HOME_HOTPRODUCTS)) { //热门商品
            startActivity(goHtmlActivity(this, getString(R.string.market_define_goods), Constants.URL_MARKET_DIFINE_GOODS)); //热门商品页面URL
        } else { //新品惠
            startActivity(goHtmlActivity(this, getString(R.string.home_new_products), Constants.URL_MARKET_DIFINE_GOODS)); //热门商品页面URL
        }
    }

    @Override
    @JavascriptInterface
    public void goDistributionGoods(String uuid) {
        //热门地区-分销商品
        //还有一个 分类-分销商品列表 待实现
        startActivity(goHtmlActivity(this, "分类-分销商品列表/热门地区-分销商品", Constants.URL_MARKET_GOODS)); //布局select_area_province.html
    }

    @Override
    @JavascriptInterface
    public void goNoticeDetail(String uuids, String pageName) {
        //跳转到公告详情
        startActivity(goHtmlActivity(this, getString(R.string.home_notice_detail), Constants.URL_HOME_CATEGORY));
    }

    @Override
    @JavascriptInterface
    public void goDistributionGoods() {
        //跳到分销商品页
        startActivity(goHtmlActivity(this, getString(R.string.market_distribution_goods), Constants.URL_MARKET_GOODS));
    }

    @Override
    @JavascriptInterface
    public void goDistributionPlan() {
        //跳转到分销计划
        startActivity(goHtmlActivity(this, getString(R.string.market_distribution_plan), Constants.URL_MARKET));
    }

    @Override
    @JavascriptInterface
    public void goSelectArea(String fromPage) {
        //跳转到选择地区-省选择
        startActivity(goHtmlActivity(this, getString(R.string.market_select_province), Constants.URL_MARKET_SELECT_PROVINCE));
    }

    @Override
    @JavascriptInterface
    public void goSelectAreaProvince(String provinceUuid, String fromPage) {
        //这里是跳到省-市选择
        startActivity(goHtmlActivity(this, getString(R.string.market_select_city), Constants.URL_MARKET_SELECT_CITY));
    }

    @Override
    @JavascriptInterface
    public void goPlanDetail(String uuid) {
        //分销计划详情
        startActivity(goHtmlActivity(this, getString(R.string.market_distribution_detail), Constants.URL_HOME_DISTRIBUTIONPLAN_VIEW));
    }

    @Override
    @JavascriptInterface
    public void goComplaintSuccess() {
        //投诉成功
        startActivity(goHtmlActivity(this, getString(R.string.mine_complaint_success), Constants.URL_MINE_COMPLAINT_SUCCESS));
    }

    @Override
    @JavascriptInterface
    public void goDistributionGoodsList(String uuid) {
        //分类-分销商品列表
        startActivity(goHtmlActivity(this, getString(R.string.market_distributiongood_list), Constants.URL_MARKET_GOODS));
    }

    @Override
    @JavascriptInterface
    public void goHotAreaDistributionGoods(String areaCode) {
        //热门地区-分销商品
        startActivity(goHtmlActivity(this, getString(R.string.market_distributiongood_list), Constants.URL_MARKET_GOODS));
    }

    @Override
    @JavascriptInterface
    public void goSelectAreaCity(String cityUuid, String fromPage) {
        //省-选择市
        startActivity(goHtmlActivity(this, getString(R.string.market_select_rigion), Constants.URL_MARKET_SELECT_REGION));
    }

    @Override
    @JavascriptInterface
    public void goComplaintDetail(String uuid) {
        //投诉详情
        startActivity(goHtmlActivity(this, getString(R.string.mine_complaint_detail), Constants.URL_MINE_COMPLAINT_DETAIL));
    }

    @Override
    @JavascriptInterface
    public void goCancelComplaintSuccess() {
        //撤销投诉成功页
        startActivity(goHtmlActivity(this, getString(R.string.mine_cancelcomplaint_success), Constants.URL_HOME_CATEGORY));
    }

    @Override
    @JavascriptInterface
    public void goFeedbackSuccess() {
        //反馈意见成功页
        startActivity(goHtmlActivity(this, getString(R.string.mine_feedback_success), Constants.URL_MINE_FEEDBACK_SUCCESS));
    }

    @Override
    @JavascriptInterface
    public void goHelpDetail(String uuid) {
        //帮助中心详情页
        startActivity(goHtmlActivity(this, getString(R.string.mine_helpcenter_detail), Constants.URL_MINE_HELPCENTER_DEATIL));
    }

    @Override
    @JavascriptInterface
    public void goWithdrawApply() {
        //提现申请
        startActivity(goHtmlActivity(this, getString(R.string.mine_drawmoney_apply), Constants.URL_MY_CASH));
    }

    @Override
    @JavascriptInterface
    public void goWithdrawSuccess() {
        //提现成功
        startActivity(goHtmlActivity(this, getString(R.string.mine_applymoney_success), Constants.URL_MINE_APPLY_SUCCESS));
    }

    @Override
    @JavascriptInterface
    public void goOrderDetail(String orderUuid, String showWhich) {
        //订单详情
        startActivity(goHtmlActivity(this, getString(R.string.mine_order_detail), Constants.URL_MINE_ORDER_DETAIL));
    }

    @Override
    @JavascriptInterface
    public void goProfitDetail(String uuid) {
        //收益详情
        startActivity(goHtmlActivity(this, getString(R.string.mine_profit_detail), Constants.URL_MINE_PROFIT_DETAIL));
    }

    /**
     * 进入商品详情页
     */
    @Override
    @JavascriptInterface
    public void goProductDetail(String uuid, String planUuid, String distributorUuid) {
        String goodsDetailUrl = Constants.URL_GOODS_DETAIL_PREFIX + uuid + "?" +
                "planUuid=" + planUuid + "&" + "distributorUuid=" + distributorUuid;
        startActivity(goHtmlActivity(this, getString(R.string.goods_detail), goodsDetailUrl));
    }

    @Override
    @JavascriptInterface
    public void goComplaintApply(String storeName, String orderNo, String storeUuid) {
        //我要投诉
        startActivity(goHtmlActivity(this, getString(R.string.mine_to_complaint), Constants.URL_MINE_TO_COMPLAINT));
    }

    /**
     * 加载html用
     *
     * @param ctx a
     * @param title a
     * @param url a
     * @return Intent
     */
    public Intent goHtmlActivity(Context ctx, String title, String url) {
        Intent intent = new Intent(ctx, HtmlActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        return intent;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void handleActionBarClick(View v) {
        switch (v.getId()) {
            default:
                this.finish();
                break;
        }
    }

}
