package com.qichen.ruida.sizeUtils;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @class Format Utils
 * @author LeonNoW
 * @version 1.0
 * @date 2014年5月8日
 */
public class FormatUtils
	//
{
	public static boolean compareVersion(String current, String target)
	{
		if (current != null && target != null)
		{
			if (current.equalsIgnoreCase(target) == false)
			{
				String[] currentVersion = current.split("\\.");
				String[] targetVersion = target.split("\\.");
				
				int currentCount = currentVersion.length;
				int targetCount = targetVersion.length;
				int versionCount = Math.max(currentCount, targetCount);
				int currentNum;
				int targetNum;
				try 
				{
					for (int i = 0; i < versionCount; i++)
					{
						currentNum = i < currentCount ? Integer.valueOf(currentVersion[i]) : 0;
						targetNum = i < targetCount ? Integer.valueOf(targetVersion[i]) : 0;
						if (currentNum < targetNum)
						{
							return false;
						}
						else if (currentNum > targetNum)
						{
							return true;
						}
					}
				} 
				catch (NumberFormatException e) {}
				return false;
			}
		}
		return false;
	}
	
	public static String convertInputStreamToString(InputStream inputStream) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;

		while ((line = reader.readLine()) != null) 
		{
			stringBuffer.append(line).append("\n");
		}
		
		reader.close();
		reader = null;
		
		return stringBuffer.toString();
	}
	
	public static String convertIntegerWithComma(int value)
	{
		return new DecimalFormat(",###").format(value);
	}
	
	@SuppressWarnings("deprecation")
	public static int convertDateToIntger(Date date)
	{
		return date.getHours() * 60 + date.getMinutes();
	}
	
	public static int convertDateToIntger(String date, String format)
	{
		try 
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
			return convertDateToIntger(dateFormat.parse(date));
		} 
		catch (ParseException e)
		{
			return 0;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String convertDateToString(Date date)
	{
		if (date == null)
		{
			date = new Date();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	public static String getNameByData()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSS", Locale.getDefault());
		return dateFormat.format(new Date());
	}
	
	public static String getNameFromURL(String url)
	{
		int lastIndex = url.lastIndexOf("/");
		int length = url.length();
		String name;
		if (lastIndex == -1)
		{
			return url;
		}
		else if(lastIndex + 1 >= length)
		{
			return "";
		}
		else
		{
			name = url.substring(lastIndex + 1).replace("?", ".");
			return name;
		}
	}
	
	public static String encodeStringSHA1(String text)
	{ 
		String result;
		try 
		{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
		    md.update(text.getBytes("utf-8"), 0, text.length());
		    result = hex(md.digest());
		} 
		catch (NoSuchAlgorithmException e)
		{
			result = "";
		}
		catch (UnsupportedEncodingException e)
		{
			result = "";
		}
	    return result;
	} 
	
	private static String hex(byte[] arr)
    {  
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {  
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
        }  
        return sb.toString();  
    }
	
	public static String convertTimeFromPosition(int position)
	{
		int temp = position / 1000;
		int minute = temp / 60;
		int hour = minute / 60;
		int second = temp % 60;
		minute %= 60;
		return hour > 0 ? String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second)
				: String.format(Locale.getDefault(), "%02d:%02d", minute, second);
	}
	
	public static String converStringToBase64(String text)
	{
		if (text != null)
		{
			try
			{
				return Base64.encodeToString(text.getBytes(), Base64.DEFAULT);
			} 
			catch (OutOfMemoryError e) { }
		}
		return "";
	}
	
	public static String converStringFromBase64(String base64)
	{
		try
		{
			return new String(Base64.decode(base64, Base64.DEFAULT));
		} 
		catch (Exception e)
		{
			return "";
		}
	}
	
//	public static ColorStateList convertColorStateListFromResource(int color)
//	{
//		try
//		{
//			return ColorStateList.createFromXml(Gakki.getContext().getResources(),
//					Gakki.getContext().getResources().getXml(color));
//		}
//		catch (Exception e)
//		{
//			return null;
//		}
//	}
	
	public static String convertIdsFromArray(int[] ids)
	{
		String content = "";
		int length = ids.length;
		for (int i = 0; i < length; i++)
		{
			content =  String.format("%1$s%2$d%3$s", content, ids[i], (i < length - 1 ? "," : ""));
		}
		return content;
	}
	
	public static String convertIdsFromArray(String[] ids)
	{
		String content = "";
		int length = ids.length;
		for (int i = 0; i < length; i++)
		{
			content =  String.format("%1$s%2$s%3$s", content, ids[i], (i < length - 1 ? "," : ""));
		}
		return content;
	}
	
//	private static String DISTANCE_UNIT_M = Gakki.getContext().getString(R.string.life_merchant_range_near_unit_m);
//	private static String DISTANCE_UNIT_KM = Gakki.getContext().getString(R.string.life_merchant_range_near_unit_km);
	
//	public static String convertDistanceFromInteger(int distance)
//	{
//		distance = Math.max(0, distance);
//		if (distance < 1000)
//		{
//			return String.format("%1$d %2$s", distance, DISTANCE_UNIT_M);
//		}
//		else
//		{
//			return String.format("%1$s %2$s", new DecimalFormat(distance % 1000 == 0 ? "#" : "#.0").format(distance / 1000.0f), DISTANCE_UNIT_KM);
//		}
//	}
	
	public static String encodeUrlString(String content)
	{
		try
		{
			return URLEncoder.encode(content, "UTF-8");
		}
		catch (Exception ex)
		{
			return content;
		}
	}
	
	public static String convertMonthToChinese(String number)
	{
		return convertMonthToChinese(number, true);
	}
	
	public static String convertMonthToChinese(String number, boolean unit)
	{
		String[] monthArray;
		if (unit == true)
		{
			monthArray = new String[] { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月","十月", "十一", "十二" };
		}
		else
		{
			monthArray = new String[] { "一", "二", "三", "四", "五", "六", "七", "八", "九","十", "十一", "十二" };
		}
		int index = Integer.valueOf(number);
		if (index <= monthArray.length + 1 && index > 0)
		{
			return monthArray[index - 1];
		}
		else
		{
			return "";
		}
	}
	
	public static String[] getDateTimeFromCalendar(Calendar calender)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(calender.getTime()).split(" ");
	}
	
	public static Calendar convertStringToDate(String text, boolean time)
	{
		Calendar calender = Calendar.getInstance();;
		SimpleDateFormat formatter = new SimpleDateFormat(time == true ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd");
		try
		{
			Date date = formatter.parse(text);
			calender.setTime(date); 
		} 
		catch (ParseException e) { }
		return calender;
	}
	
	public static String spancePhoneNumber(String phone)
	{
		int unit = 4;
		List<String> list = new ArrayList<String>();
		int start, end;
		while (phone.length() > 0)
		{
			end = phone.length();
			start = Math.max(end - unit, 0);
			list.add(0, phone.substring(start, end));
			phone = phone.substring(0, start);
		}
		int length = list.size();
		String result = "";
		for (int i = 0; i < length; i++)
		{
			result = String.format("%1$s%2$s%3$s", result, list.get(i), i < length - 1 ? " " : "");
		}
		return result;
	}
	
	public static int convertTimeFromHourAndMinute(int hour, int minute)
	{
		return hour * 60 + minute;
	}
	
	public static String convertTimeToString(int time)
	{
		int[] array = convertTimeToHourAndMinute(time);
		return String.format("%1$02d:%2$02d", array[0], array[1]);
	}
	
	public static int[] convertTimeToHourAndMinute(int time)
	{
		int[] array = new int[2];
		array[0] = time / 60;
		array[1] = time % 60;
		return array;
	}
}
