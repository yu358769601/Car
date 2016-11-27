package com.qichen.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 35876 于萌萌
 * 创建日期: 10:59 . 2016年11月14日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public class SQUtils {
   static SharedPreferences settings;
    static  SharedPreferences.Editor editor;

    /**获取一个键值对
     *
     * @param context
     * @param keyString
     * @param defu
     * @return
     */
    public static String getStrings(Context context,String keyString,String defu){
        getSettings(context);
        return getString(keyString, defu);
    }


    /**
     *  设置一个键值对
     * @param context
     * @param keyString
     * @param Values
     */
    public static void  setString(Context context,String keyString,String Values){
        getSettings(context);
        editor.putString(keyString,Values);
        editor.commit();
    }
    /**
     * 检查是否登录状态
     * @return
     */
    public static boolean isLogin(Context context) {
        getSettings(context);
        //获取登录状态
        String login =getString("login", "false");
        if ("true".equals(login)){

            return true;
        }
        return false;
    }
    /**
     * 检查是否是打车状态
     * @return
     */
    public static boolean isCallCar(Context context) {
        getSettings(context);
        //获取登录状态
        String callCarStatus =getString("callCarStatus", "-1");
        //只要不是 -1  就是 有订单
        if ("-1".equals(callCarStatus)){

            return false;
        }
        return true;
    }
    /**
     * 设置当前状态
     * @return
     */
    public static void setCallCar(Context context,String status) {
        getSettings(context);
        //获取登录状态
        setString(context,"callCarStatus", status);
    }

    /**
     * 设置设置订单号
     * @return
     */
    public static void setCallCarOrder_id(Context context,String Order_id) {
        getSettings(context);
        setString(context,"Order_id", Order_id);
    }
    /**
     * 获取订单号
     * @return
     */
    public static void getCallCarOrder_id(Context context) {
        getSettings(context);
        setString(context,"Order_id", "");
    }



    /**
     * 设置登录状态标记
     * @param context
     */
    public static void setLogin(Context context){
        getSettings(context);
        setString(context,"login", "true");
    }
    public static String getId(Context context){
        getSettings(context);

        return getStrings(context, "passenger_id","");
    }
    public static void setId(Context context,String s){
        getSettings(context);
         setString(context, "passenger_id",s);
    }



    private static SharedPreferences  getSettings(Context context){
        //获取SP 小型sql
        settings = context.getSharedPreferences("settings", 0);
        editor = settings.edit();
        return settings;
    }

    private static String getString(String keyString,String defu){
        return   settings.getString(keyString, defu);
    }


}
