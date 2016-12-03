package com.qichen.ruida.bean;

import java.util.List;

/**
 * Created by 35876 于萌萌
 * 创建日期: 15:05 . 2016年11月16日
 * 描述: 周边消息bean
 * <p>//{"data":[{"positon_name":"","positon_time":"0","positon_lat":"45.7486540","positon_driver_id":"3","juli":0.2,"positon_id":"26","positon_passenger":"0","positon_lon":"126.6051140"}],"message":"成功","result":1}
 * <p>
 * 备注:
 */

public class PeripheralInfo {
    //获取周边司机位置
//    hashtable.put("action","passengerPosition");
    //乘客经纬度
//    hashtable.put("passenger_lon",mStartPosition.longitude+"");
//    hashtable.put("passenger_lat",mStartPosition.latitude+"");
    //乘客认证信息
//    hashtable.put("passenger_id",SQUtils.getId(MainActivity.this));


    /**
     * positon_name : 名字
     * positon_time : 最后一次定位时间
     * positon_driver_id : 司机的ID
     * juli : 0.2 离我多远
     * positon_id : 定位id
     * positon_passenger : 0  未载客  1 接单未载客 2 乘客上车
    * positon_lat : 45.7486540
     * positon_lon : 126.6051140
     */

    public List<DataBean> data;

    @Override
    public String toString() {
        return "PeripheralInfo{" +
                "data=" + data +
                '}';
    }

    public static class DataBean {
        public String positon_name;
        public String positon_time;
        public String positon_lat;
        public String positon_driver_id;
        public double juli;
        public String positon_id;
        public String positon_passenger;
        public String positon_lon;

        @Override
        public String toString() {
            return "DataBean{" +
                    "positon_name='" + positon_name + '\'' +
                    ", positon_time='" + positon_time + '\'' +
                    ", positon_lat='" + positon_lat + '\'' +
                    ", positon_driver_id='" + positon_driver_id + '\'' +
                    ", juli=" + juli +
                    ", positon_id='" + positon_id + '\'' +
                    ", positon_passenger='" + positon_passenger + '\'' +
                    ", positon_lon='" + positon_lon + '\'' +
                    '}';
        }
    }
//    {
//        "data": [{
//        "positon_name": "",
//                "positon_time": "0",
//                "positon_lat": "45.7486540",
//                "positon_driver_id": "3",
//                "juli": 0.2,
//                "positon_id": "26",
//                "positon_passenger": "0",
//                "positon_lon": "126.6051140"
//    }],
//        "message": "成功",
//            "result": 1
//    }




}
