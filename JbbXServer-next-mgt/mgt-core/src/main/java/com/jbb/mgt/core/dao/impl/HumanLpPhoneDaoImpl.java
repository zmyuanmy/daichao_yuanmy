package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.HumanLpPhoneDao;
import com.jbb.mgt.core.dao.mapper.HumanLpPhoneMapper;
import com.jbb.mgt.core.domain.HumanLpPhone;

@Repository("HumanLpPhoneDao")
public class HumanLpPhoneDaoImpl implements HumanLpPhoneDao {
    @Autowired
    private HumanLpPhoneMapper humanLpPhoneMapper;

    @Override
    public List<HumanLpPhone> selectHumanLpPhoneList(String phoneNumber, Timestamp startDate, Timestamp endDate) {
        return humanLpPhoneMapper.selectHumanLpPhoneList(
            phoneNumber.substring(phoneNumber.length() - 4, phoneNumber.length()), startDate, endDate);
    }

    @Override
    public HumanLpPhone selectHumanLpPhone(String phoneNumber, Timestamp cDate) {
        return humanLpPhoneMapper.selectHumanLpPhone(phoneNumber, cDate);
    }

    @Override
    public List<HumanLpPhone> selectHumanLpPhoneByStatus(Timestamp startDate, Timestamp endDate, boolean pushStatus,
        Integer pageSize) {
        return humanLpPhoneMapper.selectHumanLpPhoneByStatus(startDate, endDate, pushStatus, pageSize);
    }

    @Override
    public void updateHumanLpPhone(Timestamp startDate, Timestamp endDate) {
        humanLpPhoneMapper.updateHumanLpPhone(startDate, endDate);
    }

    @Override
    public void updateHumanLpPhoneSendStatus(String phoneNumber, String sendStatus, Timestamp lastSendDate) {
        humanLpPhoneMapper.updateHumanLpPhoneSendStatus(phoneNumber, sendStatus, lastSendDate);
    }

    @Override
    public HumanLpPhone selectHumanLpPhoneByNumber(String phoneNumber) {
        return humanLpPhoneMapper.selectHumanLpPhoneByNumber(phoneNumber);
    }

    @Override
    public void updateHumanbyPhone(String[] phones) {
        humanLpPhoneMapper.updateHumanbyPhone(phones);
    }
}
