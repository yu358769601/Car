package com.loveplusplus.update;

class Constants {


    // json {"url":"http://192.168.205.33:8080/Hello/app_v3.0.1_Other_20150116.apk","versionCode":2,"updateMessage":"版本更新信息"}

    static final String APK_DOWNLOAD_URL = "url";
    static final String APK_UPDATE_CONTENT = "updateMessage";
    static final String APK_VERSION_CODE = "versionCode";
    static final String APK_VERSION_FORCEUPDATA = "forceUpData";


    static final int TYPE_NOTIFICATION = 2;

    static final int TYPE_DIALOG = 1;

    static final String TAG = "UpdateChecker";

   // static final String UPDATE_URL = "http://git.oschina.net/hopek9999/upData/raw/master/up.json?dir=0&filepath=up.json&oid=d0e9ea83a8614df999d58e15a64b3819600f5194&sha=b6f3486b157e541681afcd8effdef590ea0ffddb";
  //  static final String UPDATE_URL = "http://192.168.4.102:8080/up.json";
    static final String UPDATE_URL = "https://pcs.baidu.com/rest/2.0/pcs/file?method=download&access_token=21.175edfa656ee1b31397cd140dfa37bb6.2592000.1481629991.1275093937-1507596&path=/apps/Gotoway//up.json";


}
