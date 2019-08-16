package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

@Data
public class WorkInfo {
    private String company_name; //公司名称
    private int company_type; //公司类别(0未知1国有企业，2事业单位，3上市公司，4外资企业，5民营企业，6，个体工商户，7其他)
    private int company_register_capital; //注册资本(万)
    private String phone; //公司电话
    private String company_province; //公司地址省
    private String company_city; //公司地址市
    private String company_area; //公司地址区
    private String company_address; //公司详细地址
    private String company_department; //部门
    private int company_position_level; //职位层级(0、未填写，1、普通员工，2、业务主管，3、部门经理，4、副总，5、总经理及以上)
    private int work_year; //工作年限
    private int salary; //月薪收入(元)
    private String work_email; //工作邮箱
    private int total_work_year; //工作总工龄
}
