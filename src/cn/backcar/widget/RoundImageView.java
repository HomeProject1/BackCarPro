package cn.backcar.widget;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import cn.backcar.util.SystemUtils;



/**
 * Բ��ImageView����Բ�α߿�
 * 
 * @author yzw
 */
public class RoundImageView extends ImageView {
	private int mBorderThickness = 0;
	private Context mContext;
	private boolean needInside = false;
	private WeakReference<Bitmap> mWeakBitmap;

	private int mBorderInsideColor = 0;
	// �ؼ�Ĭ�ϳ�����
	private int defaultWidth = 0;
	private int defaultHeight = 0;
	Bitmap roundBitmap, b, bitmap;

	public RoundImageView(Context context) {
		super(context);
		mContext = context;
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mBorderThickness = SystemUtils.dip2px(mContext, 3);
		mBorderInsideColor = 0xfff0f0f0;
		
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;

	}

	public void setNeedInside(boolean needInside) {
		this.needInside = needInside;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// �ڻ�����ȡ��bitmap
		Bitmap bitmapweak = mWeakBitmap == null ? null : mWeakBitmap.get();
		if (null == bitmapweak || bitmapweak.isRecycled()) {

			Drawable drawable = getDrawable();
			if (drawable == null) {
				return;
			}

			if (getWidth() == 0 || getHeight() == 0) {
				return;
			}
			this.measure(0, 0);
			if (drawable.getClass() == NinePatchDrawable.class)
				return;
			b = ((BitmapDrawable) drawable).getBitmap();
			bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
			if (defaultWidth == 0) {
				defaultWidth = getWidth();

			}
			if (defaultHeight == 0) {
				defaultHeight = getHeight();
			}
			// ��֤���¶�ȡͼƬ�󲻻���ΪͼƬ��С���ı�ؼ����ߵĴ�С����Կ���Ϊwrap_content���ֵ�imageview�����ᵼ��margin��Ч��
			// if (defaultWidth != 0 && defaultHeight != 0) {
			// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			// defaultWidth, defaultHeight);
			// setLayoutParams(params);
			// }
			int radius = 0;
			if (needInside) {// ���廭һ���߿�
				radius = (defaultWidth < defaultHeight ? defaultWidth
						: defaultHeight) / 2 - mBorderThickness;
				drawCircleBorder(canvas, radius + mBorderThickness / 2,
						mBorderInsideColor);
			} else {// û�б߿�
				radius = (defaultWidth < defaultHeight ? defaultWidth
						: defaultHeight) / 2;
			}
			roundBitmap = getCroppedRoundBitmap(bitmap, radius);
			canvas.drawBitmap(roundBitmap, defaultWidth / 2 - radius,
					defaultHeight / 2 - radius, null);

		} else {
			// mPaint.setXfermode(null);
			// canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
			// return;
		}
	}

	@Override
	protected void onFinishInflate() {
		if (b != null && !b.isRecycled()) {
			b.recycle();
		}
		if (roundBitmap != null && !roundBitmap.isRecycled()) {
			roundBitmap.recycle();
		}
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
		super.onFinishInflate();
	}

	public boolean isNeedInside() {
		return needInside;
	}

	public void setmBorderInsideColor(int mBorderInsideColor) {
		this.mBorderInsideColor = mBorderInsideColor;
	}

	/**
	 * ��ȡ�ü����Բ��ͼƬ
	 * 
	 * @param radius
	 *            �뾶
	 */
	public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
		Bitmap scaledSrcBmp;
		int diameter = radius * 2;

		// Ϊ�˷�ֹ��߲���ȣ����Բ��ͼƬ���Σ���˽�ȡ�������д����м�λ������������ͼƬ
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// �ߴ��ڿ�
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// ��ȡ������ͼƬ
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else if (bmpHeight < bmpWidth) {// ����ڸ�
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else {
			squareBitmap = bmp;
		}

		if (squareBitmap.getWidth() != diameter
				|| squareBitmap.getHeight() != diameter) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,
					diameter, true);

		} else {
			scaledSrcBmp = squareBitmap;
		}
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
				scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		// bitmap����(recycle�����ڲ����ļ�XML������Ч��)
		// bmp.recycle();
		// squareBitmap.recycle();
		// scaledSrcBmp.recycle();
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;
	}

	/**
	 * ��Ե��Բ
	 */
	private void drawCircleBorder(Canvas canvas, int radius, int color) {
		Paint paint = new Paint();
		/* ȥ��� */
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(color);
		/* ����paint�ġ�style��ΪSTROKE������ */
		paint.setStyle(Paint.Style.STROKE);
		/* ����paint������� */
		paint.setStrokeWidth(mBorderThickness);
		canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);
	}
 @Override
public void invalidate() {
//	 if(b!=null&&!b.isRecycled()){
//		 b.recycle();
//	 }
//	 if(roundBitmap!=null&&!roundBitmap.isRecycled()){
//		 roundBitmap.recycle();
//	 }
//	 if(bitmap!=null&&!bitmap.isRecycled()){
//		 bitmap.recycle();
//	 }
	super.invalidate();
}
}
