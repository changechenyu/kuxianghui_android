package com.kuyu.kuxianghui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.ui.fragment.MarketFragment;
import com.kuyu.kuxianghui.util.ActionbarUtil;
import com.kuyu.kuxianghui.util.ToastUtils;

public class HomeActivity extends BaseActivity {

    public static  final int HOME_FRAGMENT = 0;
    public static  final int MARKET_FRAGMENT = 1;
    public static  final int CART_FRAGMENT = 2;
    public static  final int MINE_FRAGMENT = 3;
    private final int FRAGMENT_SIZE = 4;

    public static final String ACTION_SIGNAL = "ACTION_SIGNAL";
    public static final String TAB_TO_GO = "TAB_TO_GO";
    private int tabToGo = 0;

    private long pressSep;

    private RadioGroup bottomRg;

    public Fragment[] mFragments;
    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyApplication.addToApplicationActivityStack(this);
        setContentView(R.layout.activity_home);
        initFragment();
        initRadioGroup();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println("-=-=-=-=>>>onnewintent....");
        //完成跳转到指定的tab页
        String actionSingal = intent.getStringExtra(ACTION_SIGNAL);
        if (ACTION_SIGNAL.equals(actionSingal)){
            tabToGo = intent.getIntExtra(TAB_TO_GO,HOME_FRAGMENT);
            showTab(tabToGo);
        }
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        mFragments = new Fragment[FRAGMENT_SIZE];
        mFragments[HOME_FRAGMENT] = fragmentManager.findFragmentById(R.id.fragement_home);
        mFragments[MARKET_FRAGMENT] = fragmentManager.findFragmentById(R.id.fragement_market);
        mFragments[CART_FRAGMENT] = fragmentManager.findFragmentById(R.id.fragement_cart);
        mFragments[MINE_FRAGMENT] = fragmentManager.findFragmentById(R.id.fragement_mine);
        showFragment(HOME_FRAGMENT);
    }

    public void showFragment(int whichFragment) {
        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(mFragments[HOME_FRAGMENT])
                .hide(mFragments[MARKET_FRAGMENT])
                .hide(mFragments[CART_FRAGMENT])
                .hide(mFragments[MINE_FRAGMENT]);
        fragmentTransaction.show(mFragments[whichFragment]).commit();
        String title = getString(R.string.app_name);
        switch (whichFragment) {
            case HOME_FRAGMENT:
                title = getString(R.string.app_name);
                ActionbarUtil.makeCustomActionBar(this, getActionBar(), title, ActionbarUtil.ACTIONBAR_NORMAL);
                break;
            case MARKET_FRAGMENT:
                title = getString(R.string.market_plan);
                ActionbarUtil.makeCustomActionBar(this, getActionBar(), title, ActionbarUtil.ACTIONBAR_MARKET);
                if (getActionBar() != null)
                    ((MarketFragment) mFragments[whichFragment]).initActionBar(getActionBar().getCustomView());
                break;
            case CART_FRAGMENT:
                title = getString(R.string.actionbar_cart);
                ActionbarUtil.makeCustomActionBar(this, getActionBar(), title, ActionbarUtil.ACTIONBAR_NORMAL);
                break;
            case MINE_FRAGMENT:
                title = getString(R.string.actionbar_mine);
                ActionbarUtil.makeCustomActionBar(this, getActionBar(), title, ActionbarUtil.ACTIONBAR_NORMAL);
                break;
        }
    }

    private void initRadioGroup() {
        bottomRg = (RadioGroup) findViewById(R.id.home_radio_button_group);
        bottomRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home_tab_index:
                        showFragment(HOME_FRAGMENT);
                        break;
                    case R.id.home_tab_commodity:
                        showFragment(MARKET_FRAGMENT);
                        break;
                    case R.id.home_tab_cart:
         //               DialogUtil.showProgressDialog(getApplicationContext());
                        showFragment(CART_FRAGMENT);
                        break;
                    case R.id.home_tab_mine:
                        showFragment(MINE_FRAGMENT);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void showTab(int tabIndex){
        switch (tabIndex){
            case HOME_FRAGMENT:
                showFragment(HOME_FRAGMENT);
                bottomRg.check(R.id.home_tab_index);
                break;
            case MARKET_FRAGMENT:
                showFragment(MARKET_FRAGMENT);
                bottomRg.check(R.id.home_tab_commodity);
                break;
            case CART_FRAGMENT:
                showFragment(CART_FRAGMENT);
                bottomRg.check(R.id.home_tab_cart);
                break;
            case MINE_FRAGMENT:
                showFragment(MINE_FRAGMENT);
                bottomRg.check(R.id.home_tab_mine);
                break;
            default:
                showFragment(HOME_FRAGMENT);
                bottomRg.check(R.id.home_tab_index);
                break;
        }
    }

    // 按两下返回键退出应用
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - pressSep > 2000) {
                pressSep = System.currentTimeMillis();
                ToastUtils.makeText(this, this.getResources().getString(R.string.press_again_to_exit), 1000);
            } else {
                finish();
                mMyApplication.exitApplication(this);
            }
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}