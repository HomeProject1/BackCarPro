package cn.backcar.util;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * 网络设置
 * @author Administrator
 */
public class NetworkUtil {
	

	/**
	 * 判断network是否开启 (包括移动网络和wifi)
	 */
	public static  boolean isNetworkEnabled(Context mContext) {
		return (isNetEnable(mContext) || isWIFIEnable(mContext));
	}

	/**
	 * 判断network是否连接成功 包括wifi和移动网络
	 */
	public static boolean isNetworkConnected(Context mContext) {
		return (isWifiContected(mContext) || isNetContected(mContext));
	}

	/**
	 * 判断移动网络是否开启
	 */
	public static boolean isNetEnable(Context context) {
		boolean enable = false;
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager != null) {
			if (telephonyManager.getNetworkType() != TelephonyManager.NETWORK_TYPE_UNKNOWN) {
				enable = true;
			}
		}
		return enable;
	}

	/**
	 * 判断wifi是否开启
	 */
	public static boolean isWIFIEnable(Context context) {
		boolean enable = false;
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
			enable = true;
		}
		return enable;
	}

	/**
	 *	判断移动网络是否连接成功
	 */
	public static boolean isNetContected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetworkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断wifi是否连接成功
	 */
	public static boolean isWifiContected(Context context) {
		ConnectivityManager conncetivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = conncetivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	

	
}
