package com.rongzhiheng.util;

/**
 * 静态属性类
 * 
 * @author admin
 * 网络请求 网络基类
 */
public class Constants {
	//正常项目使用这个数值 发送网络请求
	public static final int NORMAL = 0;
	//自己写的测试 json 用0以外的
	public static final int TEST = 1;

	public static String urlBase ="http://192.168.0.102:8080/";
	public static String urlChe =urlBase+"che.txt";
	public static String urlZhifu =urlBase+"zhifu.txt";
	public static String urltest =urlBase+"oder.txt";
	public static String urlorder =urlBase+"oderinfo.txt";
	public static String urlUserInfo =urlBase+"UserInfo.txt";

	public static int i = 0;//交换名片

	public static int alumni_talk = 0;//校友聊
	public static final String LOGTAG = "ruidakuaiche";

	//测试服务器
	public static String new_url = "http://192.168.0.202/wangyueche/ruida_passenger.php";
	public static String image_url = "http://192.168.0.202/wangyueche/upload_img.php?app=ruida&passenger_id=";
	public static String fangwen_image_url = "http://192.168.0.202/wangyueche/upload_file/";

	//正式服务器
//	public static String new_url = "http://59.110.11.60/ruida_passenger.php";
//	public static String image_url = "http://59.110.11.60/upload_img.php?app=ruida&passenger_id=";
//	public static String fangwen_image_url = "http://59.110.11.60/upload_file/";

	//微信支付
	public static String weixin_url = "http://59.110.11.60/weixin.php";

	public static String key = "heilongjiang-rdk";
	public static String iv = "kuaiche-jsycc-16";
	
	public static String base_url = "http://siyuan.mashangyouli.com:8005/admin/ContentType.php?company=qhdx";

	//public static String zhifubaoPay = base_url+
}

