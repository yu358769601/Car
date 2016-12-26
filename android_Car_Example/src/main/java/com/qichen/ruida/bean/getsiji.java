package com.qichen.ruida.bean;

import java.io.Serializable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 18:11 . 2016年11月29日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public class GetSiji implements Serializable {
//    hashtable.put("action", "passengerGetOrder");
    //乘客身份认证
//    hashtable.put("passenger_id", SQUtils.getId(this));
    /**
     * 订单状态
     * order_state : 1
     * 起始地点
     * begion_address : 工农街道天鹅湾天鹅湾（群力第五大道）
     * 纬度
     * positon_lat : 45.7389580
     * 司机姓名
     * driver_realname : 金松一
     * 司机电话
     * driver_telephone : 13664608180
     * 订单添加时间
     * order_add_time : 2016-11-29 18:02:05
     * 订单编号
     * order_id : 431
     * 司机车牌号
     * driver_car : 黑A 135486
     * 结束地点
     * end_address : 哈尔滨站
     * 经度
     * positon_lon : 126.5701260
     */

        public String order_state;
        public String begion_address;
        public String positon_lat;
        public String driver_realname;
        public String driver_telephone;
        public String order_add_time;
        public String order_id;
        public String driver_car;
        public String end_address;
        public String positon_lon;


    @Override
    public String toString() {
        return "GetSiji{" +
                "order_state='" + order_state + '\'' +
                ", begion_address='" + begion_address + '\'' +
                ", positon_lat='" + positon_lat + '\'' +
                ", driver_realname='" + driver_realname + '\'' +
                ", driver_telephone='" + driver_telephone + '\'' +
                ", order_add_time='" + order_add_time + '\'' +
                ", order_id='" + order_id + '\'' +
                ", driver_car='" + driver_car + '\'' +
                ", end_address='" + end_address + '\'' +
                ", positon_lon='" + positon_lon + '\'' +
                '}';
    }
}


