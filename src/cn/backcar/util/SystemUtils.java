package cn.backcar.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class SystemUtils {

	/**
	 * 
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();

		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;

	}

	/**
	 * 
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();

		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int statusBarHeight = 20;
		try {
			Class<?> _class = Class.forName("com.android.internal.R$dimen");
			Object object = _class.newInstance();
			Field field = _class.getField("status_bar_height");
			int restult = Integer.parseInt(field.get(object).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(
					restult);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return statusBarHeight;
	}

	public static int getGudieHeight(Context con) {
		Resources resources = con.getResources();

		int rid = resources.getIdentifier("config_showNavigationBar", "bool",
				"android");
		if (rid > 0) {
			if (resources.getBoolean(rid)) {
				int resourceId = resources.getIdentifier(
						"navigation_bar_height", "dimen", "android");
				if (resourceId > 0) {
					return resources.getDimensionPixelSize(resourceId);
				} else {
					return 0;
				}

			}

		}
		return 0;

	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {

		}
		return versionName;
	}

	/**
	 * 返回当前程序版本号
	 */
	public static int getAppVersionCode(Context context) {
		int code = 0;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			code = pi.versionCode;

		} catch (Exception e) {
			e.printStackTrace();
			
			return code;
		}
		return code;
	}

	/**
	 * 
	 */
	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String d = format.format(time);

		return d;
	}
	
}
