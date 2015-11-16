package com.kuyu.kuxianghui.bright;


/**
 *
 */
public interface RemoteInvokeService {

    public void pay(float money);

    /**
     * 打开ProgressDialog
     */
    public void showProgressDialog();

    /**
     * 跳转到“热门计划”
     */
    public void goHotPlan(String uuids);

    /**
     * 关闭ProgressDialog
     */
    public void exitProgressDialog();

    /**
     * 获取token
     */
    public String getToken();

    /**
     * 调试用的show log功能
     *
     * @param str 要show的字符串
     */
    public void showLog(String str);

    /**
     * 打开通用页面
     */
    public void openCommonActivity(String template, String title, String param);


    /**
     * 打开订单确认界面
     */
    public void openOrderConfirmActivity(String title, String date);

    /**
     * 打开商品详细页
     */
    public void openDetailActivity(String title, String commodity);

    /**
     * 打开订单详情
     */
    public void openOrderDetailActivity(String title, String param);

    /**
     * 打开商品列表页
     */
    public void openCommodityListActivity(String title, String category);

    /**
     * 跳转至活动页，此活动页标题栏有返回按钮和标题
     */
    public void openActivityPage(String url, String title);

    /**
     * 分享功能
     *
     * @param picUrl    该商品图片的Url
     * @param describe  该商品大概描述
     * @param appraise  该商品价格、分享、评价，如：￥4999|分享 20|评价 105
     * @param targetUrl 链接该商品的Url
     */
    public void share(String picUrl, String title, String describe, String appraise, String targetUrl);

    /**
     * 弹出警告Dialog
     */
    public void showWarmingDialog(String title, String content);

    /**
     * 网络异常Dialog
     */
    public void showPageErrorDialog(String title, String content, String resultCode, String comfrom);

    /**
     * 首页
     */
    public void goIndex();

    /**
     * 酷商市场
     */
    public void goMarket();

    /**
     * 购物车
     */
    public void goCart();

    /**
     * "我的"
     */
    public void goMine();

    /**
     * 弹出Toast,应用在详情页收藏中
     */
    public void showToast(String content);

    /**
     * 打开提现页面
     */
    public void openTakeCashActivity();

    /**
     * 拨打电话
     */
    public void makeCall(String number);

    /**
     * 删除确认
     */
    public void deleteConfirm(String title, String content, final String js);

    /**
     * 跳转到类目列表
     */
    public void goCategorys();

    /**
     * 跳转到热门商品
     *
     * @param uuids
     * @param pageName
     */
    public void goDefineGoods(String uuids, String pageName);

    /**
     * 跳转到分类-分销商品列表
     *
     * @param uuids
     */
    public void goDistributionGoods(String uuids);

    /**
     * 跳转到公告详情
     *
     * @param uuids
     * @param pageName
     */
    public void goNoticeDetail(String uuids, String pageName);

    /**
     * 跳到分销商品
     */
    public void goDistributionGoods();

    /**
     * 跳转到分销计划
     */
    public void goDistributionPlan();

    /**
     * 跳转到选择地区
     *
     * @param fromPage
     */
    public void goSelectArea(String fromPage);

    /**
     * 跳转到省-选择市
     *
     * @param provinceUuid
     * @param fromPage
     */
    public void goSelectAreaProvince(String provinceUuid, String fromPage);

    /**
     * 跳转到分销计划详情
     *
     * @param uuid
     */
    public void goPlanDetail(String uuid);

    /**
     * 我要投诉
     */
    public void goComplaintApply(String storeName, String orderNo, String storeUuid);

    /**
     * 通用的汇银通支付接口
     *
     * @param chace_codeString 支付渠道号,后台返回支付凭证中的bank_code字段
     * @param payPZ            支付凭证，后台返回支付凭证中的bnk_inf字段
     * @param flag             标志位，后台返回支付凭证中的flag字段
     */
    public void hytPay(String chace_codeString, String payPZ, String flag);

    /**
     * 汇银通的银联支付接口
     *
     * @param payPZ 支付凭证，后台返回支付凭证中的bnk_inf字段
     * @param flag  标志位，后台返回支付凭证中的flag字段
     */
    public void upopPay(String payPZ, String flag);

    /**
     * 汇银通的支付宝接口
     *
     * @param payPZ 支付凭证，后台返回支付凭证中的bnk_inf字段
     * @param flag  标志位，后台返回支付凭证中的flag字段
     */
    public void aliPay(String payPZ, String flag);

    /**
     * 投诉成功
     */
    public void goComplaintSuccess();

    /**
     * 进入商品详情页
     */
    public void goProductDetail(String uuid, String planUuid, String distributorUuid);

    /*
     * 去分销商品列表
     *
     * @param uuid
     */
    public void goDistributionGoodsList(String uuid);

    /**
     * 去热门地区-分销商品
     *
     * @param areaCode
     */
    public void goHotAreaDistributionGoods(String areaCode);

    /**
     * 省-选择市
     *
     * @param cityUuid
     * @param fromPage
     */
    public void goSelectAreaCity(String cityUuid, String fromPage);

    /**
     * 投诉详情
     *
     * @param uuid
     */
    public void goComplaintDetail(String uuid);

    /**
     * 撤销投诉成功页
     */
    public void goCancelComplaintSuccess();

    /**
     * 反馈建议成功页
     */
    public void goFeedbackSuccess();

    /**
     * 帮助中心详情页
     *
     * @param uuid
     */
    public void goHelpDetail(String uuid);

    /**
     * 提现申请
     */
    public void goWithdrawApply();

    /**
     * 提现成功
     */
    public void goWithdrawSuccess();

    /**
     * 订单详情
     *
     * @param orderUuid
     * @param showWhich
     */
    public void goOrderDetail(String orderUuid, String showWhich);

    /**
     * 收益详情
     *
     * @param uuid
     */
    public void goProfitDetail(String uuid);

}
