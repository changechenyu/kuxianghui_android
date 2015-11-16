
package com.kuyu.kuxianghui.util;

import android.util.Log;
import com.kuyu.kuxianghui.BuildConfig;

public class CommonLog {

    public static final String TAG = "kuxianghui";

    public static void i(String mTAG, String string) {
        if (BuildConfig.DEBUG_MODE) Log.i(mTAG, string);
    }
    public static void i(String string) {
        if (BuildConfig.DEBUG_MODE) Log.i(TAG, string);
    }

}
