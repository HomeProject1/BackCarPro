package cn.backcar.activity;

import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import cn.backcar.MyApplication;
import cn.backcar.R;
import cn.backcar.util.Constant;
/**
 *  基类界面
 * @author Administrator
 *
 */
public class BaseActivity extends Activity {
	public MyApplication app;
	public Intent it;
	BitmapUtils bitmaputil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		app = (MyApplication) getApplicationContext();
		bitmaputil=new BitmapUtils(BaseActivity.this, Constant.cameraBitmapPath);
		super.onCreate(savedInstanceState);
	}

	public void finshByAnim() {
		finish();
		overridePendingTransition(R.anim.rightin, R.anim.rightout);
	}

	public void intentToClass(Class classs) {
		it = new Intent(BaseActivity.this, classs);
		startActivity(it);
		overridePendingTransition(R.anim.leftin, R.anim.leftout);
	}
}
