package com.qichen.newTimer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class MonthDateView extends View {
    private static final int NUM_COLUMNS = 7;
    private static final int NUM_ROWS = 6;
    private Paint mPaint;
    private Paint nPaint;
    private int mDayColor = Color.parseColor("#000000");
    private int mSelectDayColor = Color.parseColor("#ffffff");
    private int mSelectBGColor = Color.parseColor("#fe8802");
    private int mCurrentColor = Color.parseColor("#ff0000");
    private int mCurrYear, mCurrMonth, mCurrDay;
    private int mSelYear, mSelMonth, mSelDay;
    private String adultPrice, childPrice;
    private int mColumnSize, mRowSize;
    private DisplayMetrics mDisplayMetrics;
    private int mDaySize = 20;
    private TextView tv_date, tv_week;
    private int weekRow;
    private int[][] daysString;
    private int mCircleRadius = 6;
    private DateClick dateClick;
    private int mCircleColor = Color.parseColor("#ff0000");
    private ArrayList<DateModal> groupThingsList = new ArrayList<DateModal>();
    private boolean isCruise = false;
    private boolean isClickDateByUser = false;//用户点击了
    private boolean isAbleClick = false;//如果有团期价格才能点击
    private List<Long> unClickDate = new ArrayList<Long>();
    private int mPriceSize=10;
    private boolean isNeedSeePriceRule =true;//true 循环所有价格 false不循环所有价格
    private Map<String,DateModal>dateMap;//存储价格（包括价格是否合法）的MAP
    private Map<String,Boolean>isAbleClickList;
    public int tag;//0时为选中页

    public boolean isClickDateByUser() {
        return isClickDateByUser;
    }

    public void setClickDateByUser(boolean clickDateByUser) {
        isClickDateByUser = clickDateByUser;
    }

    public void setCruise(boolean cruise) {
        isCruise = cruise;
    }

    String price = "";

    public MonthDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        Calendar calendar = Calendar.getInstance();
        mPaint = new Paint();
        mCurrYear = calendar.get(Calendar.YEAR);
        mCurrMonth = calendar.get(Calendar.MONTH);
        mCurrDay = calendar.get(Calendar.DATE);
        setSelectYearMonth(mCurrYear, mCurrMonth, mCurrDay);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initSize();
        daysString = new int[6][7];
        mPaint.setTextSize(mDaySize * mDisplayMetrics.scaledDensity);
        String dayString;
        int mMonthDays = DateUtils.getMonthDays(mSelYear, mSelMonth);
        int weekNumber = DateUtils.getFirstDayWeek(mSelYear, mSelMonth);
        Log.d("DateView", "DateView:" + mSelMonth + "月1号周" + weekNumber);
        Log.i("MonthDateView", "--------" + mSelDay);
        for (int day = 0; day < mMonthDays; day++) {
            dayString = (day + 1) + "";
            int column = (day + weekNumber - 1) % 7;
            int row = (day + weekNumber - 1) / 7;
            daysString[row][column] = day + 1;
            int startX = (int) (mColumnSize * column + (mColumnSize - mPaint
                    .measureText(dayString)) / 2);
            int startY = (int) (mRowSize * row + mRowSize / 2 - (mPaint
                    .ascent() + mPaint.descent()) / 2);
            Log.i("MonthDateView", "--the same------" + mSelDay);

            if (dayString.equals(mSelDay + "")) {
                Log.i("MonthDateView", "--the same------" + mSelDay);
                // 绘制背景色矩形
                int startRecX = mColumnSize * column;
                int startRecY = mRowSize * row;
                int endRecX = startRecX + mColumnSize;
                int endRecY = startRecY + mRowSize;
                if (isClickDateByUser&&tag==0) {
                    mPaint.setColor(mSelectBGColor);
                } else {
                    mPaint.setColor(mSelectDayColor);
                }
                canvas.drawRect(startRecX, startRecY, endRecX, endRecY + 5,
                        mPaint);
                // 记录第几行，即第几周
                weekRow = row + 1;
            }
            // 绘制事务圆形标志
            drawCircle(row, mSelYear, mSelMonth + 1, column, day + 1, canvas);
            if (dayString.equals(mCurrDay + "") && mCurrDay != mSelDay
                    && mCurrMonth == mSelMonth) {
                // 正常月，选中其他日期，则今日为红色
                mPaint.setColor(mCurrentColor);
            } else if(isClickDateByUser&&tag==0&&dayString.equals(mSelDay + "")){
                mPaint.setColor(mSelectDayColor);
            } else {
                mPaint.setColor(mDayColor);
            }
            canvas.drawText(dayString, startX, startY, mPaint);
            if (tv_date != null) {
                tv_date.setText(mSelYear + "年" + (mSelMonth + 1) + "月");
            }

//			if (tv_week != null) {
//				tv_week.setText("第" + weekRow + "周");
//			}
        }
    }

    /**
     *计算时间是否合法
     * @param regAbleStart 可以预定开始时间
     * @param regAbleEnd 截止时间
     * @param regAbleEndHour 截止小时
     * @param year 被比较的年
     * @param month 被比较的月
     * @param day 被比较的日
     * @return
     */
    private boolean getRangeAble(String regAbleStart, String regAbleEnd, String regAbleEndHour, String year, String month, String day) {
        boolean startFlag = false;
        //获取当前日期包含小时
        //计算开始时间
//        long time = 30*60*1000;//30分钟

        try {
            if (regAbleStart != null) {
                Date rightDate = new Date();
                Calendar calendar=new GregorianCalendar();
                calendar.setTime(rightDate);
                calendar.add(calendar.DATE,Integer.valueOf(regAbleStart));
                rightDate=calendar.getTime();
                String tuanQiDate = year + "-" + month + "-" + day + " 00:00:00";

                DateCompareExclusiveToday dateCompare = new DateCompareExclusiveToday();
                //rightDate如果大于后面的 就说明团期超过了可预订日期限制，也就不能订了。所以返回true是不能订
                startFlag = dateCompare.compareTime(rightDate, tuanQiDate);
                //加上开始日期-比如开始日期是两天的限制，也就是说日期大于当天日期的都是灰色
                //加上截止日期-比如截止日期为一天的限制，也就是说日期小于等于一天的都是灰色   出了这两个范围的肯定是灰色
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //计算结束时间
        if (!startFlag && regAbleEnd != null) {
            Date rightDate = new Date();
            Calendar calendar=new GregorianCalendar();
            calendar.setTime(rightDate);
            calendar.add(calendar.DATE,Integer.valueOf(regAbleEnd));
            if (!"0".equals(regAbleEndHour)) {
                calendar.add(calendar.HOUR_OF_DAY,Integer.valueOf(regAbleEndHour));
            }
            rightDate=calendar.getTime();
            String tuanQiDate = year + "-" + month + "-" + day + " 00:00:00";

            DateCompareExclusiveToday dateCompare = new DateCompareExclusiveToday();
            //rightDate如果小于团期日期，说明超过截止日期
            startFlag = !(dateCompare.compareTime(rightDate, tuanQiDate));
        }
        return startFlag;
    }

    private void drawCircle(int row, int year, int month, int column, int day,
                            Canvas canvas) {
        nPaint = new Paint();

        //判断是否需要重新读取所有日期
        if (isNeedSeePriceRule){
            if (dateMap!=null&&dateMap.size()!=0) {
                dateMap.clear();
            }else {
                dateMap=new HashMap<String,DateModal>();
            }
            if (isAbleClickList!=null&&isAbleClickList.size()!=0){
                isAbleClickList.clear();
            }else {
                isAbleClickList=new HashMap<String,Boolean>();
            }
            //先根据groupThingsList和mSelMonth+1取出当月的结果
            for (int i = 0; i < groupThingsList.size(); i++) {
                if (mSelYear == Integer.parseInt(groupThingsList.get(i).getYear()
                        .replaceFirst("^0*", ""))) {
                    if ((mSelMonth + 1) == Integer.parseInt(groupThingsList.get(i).getMonth()
                            .replaceFirst("^0*", ""))) {
                        //当月的
                        //进行每天的状态判断
                        boolean unAbleGet;
                        DateModal dateModal = groupThingsList.get(i);
                        if (!isCruise) {
                        if ("0".equals(dateModal.getStock())){
                            //没有库存了
                            unAbleGet=true;
                        }else {
                            String regAbleStart = dateModal.getRegAbleStart();
                            String regAbleEnd = dateModal.getRegAbleEnd();
                            String regAbleEndHour = dateModal.getRegAbleEndHour();
                             unAbleGet = getRangeAble(regAbleStart, regAbleEnd, regAbleEndHour, dateModal.getYear(), dateModal.getMonth(), dateModal.getDay());
                        }
                        }else {
                            unAbleGet=false;
                        }
                        if (isCruise||!"0".equals(dateModal.getStock())) {
                            //库存不为0的话才加入可点击集合
                            if (unAbleGet) {
                                //不符合
                                isAbleClickList.put(dateModal.getYear() + dateModal.getMonth() + dateModal.getDay(), false);
                            } else {
                                isAbleClickList.put(dateModal.getYear() + dateModal.getMonth() + dateModal.getDay(), true);
                            }
                        }
                        //把当月的结果用key－value的形式存入map集合当中 key-value:day－DateModal，并存入限制判断后的状态结果islegalDate true为合法
                        dateMap.put(dateModal.getYear()+dateModal.getMonth()+dateModal.getDay(),dateModal);
                    }
                }
            }
            if (groupThingsList!=null&&groupThingsList.size()!=0) {
                isNeedSeePriceRule = false;
            }
        }

       //到此处一定是有价格规则了

        //根据dayString天数去map里找key
        String tempDay;
        String tempMonth;
        tempMonth = month<10?"0"+month:""+month;
        tempDay = day<10?"0"+day:""+day;

        DateModal dateModal = dateMap.get("" + year + tempMonth + tempDay);
        if (dateModal!=null) {
            if (!"0".equals(dateModal.getStock())) {
                //有团期有库存才绘制
                if (isCruise) {
                    price = dateModal.getFirstOutPrice();
                } else {
                    price = dateModal.getAdultPrice();
                }
                //找到对应的value
                if (isClickDateByUser && String.valueOf(year).equals(mSelYear + "") && String.valueOf(month).equals(mSelMonth + 1 + "") && String.valueOf(day).equals(mSelDay + "")) {
                    //如果是当天，并被用户选中了，那么价格是白色
                    nPaint.setColor(mSelectDayColor);
                } else {
                    //否则用红色
                    nPaint.setColor(mCircleColor);
                }
                //绘制价格
                DrawPrice(row, column, canvas);
            }else {
                nPaint.setColor(mCircleColor);
                nPaint.setTextSize(mPriceSize * mDisplayMetrics.scaledDensity);
                int startX = (int) (mColumnSize * column + (mColumnSize - nPaint
                        .measureText("已售完")) / 2);
                int startY = (int) (mRowSize * (row + 1));
                canvas.drawText("已售完", startX, startY, nPaint);
            }
        }
    }

    private void DrawPrice(int row, int column, Canvas canvas) {
        float tempPrice=Float.valueOf(price);
        int intTempPrice=(int)tempPrice;
        nPaint.setTextSize(mPriceSize * mDisplayMetrics.scaledDensity);
        int startX = (int) (mColumnSize * column + (mColumnSize - nPaint
                .measureText("￥" + intTempPrice)) / 2);
        int startY = (int) (mRowSize * (row + 1));
        canvas.drawText("￥"+intTempPrice, startX, startY, nPaint);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private int downX = 0, downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch (eventCode) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10) {// 点击事件
                    performClick();
                    doClickAction((upX + downX) / 2, (upY + downY) / 2);
                }
                break;
        }
        return true;
    }

    /**
     * 初始化列宽行高
     */
    private void initSize() {
        mColumnSize = getWidth() / NUM_COLUMNS;
        mRowSize = getHeight() / NUM_ROWS;
    }

    /**
     * 设置年月
     *
     * @param year
     * @param month
     */
    public void setSelectYearMonth(int year, int month, int day) {
        mSelYear = year;
        mSelMonth = month;
        mSelDay = day;
    }

    /**
     * 执行点击事件
     *
     * @param x
     * @param y
     */
    private void doClickAction(int x, int y) {
        try {
            int row = y / mRowSize;
            int column = x / mColumnSize;
            String tempDay;
            String tempMonth;
            tempMonth = (mSelMonth + 1) < 10 ? "0" + (mSelMonth + 1) : "" + (mSelMonth + 1);
            String day = String.valueOf(daysString[row][column]);
            tempDay = Integer.valueOf(day) < 10 ? "0" + day : "" + day;

            if (isAbleClickList.containsKey("" + mSelYear + tempMonth + tempDay)) {
                //有团期的
                tag = 0;
                //判断合法不合法
                Boolean islegalDate = isAbleClickList.get("" + mSelYear + tempMonth + tempDay);
                if (islegalDate) {
                    setSelectYearMonth(mSelYear, mSelMonth, daysString[row][column]);
                    invalidate();
                    // 执行activity发送过来的点击处理事件
                    if (dateClick != null) {
                        dateClick.onClickOnDate();
                    }
                } else {
                    Toast.makeText(super.getContext(), "该产品暂时不能预订", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 左点击，日历向后翻页
     */
    public void onLeftClick() {
        int year = mSelYear;
        int month = mSelMonth;
        int day = mSelDay;
        int tempMonth=mSelMonth+1;
        String tempDate = mSelYear + "-" + tempMonth + "-" + mSelDay;
        //获得当前日期
        String nowtime = DateUtils.getCurrentDate();
        //和当前日期做对比
        DateCompare dateCompare = new DateCompare();
        boolean resultCompare = dateCompare.compareDate(nowtime, tempDate);
        if (resultCompare) { //如果时间在之后就不能往前了
            tag--;
            isNeedSeePriceRule =true;
            if (month == 0) {// 若果是1月份，则变成12月份
                year = mSelYear - 1;
                month = 11;
            } else if (DateUtils.getMonthDays(year, month) == day) {
                // 如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
                month = month - 1;
                day = DateUtils.getMonthDays(year, month);
            } else {
                month = month - 1;
            }
            setSelectYearMonth(year, month, day);
            invalidate();
        }
    }

    /**
     * 右点击，日历向前翻页
     */
    public void onRightClick() {
        tag++;
        isNeedSeePriceRule =true;
        int year = mSelYear;
        int month = mSelMonth;
        int day = mSelDay;
        if (month == 11) {// 若果是12月份，则变成1月份
            year = mSelYear + 1;
            month = 0;
        } else if (DateUtils.getMonthDays(year, month) == day) {
            // 如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month + 1;
            day = DateUtils.getMonthDays(year, month);
        } else {
            month = month + 1;
        }
        setSelectYearMonth(year, month, day);
        invalidate();
    }

    /**
     * 获取选择的年份
     *
     * @return
     */
    public int getmSelYear() {
        return mSelYear;
    }

    /**
     * 获取选择的月份
     *
     * @return
     */
    public int getmSelMonth() {
        return mSelMonth;
    }

    /**
     * 获取选择的日期
     *
     * @return mSelDay
     */
    public int getmSelDay() {
        return this.mSelDay;
    }

    /**
     * 普通日期的字体颜色，默认黑色
     *
     * @param mDayColor
     */
    public void setmDayColor(int mDayColor) {
        this.mDayColor = mDayColor;
    }

    /**
     * 选择日期的颜色，默认为白色
     *
     * @param mSelectDayColor
     */
    public void setmSelectDayColor(int mSelectDayColor) {
        this.mSelectDayColor = mSelectDayColor;
    }

    /**
     * 选中日期的背景颜色，默认蓝色
     *
     * @param mSelectBGColor
     */
    public void setmSelectBGColor(int mSelectBGColor) {
        this.mSelectBGColor = mSelectBGColor;
    }

    /**
     * 当前日期不是选中的颜色，默认红色
     *
     * @param mCurrentColor
     */
    public void setmCurrentColor(int mCurrentColor) {
        this.mCurrentColor = mCurrentColor;
    }

    /**
     * 日期的大小，默认18sp
     *
     * @param mDaySize
     */
    public void setmDaySize(int mDaySize) {
        this.mDaySize = mDaySize;
    }

    /**
     * 设置显示当前日期的控件
     *
     * @param tv_date 显示日期
     * @param tv_week 显示周
     */
    public void setTextView(TextView tv_date, TextView tv_week) {
        this.tv_date = tv_date;
        this.tv_week = tv_week;
        invalidate();
    }

    /**
     * 设置事务天数
     *
     * @param daysHasThingList
     */
    public void setDaysHasThingList(ArrayList<DateModal> daysHasThingList) {
        this.groupThingsList = daysHasThingList;
    }

    /***
     * 设置圆圈的半径，默认为6
     *
     * @param mCircleRadius
     */
    public void setmCircleRadius(int mCircleRadius) {
        this.mCircleRadius = mCircleRadius;
    }

    /**
     * 设置圆圈的半径
     *
     * @param mCircleColor
     */
    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    /**
     * 设置日期的点击回调事件
     *
     * @author shiwei.deng
     */
    public interface DateClick {
        public void onClickOnDate();
    }

    /**
     * 设置日期点击事件
     *
     * @param dateClick
     */
    public void setDateClick(DateClick dateClick) {
        this.dateClick = dateClick;
    }

    /**
     * 跳转至今天
     */
    public void setTodayToView() {
        setSelectYearMonth(mCurrYear, mCurrMonth, mCurrDay);
        invalidate();
    }

    public Map<String, DateModal> getDateMap() {
        return dateMap;
    }
}
