package com.rongzhiheng.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.communication.CommunicationList;
//import net.sourceforge.pinyin4j.PinyinHelper;
//import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

public class Utils {
	private static ProgressDialog pDialog = null;
	private Context context;
	private String filePath;
	private String interimJsonPath;

	public static ProgressDialog progressDialog;

	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	public static ProgressDialog dialog(Activity ac) {
		progressDialog = ProgressDialog
				.show(ac, "请稍等...", "查询中...", true, true);
		return progressDialog;
	}

	/**
	 * 获取时间
	 * 
	 * @return
	 */
	public static String getSysNowTime(String type) {
		// Date now = new Date();
		// java.text.DateFormat format = new
		// java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String formatTime = format.format(now);
		// return formatTime;
		Date now = new Date();
		java.text.DateFormat format = new java.text.SimpleDateFormat(type);
		String formatTime = format.format(now);
		return formatTime;
	}

	/**
	 * 全屏
	 * 
	 * @param ac
	 */
	public static void HideFullscreen(Activity ac) {
		ac.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ac.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * 手机信息管理
	 */
	public static TelephonyManager mTelephonyManager;
	/**
	 * 网络连接状态
	 */
	public static String netWorkState = null;
	private static String networkType = null;

	/**
	 * 获取手机信息管理
	 * 
	 * @param context
	 * @return
	 */
	private static TelephonyManager getTelephonyManager(Context context) {
		if (mTelephonyManager == null) {
			mTelephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
		}
		return mTelephonyManager;
	}

	/**
	 * 获取手机号码
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		String phoneNumber;
		phoneNumber = getTelephonyManager(context).getLine1Number();
		if (phoneNumber == null)
			phoneNumber = "-1";
		return phoneNumber;
	}

	/**
	 * dialog
	 */
	public static ProgressDialog LoadingDialog(Context context) {
		pDialog = new ProgressDialog(context);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setMessage("Loading....");
		return pDialog;
	}

	/**
	 * 有状态栏
	 * 
	 * @param ac
	 */
	public static void HideTitle(Activity ac) {
		ac.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ac.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	}

	public static AlertDialog dialog(Context context, String str) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(str);
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface aDialog, int which) {
				aDialog.dismiss();
			}
		});
		return builder.create();
	}

	public static AlertDialog dialog1(Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("保存成功");
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface aDialog, int which) {
				aDialog.dismiss();
			}
		});
		return builder.create();
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetworkType(Context context) {
		if ((networkType == null) && (context != null)) {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			// ConnectivityManager cm =
			// (ConnectivityManager)context.getSystemService("connectivity");
			if (cm != null) {
				NetworkInfo activeNetInfo = cm.getActiveNetworkInfo();
				if (activeNetInfo != null) {
					networkType = activeNetInfo.getTypeName();
					if (networkType.equals("WIFI")) {
						networkType = String.valueOf("wifi");
					} else if (networkType.equals("mobile")) {
						if (activeNetInfo.getExtraInfo().equals("uninet")) {
							networkType = String.valueOf("uninet");
						} else if (activeNetInfo.getExtraInfo()
								.equals("uniwap")) {
							networkType = String.valueOf("uniwap");
						} else if (activeNetInfo.getExtraInfo().equals("cmwap")) {
							networkType = String.valueOf("cmwap");
						} else if (activeNetInfo.getExtraInfo().equals("cmnet")) {
							networkType = String.valueOf("cmnet");
						} else if (activeNetInfo.getExtraInfo().equals("ctwap")) {
							networkType = String.valueOf("ctwap");
						} else if (activeNetInfo.getExtraInfo().equals("ctnet")) {
							networkType = String.valueOf("ctnet");
						} else if (activeNetInfo.getExtraInfo().equals("3gwap")) {
							networkType = String.valueOf("3gwap");
						} else if (activeNetInfo.getExtraInfo().equals("3gnet")) {
							networkType = String.valueOf("3gnet");
						}
					}
				} else {
					networkType = "";
				}
			}
		}
		return networkType;
	}

	/**
	 * 判断当前网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			System.out.println("**** newwork is off");
			return false;
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info == null) {
				return false;
			}
			if (info == null) {
				System.out.println("**** newwork is off");
				return false;
			} else {
				if (info.isAvailable()) {
					System.out.println("**** newwork is on");
					return true;
				}
			}
		}
		System.out.println("**** newwork is off");
		return false;
	}

	/**
	 * install Application
	 * 
	 * @param apkFile
	 * @return
	 */
	public static Intent installApplication(String apkFile) {
		Uri uri = Uri.parse(apkFile);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		it.setData(uri);
		it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		it.setClassName("com.android.packageinstaller",
				"com.android.packageinstaller.PackageInstallerActivity");
		return it;
		// make sure the url_of_apk_file is readable for all users
		/*
		 * Uri installUri = Uri.fromParts("package", "", null); returnIt = new
		 * Intent(Intent.ACTION_PACKAGE_ADDED, installUri);
		 */

	}

	/**
	 * uninstall application
	 * 
	 * @param activityInfo
	 *            activityInfo.packageName
	 * @return is the activityInfo is null. true if the param activityInfo isn't
	 *         null else return false.
	 */
	public static Intent uninstallApplication(String packageName) {
		if (packageName == null) {
			return null;
		}
		Uri uri = Uri.fromParts("package", packageName, null);
		Intent it = new Intent(Intent.ACTION_DELETE, uri);
		return it;
	}

	/**
	 * String转InputStream
	 * 
	 * @param str
	 * @return
	 */
	public static InputStream StringToInputStream(String str) {
		InputStream in = new ByteArrayInputStream(str.getBytes());
		return in;
	}

	/**
	 * 把输入流转换成字符数组
	 * 
	 * @param inputStream
	 *            输入流
	 * @return 字符数组
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inputStream) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			bout.write(buffer, 0, len);
		}
		bout.close();
		inputStream.close();
		return bout.toByteArray();
	}

	public static void deleteApk(String fileName) {
		File updateFile = new File(fileName);
		if (updateFile.exists()) {
			// 当不需要的时候，清除之前的下载文件，避免浪费用户空间
			updateFile.delete();
		}
	}

	/**
	 * 创建随机数
	 * 
	 * @return
	 */
	public static int myRandom() {
		int randomNum = 0;
		randomNum = (int) (Math.random() * 9000 + 1000);
		return randomNum;
	}

	/**
	 * 后台发送短信
	 * 
	 * @param activity
	 * @param phoneNumber
	 * @param context
	 */
	public static void backstageSmsTo(Activity activity, String phoneNumber,
			String context) {
		SmsManager sms = SmsManager.getDefault();
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
		PendingIntent sentPI = PendingIntent.getBroadcast(activity, 0,
				new Intent(SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(activity, 0,
				new Intent(DELIVERED), 0);
		sms.sendTextMessage(phoneNumber, null, context, sentPI, deliveredPI);
	}

	public static String postUrlData(String urlstr, String para) {
		String TAG = "";
		String result = "";
		Log.d(TAG, "[postUrlData]para=[" + para + "]");
		HttpURLConnection con = null;
		try {
			if (Thread.interrupted())
				throw new InterruptedException();
			URL url = new URL(urlstr);
			con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(10000 /* milliseconds */);
			con.setConnectTimeout(15000 /* milliseconds */);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setUseCaches(false);
			con.setInstanceFollowRedirects(true);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			con.connect();
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(para);
			out.flush();
			out.close();
			if (Thread.interrupted())
				throw new InterruptedException();

			// 获取数据
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine = null;
			// 使用循环来读取获得的数据
			while (((inputLine = reader.readLine()) != null)) {
				result += inputLine;
			}
			reader.close();
			con.disconnect();
			Log.d(TAG, "[postUrlData]result=[" + result + "]");
			if (Thread.interrupted())
				throw new InterruptedException();

		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		} catch (InterruptedException e) {
			Log.d(TAG, "InterruptedException", e);
			result = "InterruptedException";
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		return result;
	}

	/**
	 * httpPost 请求方法
	 * 
	 * @param url
	 *            地址
	 * @param parameters
	 *            post的内容
	 * @return
	 */
	public static String httpPost(String url, String parameters) {
		String result = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			// 创建一个连接
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 30000);
			// if (AdManager.getNetworkType(context).equals("cmwap")) {
			// HttpHost proxy = new HttpHost("10.0.0.172", 80, "http");
			// httpClient.getParams().setParameter(
			// ConnRoutePNames.DEFAULT_PROXY, proxy);
			// }
			StringEntity stringEntity = new StringEntity(parameters, HTTP.UTF_8);
			httpPost.setEntity(stringEntity);
			HttpResponse rsp = httpClient.execute(httpPost);
			int code = rsp.getStatusLine().getStatusCode();
			if (code == HttpURLConnection.HTTP_OK) {
				HttpEntity httpEntity = rsp.getEntity();
				result = EntityUtils.toString(httpEntity);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		return result;
	}

	/**
	 * 
	 * @param 待验证的字符串
	 * 
	 * @return 如果是符合邮箱格式的字符串,返回<b>true</b>,否则为<b>false</b>
	 */

	public static boolean isEmail(String email) {

		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		email = email.toLowerCase();
		if (email.endsWith(".con"))
			return false;
		if (email.endsWith(".cm"))
			return false;
		if (email.endsWith("@gmial.com"))
			return false;
		if (email.endsWith("@gamil.com"))
			return false;
		if (email.endsWith("@gmai.com"))
			return false;
		return match(regex, email);
	}

	/**
	 * 
	 * @param 待验证的字符串
	 * 
	 * @return 如果是符合网址格式的字符串,返回<b>true</b>,否则为<b>false</b>
	 */

	public static boolean isHomepage(String str) {

		String regex = "http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*";

		return match(regex, str);

	}

	/**
	 * 
	 * @param regex
	 *            正则表达式字符串
	 * 
	 * @param str
	 *            要匹配的字符串
	 * 
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */

	private static boolean match(String regex, String str) {

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(str);

		return matcher.matches();

	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetWork(Context context) {
		boolean newWorkOK = false;
		ConnectivityManager connectManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectManager.getActiveNetworkInfo() != null) {
			newWorkOK = true;
		}
		return newWorkOK;
	}

	// 转换dip为px
	public static int convertDipOrPx(Context context, int dip) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}

	// 转换px为dip
	public static int convertPxOrDip(Context context, int px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
	}

	/**
	 * 处理列表，加入开头字幕索引
	 * 
	 * @author johnson 安顺 北京 ==加工==> A 安顺 B 北京
	 * **/

	public static ArrayList<CommunicationList> rebuildList(
			List<CommunicationList> mList) {

		String cityPinyin = "";
		String indexPinyin = "0";

		final ArrayList<CommunicationList> buildList = new ArrayList<CommunicationList>();
		for (CommunicationList city : mList) {
			cityPinyin = city.getCommunicationListSx().substring(0, 1);
			if (!cityPinyin.equals(indexPinyin)) {
				indexPinyin = cityPinyin;
				buildList.add(addCityIndex(indexPinyin));
				buildList.add(city);
			} else {
				buildList.add(city);
			}

		}
		return buildList;

	}

	/**
	 * 开头字幕索引
	 * 
	 * @author johnson
	 */
	public static CommunicationList addCityIndex(String str) {

		return new CommunicationList(str,  str,"-" + str,str, str, str, str, str);

	}

	/**
	 * 判断字符串是否包含有中文
	 */
	public static boolean isChinese(String str) {
		String regex = "[\\u4e00-\\u9fa5]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	 //使用PinYin4j.jar将汉字转换为拼音   
//    public static String chineneToSpell(String chineseStr,HanyuPinyinOutputFormat spellFormat){   
//        return PinyinHelper.toHanyuPinyinString(chineseStr , spellFormat ,"");   
//    }
}
