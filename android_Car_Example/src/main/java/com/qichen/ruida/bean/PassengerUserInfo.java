package com.qichen.ruida.bean;

/**
 * Created by 35876 于萌萌
 * 创建日期: 17:59 . 2016年11月17日
 * 描述:个人信息 bean
 * <p>
 * <p>
 * 备注:
 */

public class PassengerUserInfo {


    /**
     * passenger_id : 5
     * passenger_telephone : 18603663028
     * passenger_password :
     * passenger_nickname : 王瑞环
     * passenger_photo :
     * passenger_sex : 男
     * passenger_industry : 发明
     * passenger_money : 151.40
     * passenger_job : 美工
     * passenger_time : 0
     * passenger_from : Android
     * passenger_lat : 45.7465470
     * passenger_lon : 126.6051220
     * passenger_address_time : 1479376923
     * passenger_address_name :
     */

        public String passenger_id;
        public String passenger_telephone;
        public String passenger_password;
        public String passenger_nickname;
        public String passenger_photo;
        public String passenger_sex;
        public String passenger_industry;
        public String passenger_money;
        public String passenger_job;
        public String passenger_time;
        public String passenger_from;
        public String passenger_lat;
        public String passenger_lon;
        public String passenger_address_time;
        public String passenger_address_name;

    @Override
    public String toString() {
        return "PassengerUserInfo{" +
                "passenger_id='" + passenger_id + '\'' +
                ", passenger_telephone='" + passenger_telephone + '\'' +
                ", passenger_password='" + passenger_password + '\'' +
                ", passenger_nickname='" + passenger_nickname + '\'' +
                ", passenger_photo='" + passenger_photo + '\'' +
                ", passenger_sex='" + passenger_sex + '\'' +
                ", passenger_industry='" + passenger_industry + '\'' +
                ", passenger_money='" + passenger_money + '\'' +
                ", passenger_job='" + passenger_job + '\'' +
                ", passenger_time='" + passenger_time + '\'' +
                ", passenger_from='" + passenger_from + '\'' +
                ", passenger_lat='" + passenger_lat + '\'' +
                ", passenger_lon='" + passenger_lon + '\'' +
                ", passenger_address_time='" + passenger_address_time + '\'' +
                ", passenger_address_name='" + passenger_address_name + '\'' +
                '}';
    }
}
//
//{
//        "result": 1,
//        "message": "成功",
//        "data": {
//        "passenger_id": "5",
//        "passenger_telephone": "18603663028",
//        "passenger_password": "",
//        "passenger_nickname": "王瑞环",
//        "passenger_photo": "",
//        "passenger_sex": "男",
//        "passenger_industry": "发明",
//        "passenger_money": "151.40",
//        "passenger_job": "美工",
//        "passenger_time": "0",
//        "passenger_from": "Android",
//        "passenger_lat": "45.7465470",
//        "passenger_lon": "126.6051220",
//        "passenger_address_time": "1479376923",
//        "passenger_address_name": ""
//        }
//        }
