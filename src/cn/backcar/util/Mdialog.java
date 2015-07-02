package cn.backcar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.backcar.MyApplication;
import cn.backcar.R;
import cn.backcar.entiy.IntentInfo;
import cn.backcar.widget.TopView;
import cn.backcar.widget.TopView.onAddClck;

/**
 * 对话框类
 * 
 * @author yangzhiwei
 * 
 */
public class Mdialog {
	Dialog editviewDlg;
	Dialog payDlg;
	Dialog myLoadingDlg;
	PopupWindow remindDlg;
	DatePickerDialog pickerrdialg;
	ProgressDialog loadingDlg;
	private Context context;
	Dialog alertDialog;
	int selectedIndex = 0;
	MyApplication app;
	int system_height, system_width, system_guide_height, system_state_height;
	JudgeMethods judge;
	boolean isLoadingCancel = true;// 进度条是否能取消

	public boolean isLoadingCancel() {
		return isLoadingCancel;
	}

	public void setLoadingCancel(boolean isLoadingCancel) {
		this.isLoadingCancel = isLoadingCancel;
	}

	public Mdialog(Context context) {
		this.context = context;

		system_height = SystemUtils.getDisplayHeight(context);
		system_guide_height = SystemUtils.getGudieHeight(context);
		system_width = SystemUtils.getDisplayWidth(context);
		system_state_height = SystemUtils.getStatusBarHeight(context);
		app = (MyApplication) context.getApplicationContext();
		judge = new JudgeMethods();
	}

	/**
	 * 日期选择框
	 * 
	 * @param title
	 *            标题
	 * @param str
	 *            带入的内容
	 * @param type
	 *            类型 0全部显示 1只显示月日
	 * @param dlg
	 *            回调接口
	 */
	@SuppressLint({ "NewApi", "SimpleDateFormat" })
	public void showDateDialog(String title, String str, final int type,
			final DlgInterface dlg) {
		Date date = null;
		SimpleDateFormat sdf = null;
		if (str.equals("")) {
			date = new Date(); // 获取当前日期Date对象
		} else {
			if (type == 1) {

				sdf = new SimpleDateFormat("MM-dd");
			} else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}

			try {
				date = new Date();
				date = sdf.parse(str);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		Calendar mycalendar = Calendar.getInstance(Locale.CHINA);

		mycalendar.setTime(date);// //为Calendar对象设置时间为当前日期

		int year = mycalendar.get(Calendar.YEAR); // 获取Calendar对象中的年
		int month = mycalendar.get(Calendar.MONTH);// 获取Calendar对象中的月
		int day = mycalendar.get(Calendar.DAY_OF_MONTH);// 获取这个月的第几天
		DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				if (dlg != null) {
					if (type == 1) {
						dlg.sure((monthOfYear + 1) + "-" + dayOfMonth);
					} else {
						dlg.sure(year + "-" + (monthOfYear + 1) + "-"
								+ dayOfMonth);
					}

					pickerrdialg = null;
				}
			}
		};

		pickerrdialg = new DatePickerDialog(context,
				android.R.style.Theme_Holo_Light_Dialog, onDateSetListener,
				year, month, day);
		if (type == 1) {
			DatePicker dp = pickerrdialg.getDatePicker();
			if (dp != null) {

				((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
						.getChildAt(0).setVisibility(View.GONE);
			}

		}

		pickerrdialg.setTitle(title);
		pickerrdialg.setCanceledOnTouchOutside(true);

		pickerrdialg.show();
	}

	/**
	 * 显示输入对话框
	 * 
	 * @param info
	 * @param inter
	 */
	public void showEditDlg(final IntentInfo info, final DlgInterface inter) {

		if (editviewDlg != null && editviewDlg.isShowing()) {
			editviewDlg.dismiss();
			return;
		}

		View view;
		final EditText et_edit;
		TextView tv_key;
		TopView topview;
		view = (View) LayoutInflater.from(context).inflate(
				R.layout.layout_edit, null);
		tv_key = (TextView) view.findViewById(R.id.tv_key);
		if (!TextUtils.isEmpty(info.unit)) {
			tv_key.setText(info.key + "(" + info.unit + ")");
		} else {
			tv_key.setText(info.key);
		}
		et_edit = (EditText) view.findViewById(R.id.et_edit);

		et_edit.setText(info.value);
		et_edit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				info.length) });
		if (info.type == Constant.TYPE_EDIT_NUMBER) {
			et_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
		}
		topview = (TopView) view.findViewById(R.id.title_bar);
		topview.setTitle(info.key);
		topview.setRightText("完成");
		topview.setAddClick(new onAddClck() {

			@Override
			public void add() {
				if (inter != null)
					inter.sure(et_edit.getText().toString() + info.unit);
				editviewDlg.dismiss();
				editviewDlg = null;
			}

			@Override
			public void back() {
				editviewDlg.dismiss();
				editviewDlg = null;

			}
		});

		editviewDlg = new android.app.Dialog(context, R.style.FullHeightDialog);
		editviewDlg.setContentView(view);

		Window window = editviewDlg.getWindow();
		window.setGravity(Gravity.CENTER);
		window.setWindowAnimations(R.style.DialogAnimtion);
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.main_ll);
		LayoutParams params = layout.getLayoutParams();

		params.width = system_width;
		params.height = system_height;

		layout.setLayoutParams(params);
		editviewDlg.setCanceledOnTouchOutside(true);
		editviewDlg.setCancelable(true);
		editviewDlg.show();
	}

	public boolean isShowDialog() {
		if (alertDialog != null && alertDialog.isShowing()) {
			return true;

		}
		return false;
	}

	public void showSureDialog(final DlgInterface inter, String mes) {

		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.dismiss();
			return;
		}

		View view;
		TextView tv_mes;
		Button bt_cancel, bt_goon;
		view = (View) LayoutInflater.from(context).inflate(
				R.layout.layout_common_dlg, null);
		tv_mes = (TextView) view.findViewById(R.id.tv_mes);
		tv_mes.setText(mes);
		bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		bt_goon = (Button) view.findViewById(R.id.bt_goon);

		bt_goon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				alertDialog.dismiss();
			}
		});
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				inter.sure(null);
				alertDialog.dismiss();

			}
		});
		alertDialog = new android.app.Dialog(context, R.style.FullHeightDialog);
		alertDialog.setContentView(view);

		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setCancelable(false);
		alertDialog.show();

	}

	Toast toast;

	/**
	 * 提示框 可自定义VIEW
	 * 
	 * @param context
	 * 
	 * 
	 * @param message
	 */
	public void showToast(String message) {
		if (toast == null) {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		} else {
			toast.setText(message);
		}
		toast.show();

	}

	/**
	 * 提示框 可自定义VIEW
	 * 
	 * @param context
	 * 
	 * 
	 * @param message
	 */
	public void showToastHandler(String message) {
		Message mes = new Message();
		mes.obj = message;
		mes.what = 1;
		hd.sendMessage(mes);
	}

	Handler hd = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (context != null) {
					if (toast == null) {
						toast = Toast.makeText(context, (String) msg.obj,
								Toast.LENGTH_SHORT);
					} else {
						toast.setText((String) msg.obj);
					}
					toast.show();

				}
			} else if (msg.what == 2) {
				if (context != null) {

					loadingDlg.setMessage((String) msg.obj);
				}
			}
		};
	};

//	/**
//	 * 自定义进度条
//	 *
//	 * @param context
//	 *
//	 *
//	 * @param message
//	 */
//	public void showLoading() {
//
//		View view;
//		final ImageView iv_p;
//		ImageView iv_circle;
//		if (myLoadingDlg != null && myLoadingDlg.isShowing()) {
//			myLoadingDlg.dismiss();
//			return;
//		}
//		view = (FrameLayout) LayoutInflater.from(context).inflate(
//				R.layout.layout_loading, null);
//		iv_p = (ImageView) view.findViewById(R.id.p);
//
//		iv_circle = (ImageView) view.findViewById(R.id.circle);
//		Animation anim = AnimationUtils.loadAnimation(context,
//				R.anim.loading_image);
//		final Animation alpha_anim = AnimationUtils.loadAnimation(context,
//				R.anim.loading_alpha_image);
//		final Animation alpha_anim_down = AnimationUtils.loadAnimation(context,
//				R.anim.loading_alpha_down);
//		iv_circle.startAnimation(anim);
//		iv_p.startAnimation(alpha_anim);
//		alpha_anim.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				iv_p.startAnimation(alpha_anim_down);
//
//			}
//		});
//		alpha_anim_down.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				iv_p.startAnimation(alpha_anim);
//
//			}
//		});
//		myLoadingDlg = new Dialog(context, R.style.FullHeightDialog);
//		myLoadingDlg.setContentView(view);
//		/** 设置选择框居中显示 */
//		// Window window = myLoadingDlg.getWindow();
//		// window.setGravity(Gravity.CENTER);
//
//		myLoadingDlg.setCancelable(isLoadingCancel);
//		myLoadingDlg.setCanceledOnTouchOutside(false);
//		if (!myLoadingDlg.isShowing()) {
//			myLoadingDlg.show();
//		}
//
//	}
//
//	//
//	public void dismissLoading() {
//		if (myLoadingDlg != null && myLoadingDlg.isShowing()) {
//			myLoadingDlg.dismiss();
//		}
//		if (loadingDlg != null && loadingDlg.isShowing()) {
//			loadingDlg.dismiss();
//		}
//	}
//
//	public void setLodingMessage(String mes) {
//		if (loadingDlg != null && loadingDlg.isShowing()) {
//			Message mess = new Message();
//			mess.what = 2;
//			mess.obj = mes;
//			hd.sendMessage(mess);
//		}
//	}
//
//	/**
//	 * 进度条
//	 * 
//	 * @param context
//	 * 
//	 * 
//	 * @param message
//	 */
//	public void showLoading(String message) {
//
//		if (loadingDlg != null && loadingDlg.isShowing()) {
//			loadingDlg.dismiss();
//			return;
//		}
//
//		loadingDlg = new ProgressDialog(context,
//				android.R.style.Theme_Holo_Light_Dialog);
//
//		loadingDlg.setMessage(message);
//
//		loadingDlg.setCancelable(true);
//		loadingDlg.setCanceledOnTouchOutside(false);
//		loadingDlg.show();
//
//	}

	

	

	


	
}
