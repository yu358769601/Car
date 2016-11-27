package com.qichen.Utils;

import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by 35876 于萌萌
 * 创建日期: 8:57 . 2016年11月10日
 * 描述:自己的  字符串工具类
 * <p>
 * <p>
 * 备注:
 */

public class UtilsMyText {



    /**
     * 判断 textView 是否为空
     * @param textView
     * @return
     */
    public static Boolean isEmptys(TextView textView){
       return TextUtils.isEmpty(textView.getText().toString());
    }

    /**
     * 获取 textView 里面的东西
     * @param textView
     * @return
     */
    public static String getTextView(TextView textView){
        if (TextUtils.isEmpty(textView.getText().toString())){
            return "";
        }else{
            return textView.getText().toString().trim();

        }

    }

    /**
     * 隐藏四位电话号码
     * @param phone
     * @return
     */
    public static String hide4Num(String phone){
      // String phone2 = "15988888888";
        if (!TextUtils.isEmpty(phone)){
            return  phone.substring(0,3) + "****" + phone.substring(7, phone.length());
        }else{
            return "";
        }

    }

    /**
     * 获取字符串的第一个字符串
     * @param string
     * @return
     */
    public static String getfristString(String string){
        if (!TextUtils.isEmpty(string)){
            return string.substring(0,1);
        }else{
            return "";
        }

    }

    /**
     * 获取保留两位的 字符串 浮点数
     * @param doubleString 想要保留的浮点数 字符串
     * @return
     */
    public static String get2DoubleString(String doubleString){
        if (!TextUtils.isEmpty(doubleString)){
            double v = Double.parseDouble(doubleString);
            return String.format("%.2f",v);
        }else {
            return "";
        }
    }


}
