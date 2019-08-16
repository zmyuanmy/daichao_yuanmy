package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.AccountLoginLogDao;
import com.jbb.mgt.core.service.AccountLoginLogService;

@Service("AccountLoginLogService")
public class AccountLoginLogServiceImpl implements AccountLoginLogService{
	
	@Autowired
	AccountLoginLogDao accountLoginLogDao;
	@Override
	public void insertAccountLoginLog(int userId, String ipAddress, Timestamp loginDate) {
	    accountLoginLogDao.insertAccountLoginLog(userId, ipAddress, loginDate);
		
	}

}
