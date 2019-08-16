package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

@Data
public class BasicInfo {
    private String identity_card;
    private String real_name;
    private String mobile_phone;
    private long register_time;
    private String living_province;
    private String living_city;
    private String living_area;
    private String living_address;
    private int degree; //学历（0未知，1中专及以下，2大专，3本科， 4硕士，5博士及以上）
    private int device_type; //(0:安卓  1:IOS  2：网页)

}
