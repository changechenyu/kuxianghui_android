package com.kuyu.kuxianghui.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.igexin.sdk.PushManager;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.config.Constants;
import com.kuyu.kuxianghui.network.AppController;
import com.kuyu.kuxianghui.network.VolleyRequest;
import com.kuyu.kuxianghui.ui.app.AppSharedPreferences;
import com.kuyu.kuxianghui.ui.dialog.Effectstype;
import com.kuyu.kuxianghui.ui.dialog.NiftyDialogBuilder;
import com.kuyu.kuxianghui.util.BaiduLocationUtil;
import com.kuyu.kuxianghui.util.IntentFactory;
import com.kuyu.kuxianghui.util.NetState;
import com.kuyu.kuxianghui.util.ToastUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.utils.Log;
import com.umeng.update.UmengUpdateAgent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fish on 15/9/23.
 */
public class SplashActivity extends BaseActivity {


    protected RelativeLayout mView;
    protected AppSharedPreferences mAppSharedPreferences;
    protected BaiduLocationUtil baiduLocationUtil;
    public static String result = "";
    public String token="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengUpdateAgent.update(this);
        mAppSharedPreferences = new AppSharedPreferences(this);
        PushManager.getInstance().initialize(getApplicationContext());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        mMyApplication.addToApplicationActivityStack(this);

        findViews();
        //通知设置更新
        //MobclickAgent.updateOnlineConfig(this);
        UmengUpdateAgent.update(this);
        //延迟加载初始化
        initImageLoader(this);
        int state = NetState.checkState(this);
        if (state == NetState.CONNECTION_NO) {
            showNoNetWorkDialog();
        } else {
            //  用来定位
            baiduLocationUtil = new BaiduLocationUtil();
            result = baiduLocationUtil.getCity(this.getApplication());
            token = new AppSharedPreferences(getApplicationContext()).get_token();
            if (!TextUtils.isEmpty(token)) {
                checkToken(token);
            }
            Log.i("result", result);
        }
    }

    private void findViews() {
        mView = (RelativeLayout) findViewById(R.id.rl_splash_whole);
    }

    @Override
    public void onResume() {
        super.onResume();
        enterHomeView();
    }

    /**
     * 无法连接网络的Dialog
     */
    NiftyDialogBuilder dialogBuilder;

    private void showNoNetWorkDialog() {
        if (!isChangingConfigurations() && dialogBuilder != null && dialogBuilder.isShowing())
            return;
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withPPMaoDef()
                .withMessage(R.string.no_netword_connect)
                .isCancelableOnTouchOutside(false)
                .withEffect(Effectstype.Slidetop)
                .withButton1Text(R.string.go_to_set_network)
                .withButton2Text(R.string.cancel)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        finish();
                    }
                })
                .show();
    }

    protected void enterHomeView() {
        //渐变展示启动屏,试用动画进行计时，动画结束后跳转
        AlphaAnimation aa = new AlphaAnimation(0.8f, 1.0f);
        aa.setDuration(2000);
        mView.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                if (mAppSharedPreferences.get_first()) {
                    startActivity(IntentFactory.goGuideActivity(SplashActivity.this));
                    mAppSharedPreferences.save_first(true);
                } else {
                    startActivity(IntentFactory.goHomeActivity(SplashActivity.this));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });
    }

    /**
     * 延迟加载配置
     *
     * @param context
     */
    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() // Not  necessary in common
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().handleSlowNetwork(true);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    /**
     * 检验Token
     */
    public void checkToken(final String token) {
        JSONObject request = VolleyRequest.getInstance(this).checkTokenRequest(token);
        Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
            @Override    //返回成功
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String resultCode = (String) jsonObject.get("resultCode");
                    String resultMsg = (String) jsonObject.get("resultMsg");
                    if (Constants.RESUlTCODE_SUCCESS.equals(resultCode)) {
                        getUerInfo(token);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AppController.customRequest(getApplication(), AppController.COMMON_REQUEST_TAG, request, onSuccess,
                AppController.dErrorListener);
    }
    /**
     * 通过Token拿用户信息
     */
    public void  getUerInfo(String token){
        JSONObject request = VolleyRequest.getInstance(this).getUserInfoRequest(token);
        Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
            @Override    //返回成功
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String resultCode = (String) jsonObject.get("resultCode");
                    String resultMsg = (String) jsonObject.get("resultMsg");
                    if (Constants.RESUlTCODE_SUCCESS.equals(resultCode)) {
                        Log.i("通过token拿到用户信息","suceeess");
                        //获取用户信息
                    }else{
                        //跳转到登陆记得面
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AppController.customRequest(getApplication(), AppController.COMMON_REQUEST_TAG, request, onSuccess,
                AppController.dErrorListener);
    }
}