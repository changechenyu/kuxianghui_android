package com.kuyu.kuxianghui.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.kuyu.kuxianghui.R;

public class ToastUtils {

	public static final int ONE_SECOND = 1 * 1000;
	public static final int TWO_SECOND = 2 * 1000;
	public static final int THREE_SECOND = 3 * 1000;

	private static Toast toast;

	public static void makeText(Context context, final String msg){
		makeText(context,msg,ONE_SECOND);
	}

	public static void makeText(Context context, final String msg,
			final int duration) {
		Log.d("ToastUtils", String.format("%s", msg));
		Handler handler = new Handler(Looper.getMainLooper());
		final Context ctx = context;
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (null == ctx) {
					return;
				}
				if (toast == null)
					toast = new Toast(ctx);
				View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_toast, null);
				TextView message = (TextView) view.findViewById(R.id.tv_msg);
				message.setText(msg);

				toast.setGravity(Gravity.CENTER, Gravity.FILL_HORIZONTAL, Gravity.FILL_VERTICAL);
				toast.setDuration(duration);
				toast.setView(view);
				toast.show();
			}
		});
	}

	public static void showText(final Context context, final int resId,
			final int duration) {
		Handler handler = new Handler(Looper.getMainLooper());
		final Context ctx = context;
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (null == context) {
					return;
				}
				View view = LayoutInflater.from(ctx).inflate(
						R.layout.dialog_toast, null);
				TextView message = (TextView) view.findViewById(R.id.tv_msg);
				message.setText(resId);

				if (toast == null)
					toast = new Toast(ctx);
				toast.setGravity(Gravity.CENTER, Gravity.FILL_HORIZONTAL, Gravity.FILL_VERTICAL);
				toast.setDuration(duration);
				toast.setView(view);
				toast.show();
			}
		});
		Log.d("ToastUtils", context.getResources().getString(resId));
	}
}
