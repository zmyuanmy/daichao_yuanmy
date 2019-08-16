package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.HumanLpPhoneDao;
import com.jbb.mgt.core.domain.HumanLpPhone;
import com.jbb.mgt.core.service.HumanLpPhoneService;

@Service("HumanLpPhoneService")
public class HumanLpPhoneServiceImpl implements HumanLpPhoneService {

    @Autowired
    private HumanLpPhoneDao humanLpPhoneDao;

    @Override
    public List<HumanLpPhone> selectHumanLpPhoneList(String phoneNumber, Timestamp startDate, Timestamp endDate) {
        return humanLpPhoneDao.selectHumanLpPhoneList(phoneNumber, startDate, endDate);
    }

    @Override
    public HumanLpPhone selectHumanLpPhone(String phoneNumber, Timestamp cDate) {
        return humanLpPhoneDao.selectHumanLpPhone(phoneNumber, cDate);
    }

    @Override
    public List<HumanLpPhone> getHumanLpPhoneByStatus(Timestamp startDate, Timestamp endDate, boolean pushStatus,
        Integer pageSize) {
        return humanLpPhoneDao.selectHumanLpPhoneByStatus(startDate, endDate, pushStatus, pageSize);
    }

    @Override
    public void updateHumanLpPhone(Timestamp startDate, Timestamp endDate) {
        humanLpPhoneDao.updateHumanLpPhone(startDate, endDate);
    }

    @Override
    public HumanLpPhone selectHumanLpPhoneByNumber(String phoneNumber) {
        return humanLpPhoneDao.selectHumanLpPhoneByNumber(phoneNumber);
    }

    @Override
    public void updateHumanLpPhoneSendStatus(String phoneNumber, String sendStatus, Timestamp lastSendDate) {
        humanLpPhoneDao.updateHumanLpPhoneSendStatus(phoneNumber, sendStatus, lastSendDate);
    }

    @Override
    public void updateHumanbyPhone(String[] phones) {
        humanLpPhoneDao.updateHumanbyPhone(phones);
    }
}
