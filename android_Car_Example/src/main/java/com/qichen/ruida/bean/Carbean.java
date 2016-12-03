package com.qichen.ruida.bean;

import java.util.List;

/**
 * Created by 35876 于萌萌
 * 创建日期: 9:11 . 2016年11月11日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public class Carbean {



    /**
     * order_number : 3
     * order_exception : 0
     * order_state : 0
     * end_address : 太平国际机场
     * down_time : 1478843823
     * order_receive_time : 1478845520
     * begion_address : 西桥住宅小区
     * begion_lat : 45.7413220
     * up_time : 1478843792
     * order_compute_money : 0.00
     * juli : 1.7
     * order_id : 4
     * order_describe :
     * order_mileage : 0
     * begion_lon : 126.6252350
     * order_compute_time : 0
     * order_add_time : 1478066719
     * order_money : 0.00
     * passenger_id : 1
     * driver_id : 0
     * order_from : 1
     * order_compute_mileage : 0
     * end_lon : 126.2436050
     * order_type : 0
     * end_lat : 45.6224320
     */

    public List<DataBean> data;

    public static class DataBean {
        public String order_number;
        public String order_exception;
        public String order_state;
        public String end_address;
        public String down_time;
        public String order_receive_time;
        public String begion_address;
        public String begion_lat;
        public String up_time;
        public String order_compute_money;
        public double juli;
        public String order_id;
        public String order_describe;
        public String order_mileage;
        public String begion_lon;
        public String order_compute_time;
        public String order_add_time;
        public String order_money;
        public String passenger_id;
        public String driver_id;
        public String order_from;
        public String order_compute_mileage;
        public String end_lon;
        public String order_type;
        public String end_lat;

        @Override
        public String toString() {
            return "DataBean{" +
                    "order_number='" + order_number + '\'' +
                    ", order_exception='" + order_exception + '\'' +
                    ", order_state='" + order_state + '\'' +
                    ", end_address='" + end_address + '\'' +
                    ", down_time='" + down_time + '\'' +
                    ", order_receive_time='" + order_receive_time + '\'' +
                    ", begion_address='" + begion_address + '\'' +
                    ", begion_lat='" + begion_lat + '\'' +
                    ", up_time='" + up_time + '\'' +
                    ", order_compute_money='" + order_compute_money + '\'' +
                    ", juli=" + juli +
                    ", order_id='" + order_id + '\'' +
                    ", order_describe='" + order_describe + '\'' +
                    ", order_mileage='" + order_mileage + '\'' +
                    ", begion_lon='" + begion_lon + '\'' +
                    ", order_compute_time='" + order_compute_time + '\'' +
                    ", order_add_time='" + order_add_time + '\'' +
                    ", order_money='" + order_money + '\'' +
                    ", passenger_id='" + passenger_id + '\'' +
                    ", driver_id='" + driver_id + '\'' +
                    ", order_from='" + order_from + '\'' +
                    ", order_compute_mileage='" + order_compute_mileage + '\'' +
                    ", end_lon='" + end_lon + '\'' +
                    ", order_type='" + order_type + '\'' +
                    ", end_lat='" + end_lat + '\'' +
                    '}';
        }
    }
}
