package com.kuyu.kuxianghui.util;


import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.kuyu.kuxianghui.R;

public class ActionbarUtil {
	
	//custom actionbar type id define
	public static final int ACTIONBAR_RETURN = 0;
	public static final int ACTIONBAR_MARKET = 1;
	public static final int ACTIONBAR_NORMAL = 2;

	public static void makeCustomActionBar(Context context,
			ActionBar actionBar, String title, int actionbartype) {
		actionBar.setDisplayHomeAsUpEnabled(true);
		View view = null;
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		switch (actionbartype) {
		case ACTIONBAR_RETURN:
			view = mInflater.inflate(R.layout.actionbar_return, null);
			break;
		case ACTIONBAR_MARKET:
			view = mInflater.inflate(R.layout.actionbar_market, null);
			break;
		case ACTIONBAR_NORMAL:
			view = mInflater.inflate(R.layout.actionbar_normal, null);
			break;
		default:
			view = mInflater.inflate(R.layout.actionbar_normal, null);
			break;
		}

		TextView textView0 = (TextView) view.findViewById(R.id.action_bar_text);
		if(textView0!=null){
			textView0.setText(title);
//			textView0.setSelected(true);
		}
		actionBar.setCustomView(view, new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT));
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}
}
