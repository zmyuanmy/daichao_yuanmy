package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.HumanLpPhone;

public interface HumanLpPhoneMapper {

    List<HumanLpPhone> selectHumanLpPhoneList(@Param(value = "number") String number,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate);

    HumanLpPhone selectHumanLpPhoneByNumber(@Param(value = "phoneNumber") String phoneNumber);

    HumanLpPhone selectHumanLpPhone(@Param(value = "phoneNumber") String phoneNumber,
        @Param(value = "cDate") Timestamp cDate);

    // 查询未被拉取的数据
    List<HumanLpPhone> selectHumanLpPhoneByStatus(@Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate, @Param("pushStatus") boolean pushStatus,
        @Param("pageSize") Integer pageSize);

    // 修改被拉取数据的状态
    void updateHumanLpPhone(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    // 修改状态
    void updateHumanbyPhone(@Param("phones") String[] phones);

    void updateHumanLpPhoneSendStatus(@Param(value = "phoneNumber") String phoneNumber,
        @Param(value = "lastSendStatus") String sendStatus, @Param("lastSendDate") Timestamp lastSendDate);
}
