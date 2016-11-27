package com.qichen.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by 35876 于萌萌
 * 创建日期: 11:26 . 2016年10月08日
 * 描述:设置 dimens 里面的尺寸 完美适配
 * <p/>
 * <p/>
 * 备注:
 */
public class UtilsSetSize {

    public static void setTextSize(Context context, TextView textView, int layouDimens){
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(layouDimens);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,dimensionPixelSize);
    }
    public static int setPx(Context context, int layouDimens) {
        Resources r = context.getResources();
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(layouDimens);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                dimensionPixelSize, r.getDisplayMetrics());
    }
}
