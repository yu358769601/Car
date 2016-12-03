//package com.qichen.newTimer;
//
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//
//import com.qichen.app.AppContext;
//
//import java.util.Map;
//
//public class PreferenceUtil {
//
//	//存入信息
//	public static void setPreString(String key, String value) {
//		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.getApplication());
//		sharedPreferences.edit().putString(key, value).commit();
//	}
//
//	public static void setPreInt(String key, int value) {
//		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.getApplication());
//		sharedPreferences.edit().putInt(key, value).commit();
//	}
//
//	//key为pass，为true表示已经注册和登录，false为为登录
//	public static void setPreBoolean(String key, Boolean value) {
//		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.getApplication());
//		sharedPreferences.edit().putBoolean(key, value).commit();
//	}
//
//	public static String getPreString(String key) {
//		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.getApplication());
//		return sharedPreferences.getString(key, "");
//	}
//
//	public static int getPreInt(String key, int defaultValue) {
//		SharedPreferences sharedPreferences = PreferenceManager
//				.getDefaultSharedPreferences(AppContext.getApplication());
//		return sharedPreferences.getInt(key, defaultValue);
//	}
//
//	public static Boolean getPreBoolean(String key, Boolean defaultValue) {
//		SharedPreferences sharedPreferences = PreferenceManager
//				.getDefaultSharedPreferences(AppContext.getApplication());
//		return sharedPreferences.getBoolean(key, defaultValue);
//	}
//
//	// 移除偏好某个key值已经对应的值
//	public static void remove(String key) {
//		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.getApplication());
//		sharedPreferences.edit().remove(key);
//	}
//
//	// 查询偏好某个key是否已经存在
//	public static Boolean contain(String key) {
//		SharedPreferences sharedPreferences = PreferenceManager
//				.getDefaultSharedPreferences(AppContext.getApplication());
//		return sharedPreferences.contains(key);
//	}
//
//	// 返回偏好所有的键值对
//	public static Map<String, ?> getAll() {
//		SharedPreferences sharedPreferences = PreferenceManager
//				.getDefaultSharedPreferences(AppContext.getApplication());
//		return sharedPreferences.getAll();
//	}
//
//	// 清除偏好
//	public static void clear(SharedPreferences p) {
//		SharedPreferences.Editor editor = p.edit();
//		editor.clear();
//		editor.commit();
//	}
//}
