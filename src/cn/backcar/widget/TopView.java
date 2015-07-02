package cn.backcar.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.backcar.R;


public class TopView extends LinearLayout {

	public LinearLayout iv_back;
	public TextView tv_title;
	public Context con;
	public TextView tv_right;
	private onAddClck addclcik;
   private ImageView iv;
	public TopView(Context context) {
		super(context);
	}

	public TopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.con = context;
		View viewItem = LayoutInflater.from(context).inflate(
				R.layout.title_bar, this, false);

		iv_back = (LinearLayout) viewItem.findViewById(R.id.ll_back);
		tv_title = (TextView) viewItem.findViewById(R.id.tv_title);
		tv_right = (TextView) viewItem.findViewById(R.id.add);
		iv=(ImageView)viewItem.findViewById(R.id.iv);
		iv_back.setOnClickListener(onClickListener);

		tv_right.setOnClickListener(onClickListener);
		addView(viewItem);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ll_back:
				if (addclcik != null)

					addclcik.back();

				break;

			case R.id.add:
				if (addclcik != null)
					addclcik.add();
				break;
			}
		}
	};

	public void setAddClick(onAddClck click) {
		this.addclcik = click;
	}

	public void setTitle(String title) {
		tv_title.setText(title);
	}

	public void setRightText(String text) {
		tv_right.setText(text);
	}
	public void setRightIcon(int icon) {
		tv_right.setBackgroundResource(icon);
		tv_right.setText("");
	}
	public void setLefIcon(int icon) {
		iv.setImageResource(icon);
		
	}
	public void setLeftVisible(int view) {
		iv.setVisibility(view);;
		
	}
	public interface onAddClck {
		public void add();

		public void back();
	}

}
