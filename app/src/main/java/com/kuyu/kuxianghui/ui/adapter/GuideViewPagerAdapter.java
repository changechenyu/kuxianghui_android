package com.kuyu.kuxianghui.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.kuyu.kuxianghui.R;
import com.kuyu.kuxianghui.ui.app.AppSharedPreferences;
import com.kuyu.kuxianghui.util.IntentFactory;

public class GuideViewPagerAdapter extends PagerAdapter {

    private int res[];
    private Activity mContext;
    private LayoutInflater inflater;
    protected AppSharedPreferences mAppSharedPreferences ;


    public GuideViewPagerAdapter(Activity context, int res[]) {
        this.res = res;
        this.mContext = context;
        mAppSharedPreferences = new AppSharedPreferences(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return res.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View contentView = inflater.inflate(res[position], null);
        if (res[position] == R.layout.fragment_guide_4) {
            Button vEnter = (Button) contentView.findViewById(R.id.btn_enter_app);
            vEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(IntentFactory.goHomeActivity(mContext));
                    mContext.finish();
                    mAppSharedPreferences.save_first(false);
                }
            });
        }
        container.addView(contentView);
        return contentView;
    }

}