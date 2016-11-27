package com.rongzhiheng.util;

import com.qichen.ruida.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆形的Imageview
 * 
 * @since 2012-11-02
 * 
 * @author bingyang.djj
 * 
 */
public class CircleImageView extends ImageView {

	public static int style;
	private Paint paint = new Paint();

	public CircleImageView(Context context) {
		super(context);
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Drawable drawable = getDrawable();
		if (null != drawable) {
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			// Log.i("bitmap.getWidth()", bitmap.getWidth()+"");
			// Log.i("bitmap.getHeight()", bitmap.getHeight()+"");
			// Bitmap b = toRoundCorner(bitmap, 14);
			Bitmap b = toRoundCorner(scaleWidthAndHeight(bitmap), 14);
			final Rect rect = new Rect(0, 0, b.getWidth(), b.getHeight());
			paint.reset();
			canvas.drawBitmap(b, rect, rect, paint);

		} else {
			super.onDraw(canvas);
		}
	}

	private Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		int x = bitmap.getWidth();
		canvas.drawCircle(x / 2, x / 2, x / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	private Bitmap scaleWidthAndHeight(final Bitmap bitmapOrg) {

		// 加载需要操作的图片，这里是一张图片
		// Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),R.drawable.img_sy_banner1);

		// 获取这个图片的宽和高
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();

		CircleImageView photoimgView = null;
		switch (style) {
		case 1:
			photoimgView = (CircleImageView) findViewById(R.id.personcenter_photo);
			break;
		case 2:
			photoimgView = (CircleImageView) findViewById(R.id.passenger_photo);
			break;

		}

		// 定义预转换成的图片的宽度和高度
		int newWidth = photoimgView.getWidth();
		int newHeight = photoimgView.getHeight();

		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 旋转图片 动作
		// matrix.postRotate(45);

		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);

		return resizedBitmap;
	}
}