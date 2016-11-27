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

import java.util.ArrayList;
import java.util.Calendar;

public class VisaMonthDateView extends View {
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
    private boolean isClickDateByUser = false;//用户点击了
    private boolean isAbleClick = false;//只要是之前的就不让他点
    private int mPriceSize=10;

    public boolean isClickDateByUser() {
        return isClickDateByUser;
    }

    public void setClickDateByUser(boolean clickDateByUser) {
        isClickDateByUser = clickDateByUser;
    }

    String price = "";

    public VisaMonthDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        Calendar calendar = Calendar.getInstance();
        mPaint = new Paint();
        nPaint = new Paint();
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
        for (int day = 0; day < mMonthDays; day++) {
            dayString = (day + 1) + "";
            int column = (day + weekNumber - 1) % 7;
            int row = (day + weekNumber - 1) / 7;
            daysString[row][column] = day + 1;
            int startX = (int) (mColumnSize * column
                    + (mColumnSize - mPaint.measureText(dayString)) / 2);
            int startY = (int) (mRowSize * row + mRowSize / 2
                    - (mPaint.ascent() + mPaint.descent()) / 2);
            if (dayString.equals(mSelDay + "")) {
                // 绘制背景色矩形
                int startRecX = mColumnSize * column;
                int startRecY = mRowSize * row;
                int endRecX = startRecX + mColumnSize;
                int endRecY = startRecY + mRowSize;
                if (isClickDateByUser) {
                    mPaint.setColor(mSelectBGColor);
                } else {
                    mPaint.setColor(mSelectDayColor);
                }
                canvas.drawRect(startRecX, startRecY, endRecX, endRecY + 5, mPaint);
                // 记录第几行，即第几周
                weekRow = row + 1;
            }
            // 绘制事务圆形标志
            drawCircle(row, column, mSelYear, mSelMonth, day + 1, canvas,dayString);
            if (!isClickDateByUser&&dayString.equals(mCurrDay + "")) {
                mPaint.setColor(mCurrentColor);
            }else if (dayString.equals(mCurrDay + "")
                    && mCurrDay != mSelDay
                    && mCurrMonth == mSelMonth) {
                // 正常月，选中其他日期，则今日为红色
                mPaint.setColor(mCurrentColor);
            } else if(isClickDateByUser&&dayString.equals(mSelDay + "")){
                mPaint.setColor(mSelectDayColor);
            } else {
                mPaint.setColor(mDayColor);
            }
            canvas.drawText(dayString, startX, startY, mPaint);
            if (tv_date != null) {
                tv_date.setText(mSelYear + "年" + (mSelMonth + 1) + "月");
            }
        }
    }

    private void drawCircle(int row, int column, int year, int month, int day, Canvas canvas, String dayString) {

        if (groupThingsList != null && groupThingsList.size() > 0) {
            // mPaint.setColor(mCircleColor);
            if (isClickDateByUser&&dayString.equals(mSelDay + "")) {
                nPaint.setColor(mSelectDayColor);
            }else {
                nPaint.setColor(mCircleColor);
            }
            nPaint.setTextSize(mPriceSize * mDisplayMetrics.scaledDensity);
            float circleX = (float) (mColumnSize * column + 0.2 * mColumnSize);
            float circley = (float) (mRowSize * row + mRowSize);
            // canvas.drawCircle(circleX, circley, mCircleRadius, mPaint);
            int startX = (int) (mColumnSize * column
                    + (mColumnSize - nPaint.measureText(
                    "￥" + groupThingsList.get(0).getAdultPrice())) / 2);
            int startY = (int) (mRowSize * (row + 1));

            DateModal dateModa = groupThingsList.get(0);
            String mYear = dateModa.getYear();
            String mMonth = dateModa.getMonth();
            String mDay = dateModa.getDay();
            String s1 = mYear + "-" + mMonth + "-" + mDay;
            String s2 = year + "-" + (month + 1) + "-" + day;
            DateCompare dateCompare = new DateCompare();
            if (dateCompare.compareDate(s1, s2)) {
                canvas.drawText("￥" + groupThingsList.get(0).getAdultPrice(), startX, startY,
                        nPaint);
            }
        }
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
     */
    private void setSelectYearMonth(int year, int month, int day) {
        mSelYear = year;
        mSelMonth = month;
        mSelDay = day;
    }

    /**
     * 执行点击事件
     */
    private void doClickAction(int x, int y) {
        int row = y / mRowSize;
        int column = x / mColumnSize;
        String day = String.valueOf(daysString[row][column]);
        String tempMonth = mSelMonth + 1 + "";
        if (Integer.valueOf(day) < 10) {
            day = "0" + day;
        }
        if (Integer.valueOf(tempMonth) < 10) {
            tempMonth = "0" + tempMonth;
        }
        //获得当前日期
        String nowtime = DateUtils.getCurrentDate();
        //和当前日期做对比
        DateCompare dateCompare = new DateCompare();
        String tempDate = mSelYear + "-" + tempMonth + "-" + day;
        boolean resultCompare = dateCompare.compareDate(nowtime, tempDate);
        if (resultCompare) {
            setSelectYearMonth(mSelYear, mSelMonth, daysString[row][column]);
            invalidate();
            // 执行activity发送过来的点击处理事件
            if (dateClick != null) {
                dateClick.onClickOnDate();
            }
        }
    }

    /**
     * 左点击，日历向后翻页
     */
    public void onLeftClick() {
        int year = mSelYear;
        int month = mSelMonth;
        int day = mSelDay;
        String tempDate = mSelYear + "-" + mSelMonth + "-" + mSelDay;
        //获得当前日期
        String nowtime = DateUtils.getCurrentDate();
        //和当前日期做对比
        DateCompare dateCompare = new DateCompare();
        boolean resultCompare = dateCompare.compareDate(nowtime, tempDate);
        if (resultCompare) { //如果时间在之后就不能往前了
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
     */
    public int getmSelYear() {
        return mSelYear;
    }

    /**
     * 获取选择的月份
     */
    public int getmSelMonth() {
        return mSelMonth;
    }

    /**
     * 获取选择的日期
     */
    public int getmSelDay() {
        return this.mSelDay;
    }

    /**
     * 普通日期的字体颜色，默认黑色
     */
    public void setmDayColor(int mDayColor) {
        this.mDayColor = mDayColor;
    }

    /**
     * 选择日期的颜色，默认为白色
     */
    public void setmSelectDayColor(int mSelectDayColor) {
        this.mSelectDayColor = mSelectDayColor;
    }

    /**
     * 选中日期的背景颜色，默认蓝色
     */
    public void setmSelectBGColor(int mSelectBGColor) {
        this.mSelectBGColor = mSelectBGColor;
    }

    /**
     * 当前日期不是选中的颜色，默认红色
     */
    public void setmCurrentColor(int mCurrentColor) {
        this.mCurrentColor = mCurrentColor;
    }

    /**
     * 日期的大小，默认18sp
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
     */
    public void setDaysHasThingList(ArrayList<DateModal> daysHasThingList) {
        this.groupThingsList = daysHasThingList;
    }

    /***
     * 设置圆圈的半径，默认为6
     */
    public void setmCircleRadius(int mCircleRadius) {
        this.mCircleRadius = mCircleRadius;
    }

    /**
     * 设置圆圈的半径
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
}
