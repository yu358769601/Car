package com.qichen.ruida.bean;

/**
 * Created by 35876 于萌萌
 * 创建日期: 9:47 . 2016年11月29日
 * 描述: 获取单个司机bean  和 乘客的状态
 * <p>"action","getDriverPositon"
 * <p>
 * 备注:
 */

public class DriverPositon {
////必要字段
//
//    //获取周边司机位置
//    hashtable.put("action","getDriverPositon");
    //订单编号
//    hashtable.put("order_id",mOder_id);

    /**  纬度
         * positon_lat : 45.7389610
     *  订单状态
         * order_state : 1
     * 司机编号
         * driver_id : 2
     * 经度
         * positon_lon : 126.5699140
     */

        public String positon_lat;
        public String order_state;
        public String driver_id;
        public String positon_lon;
    }

//{
//        "message": "成功",
//        "result": 1,
//        "data": {
//        "positon_lat": "45.7389610",
//        "order_state": "1",
//        "driver_id": "2",
//        "positon_lon": "126.5699140"
//        }
//        }