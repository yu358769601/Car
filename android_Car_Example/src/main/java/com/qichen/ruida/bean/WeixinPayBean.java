package com.qichen.ruida.bean;

/**
 * Created by 35876 于萌萌
 * 创建日期: 15:49 . 2016年11月23日
 * 描述:微信支付bean
 * <p>
 * <p>
 * 备注:
 */

public class WeixinPayBean {


    /**
     * sign : 6B9DEB9A71A6A006592FE6FB0DE0980A
     * partnerid : 1413235402
     * appid : wx61f9402d62035e9b
     * timestamp : 1479888447
     * packageValue : Sign=WXPay
     * noncestr : hknOJfqA1czL3Ny10XMEP57E5J4gk1qa
     * prepayid : wx2016112316072713ceaa538d0602635997
     */

        public String sign;
        public String partnerid;
        public String appid;
        public int timestamp;
        public String packageValue;
        public String noncestr;
        public String prepayid;
    }
//{
//        "data": {
//        "sign": "6B9DEB9A71A6A006592FE6FB0DE0980A",
//        "partnerid": "1413235402",
//        "appid": "wx61f9402d62035e9b",
//        "timestamp": 1479888447,
//        "packageValue": "Sign=WXPay",
//        "noncestr": "hknOJfqA1czL3Ny10XMEP57E5J4gk1qa",
//        "prepayid": "wx2016112316072713ceaa538d0602635997"
//        },
//        "message": "生成预订单",
//        "result": 1
//        }