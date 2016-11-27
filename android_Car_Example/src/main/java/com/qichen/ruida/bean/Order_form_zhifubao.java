package com.qichen.ruida.bean;

/**
 * Created by 35876 于萌萌
 * 创建日期: 13:35 . 2016年11月15日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public class Order_form_zhifubao {
    /**
     * payType : 支付宝
     * payMoney : 10
     * body : 2016-11-1513:28:57_账户充值
     * subject : 账户充值
     * pay : _input_charset=utf-8&body=2016-11-1513:28:57_账户充值&notify_url=http://lezhong.wei59.com/admin/&out_trade_no=147918773770365&partner=2088521039496674&payment_type=1&seller_id=xinyanruida@163.com&service=create_direct_pay_by_user&subject=账户充值&total_fee=10&sign="XdwZ0TZaa3Ze5ZpvJUZkqf9+nbWT26jtFTG7YVWbEYTr9x8x6Dds+IS2xXVlCMGzcDuXAIEq1Z467b156nSdal1gMJI7Vlp8dH1E0J7S1MCQOHzy8Ut395rUlXDVDMO5D12hVoW3Ypg1nuGt0kkq5cIXQpLy0Re/NQuSw0Iw+ek="&sign_type="RSA"
     */
    /**
     *
     * payType : 支付类型
     * payMoney : 支付近千
     * body : 商品详情
     * subject : 商品名称
     * pay : 加密之后的订单
     *
     */



    /**
     * data : {"payType":"支付宝","payMoney":"10","body":"2016-11-1513:28:57_账户充值","subject":"账户充值","pay":"_input_charset=utf-8&body=2016-11-1513:28:57_账户充值&notify_url=http://lezhong.wei59.com/admin/&out_trade_no=147918773770365&partner=2088521039496674&payment_type=1&seller_id=xinyanruida@163.com&service=create_direct_pay_by_user&subject=账户充值&total_fee=10&sign=\"XdwZ0TZaa3Ze5ZpvJUZkqf9+nbWT26jtFTG7YVWbEYTr9x8x6Dds+IS2xXVlCMGzcDuXAIEq1Z467b156nSdal1gMJI7Vlp8dH1E0J7S1MCQOHzy8Ut395rUlXDVDMO5D12hVoW3Ypg1nuGt0kkq5cIXQpLy0Re/NQuSw0Iw+ek=\"&sign_type=\"RSA\""}
     * message : 成功
     * result : 1
     */

        public String payType;
        public String payMoney;
        public String body;
        public String subject;
        public String pay;

    @Override
    public String toString() {

        return "Order_form_zhifubao{" +
                "payType='" + payType + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                ", pay='" + pay + '\'' +
                '}';

    }
}
//    原始网络数据
//    {
//        "data": {
//        "payType": "支付宝",
//                "payMoney": "10",
//                "body": "2016-11-1513:28:57_账户充值",
//                "subject": "账户充值",
//                "pay": "_input_charset=utf-8&body=2016-11-1513:28:57_账户充值&notify_url=http://lezhong.wei59.com/admin/&out_trade_no=147918773770365&partner=2088521039496674&payment_type=1&seller_id=xinyanruida@163.com&service=create_direct_pay_by_user&subject=账户充值&total_fee=10&sign=\"XdwZ0TZaa3Ze5ZpvJUZkqf9+nbWT26jtFTG7YVWbEYTr9x8x6Dds+IS2xXVlCMGzcDuXAIEq1Z467b156nSdal1gMJI7Vlp8dH1E0J7S1MCQOHzy8Ut395rUlXDVDMO5D12hVoW3Ypg1nuGt0kkq5cIXQpLy0Re/NQuSw0Iw+ek=\"&sign_type=\"RSA\""
//    },
//        "message": "成功",
//            "result": 1
//    }


