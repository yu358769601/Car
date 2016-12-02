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

    /**
     * order_state : 1
     * begion_address : 工农街道天鹅湾天鹅湾（群力第五大道）
     * positon_lat : 45.7389580
     * driver_realname : 金松一
     * driver_telephone : 13664608180
     * order_add_time : 2016-11-29 18:02:05
     * order_id : 431
     * driver_car : 黑A 135486
     * end_address : 哈尔滨站
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


