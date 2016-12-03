package com.qichen.ruida.bean;

import java.io.Serializable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 14:21 . 2016年11月21日
 * 描述:订单详情bean  订单列表item 下级菜单
 * <p>getOrderDetail
 * <p>
 * 备注:
 */

public class OrderInfo_Details implements Serializable{
//    hashtable.put("action", "getOrderDetail");
    //订单编号
//    hashtable.put("order_id",oders.order_id );

//    "订单号:"+mOrderInfo_details.order_id);
//    "起点:"+mOrderInfo_details.begion_address);
//    "终点:"+mOrderInfo_details.end_address);
//
//    "司机电话:"+UtilsMyText.hide4Num(mOrderInfo_details.driver_telephone));
//
//    "司机姓名:" + UtilsMyText.getfristString(mOrderInfo_details.driver_realname)+"师傅");
//
//    "开始时间:"+mOrderInfo_details.up_time1);
//    "结束时间:"+mOrderInfo_details.down_time1);
//    "里程数:"+mOrderInfo_details.order_mileage);
//    "路桥费:"+mOrderInfo_details.order_luqiao);
//    "停车费:"+mOrderInfo_details.order_tingche);
//    "清洁费:"+mOrderInfo_details.order_qingjie);
//    "高峰倍:"+mOrderInfo_details.order_scale);
//    "时长:"+UtilsMyText.get2DoubleString(mOrderInfo_details.chengche_time)+"分");
//    "价格:"+mOrderInfo_details.order_money+"元");



    /**
     * order_mileage : 0
     * order_from : 0
     * order_pingfen : 0
     * order_receive_address : 通达街靠近银恒大厦
     * driver_id : 2
     * end_lat : 45.7615540
     * order_tingche : 0.00
     * passenger_money : 10.94
     * order_pingjia :
     * order_type : 0
     * up_time : 1479707644
     * order_driver_lat : 45.7465660
     * up_address : 共乐街道聚贤花园  起点
     * begion_lat : 45.7473360
     * end_address : 哈尔滨站 终点
     * up_time1 : 2016-11-21 13:54:04
     * order_qingjie : 0.00
     * order_receive_lat : 45.7465710
     * driver_realname : 金松一 司机名字
     * driver_telephone : 13664608180  司机电话
     * order_add_time : 2016-11-21 13:40:54
     * order_compute_mileage : 4.352
     * order_compute_money : 16.50
     * order_scale : 1.00
     * order_exception : 0
     * order_driver_address : 共乐街道聚贤花园
     * order_number :
     * order_cancle_describe :
     * passenger_telephone : 18603663028
     * down_time : 1479707656
     * begion_lon : 126.6035180
     * end_lon : 126.6329790
     * begion_address : 建国街道通达街附288-2号
     * down_time1 : 2016-11-21 13:54:16
     * passenger_id : 7
     * up_lon : 126.6048650
     * order_receive_time : 2016-11-21 13:52:59
     * down_address : 共乐街道聚贤花园
     * order_receive_lon : 126.6048660
     * order_state : 3
     * up_lat : 45.7465660
     * order_money : 9.06
     * down_lon : 126.6048650
     * order_wait_time : 0
     * chengche_time : 0.2
     * order_luqiao : 0.00
     * order_id : 123
     * order_get_order : 2016-11-21 13:53:57
     * order_compute_time : 16
     * passenger_nickname :
     * order_describe :
     * down_lat : 45.7465600
     * order_driver_lon : 126.6048650
     */
    public String order_mileage;
        public String order_from;
        public String order_pingfen;
        public String order_receive_address;
        public String driver_id;
        public String end_lat;
        public String order_tingche;
        public String passenger_money;
        public String order_pingjia;
        public String order_type;
        public String up_time;
        public String order_driver_lat;
        public String up_address;
        public String begion_lat;
        public String end_address;
        public String up_time1;
        public String order_qingjie;
        public String order_receive_lat;
        public String driver_realname;
        public String driver_telephone;
        public String order_add_time;
        public String order_compute_mileage;
        public String order_compute_money;
        public String order_scale;
        public String order_exception;
        public String order_driver_address;
        public String order_number;
        public String order_cancle_describe;
        public String passenger_telephone;
        public String down_time;
        public String begion_lon;
        public String end_lon;
        public String begion_address;
        public String down_time1;
        public String passenger_id;
        public String up_lon;
        public String order_receive_time;
        public String down_address;
        public String order_receive_lon;
        public String order_state;
        public String up_lat;
        public String order_money;
        public String down_lon;
        public String order_wait_time;
        public String chengche_time;
        public String order_luqiao;
        public String order_id;
        public String order_get_order;
        public String order_compute_time;
        public String passenger_nickname;
        public String order_describe;
        public String down_lat;
        public String order_driver_lon;


    @Override
    public String toString() {
        return "OrderInfo_Details{" +
                "order_from='" + order_from + '\'' +
                ", order_pingfen='" + order_pingfen + '\'' +
                ", order_receive_address='" + order_receive_address + '\'' +
                ", driver_id='" + driver_id + '\'' +
                ", end_lat='" + end_lat + '\'' +
                ", order_tingche='" + order_tingche + '\'' +
                ", passenger_money='" + passenger_money + '\'' +
                ", order_pingjia='" + order_pingjia + '\'' +
                ", order_type='" + order_type + '\'' +
                ", up_time='" + up_time + '\'' +
                ", order_driver_lat='" + order_driver_lat + '\'' +
                ", up_address='" + up_address + '\'' +
                ", begion_lat='" + begion_lat + '\'' +
                ", end_address='" + end_address + '\'' +
                ", up_time1='" + up_time1 + '\'' +
                ", order_qingjie='" + order_qingjie + '\'' +
                ", order_receive_lat='" + order_receive_lat + '\'' +
                ", driver_realname='" + driver_realname + '\'' +
                ", driver_telephone='" + driver_telephone + '\'' +
                ", order_add_time='" + order_add_time + '\'' +
                ", order_compute_mileage='" + order_compute_mileage + '\'' +
                ", order_compute_money='" + order_compute_money + '\'' +
                ", order_scale='" + order_scale + '\'' +
                ", order_exception='" + order_exception + '\'' +
                ", order_driver_address='" + order_driver_address + '\'' +
                ", order_number='" + order_number + '\'' +
                ", order_cancle_describe='" + order_cancle_describe + '\'' +
                ", passenger_telephone='" + passenger_telephone + '\'' +
                ", down_time='" + down_time + '\'' +
                ", begion_lon='" + begion_lon + '\'' +
                ", end_lon='" + end_lon + '\'' +
                ", begion_address='" + begion_address + '\'' +
                ", down_time1='" + down_time1 + '\'' +
                ", passenger_id='" + passenger_id + '\'' +
                ", up_lon='" + up_lon + '\'' +
                ", order_receive_time='" + order_receive_time + '\'' +
                ", down_address='" + down_address + '\'' +
                ", order_receive_lon='" + order_receive_lon + '\'' +
                ", order_state='" + order_state + '\'' +
                ", up_lat='" + up_lat + '\'' +
                ", order_money='" + order_money + '\'' +
                ", down_lon='" + down_lon + '\'' +
                ", order_wait_time='" + order_wait_time + '\'' +
                ", chengche_time=" + chengche_time +
                ", order_luqiao='" + order_luqiao + '\'' +
                ", order_id='" + order_id + '\'' +
                ", order_get_order='" + order_get_order + '\'' +
                ", order_compute_time='" + order_compute_time + '\'' +
                ", passenger_nickname='" + passenger_nickname + '\'' +
                ", order_describe='" + order_describe + '\'' +
                ", down_lat='" + down_lat + '\'' +
                ", order_driver_lon='" + order_driver_lon + '\'' +
                '}';
    }
}

//{
//        "data": {
//        "order_mileage": "0",
//        "order_from": "0",
//        "order_pingfen": "0",
//        "order_receive_address": "通达街靠近银恒大厦",
//        "driver_id": "2",
//        "end_lat": "45.7615540",
//        "order_tingche": "0.00",
//        "passenger_money": "10.94",
//        "order_pingjia": "",
//        "order_type": "0",
//        "up_time": "1479707644",
//        "order_driver_lat": "45.7465660",
//        "up_address": "共乐街道聚贤花园",
//        "begion_lat": "45.7473360",
//        "end_address": "哈尔滨站",
//        "up_time1": "2016-11-21 13:54:04",
//        "order_qingjie": "0.00",
//        "order_receive_lat": "45.7465710",
//        "driver_realname": "金松一",
//        "driver_telephone": "13664608180",
//        "order_add_time": "2016-11-21 13:40:54",
//        "order_compute_mileage": "4.352",
//        "order_compute_money": "16.50",
//        "order_scale": "1.00",
//        "order_exception": "0",
//        "order_driver_address": "共乐街道聚贤花园",
//        "order_number": "",
//        "order_cancle_describe": "",
//        "passenger_telephone": "18603663028",
//        "down_time": "1479707656",
//        "begion_lon": "126.6035180",
//        "end_lon": "126.6329790",
//        "begion_address": "建国街道通达街附288-2号",
//        "down_time1": "2016-11-21 13:54:16",
//        "passenger_id": "7",
//        "up_lon": "126.6048650",
//        "order_receive_time": "2016-11-21 13:52:59",
//        "down_address": "共乐街道聚贤花园",
//        "order_receive_lon": "126.6048660",
//        "order_state": "3",
//        "up_lat": "45.7465660",
//        "order_money": "9.06",
//        "down_lon": "126.6048650",
//        "order_wait_time": "0",
//        "chengche_time": 0.2,
//        "order_luqiao": "0.00",
//        "order_id": "123",
//        "order_get_order": "2016-11-21 13:53:57",
//        "order_compute_time": "16",
//        "passenger_nickname": "",
//        "order_describe": "",
//        "down_lat": "45.7465600",
//        "order_driver_lon": "126.6048650"
//        },
//        "message": "成功",
//        "result": 1
//        }


