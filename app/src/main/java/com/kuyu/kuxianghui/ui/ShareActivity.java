package com.kuyu.kuxianghui.ui;

import android.app.ActionBar;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.config.MyApplication;
import com.kuyu.kuxianghui.db.UserDBDao;
import com.kuyu.kuxianghui.network.VolleyImageDao;
import com.kuyu.kuxianghui.util.ActionbarUtil;
import com.kuyu.kuxianghui.util.CommonLog;
import com.kuyu.kuxianghui.util.NetState;
import com.kuyu.kuxianghui.util.ToastUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.ShareType;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.MailShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import java.util.Hashtable;


/**
 * Created by fish on 15/9/21.
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private RadioGroup rg_button;
    private RadioButton rg_link;
    private RadioButton rg_qrcode;

    private TextView tv_title;
    private NetworkImageView iv_qrcode;
    private EditText et_describe;

    protected Button btn_wx_moments;

    protected Button btn_wx_friend;

    protected Button btn_sina;

    protected Button btn_tencent;

    protected Button btn_qzone;

    protected Button btn_qq;
    protected Button btn_link;
    protected Button btn_mail;

    public UMSocialService mController;
    private UMQQSsoHandler qqSsoHandler;
    private UserDBDao userDBDao;
    private String picUrl;//商品图片
    private String title;
    private String describe;
    private String targetUrl;
    private String where="other";

    //图片加载
    VolleyImageDao imageDao = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyApplication.addToApplicationActivityStack(this);
        setContentView(R.layout.activity_share);
        imageDao = VolleyImageDao.getInstance(getApplicationContext());
        ActionBar bar = getActionBar();
        ActionbarUtil.makeCustomActionBar(this, bar, getString(R.string.share), ActionbarUtil.ACTIONBAR_RETURN);

        Intent intent = getIntent();
        picUrl = intent.getStringExtra("picUrl");
        title = intent.getStringExtra("title");//
        describe = intent.getStringExtra("describe");//可编辑的内容
        targetUrl = intent.getStringExtra("targetUrl");
        afterViews();
    }


    protected void afterViews() {
        findViewById(R.id.btn_link).setOnClickListener(this);
        findViewById(R.id.btn_mail).setOnClickListener(this);
        findViewById(R.id.btn_wx_friend).setOnClickListener(this);
        findViewById(R.id.btn_wx_moments).setOnClickListener(this);
        findViewById(R.id.btn_qq).setOnClickListener(this);
        findViewById(R.id.btn_qzone).setOnClickListener(this);
        findViewById(R.id.btn_sina).setOnClickListener(this);
        findViewById(R.id.btn_tencent).setOnClickListener(this);

        rg_button = (RadioGroup)findViewById(R.id.rg_button);
        rg_link = (RadioButton)findViewById(R.id.rb_link);
        rg_qrcode = (RadioButton)findViewById(R.id.rb_qrcode);

        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_qrcode = (NetworkImageView)findViewById(R.id.iv_qrcode);
        et_describe = (EditText)findViewById(R.id.et_share);

        rg_button.setOnCheckedChangeListener(this);
        rg_link.setOnCheckedChangeListener(this);
        if (title!=null) {
            tv_title.setText(String.valueOf(title));
        }
        if (describe!=null) {
            et_describe.setText(String.valueOf(describe));
            et_describe.setSelection(describe.length());//将光标移至文字末尾
        }
        userDBDao=UserDBDao.getInstance(this);
        // 图片加载
        initPicData();

        initShareController();
        //setTargetUrl();
    }

    public void onClick(View v) {
        describe = et_describe.getText().toString();
        mController.getConfig().cleanListeners();
        switch (v.getId()) {
            case R.id.btn_wx_moments:{ // 朋友圈
                showProgressDialog();
                CircleShareContent circleMedia = new CircleShareContent();
                circleMedia.setShareContent(describe);
                circleMedia.setTitle(title);
                circleMedia.setShareImage(newUMImage());
                circleMedia.setTargetUrl(targetUrl);
                mController.setShareMedia(circleMedia);
                where = "weixin_circle";
                mController.postShare(this, SHARE_MEDIA.WEIXIN_CIRCLE, snsListener);
                break;
            }
            case R.id.btn_wx_friend: { // 微信
                showProgressDialog();
                WeiXinShareContent weixinContent = new WeiXinShareContent();
                weixinContent.setShareContent(describe);
                weixinContent.setTitle(title);
                weixinContent.setShareImage(newUMImage());
                weixinContent.setTargetUrl(targetUrl);
                mController.setShareMedia(weixinContent);
                where = "weixin";
                mController.postShare(this, SHARE_MEDIA.WEIXIN, snsListener);
                break;
            }
            case R.id.btn_sina: { // 新浪微博
                showProgressDialog();
                SinaShareContent sinaContent = new SinaShareContent();
                sinaContent.setShareContent(describe);
                sinaContent.setShareImage(newUMImage());
                sinaContent.setTargetUrl(targetUrl);
                sinaContent.setTitle(title);
                mController.setShareMedia(sinaContent);
                where = "tsina";
                mController.postShare(this, SHARE_MEDIA.SINA, snsListener);
                break;
            }
            case R.id.btn_qzone: { // QQ空间
                if(describe==null||describe.length()<=0){
                    ToastUtils.makeText(this,"请输入分享语");
                    break;
                }
                showProgressDialog();
                QZoneShareContent qzone = new QZoneShareContent();
                qzone.setShareContent(describe);
                qzone.setTitle(title);
                qzone.setTargetUrl(targetUrl);
                qzone.setShareImage(newUMImage());
                mController.setShareMedia(qzone);
                where = "qzone";
                mController.postShare(ShareActivity.this, SHARE_MEDIA.QZONE, snsListener);
                break;
            }
//            case R.id.btn_tencent: { // 腾讯微博
//                showProgressDialog();
//                TencentWbShareContent tencent = new TencentWbShareContent();
//                tencent.setShareContent(inputString + "\n" + title + appraise
//                        + "\n" + targetUrl + "\n");
//                tencent.setShareImage(new UMImage(this, picUrl));
//                mController.setShareMedia(tencent);
//                where = "tqq";
//                mController.postShare(ShareActivity.this, SHARE_MEDIA.TENCENT, snsListener);
//                break;
//            }
            case R.id.btn_tencent:{
                // 设置短信分享内容
                showProgressDialog();
                SmsShareContent sms = new SmsShareContent();
                sms.setShareContent(title + "\n" + describe + "\n" + targetUrl);
                // sms.setShareImage(urlImage);
                mController.setShareMedia(sms);
                mController.postShare(ShareActivity.this, SHARE_MEDIA.SMS, snsListener);
             break;
            }
            case R.id.btn_qq: { // QQ好友
                showProgressDialog();
                QQShareContent qq = new QQShareContent();
                qq.setShareContent(describe);
                qq.setTitle(title);
                qq.setTargetUrl(targetUrl);
                qq.setShareImage(newUMImage());
                mController.setShareMedia(qq);
                where = "sqq";
                mController.postShare(ShareActivity.this, SHARE_MEDIA.QQ, snsListener);
                break;
            }
            case R.id.btn_link: { // 粘贴板
                ClipboardManager cmb = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(title+"\n"+describe+"\n"+targetUrl);
                //ClipData data=new ClipData(ClipData.newPlainText(targetUrl, shareTitle));
                //cmb.setPrimaryClip(data);
                ToastUtils.showText(ShareActivity.this, R.string.share_has_copy_to_clipboard, ToastUtils.TWO_SECOND);
                break;
            }
            case R.id.btn_mail: { // 邮件
                showProgressDialog();
                MailShareContent mail = new MailShareContent();
                mail.setShareContent(describe+ "\n" + targetUrl);
                mail.setTitle(title);
                //mail.setShareImage(new UMImage(this, picUrl));
                where = "email";
                mController.setShareMedia(mail);
                mController.setShareType(ShareType.NORMAL);
                mController.postShare(ShareActivity.this, SHARE_MEDIA.EMAIL, snsListener);
                break;
            }
            default:
                break;
        }
    }

    private UMImage newUMImage(){
        UMImage image = null;
        if (rg_button.getCheckedRadioButtonId()==R.id.rb_link){
            image = new UMImage(this,picUrl);
        }else if (rg_button.getCheckedRadioButtonId()==R.id.rb_qrcode){
            image = new UMImage(this,createQRImage(targetUrl,iv_qrcode.getWidth(),iv_qrcode.getHeight()));
        }
        return image;
    }

    SocializeListeners.SnsPostListener snsListener =new SocializeListeners.SnsPostListener() {

        @Override
        public void onStart() {
            //exitProgressDialog();
        }
        @Override
        public void onComplete(SHARE_MEDIA arg0, int code, SocializeEntity arg2) {
            //成功分享,立即返回200
            //成功分享,留在微信不返回,再退出微信,不调用此方法
            //打开分享界面,不分享直接返回4000
            if(code==200){
                ToastUtils.showText(ShareActivity.this, R.string.share_succeed, ToastUtils.ONE_SECOND);
                //调试阶段,暂时注释
                // uploadShareMsg(targetUrl, inputString, where);
            }
            exitProgressDialog();
        }
    };


    private void initPicData() {
        // 图片加载器配置
        //picUrl = "http://www.tcl.com/content/pc/images/logo_tcl.png";
        //title = "今天看到了TCL Q65H9700 65英寸真4K电视北欧设计IFA量子点智能电视,真的很好推荐你们使用哦!";
        //describe="TCL Q65H9700 65英寸真4K电视 北欧设计IFA量子点智能电视";
        //targetUrl="http://shop.tcl.com/wap/goods/index/cat_id/21/attrs_51/518";
        imageDao.setNetworkImage(iv_qrcode, picUrl);
    }


    // 初始化分享工具
    private void initShareController() {
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        //mController.registerListener(snsListener);
        mController.getConfig().closeToast();
        mController.getConfig().setDefaultShareLocation(false);

        // 添加QQ空间分享渠道
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, Constants.QQ_APP_ID, Constants.QQ_APP_KEY);
        qZoneSsoHandler.addToSocialSDK();
        // 添加email分享渠道
        EmailHandler emailHandler = new EmailHandler();
        emailHandler.addToSocialSDK();
        // 添加微信平台分享渠道
        UMWXHandler wxHandler = new UMWXHandler(this,Constants.WEIXIN_APP_ID,Constants.WEIXIN_APP_SECRET);
        wxHandler.addToSocialSDK();
        // 添加朋友圈分享渠道
        UMWXHandler wxCircleHandler = new UMWXHandler(this,Constants.WEIXIN_APP_ID,Constants.WEIXIN_APP_SECRET);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        // 添加QQ好友分享渠道
        qqSsoHandler = new UMQQSsoHandler(this,Constants.QQ_APP_ID, Constants.QQ_APP_KEY);
        qqSsoHandler.addToSocialSDK();

        // 设置短信分享内容
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();

        //设置SSO handler，目前只支持新浪微博、腾讯微博、QQ空间
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        mController.getConfig().setSsoHandler(qZoneSsoHandler);
    }
//
//    //@SuppressLint("DefaultLocale")
//    //@Background
    protected void uploadShareMsg(String url, String describe, String plantName) {
        String token=userDBDao.getToken();
        try{

            if(!NetState.IfNet(this)){
                return;
            }
//            String req=netRequest.newShareMsgRequest(token,
//                    url, describe, plantName.toLowerCase(), "app");
//            String response =netDao.PostToService(req);
//            NetResponse rsp=new NetResponse(this,response);
        }catch(Exception e){
            CommonLog.i(CommonLog.TAG, "uploadShareMsg erro");
        }

    }
//
//    //@Background
    protected void setTargetUrl() {
        String pid=userDBDao.getPid();
        targetUrl = targetUrl + "&pid=" + pid + "&tsmp="+ String.valueOf(System.currentTimeMillis());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        //UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onResume() {
        super.onResume();
        exitProgressDialog();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (R.id.rb_link==checkedId){
            imageDao.setNetworkImage(iv_qrcode,null);
            imageDao.setNetworkImage(iv_qrcode,picUrl);
            //默认图片地址
        }else if (R.id.rb_qrcode==checkedId){
            Bitmap bitmap = createQRImage(targetUrl, iv_qrcode.getWidth(), iv_qrcode.getHeight());
            iv_qrcode.setImageBitmap(bitmap);
            //二维码地址
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    /**
     * 生成二维码图片的代码  函数会把imageView替换成二维码，url是我们的目标连接，QR_WIDTH 二维码的宽度度，QR_HEIGHT二维码的高度
     */
    public Bitmap createQRImage(String url,int QR_WIDTH,int QR_HEIGHT) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            return bitmap;//imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


}
