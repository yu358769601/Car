package com.qichen.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by 35876 于萌萌
 * 创建日期: 13:27 . 2016年11月09日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public class AppContext extends Application {
  //  static Context context;
    // 获取到主线程的上下文
    private static AppContext mContext = null;
    // 获取到主线程的handler
    private static Handler mMainThreadHandler = null;

    // 获取到主线程
    private static Thread mMainThread = null;
    // 获取到主线程的id
    private static int mMainThreadId;
    // 获取到主线程的looper
    private static Looper mMainThreadLooper = null;

    @Override
    public void onCreate() {
        super.onCreate();
      //  this.context = this;
        this.mContext = this;
        this.mMainThreadHandler = new Handler();
        this.mMainThread = Thread.currentThread();
        this.mMainThreadId = android.os.Process.myTid();
        this.mMainThreadLooper = getMainLooper();
    }
    // 对外暴露上下文
    public static AppContext getApplication() {
        return mContext;
    }

    // 对外暴露主线程的handler
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    // 对外暴露主线程
    public static Thread getMainThread() {
        return mMainThread;
    }

    // 对外暴露主线程id
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    // 对外暴露主线程的looper
    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

//    public static Context getContext() {
//        return context;
//    }
}
