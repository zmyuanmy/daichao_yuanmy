package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.HumanLpPhone;

public interface HumanLpPhoneDao {

    List<HumanLpPhone> selectHumanLpPhoneList(String phoneNumber, Timestamp startDate, Timestamp endDate);

    HumanLpPhone selectHumanLpPhone(String phoneNumber, Timestamp cDate);

    // 查询未被拉取的数据
    List<HumanLpPhone> selectHumanLpPhoneByStatus(Timestamp startDate, Timestamp endDate, boolean pushStatus,
        Integer pageSize);

    // 修改被拉取数据的状态
    void updateHumanLpPhone(Timestamp startDate, Timestamp endDate);

    // 修改状态
    void updateHumanbyPhone(String[] phones);

    void updateHumanLpPhoneSendStatus(String phoneNumber, String sendStatus, Timestamp lastSendDate);

    HumanLpPhone selectHumanLpPhoneByNumber(String phoneNumber);

}
