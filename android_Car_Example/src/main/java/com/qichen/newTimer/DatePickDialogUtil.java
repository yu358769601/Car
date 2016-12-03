//package com.qichen.newTimer;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.util.Log;
//import android.view.View;
//import android.widget.DatePicker;
//import android.widget.DatePicker.OnDateChangedListener;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.qichen.app.AppContext;
//import com.qichen.ruida.R;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
///**
// * 日期时间选择控件 使用方法：
// * private EditText inputDate;//需要设置的日期时间文本编辑框
// * private String
// * initDateTime="2012年9月3日 14:44",
// * //初始日期时间值 在点击事件中使用：
// * inputDate.setOnClickListener(new OnClickListener() {
// *
// * @author
// * @Override public void onClick(View v) { DateTimePickDialogUtil
// * dateTimePicKDialog=new
// * DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
// * dateTimePicKDialog.dateTimePicKDialog(inputDate);
// * <p/>
// * } });
// */
//public class DatePickDialogUtil implements OnDateChangedListener {
//    private DatePicker datePicker;
//    private AlertDialog ad;
//    private String dateTime;
//    private String initDateTime;
//    private Activity activity;
//    private int flag;
//
//    /**
//     * 日期时间弹出选择框构造函数
//     *
//     * @param activity     ：调用的父activity
//     * @param initDateTime 初始日期时间值，作为弹出窗口的标题和日期时间初始值
//     */
//    public DatePickDialogUtil(Activity activity, String initDateTime, int flag) {
//        this.activity = activity;
//        this.initDateTime = initDateTime;
//        this.flag = flag;
//    }
//
//    public void init(DatePicker datePicker) {
//        Calendar calendar = Calendar.getInstance();
//        if (!(null == initDateTime || "".equals(initDateTime))) {
//            calendar = this.getCalendarByInintData(initDateTime);
//        } else {
//            initDateTime = calendar.get(Calendar.YEAR)
//                    + "/"
//                    + calendar.get(Calendar.MONTH)
//                    + "/"
//                    + calendar.get(Calendar.DAY_OF_MONTH);
//        }
//        Log.i("当前日期", calendar.get(Calendar.YEAR)
//                + "/"
//                + calendar.get(Calendar.MONTH)
//                + "/"
//                + calendar.get(Calendar.DAY_OF_MONTH));
//        //SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日HH-mm-ss");
//        // String time2 = format2.format(Calendar.getInstance().getTime());
//        //System.out.println("完整的日期： " + time2);
//        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH), this);
//    }
//
//    /**
//     * 弹出日期时间选择框方法
//     *
//     * @param inputDate :为需要设置的日期时间文本编辑框
//     */
//    public AlertDialog dateTimePicKDialog(final TextView inputDate) {
//        LinearLayout dateTimeLayout =
//                (LinearLayout) activity.getLayoutInflater().inflate(R.layout.common_datetime, null);
//        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
//        init(datePicker);
//        ad = new AlertDialog.Builder(activity).setTitle(initDateTime)
//                .setView(dateTimeLayout)
//                .setPositiveButton("设置", null)
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        inputDate.setText("");
//                        ad.dismiss();
//                    }
//                })
//                .show();
//        ad.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //获得当前时间
//                String currentDate = DateUtils.getCurrentDate();
//                DateCompare dateCompare = new DateCompare();
//                String month = "";
//                String day = "";
//                if (datePicker.getMonth() < 10) {
//                    month = "0" + (datePicker.getMonth() + 1);
//                } else {
//                    month = "" + (datePicker.getMonth() + 1);
//                }
//                if (datePicker.getDayOfMonth() < 10) {
//                    day = "0" + datePicker.getDayOfMonth();
//                } else {
//                    day = "" + datePicker.getDayOfMonth();
//                }
//                boolean result = dateCompare.compareDate(currentDate,
//                        datePicker.getYear() + "-" + month + "-" + day);
//                if (flag == 1) {
//                    if (result) {
//                        inputDate.setText(dateTime);
//                        ad.dismiss();
//                    } else {
//                        Toast.makeText(AppContext.getApplication(), "该日期已过期", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                if (flag == 2) {
//                    if (!result) {
//                        inputDate.setText(dateTime);
//                        ad.dismiss();
//                    } else {
//                        Toast.makeText(AppContext.getApplication(), "生日选择错误", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//        onDateChanged(null, 0, 0, 0);
//
//        return ad;
//    }
//
//    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        // 获得日历实例
//        Calendar calendar = Calendar.getInstance();
//
//        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//
//        dateTime = sdf.format(calendar.getTime());
//        ad.setTitle(dateTime);
//    }
//
//    /**
//     * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
//     *
//     * @param initDateTime 初始日期时间值 字符串型
//     * @return Calendar
//     */
//    private Calendar getCalendarByInintData(String initDateTime) {
//        Calendar calendar = Calendar.getInstance();
//
//        // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
//        String date = spliteString(initDateTime, "日", "index", "front"); // 日期
//        String time = spliteString(initDateTime, "日", "index", "back"); // 时间
//
//        String yearStr = spliteString(date, "年", "index", "front"); // 年份
//        String monthAndDay = spliteString(date, "年", "index", "back"); // 月日
//
//        String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月
//        String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 日
//
//        String hourStr = spliteString(time, ":", "index", "front"); // 时
//        String minuteStr = spliteString(time, ":", "index", "back"); // 分
//
//        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
//        int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
//        int currentDay = Integer.valueOf(dayStr.trim()).intValue();
//
//        calendar.set(currentYear, currentMonth, currentDay);
//        return calendar;
//    }
//
//    /**
//     * 截取子串
//     *
//     * @param srcStr  源串
//     * @param pattern 匹配模式
//     */
//    public static String spliteString(String srcStr, String pattern, String indexOrLast,
//                                      String frontOrBack) {
//        String result = "";
//        int loc = -1;
//        if (indexOrLast.equalsIgnoreCase("index")) {
//            loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
//        } else {
//            loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
//        }
//        if (frontOrBack.equalsIgnoreCase("front")) {
//            if (loc != -1) result = srcStr.substring(0, loc); // 截取子串
//        } else {
//            if (loc != -1) result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
//        }
//        return result;
//    }
//}
