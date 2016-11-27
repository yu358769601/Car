package com.qichen.newTimer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCompareExclusiveToday {

	public boolean compareDate(String s1, String s2) {
		java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Calendar c1 = java.util.Calendar.getInstance();

		java.util.Calendar c2 = java.util.Calendar.getInstance();

		try

		{

			c1.setTime(df.parse(s1));

			c2.setTime(df.parse(s2));

		} catch (ParseException e) {

			System.err.println("格式不正确");

		}

		int result = c1.compareTo(c2);

		if (result >= 0) {
			System.out.println("c1大于等于c2");
			return false;
		} else if (result < 0) {
			System.out.println("c1小于c2");
			return true;
		}

		return false;

	}

	/**
	 *
	 * @param date1
	 * @param date2
	 * @return true date1的日期在date2之前
	 */
	public boolean compareTime(Date date1, String date2) {
		java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Calendar c1 = java.util.Calendar.getInstance();

		java.util.Calendar c2 = java.util.Calendar.getInstance();
		try {
			c1.setTime(df.parse(df.format(date1)));
			c2.setTime(df.parse(date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int result = c1.compareTo(c2);

		if (result >= 0) {
			System.out.println("c1大于等于c2");
			return false;
		} else if (result < 0) {
			System.out.println("c1小于c2");
			return true;
		}

		return false;

	}

}
