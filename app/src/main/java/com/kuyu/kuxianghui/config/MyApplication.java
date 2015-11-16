package com.kuyu.kuxianghui.config;

import android.app.Activity;
import android.content.Context;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.network.AppController;
import com.kuyu.kuxianghui.util.CommonLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fish on 15/9/22.
 */
public class MyApplication {
    public static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication instance = new MyApplication();

    List<Activity> activities = new ArrayList<>(); // 整个应用栈中存在的Activity

    public static MyApplication getInstance() {
        return instance;
    }

    private  MyApplication() {
    }

    // 退出应用
    public void exitApplication(Context mContext) {
        CommonLog.i(mContext.getString(R.string.exit_appliaction));
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        AppController.getInstance(mContext).getRequestQueue().cancelAll(AppController.COMMON_REQUEST_TAG);
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void addToApplicationActivityStack(Activity activity) {
        this.activities.add(activity);
    }
}
