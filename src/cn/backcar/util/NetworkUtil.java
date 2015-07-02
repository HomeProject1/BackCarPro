package cn.backcar.util;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * ��������
 * @author Administrator
 */
public class NetworkUtil {
	

	/**
	 * �ж�network�Ƿ��� (�����ƶ������wifi)
	 */
	public static  boolean isNetworkEnabled(Context mContext) {
		return (isNetEnable(mContext) || isWIFIEnable(mContext));
	}

	/**
	 * �ж�network�Ƿ����ӳɹ� ����wifi���ƶ�����
	 */
	public static boolean isNetworkConnected(Context mContext) {
		return (isWifiContected(mContext) || isNetContected(mContext));
	}

	/**
	 * �ж��ƶ������Ƿ���
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
	 * �ж�wifi�Ƿ���
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
	 *	�ж��ƶ������Ƿ����ӳɹ�
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
	 * �ж�wifi�Ƿ����ӳɹ�
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
