package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class XjlApplyRecordListVO {
        private String applyId;      //applyId
        private String jbbUserId;    //帮帮id
        private String userName;     //用户名
        private String phoneNumber;  //手机号
        private String idCard;      //身份证件号
        private String platform;     //来源
        private int loans;           //复借次数
        private Date creationDate;   //申请时间
        private int amount;          //申请金额
        private int serviceFee;      //息费
        private Date loanDate;       //放款日期
        private Date repaymentDate;  //应还款日期
        private Date actualRepaymentDate; //实际还款日期
        private int status;          //状态
        private String auditor;      //审核员
        private String exwheat;      //催告员
        private String standByTrial; //待机审
        private String refusalByMachine; //机审拒绝
        private String machineTrial; //机审通过
        private String humanRefusal; //人审拒绝
        private String pendingMoney; //待放款
        private String alreadyReleased;// 已放款
        private String expired;  //已到期
        private String overdue; //已逾期
        private String reimbursement; //已还款
}
