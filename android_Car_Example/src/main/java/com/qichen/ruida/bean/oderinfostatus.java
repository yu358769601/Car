package com.qichen.ruida.bean;

import java.io.Serializable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 13:05 . 2016年11月19日
 * 描述:passengerGetOrder
 * <p>
 * <p>
 * 备注:
 */

public class oderinfostatus implements Serializable {
    /**
     * up_lon : 0.0000000
     * order_exception : 0
     * up_address :
     * order_state : 1
     * order_luqiao : 0.00
     * begion_lat : 45.7464600
     * driver_time : 1479622906
     * order_compute_money : 102.90
     * driver_car_license_photo :
     * down_lon : 0.0000000
     * down_lat : 0.0000000
     * begion_lon : 126.6053210
     * order_receive_address : 通达街靠近银恒大厦
     * order_compute_time : 51
     * driver_telephone : 18945017628  司机电话
     * order_money : 0.00
     * driver_id : 48
     * driver_deviceId : A0000065C2FEE8
     * order_from : 0
     * order_compute_mileage : 34.499
     * driver_money : 24.82
     * order_get_order : 0
     * end_lat : 45.6224320
     * order_number :
     * order_receive_time : 1479690715
     * driver_bank : 0
     * order_describe :
     * order_pingfen : 0
     * order_receive_lon : 126.6049060
     * order_add_time : 2016-11-21 08:50:00
     * driver_type :
     * driver_card_id : 230106198706280837
     * driver_end_inspect : 1479484800
     * down_time : 0
     * driver_zfbtx :
     * order_id : 46
     * order_tingche : 0.00
     * down_address :
     * driver_wxtx :
     * driver_photo :
     * order_scale : 1.00
     * order_driver_lat : 0.0000000
     * driver_password :
     * end_lon : 126.2436050
     * order_driver_address :
     * order_cancle_describe :
     * order_qingjie : 0.00
     * end_address : 太平国际机场
     * begion_address : 共乐街道通达街254号铂宫
     * driver_car : 公司内部测试人员  车牌号码
     * order_wait_time : 0
     * up_time : 0
     * driver_realname : 崔鹏   司机名字
     * order_pingjia :
     * driver_sex : 男
     * driver_car_color :
     * order_mileage : 0
     * order_driver_lon : 0.0000000
     * passenger_id : 2
     * up_lat : 0.0000000
     * order_receive_lat : 45.7465780
     * order_type : 0
     */

        public String up_lon;
        public String order_exception;
        public String up_address;
    //订单状态 0 未被接单  1 已接单  2 乘客已上车 3 订单完成  4 乘客取消订单 5 司机到达目的地(乘客未上车) 6司机取消订单
        public String order_state;
        public String order_luqiao;
        public String begion_lat;
        public String driver_time;
        public String order_compute_money;
        public String driver_car_license_photo;
        public String down_lon;
        public String down_lat;
        public String begion_lon;
        public String order_receive_address;
        public String order_compute_time;
        public String driver_telephone;
        public String order_money;
        public String driver_id;
        public String driver_deviceId;
        public String order_from;
        public String order_compute_mileage;
        public String driver_money;
        public String order_get_order;
        public String end_lat;
        public String order_number;
        public String order_receive_time;
        public String driver_bank;
        public String order_describe;
        public String order_pingfen;
        public String order_receive_lon;
        public String order_add_time;
        public String driver_type;
        public String driver_card_id;
        public String driver_end_inspect;
        public String down_time;
        public String driver_zfbtx;
        public String order_id;
        public String order_tingche;
        public String down_address;
        public String driver_wxtx;
        public String driver_photo;
        public String order_scale;
        public String order_driver_lat;
        public String driver_password;
        public String end_lon;
        public String order_driver_address;
        public String order_cancle_describe;
        public String order_qingjie;
        public String end_address;
        public String begion_address;
        public String driver_car;
        public String order_wait_time;
        public String up_time;
        public String driver_realname;
        public String order_pingjia;
        public String driver_sex;
        public String driver_car_color;
        public String order_mileage;
        public String order_driver_lon;
        public String passenger_id;
        public String up_lat;
        public String order_receive_lat;
        public String order_type;


    @Override
    public String toString() {
        return "oderinfostatus{" +
                "up_lon='" + up_lon + '\'' +
                ", order_exception='" + order_exception + '\'' +
                ", up_address='" + up_address + '\'' +
                ", order_state='" + order_state + '\'' +
                ", order_luqiao='" + order_luqiao + '\'' +
                ", begion_lat='" + begion_lat + '\'' +
                ", driver_time='" + driver_time + '\'' +
                ", order_compute_money='" + order_compute_money + '\'' +
                ", driver_car_license_photo='" + driver_car_license_photo + '\'' +
                ", down_lon='" + down_lon + '\'' +
                ", down_lat='" + down_lat + '\'' +
                ", begion_lon='" + begion_lon + '\'' +
                ", order_receive_address='" + order_receive_address + '\'' +
                ", order_compute_time='" + order_compute_time + '\'' +
                ", driver_telephone='" + driver_telephone + '\'' +
                ", order_money='" + order_money + '\'' +
                ", driver_id='" + driver_id + '\'' +
                ", driver_deviceId='" + driver_deviceId + '\'' +
                ", order_from='" + order_from + '\'' +
                ", order_compute_mileage='" + order_compute_mileage + '\'' +
                ", driver_money='" + driver_money + '\'' +
                ", order_get_order='" + order_get_order + '\'' +
                ", end_lat='" + end_lat + '\'' +
                ", order_number='" + order_number + '\'' +
                ", order_receive_time='" + order_receive_time + '\'' +
                ", driver_bank='" + driver_bank + '\'' +
                ", order_describe='" + order_describe + '\'' +
                ", order_pingfen='" + order_pingfen + '\'' +
                ", order_receive_lon='" + order_receive_lon + '\'' +
                ", order_add_time='" + order_add_time + '\'' +
                ", driver_type='" + driver_type + '\'' +
                ", driver_card_id='" + driver_card_id + '\'' +
                ", driver_end_inspect='" + driver_end_inspect + '\'' +
                ", down_time='" + down_time + '\'' +
                ", driver_zfbtx='" + driver_zfbtx + '\'' +
                ", order_id='" + order_id + '\'' +
                ", order_tingche='" + order_tingche + '\'' +
                ", down_address='" + down_address + '\'' +
                ", driver_wxtx='" + driver_wxtx + '\'' +
                ", driver_photo='" + driver_photo + '\'' +
                ", order_scale='" + order_scale + '\'' +
                ", order_driver_lat='" + order_driver_lat + '\'' +
                ", driver_password='" + driver_password + '\'' +
                ", end_lon='" + end_lon + '\'' +
                ", order_driver_address='" + order_driver_address + '\'' +
                ", order_cancle_describe='" + order_cancle_describe + '\'' +
                ", order_qingjie='" + order_qingjie + '\'' +
                ", end_address='" + end_address + '\'' +
                ", begion_address='" + begion_address + '\'' +
                ", driver_car='" + driver_car + '\'' +
                ", order_wait_time='" + order_wait_time + '\'' +
                ", up_time='" + up_time + '\'' +
                ", driver_realname='" + driver_realname + '\'' +
                ", order_pingjia='" + order_pingjia + '\'' +
                ", driver_sex='" + driver_sex + '\'' +
                ", driver_car_color='" + driver_car_color + '\'' +
                ", order_mileage='" + order_mileage + '\'' +
                ", order_driver_lon='" + order_driver_lon + '\'' +
                ", passenger_id='" + passenger_id + '\'' +
                ", up_lat='" + up_lat + '\'' +
                ", order_receive_lat='" + order_receive_lat + '\'' +
                ", order_type='" + order_type + '\'' +
                '}';
    }
}




//
//{
//        "message": "成功",
//        "result": 1,
//        "data": {
//        "up_lon": "0.0000000",
//        "order_exception": "0",
//        "up_address": "",
//        "order_state": "1",
//        "order_luqiao": "0.00",
//        "begion_lat": "45.7464600",
//        "driver_time": "1479622906",
//        "order_compute_money": "102.90",
//        "driver_car_license_photo": "",
//        "down_lon": "0.0000000",
//        "down_lat": "0.0000000",
//        "begion_lon": "126.6053210",
//        "order_receive_address": "通达街靠近银恒大厦",
//        "order_compute_time": "51",
//        "driver_telephone": "18945017628",
//        "order_money": "0.00",
//        "driver_id": "48",
//        "driver_deviceId": "A0000065C2FEE8",
//        "order_from": "0",
//        "order_compute_mileage": "34.499",
//        "driver_money": "24.82",
//        "order_get_order": "0",
//        "end_lat": "45.6224320",
//        "order_number": "",
//        "order_receive_time": "1479690715",
//        "driver_bank": "0",
//        "order_describe": "",
//        "order_pingfen": "0",
//        "order_receive_lon": "126.6049060",
//        "order_add_time": "2016-11-21 08:50:00",
//        "driver_type": "",
//        "driver_card_id": "230106198706280837",
//        "driver_end_inspect": "1479484800",
//        "down_time": "0",
//        "driver_zfbtx": "",
//        "order_id": "46",
//        "order_tingche": "0.00",
//        "down_address": "",
//        "driver_wxtx": "",
//        "driver_photo": "",
//        "order_scale": "1.00",
//        "order_driver_lat": "0.0000000",
//        "driver_password": "",
//        "end_lon": "126.2436050",
//        "order_driver_address": "",
//        "order_cancle_describe": "",
//        "order_qingjie": "0.00",
//        "end_address": "太平国际机场",
//        "begion_address": "共乐街道通达街254号铂宫",
//        "driver_car": "公司内部测试人员",
//        "order_wait_time": "0",
//        "up_time": "0",
//        "driver_realname": "崔鹏",
//        "order_pingjia": "",
//        "driver_sex": "男",
//        "driver_car_color": "",
//        "order_mileage": "0",
//        "order_driver_lon": "0.0000000",
//        "passenger_id": "2",
//        "up_lat": "0.0000000",
//        "order_receive_lat": "45.7465780",
//        "order_type": "0"
//        }
//        }


