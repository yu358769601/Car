package com.loveplusplus.update;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author feicien (ithcheng@gmail.com)
 * @since 2016-07-05 19:25
 */
public class HttpUtils {


    public static String get(String urlStr) {
        Log.i("HttpUtils", "get: 准备获取网络了"+urlStr);
        HttpURLConnection uRLConnection = null;
        InputStream is = null;
        BufferedReader buffer = null;
        String result = null;
        try {
            Log.i("HttpUtils", "get: 我们要访问的网址是"+urlStr);
            URL url = new URL(urlStr);

            Log.i("HttpUtils", "get: 打开openConnection");
            uRLConnection = (HttpURLConnection) url.openConnection();

            Log.i("HttpUtils", "get: 设置请求是GET");
            uRLConnection.setRequestMethod("GET");

            Log.i("HttpUtils", "get: 获取输入流");
            is = uRLConnection.getInputStream();

            Log.i("HttpUtils", "get: 拼接输入流");
            buffer = new BufferedReader(new InputStreamReader(is));

            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                strBuilder.append(line);
            }
            result = strBuilder.toString();
            Log.i("HttpUtils", "get: 输入流拼接完毕内容是"+result);
        } catch (Exception e) {
            Log.e(Constants.TAG, "http post error");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException ignored) {

                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {

                }
            }
            if (uRLConnection != null) {
                uRLConnection.disconnect();
            }
        }
        return result;
    }
}
