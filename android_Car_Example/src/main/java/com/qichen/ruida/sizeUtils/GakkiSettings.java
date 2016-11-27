package com.qichen.ruida.sizeUtils;

import android.os.Environment;

/**
 * @class Gakki Settings
 * @author LeonNoW
 * @version 1.0
 * @date 2014年5月8日
 */
public class GakkiSettings
	//
{
	//private static Gakki _application;
	
	public static final int NETWORK_CONNECT_TIMEOUT = 10000;
	public static final int NETWORK_READ_TIMEOUT = 10000;
	public static final int NETWORK_TASK_RETRY_TIMES = 2;
	
	public static final int CACHE_IMAGE_MEMORY_EXPECTED_SIZE = (int) (Runtime.getRuntime().maxMemory() / 4); // 4096000;
	public static final int CACHE_IMAGE_STROAGE_EXPECTED_SIZE = 40960000;
	
	public static final String BASE_STROAGE_PATH = String.format("%1$s/LeHuoHN/", Environment.getExternalStorageDirectory().getPath());
	public static final String CACHE_STROAGE_PATH = String.format("%1$sCaches/", BASE_STROAGE_PATH);
	public static final String CACHE_IMAGE_STROAGE_PATH = String.format("%1$sImages/", CACHE_STROAGE_PATH);
	
	public static final String SAVE_PATH = String.format("%1$sSaves/", BASE_STROAGE_PATH);
	public static final String IMAGE_SAVE_PATH = String.format("%1$sImages/", SAVE_PATH);
	
	public static final String LOG_PATH = String.format("%1$sLogs/exception.log", BASE_STROAGE_PATH);
	
//	public static final int DIVIDER_HEIGHT = Gakki.getContext().getResources().getDimensionPixelSize(R.dimen.list_divider_height);
	
	public final static int INPUT_DELAY = 250;
	
	public static final String WEB_ENCODING = "utf-8";
	public static final String WEB_MINE_TYPE = "text/html";
	
	public static int VERSION_CODE;
	public static String VERSION_NAME;
	
//	public static void init()
//	{
//		_application = Gakki.getApplication();
//
//		initPackageInfo();
//	}
	 
//	private static void initPackageInfo()
//	{
//		PackageInfo info;
//		try
//		{
//			info = _application.getPackageManager().getPackageInfo(_application.getPackageName(), 0);
//			VERSION_CODE = info.versionCode;
//			VERSION_NAME = info.versionName;
//			info = null;
//		}
//		catch (NameNotFoundException e) {}
//	}
}
