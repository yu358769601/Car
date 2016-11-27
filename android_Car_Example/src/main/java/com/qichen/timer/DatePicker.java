//package com.qichen.timer;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.os.Build;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.FrameLayout;
//import android.widget.NumberPicker;
//
//import com.qichen.ruida.R;
//
//import java.util.Calendar;
//
//public class DatePicker extends FrameLayout implements OnClickListener {
//	private final String YEARMONTHDAY = "year_month_day";
//	private final String NUMBERNUMBERNUMBER = "number_number_number";
//	private final String NIANFEN = "nianfen";
//	private final String YEARMONTHDAYQIBAO = "year_month_day_qibao";
//	private final String YEARMONTHDAYNORMAL ="year_month_day_normal";
//	private Context mContext;
//	//选择的年-月-日控件
//	private NumberPicker yearPicker;
//	private NumberPicker monthPicker;
//	private NumberPicker dayPicker;
//	//通过Calendar获取系统时间
//	private Calendar mCalendar;
//	private int currentYear, currentMonth, currentDay;// 当前年月日
//	int[] months_big = { 1, 3, 5, 7, 8, 10, 12 };// 大月
//	int[] months_little = { 4, 6, 9, 11 };// 小月
//	private String datePicker_style;// 自定义属性
//
//	public DatePicker(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		mContext = context;
//		TypedArray array = context.obtainStyledAttributes(attrs,
//				R.styleable.datepicker);
//		datePicker_style = array
//				.getString(R.styleable.datepicker_datePicker_style);
//		int maxyear = array.getInt(R.styleable.datepicker_maxYear, -1);
//		if (datePicker_style == null) {
//			return;
//		}
//		if (datePicker_style.equals(YEARMONTHDAY)) {
//			//初登
//			mCalendar = Calendar.getInstance();
//			currentYear = mCalendar.get(Calendar.YEAR);
//			currentMonth = mCalendar.get(Calendar.MONTH) + 1;
//			currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
//			mCalendar.add(Calendar.MONTH, -1);
//			((LayoutInflater) mContext
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
//					.inflate(R.layout.layout_time_picker, this, true);
//			yearPicker = (NumberPicker) findViewById(R.id.time_day);
//			monthPicker = (NumberPicker) findViewById(R.id.time_hours);
//			dayPicker = (NumberPicker) findViewById(R.id.time_minutes);
//			yearPicker.setEnabled(false);
//			monthPicker.setEnabled(false);
//			dayPicker.setEnabled(false);
//			monthPicker.setWrapSelectorWheel(false);
//			setYearMonthDayFromZDY();
//			monthPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
//			dayPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
//
//			yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//
//				@Override
//				public void onValueChange(NumberPicker picker, int oldVal,
//						int newVal) {
//					setDayFromMonthAndYear(false, 0, 1, false);
//						if(newVal>=currentYear){
//							//选择的年大于当前年份
//							yearPicker.setValue(currentYear);
//							if(monthPicker.getValue()>=currentMonth){
//								monthPicker.setValue(currentMonth);
//								if(dayPicker.getValue()>=currentDay){
//									dayPicker.setValue(currentDay);
//								}
//							}
//					}
//				}
//			});
//			monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//
//				@Override
//				public void onValueChange(NumberPicker picker, int oldVal,
//						int newVal) {
//					setDayFromMonthAndYear(false, 0, 1, false);
//					if(yearPicker.getValue() == currentYear && newVal >= currentMonth){
//						//如果当前选择的年份==当前的年份，并且选择的月份大于当前月份
//							monthPicker.setValue(currentMonth);
//							if(dayPicker.getValue()>=currentDay){
//								dayPicker.setValue(currentDay);
//							}
//					}
//				}
//			});
//			dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//				@Override
//				public void onValueChange(NumberPicker picker, int oldVal,
//						int newVal) {
//					if(yearPicker.getValue() == currentYear && monthPicker.getValue() == currentMonth && newVal >= currentDay){
//						dayPicker.setValue(currentDay);
//					}
//				}
//			});
//		} else if (datePicker_style.equals(NUMBERNUMBERNUMBER)) {
//			((LayoutInflater) mContext
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
//					.inflate(R.layout.layout_time_picker_chejia, this, true);
//			yearPicker = (NumberPicker) findViewById(R.id.time_day);
//			monthPicker = (NumberPicker) findViewById(R.id.time_hours);
//			dayPicker = (NumberPicker) findViewById(R.id.time_minutes);
//			yearPicker.setEnabled(false);
//			monthPicker.setEnabled(false);
//			dayPicker.setEnabled(false);
//			yearPicker.setWrapSelectorWheel(true);
//			monthPicker.setWrapSelectorWheel(true);
//			dayPicker.setWrapSelectorWheel(true);
//			yearPicker.setMinValue(0);
//			yearPicker.setMaxValue(9);
//			yearPicker.setValue(0);
//			monthPicker.setMinValue(0);
//			monthPicker.setMaxValue(9);
//			monthPicker.setValue(0);
//			dayPicker.setMinValue(0);
//			dayPicker.setMaxValue(9);
//			dayPicker.setValue(0);
//		} else if (datePicker_style.equals(NIANFEN)) {
//			mCalendar = Calendar.getInstance();
//			currentYear = mCalendar.get(Calendar.YEAR);
//			((LayoutInflater) mContext
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
//					.inflate(R.layout.layout_time_picker_nianfen, this, true);
//			yearPicker = (NumberPicker) findViewById(R.id.time_day);
//			yearPicker.setEnabled(false);
//			yearPicker.setWrapSelectorWheel(true);
//			yearPicker.setMinValue(currentYear - 10);
//			yearPicker.setMaxValue(currentYear);
//			yearPicker.setValue(0);
//
//		}else if (datePicker_style.equals(YEARMONTHDAYQIBAO)) {
//			//起保
//			mCalendar = Calendar.getInstance();
//			currentYear = mCalendar.get(Calendar.YEAR);
//			currentMonth = mCalendar.get(Calendar.MONTH) + 1;
//			currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
//			mCalendar.add(Calendar.MONTH, -1);
//			((LayoutInflater) mContext
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
//					.inflate(R.layout.layout_time_picker, this, true);
//			yearPicker = (NumberPicker) findViewById(R.id.time_day);
//			monthPicker = (NumberPicker) findViewById(R.id.time_hours);
//			dayPicker = (NumberPicker) findViewById(R.id.time_minutes);
//			yearPicker.setEnabled(false);
//			monthPicker.setEnabled(false);
//			dayPicker.setEnabled(false);
//			monthPicker.setWrapSelectorWheel(false);
//			setYearMonthDayFromZDY();
//			monthPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
//			dayPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
//
//			yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//
//				@Override
//				public void onValueChange(NumberPicker picker, int oldVal,
//						int newVal) {
//					setDayFromMonthAndYear(false, 0, 1, false);
//						if(newVal<=currentYear){
//							yearPicker.setValue(currentYear);
//							if(monthPicker.getValue()<=currentMonth){
//								monthPicker.setValue(currentMonth);
//								if(dayPicker.getValue()<=currentDay){
//									dayPicker.setValue(currentDay);
//								}
//							}
//
//						}
//				}
//			});
//			monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//
//				@Override
//				public void onValueChange(NumberPicker picker, int oldVal,
//						int newVal) {
//					setDayFromMonthAndYear(false, 0, 1, false);
//					if(yearPicker.getValue() == currentYear && newVal <= currentMonth){
//						//如果当前选择的年份==当前的年份，并且选择的月份大于当前月份
//							monthPicker.setValue(currentMonth);
//							if(dayPicker.getValue()<=currentDay){
//								dayPicker.setValue(currentDay);
//							}
//					}
//				}
//			});
//			dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//				@Override
//				public void onValueChange(NumberPicker picker, int oldVal,
//						int newVal) {
//					if(yearPicker.getValue() == currentYear && monthPicker.getValue() == currentMonth && newVal <= currentDay){
//						dayPicker.setValue(currentDay);
//					}
//				}
//			});
//
//		}else if(datePicker_style.equals(YEARMONTHDAYNORMAL)){
//			mCalendar = Calendar.getInstance();
//			currentYear = mCalendar.get(Calendar.YEAR);
//			currentMonth = mCalendar.get(Calendar.MONTH) + 1;
//			currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
//			mCalendar.add(Calendar.MONTH, -1);
//			((LayoutInflater) mContext
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
//					.inflate(R.layout.layout_time_picker, this, true);
//			yearPicker = (NumberPicker) findViewById(R.id.time_day);
//			monthPicker = (NumberPicker) findViewById(R.id.time_hours);
//			dayPicker = (NumberPicker) findViewById(R.id.time_minutes);
//			yearPicker.setEnabled(false);
//			monthPicker.setEnabled(false);
//			dayPicker.setEnabled(false);
//			monthPicker.setWrapSelectorWheel(false);
//			setYearMonthDayFromZDY();
//			monthPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
//			dayPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
//
//			yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//
//				@Override
//				public void onValueChange(NumberPicker picker, int oldVal,
//						int newVal) {
//					setDayFromMonthAndYear(false, 0, 1, false);
//
//				}
//			});
//			monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//
//				@Override
//				public void onValueChange(NumberPicker picker, int oldVal,
//						int newVal) {
//					setDayFromMonthAndYear(false, 0, 1, false);
//
//				}
//			});
//			dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//				@Override
//				public void onValueChange(NumberPicker picker, int oldVal,
//						int newVal) {
//
//				}
//			});
//		}
//
//		// updateTime();
//
//	}
//
//	/**
//	 * 设置年月日的范围从自定义属性
//	 */
//	private void setYearMonthDayFromZDY() {
//		if (datePicker_style.equals(YEARMONTHDAYQIBAO)) {
//			yearPicker.setMaxValue(currentYear+50);
//		} else {
//			yearPicker.setMaxValue(currentYear+10);
//		}
//		yearPicker.setMinValue(1940);
//		yearPicker.setValue(currentYear);
//		monthPicker.setMinValue(1);
//		monthPicker.setMaxValue(12);
//		monthPicker.setValue(currentMonth);
//		setDayFromMonthAndYear(true, 0, currentDay, true);
//	}
//	private void setDayValue(int dayTime){
//		if(datePicker_style.equals(YEARMONTHDAY)){
//			//初登日期
//			dayPicker.setValue(dayTime);
//		}else if(datePicker_style.equals(YEARMONTHDAYQIBAO)){
//			//起保日期
//			dayPicker.setValue(dayTime);
//		}else{
//			dayPicker.setValue(dayTime);
//		}
//
//	}
//	/**
//	 *
//	 * @param isBack
//	 *            //是否恢复上次时间
//	 * @param position
//	 *            //时间框的位置
//	 * @param day
//	 *            //天数
//	 * @param isLock
//	 *            //是否锁定限制时间范围
//	 */
//	private void setDayFromMonthAndYear(boolean isBack, int position, int day,
//										boolean isLock) {
//		if (isBack) {
//			dayPicker.setMinValue(1);
//			for (int i : months_big) {
//				if (monthPicker.getValue() == i) {
//					if (position == 0) {
//						if (isLock)
//
//							dayPicker.setMaxValue(31);
//						else
//							dayPicker.setMaxValue(31);
//						setDayValue(day);
//						return;
//					}
//					if (isLock)
//						dayPicker.setMaxValue(31);
//					else
//						dayPicker.setMaxValue(31);
//					setDayValue(day);
//					return;
//				}
//			}
//			for (int i : months_little) {
//				if (monthPicker.getValue() == i) {
//					if (position == 0) {
//						if (isLock)
//							dayPicker.setMaxValue(30);
//						else
//							dayPicker.setMaxValue(30);
//						setDayValue(day);
//						return;
//					}
//					if (isLock)
//						dayPicker.setMaxValue(30);
//					else
//						dayPicker.setMaxValue(30);
//					setDayValue(day);
//					return;
//				}
//			}
//			if ((yearPicker.getValue() % 4 == 0 && yearPicker.getValue() % 100 != 0)
//					|| yearPicker.getValue() % 400 == 0)// 闰年
//			{
//				if (position == 0) {
//					if (isLock)
//						dayPicker.setMaxValue(29);
//					else
//						dayPicker.setMaxValue(29);
//					setDayValue(day);
//					return;
//				}
//				if (isLock)
//					dayPicker.setMaxValue(29);
//				else
//					dayPicker.setMaxValue(29);
//				setDayValue(day);
//			} else {
//				if (position == 0) {
//					if (isLock)
//						dayPicker.setMaxValue(currentDay);
//					else
//						dayPicker.setMaxValue(28);
//					setDayValue(day);
//					return;
//				}
//				if (isLock)
//					dayPicker.setMaxValue(28);
//				else
//					dayPicker.setMaxValue(28);
//				setDayValue(day);
//			}
//
//		} else {
//			dayPicker.setMinValue(1);
//			for (int i : months_big) {
//				if (monthPicker.getValue() == i) {
//					if (position == 0) {
//						if (isLock)
//							dayPicker.setMaxValue(31);
//						else
//							dayPicker.setMaxValue(31);
//						dayPicker.setValue(1);
//						return;
//					}
//					if (isLock)
//						dayPicker.setMaxValue(31);
//					else
//						dayPicker.setMaxValue(31);
//					setDayValue(currentDay);
//					return;
//				}
//			}
//			for (int i : months_little) {
//				if (monthPicker.getValue() == i) {
//					if (position == 0) {
//						if (isLock)
//							dayPicker.setMaxValue(30);
//						else
//							dayPicker.setMaxValue(30);
//						setDayValue(currentDay);
//						return;
//					}
//					if (isLock)
//						dayPicker.setMaxValue(30);
//					else
//						dayPicker.setMaxValue(30);
//					setDayValue(currentDay);
//					return;
//				}
//			}
//			if ((yearPicker.getValue() % 4 == 0 && yearPicker.getValue() % 100 != 0)
//					|| yearPicker.getValue() % 400 == 0)// 闰年
//			{
//				if (position == 0) {
//					if (isLock)
//						dayPicker.setMaxValue(29);
//					else
//						dayPicker.setMaxValue(29);
//					setDayValue(currentDay);
//					return;
//				}
//				if (isLock)
//					dayPicker.setMaxValue(29);
//				else
//					dayPicker.setMaxValue(29);
//				setDayValue(currentDay);
//			} else {
//				if (position == 0) {
//					if (isLock)
//						dayPicker.setMaxValue(28);
//					else
//						dayPicker.setMaxValue(28);
//					setDayValue(currentDay);
//					return;
//				}
//				if (isLock)
//					dayPicker.setMaxValue(28);
//				else
//					dayPicker.setMaxValue(28);
//				setDayValue(currentDay);
//			}
//
//		}
//
//	}
//	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
//	public DatePicker(Context context) {
//		this(context, null);
//	}
//
//	@Override
//	public void onClick(View v) {
//
//	}
//
//	/**
//	 * 这个月有多少天
//	 *
//	 * @return
//	 */
//	public static int getCurrentMonthDay() {
//
//		Calendar a = Calendar.getInstance();
//		a.set(Calendar.DATE, 1);
//		a.roll(Calendar.DATE, -1);
//		int maxDate = a.get(Calendar.DATE);
//		return maxDate;
//	}
//
//	/**
//	 * 上个月有多少天
//	 */
//	public static int getLastMonthDay() {
//		Calendar a = Calendar.getInstance();
//		a.add(Calendar.MONTH, -1);
//		a.set(Calendar.DATE, 1);
//		a.roll(Calendar.DATE, -1);
//		int maxDate = a.get(Calendar.DATE);
//		return maxDate;
//	}
//
//	public String getYear() {
//		return yearPicker.getValue() + "";
//	}
//
//	public String getMonth() {
//		return monthPicker.getValue() < 10 ? "0" + monthPicker.getValue()
//				: monthPicker.getValue() + "";
//	}
//
//	public String getDay() {
//		return dayPicker.getValue() < 10 ? "0" + dayPicker.getValue()
//				: dayPicker.getValue() + "";
//	}
//
//	public String getTime() {
//		if (datePicker_style.equals(YEARMONTHDAY) || datePicker_style.equals(YEARMONTHDAYQIBAO)) {
//			return getYear() + "-" + getMonth() + "-" + getDay();
//		} else if (datePicker_style.equals(NUMBERNUMBERNUMBER)) {
//			return getYear() + monthPicker.getValue() + dayPicker.getValue();
//		} else if (datePicker_style.equals(NIANFEN)) {
//			return getYear();
//		}else if(datePicker_style.equals(YEARMONTHDAYNORMAL)){
//			return getYear() + "-" + getMonth() + "-" + getDay();
//		}
//		return "";
//	}
//}
