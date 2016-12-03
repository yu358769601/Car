package com.qichen.ruida.bean;

/**
 * Created by 35876 于萌萌
 * 创建日期: 11:41 . 2016年11月20日
 * 描述:订单bean
 * passengerGetOrderDetail
 * <p>
 * <p>
 * 备注:
 */

public class Order {
//    hashtable.put("action", "passengerGetOrderDetail");
    //订单编号
//    hashtable.put("order_id",mActivity1.mOder_id );

    /**结束地点
     * end_address : 哈尔滨站
     * 订单状态
     * order_state : 0
     * 开始地点
     * begion_address : 松花江街道哈尔滨海关驻邮局办事处
     * 订单创建时间
     * order_add_time1 : 2016-11-20 11:06:13
     */

        public String end_address;
        public String order_state;
        public String begion_address;
        public String order_add_time1;
    }
//{
//        "message": "成功",
//        "result": 1,
//        "data": {
//        "end_address": "哈尔滨站",
//        "order_state": "0",
//        "begion_address": "松花江街道哈尔滨海关驻邮局办事处",
//        "order_add_time1": "2016-11-20 11:06:13"
//        }
//        }