package com.qichen.ruida.WX;

import java.util.Random;

/**
 * Created by Administrator 于萌萌
 * 创建日期: 18:06 . 2016年06月30日
 * 描述: 里面包括为自己定义的一个实体类，包括： Appid，MchId，，NonceStr 时间戳
 *
 *
 * <p>PrepayId 这个待定
 * <p>
 * 备注:
 */
public class WeChatPayModel {
    //app注册的
    public static final String APP_ID = "";
    //public static final String SECRET = "656739c79f8661e422f31e12de748809";
    //秘钥
    public static final String KEY = "";
    //商家 ID
    public static final String MCH_ID = "";

    public static String genNonceStr() {
        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
//                .getBytes());
        return String.valueOf(random.nextInt(10000));

    }
    public static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


}
