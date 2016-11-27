package com.qichen.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 35876 于萌萌
 * 创建日期: 16:41 . 2016年09月27日
 * 描述:单例吐司
 * <p/>
 * <p/>
 * 备注:
 */
public class UtilsToast {

    private static Toast mToast;

    /**
     * 展示吐司
     *
     * @param context
     *            环境
     * @param text
     *            内容
     */
    public static void showToast(Context context, String text) {
        if (context != null && text != null) {
            if (mToast == null) {
                    if (mToast == null) {
                        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                    }
            }
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
            mToast.show();
        }
    }
}
