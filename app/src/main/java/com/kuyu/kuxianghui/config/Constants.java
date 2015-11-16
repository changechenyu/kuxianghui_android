package com.kuyu.kuxianghui.config;

import com.kuyu.kuxianghui.BuildConfig;
import com.kuyu.kuxianghui.util.BaiduLocationUtil;

/**
 * Created by fish on 15/9/21.
 */
public class Constants {

//    public static final String DEBUG_SERVER_URL = "http://10.68.5.30/api/distributorCall";
    public static final String DEBUG_SERVER_URL = "http://58.252.192.49/api/distributorCall";
    public static final String RELEASE_SERVER_URL = "http://www.pengpengmao.com/api/v2";
    public static final String SERVER_URL = BuildConfig.DEBUG_MODE ? DEBUG_SERVER_URL : RELEASE_SERVER_URL;

    //API 超时时间 initialTimeoutMs
    public static int initialTimeoutMs = 6000;
    public static int longTimeoutMs = 150000;
    public static final int DEFAULT_MAX_RETRIES = 1;//最大重连次数
    public static final int DEFAULT_NO_RETRIES = 0;

    public static final String RESUlTCODE_SUCCESS = "0";//返回成功
    public static final String USERNAMEISNULL = "2";//用户名为空
    public static final String RESULTCODE_NO_REGISTER = "112";//没有注册
    public static final String USENAMERORPASSWORDISERROR = "-1";//返回错误
    public static final String LOCKUSERNAME = "4";//账号被锁
    public static final String NEEDCODE = "-4";
    public static final String CODEISERROR = "-5";
    public static final String ISNULL="3";

    public static final int PAGE_DETAIL = 101;
    public static final int PAGE_LIST = 102;
    public static final int PAGE_ACTIVITY = 103;
    public static final int PAGE_COMMON = 104;
    public static final int PAGE_OTHER = 110;

    public static final String ALIPAY = "alipay_app";
    public static final String UNIONPAY = "unionpay_app";
    public static final String WECHATPAY = "wechatpay_app";

    public static final String WEIXIN_APP_ID = "wxae47f06b61301db0";
    public static final String WEIXIN_APP_SECRET = "34c3bf52e8146c4df4a239ed1228a73e";
    public static final String QQ_APP_ID = "1104826559";
    public static final String QQ_APP_KEY = "V249P9Xm6YvvWcMW";

    public static final String URL_REGISTER_AGREEMENT = "file:///android_asset/agreement.html";

    public static final String URL_COMMODITY = "file:///android_asset/list.html";
    public static final String URL_DETAIL = "file:///android_asset/detail.html";
    public static final String URL_FIND = "file:///android_asset/list.html";
    public static final String URL_HOME = "file:///android_asset/index.html";
    //    public static final String URL_HOME = "http://10.68.245.189:8888/test/test.html";
    public static final String URL_MARKET = "file:///android_asset/distributionplan.html?province="+BaiduLocationUtil.provice+"&city="+BaiduLocationUtil.city;
    //    public static final String URL_MARKET = "http://10.68.5.30/test.html";
    public static final String URL_MARKET_GOODS = "file:///android_asset/distribution_goods.html";
//    public static final String URL_CART = "http://m.mall.tcl.com/cart/cartshow";
    public static final String URL_CART = "http://m.testmall.tcl.com/cart/cartshow";
    public static final String URL_LOCATE = "file:///android_asset/select_area_province.html";
    //测试环境的商品详情页前缀
    public static final String URL_GOODS_DETAIL_PREFIX = "http://10.120.99.171/tclwap/front/product/toProduct/";
//    public static final String URL_GOODS_DETAIL_PREFIX = "http://m.testmall.tcl.com/front/product/toProduct/";
    //    public static final String URL_CART="file:///android_asset/cart.html";
    //public static final String URL_MY_INCOME="file:///android_asset/my_income.html";
    public static final String URL_SITE_FLOW = "file:///android_asset/site_flow.html";
    public static final String URL_ORDER_DETAIL = "file:///android_asset/my_order_detail.html";
    public static final String URL_EDIT_ADDRESS = "file:///android_asset/edit_address.html";
    public static final String URL_ORDER_COMPLETE = "file:///android_asset/complete.html";
    public static final String URL_SELECT_ADDRESS = "file:///android_asset/address_list.html";
    public static final String URL_ORDER_CONFIRM = "file:///android_asset/order.html";

    public static final String URL_MY_SHARE = "file:///android_asset/my_share.html";
    //public static final String URL_MY_ORDER="file:///android_asset/my_order.html";
    public static final String URL_MY_COLLECT = "file:///android_asset/my_collect.html";
    //public static final String URL_MY_CUSTOMER="file:///android_asset/my_client.html";
    public static final String URL_MY_DEPOSIT = "file:///android_asset/my_record.html";

    public static final String URL_PAY_BANKT = "file:///android_asset/pay_bank.html";


    //yaojt add
    //类目列表URL
    public static final String URL_HOME_CATEGORY = "file:///android_asset/product_category.html";
    public static final String URL_HOME_DISTRIBUTIONPLAN_VIEW = "file:///android_asset/distributionplan_view.html";
    public static final String URL_MINE_TO_COMPLAINT = "file:///android_asset/complaint.html";//去投诉页面
    public static final String URL_MINE_COMPLAINT_SUCCESS = "file:///android_asset/complaint_success.html";
    public static final String URL_MARKET_HOT_PLNA = "file:///android_asset/distributionplanhot.html";
    public static final String URL_MARKET_SELECT_REGION = "file:///android_asset/select_area_region.html";
    public static final String URL_MARKET_SELECT_PROVINCE = "file:///android_asset/select_area_province.html";
    public static final String URL_MARKET_SELECT_CITY = "file:///android_asset/select_area_city.html";
    public static final String URL_MINE_COMPLAINT_DETAIL = "file:///android_asset/complaint_view.html";
    public static final String URL_MINE_FEEDBACK_SUCCESS = "file:///android_asset/feedback_operation_success.html";
    public static final String URL_MINE_HELPCENTER_DEATIL = "file:///android_asset/help_view.html";
    public static final String URL_MINE_APPLY_SUCCESS = "file:///android_asset/apply_for_success.html";
    public static final String URL_MINE_ORDER_DETAIL = "file:///android_asset/order_view.html";
    public static final String URL_MINE_PROFIT_DETAIL = "file:///android_asset/profit_view.html";
    public static final String URL_MARKET_DIFINE_GOODS = "file:///android_asset/distribution_define_goods.html";
    public static final String URL_HOME_HOTPRODUCTS = "hotProducts";


    //酷享汇的html引用常量
    public static final String URL_MY_CUSTOMER = "file:///android_asset/my_customer.html";
    //我的提现
    public static final String URL_MY_CASH = "file:///android_asset/apply_for.html";
    //我的订单
    public static final String URL_MY_ORDER = "file:///android_asset/order_list.html";
    //我的收益
    public static final String URL_MY_INCOME = "file:///android_asset/profit.html";
    //我要投诉
    public static final String URL_MY_COMPLIANT = "file:///android_asset/complaint_list.html";
    //帮助中心
    public static final String URL_HELP = "file:///android_asset/help.html";
    //反馈建议
    public static final String URL_FEEDBACK = "file:///android_asset/feedback.html";
    public static final String URL_WAP_TCL_INDEX = "http://m.mall.tcl.com";
    public static final String URL_WAP_TCL_INDEX_2 = "http://m.mall.tcl.com/";
    public static final String URL_WAP_TCL_INDEX_SUFF = "http://m.mall.tcl.com/index";
    //酷享汇的html引用常量

    //register validate
    public static final int USER_NAME_PASSWORD_MIN = 6;
    public static final int USER_NAME_PASSWORD_MAX = 20;

    public static final String ACTION_GO_TO_CART = "action_go_to_cart";
    public static final String ACTION_GO_SHOPPING = "action_go_shopping";
    public static final String ACTION_REFLASH_CART = "action_reflash_cart";
    public static final String ACTION_REFLASH_ORDER_LIST = "action_reflash_order_list";

    public static final int RQF_PAY = 1;
    public static final int RQF_LOGIN = 2;

    public static final String SHAREPREFERENCE = "sharepreferences";
    public static final String COMMODITY_LIST_FIRST_TIME = "list_first_time";
    public static final String SLIDEMENU_FIRST_TIME = "silemenu_first_time";
    public static final String DETAIL_FIRST_TIME = "detail_first_time";
    public static final String GUIDE_FIRST_TIME = "guide_first_time";

    public static final String SHOW_LOGIN_TIPS_OR_NOT = "show_login_tips_or_not";
}
