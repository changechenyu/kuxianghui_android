package com.kuyu.kuxianghui.util;

import android.content.Context;
import android.content.Intent;

import com.kuyu.kuxianghui.ui.GuideActivity;
import com.kuyu.kuxianghui.ui.HomeActivity;
import com.kuyu.kuxianghui.ui.LocateActivity;
import com.kuyu.kuxianghui.ui.LoginActivity;

/**
 * Created by fish on 15/9/23.
 */
public class IntentFactory {
    public static Intent goHomeActivity(Context ctx) {
        Intent intent = new Intent(ctx, HomeActivity.class);
        return intent;
    }

    public static Intent goHomeFragment(Context ctx) {
        Intent intent = new Intent(ctx, HomeActivity.class);
        intent.putExtra(HomeActivity.ACTION_SIGNAL,HomeActivity.ACTION_SIGNAL);
        intent.putExtra(HomeActivity.TAB_TO_GO, HomeActivity.HOME_FRAGMENT);
        return intent;
    }

    public static Intent goMarketFragment(Context ctx) {
        Intent intent = new Intent(ctx, HomeActivity.class);
        intent.putExtra(HomeActivity.ACTION_SIGNAL,HomeActivity.ACTION_SIGNAL);
        intent.putExtra(HomeActivity.TAB_TO_GO,HomeActivity.MARKET_FRAGMENT);
        return intent;
    }

    public static Intent goCartFragment(Context ctx) {
        Intent intent = new Intent(ctx, HomeActivity.class);
        intent.putExtra(HomeActivity.ACTION_SIGNAL,HomeActivity.ACTION_SIGNAL);
        intent.putExtra(HomeActivity.TAB_TO_GO,HomeActivity.CART_FRAGMENT);
        return intent;
    }

    public static Intent goMineFragment(Context ctx) {
        Intent intent = new Intent(ctx, HomeActivity.class);
        intent.putExtra(HomeActivity.ACTION_SIGNAL,HomeActivity.ACTION_SIGNAL);
        intent.putExtra(HomeActivity.TAB_TO_GO,HomeActivity.MINE_FRAGMENT);
        return intent;
    }

    public static Intent goGuideActivity(Context ctx) {
        Intent intent = new Intent(ctx, GuideActivity.class);
        return intent;
    }

    public static Intent goLoginActivity(Context ctx) {
        Intent intent = new Intent(ctx, LoginActivity.class);
        return intent;
    }

    public static Intent goLocateActivity(Context ctx) {
        Intent intent = new Intent(ctx, LocateActivity.class);
        return intent;
    }
}
