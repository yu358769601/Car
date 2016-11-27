package com.loveplusplus.update;

import android.content.Context;
import android.util.Log;

public class UpdateChecker {


    public static void checkForDialog(Context context, CallBack callBack) {
        if (context != null) {
            Log.i("UpdateChecker", "checkForDialog: 启动一个AsyncTask开始下载");
            new CheckUpdateTask(context, Constants.TYPE_DIALOG,true,callBack).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }


    public static void checkForNotification(Context context) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_NOTIFICATION, false).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }

    }


}
