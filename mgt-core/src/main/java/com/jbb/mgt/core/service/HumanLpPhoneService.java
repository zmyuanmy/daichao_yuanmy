package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.HumanLpPhone;

public interface HumanLpPhoneService {

    List<HumanLpPhone> selectHumanLpPhoneList(String phoneNumber, Timestamp startDate, Timestamp endDate);

    HumanLpPhone selectHumanLpPhone(String phoneNumber, Timestamp cDate);

    // 查询未被拉取的数据
    List<HumanLpPhone> getHumanLpPhoneByStatus(Timestamp startDate, Timestamp endDate, boolean pushStatus,
        Integer pageSize);

    // 修改被拉取数据的状态
    void updateHumanLpPhone(Timestamp startDate, Timestamp endDate);

    // 修改状态
    void updateHumanbyPhone(String[] phones);

    HumanLpPhone selectHumanLpPhoneByNumber(String phoneNumber);

    void updateHumanLpPhoneSendStatus(String phoneNumber, String sendStatus, Timestamp lastSendDate);

}
