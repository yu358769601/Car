//package com.qichen.newTimer;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class DateCompare {
//    /**
//     * 比较时间 到天
//     *
//     * @param s1
//     * @param s2
//     * @return true date1的日期在date2之前
//     */
//    public boolean compareDate(String s1, String s2) {
//        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//        java.util.Calendar c1 = java.util.Calendar.getInstance();
//
//        java.util.Calendar c2 = java.util.Calendar.getInstance();
//
//        try
//
//        {
//
//            c1.setTime(df.parse(s1));
//
//            c2.setTime(df.parse(s2));
//
//        } catch (java.text.ParseException e) {
//
//            System.err.println("格式不正确");
//
//        }
//
//        int result = c1.compareTo(c2);
//
//        if (result > 0) {
//            System.out.println("c1大于等于c2");
//            return false;
//        } else if (result <= 0) {
//            System.out.println("c1小于c2");
//            return true;
//        }
//
//        return false;
//
//    }
//
//    /***
//     * 给日期增加多少天
//     *
//     * @param calDate
//     * @param addDate 类型必须是long
//     * @return
//     */
//    public static String addCalendarDay(Date calDate, long addDate) {
//        long time = calDate.getTime();
//        addDate = addDate * 24 * 60 * 60 * 1000;
//        time += addDate;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        return dateFormat.format(new Date(time));
//    }
//
//
//}
