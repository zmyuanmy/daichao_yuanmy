package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.PayRecord;
import com.jbb.mgt.core.domain.XjlApplyRecord;

public interface XjlRemindMsgCodeService {

    /**
     * 查询明日到期 已到期 已逾期(1-7天)数据
     */
    void getXjlApplyRecordsByStatus(Integer status, String startDate, Integer startDay, Integer endDay);

    // 放款发送短信
    void loanSendCode(PayRecord payRecord, XjlApplyRecord xjlApply);
}
