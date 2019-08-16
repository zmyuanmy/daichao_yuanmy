package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

@Data
public class User {
    private String user_source; //号码类型（移动，联通，电信）
    private String id_card; //身份证号（联通，移动部分省份，电信暂不支持），
    private String addr; //注册该号码所填写的地址
    private String real_name; //用户姓名（为该号码所注册的实名用户姓名）
    private String phone_remain; //当前账户余额
    private String phone; //电话号码
    private String reg_time; //入网时间（手机号码在运营商的实名认证时间）(精确到秒)
    private String score; //用户积分
    private String contact_phone; //联系人号码
    private String star_level; //用户星级
    private String authentication; //用户实名状态
    private String phone_status; //客户状态
    private String package_name; //套餐名称
}
