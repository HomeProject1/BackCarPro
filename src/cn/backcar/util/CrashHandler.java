package cn.backcar.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import cn.backcar.MyApplication;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";

	private static CrashHandler myCrashHandler;

	private Context mContext;

	private Thread.UncaughtExceptionHandler mDefaultHandler;

	LogUtil log = LogUtil.getInstance();
    MyApplication app;
	private CrashHandler() {

	}

	public static CrashHandler getInstance() {

		if(myCrashHandler == null){
			myCrashHandler = new CrashHandler();
		}
		return myCrashHandler;
	}

	public void init(Context ctx) {

		mContext = ctx;

		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

		Thread.setDefaultUncaughtExceptionHandler(this);
		app=(MyApplication) mContext.getApplicationContext();
	}

	@Override
	public void uncaughtException(Thread thread, final Throwable ex) {
		// getErrorInfo(ex);
		if (!handleException(ex) && mDefaultHandler != null) {

			mDefaultHandler.uncaughtException(thread, ex);

		} else {

			try {
				Thread.sleep(3000);// ��������ˣ��ó����������3�����˳�
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// �˳�����
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
			

		}

	}

	/**
	 * 
	 * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����. �����߿��Ը����Լ���������Զ����쳣�����߼�
	 * 
	 * 
	 * 
	 * @param ex
	 * 
	 * @return true:��������˸��쳣��Ϣ;���򷵻�false
	 */
	private boolean handleException(Throwable ex) {

		if (ex == null) {

			return false;

		}
		saveCrashInfoToFile(ex, mContext);
		
		   new Thread() {  
	            public void run() {  
	                Looper.prepare();  
	                Toast.makeText(mContext, "�����˸�С���  ���Ե�", 0).show();  
	                Looper.loop();  
	            }  
	        }.start();  
		return true;

	}

	public void saveCrashInfoToFile(Throwable ex, Context ctx) {

		try{
			String result = null;			
			Writer info = new StringWriter();
			PrintWriter printWrite = new PrintWriter(info);
			ex.printStackTrace(printWrite);
			
			Throwable cause = ex.getCause();
			while(cause != null){
				cause.printStackTrace(printWrite);
				cause = cause.getCause();
			}	
			printWrite.close();
			PackageManager pm = ctx.getPackageManager();
			
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			
			if(pi != null){
				result = "�����쳣: "+pi.applicationInfo.loadLabel(pm).toString()+",  �汾��:��" + pi.versionName+", " + info.toString();
				log.writeLog(result);
			}
			
			
		}
		catch(Exception e){
			
		}
		
	}
}