package com.qichen.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.qichen.AppSQL.AppClass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 35876 于萌萌
 * 创建日期: 16:43 . 2016年11月09日
 * 描述:时间timer 工具类
 * <p>
 * <p>
 * 备注:
 */

public class TimerUtils  {
    /**
     *
     * @param DATE1 当前时间
     * @param DATE2 选择时间
     * @return 1当前时间比选定的大  就对了
     */
    public  static int compare_date(String DATE1, String DATE2) {


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                LogUtils.i("dt1"+"比"+"dt2"+"大");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                LogUtils.i("dt2"+"比"+"dt1"+"大");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    public static int time_(int shiNum , int fenNum){

        // HH:mm 获取当前 时间 用前面的这个格式
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date curDate =  new Date(System.currentTimeMillis());
        String getTime = df.format(curDate);

        LogUtils.i("当前时间呀"+getTime);
        String s = shiNum + "";
        String b = fenNum + "";
        String sb = s+":"+b;
//        Time time = new Time();
//        time.setToNow();
//        int year = time.year;
//        int month = time.month;
//        int day = time.monthDay;
//        int minute = time.minute;
//        int hour = time.hour;
//        int sec = time.second;

//        Calendar c = Calendar.getInstance();
//        int hourOfDay = c.HOUR_OF_DAY;
//        LogUtils.i("获取当前"+" "+ year +
//                "年 " + (month+1) +
//                        "月 " + day +
//                        "日 " + hour +
//                        "时 " + minute +
//                        "分 " + sec +
//                        "秒");
//        String getTime = hourOfDay+":"+minute;
        try {
            //选择时间
            Date s1 = df.parse(sb);
            //当前时间
            Date s2 = df.parse(getTime);
            //选择时间
            long time1 = s1.getTime();
            //当前时间
            long time2 = s2.getTime();
            LogUtils.i("选择时间"+sb+"当前时间"+getTime);
            if (time2-time1>0){
                LogUtils.i("当前时间比选择时间大");
                return 1;
            }
            if (time1-time2>1800000){
                LogUtils.i("选择时间比当前时间大于30分钟");
                return 2;
            }
            if (time2-time1<0){
                LogUtils.i("当前时间比选择时间小");
                return -1;
                //的选择 当前时间的 30分钟以后 预约
            }

            else{
                //return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void  set0 (int num2 ,int num3, TextView textView){
        String numString2 = "";
        String numString3 = "";

        boolean num2Tag = false;
        boolean num3Tag = false;
        for (int i = 0; i < 10; i++) {
            if (num2==i){
                num2Tag = true;
                break;
            }
        }
        for (int i = 0; i < 10; i++) {
            if (num3==i){
                num3Tag = true;
                break;
            }
        }
        if (num2Tag){
            numString2 = "0"+num2;
        }else{
            numString2 = ""+num2;
        }
        if (num3Tag){
            numString3 = "0"+num3;
        }else{
            numString3 = ""+num3;
        }
        set(numString2,numString3,textView);
    }

    private static void set(String num2 ,String num3, TextView textView){
        if (TextUtils.isEmpty(AppClass.DataString)){
            return;
        }
        String string = textView.getText().toString();
        AppClass.DataString  = AppClass.DataString+string+"\t"+num2+":"+num3;
        textView.setText( AppClass.DataString);
        AppClass.DataString ="";
    }
    private static void set(int num1 ,String num2 ,String num3, TextView textView){
        //保存全局
        AppClass.DataString
                = num1+"-"+num2+"-"+num3;

       // textView.setText(num1+"-"+num2+"-"+num3);
    }
    public static void  set0 (int num1,int num2 ,int num3, TextView textView){
        String numString1 = "";
        String numString2 = "";
        String numString3 = "";

        boolean num2Tag = false;
        boolean num3Tag = false;
        for (int i = 0; i < 10; i++) {
            if (num2==i){
                num2Tag = true;
                break;
            }
        }
        for (int i = 0; i < 10; i++) {
            if (num3==i){
                num3Tag = true;
                break;
            }
        }
        if (num2Tag){
            numString2 = "0"+num2;
        }else{
            numString2 = ""+num2;
        }
        if (num3Tag){
            numString3 = "0"+num3;
        }else{
            numString3 = ""+num3;
        }
        set(num1,numString2,numString3,textView);
    }

    /**
     *
     * @param context
     * @param year
     * @param month
     * @param day
     * @param textView
     * @return  返回0  说明 返回了当天时间  返回1 选择了今天之前  返回 -1 是选择了今天之后
     */
    public static int setDatas(Context context, int year, int month, int day,TextView textView) {
        String t2 = year + "-" + (month+1) + "-" + day;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String t1=format.format(new Date());

        LogUtils.i("计算出来当前时间"+t1+"选择的日期是"+t2+"比较结果是"+ TimerUtils.compare_date(t1, t2));

        if (TimerUtils.compare_date(t1, t2)==-1){
            //Toast.makeText(context, "new date:" + year + "-" + (month+1) + "-" + day, Toast.LENGTH_LONG).show();
            //textView.setText(year + "-" + (month+1) + "-" + day);
            set0(year,(month+1),day,textView);
            return -1;
        }else if (TimerUtils.compare_date(t1, t2)==0){
            //Toast.makeText(context, "new date:" + year + "-" + (month+1) + "-" + day, Toast.LENGTH_LONG).show();
            //textView.setText(year + "-" + (month+1) + "-" + day);
            set0(year,(month+1),day,textView);
           return 0;
        }else{
            Toast.makeText(context, "你选择的日期在今天之前请重新选择", Toast.LENGTH_LONG).show();
            return 1;
        }
    }

    /**
     * 获取30分钟以后的 时 分
     * @return
     */
    @NonNull
    public static int[] get30After() {
        //显示就是30分钟以后的时间
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date curDate =  new Date(System.currentTimeMillis()+1800000);
        String getTime = df.format(curDate);
        String[] split = getTime.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        int[] time ={hour,minute};
        return  time;
    }

    public static boolean getToday(String date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate =  new Date(System.currentTimeMillis());
        String getTime = df.format(curDate);

        LogUtils.i("进来的日期"+date+"当前的日期"+getTime);
        if (date.equals(getTime)){
            return true;
        }else{

            return false;
        }
    }
}
