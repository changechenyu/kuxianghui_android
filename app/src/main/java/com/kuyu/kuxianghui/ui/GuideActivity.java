package com.kuyu.kuxianghui.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.ui.adapter.GuideViewPagerAdapter;

/**
 * 新手引导页（第一次进入应用时）
 *
 * @author fish
 */
public class GuideActivity extends BaseActivity {

    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        viewPager = (ViewPager) findViewById(R.id.vp_pager);
        int res[] = {R.layout.fragment_guide_1, R.layout.fragment_guide_2, R.layout.fragment_guide_3, R.layout
                .fragment_guide_4};
        GuideViewPagerAdapter adapter = new GuideViewPagerAdapter(this, res);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        //do nothing
        finish();
    }

}
