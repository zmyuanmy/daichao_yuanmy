package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.AccountLoginLogDao;
import com.jbb.mgt.core.dao.mapper.AccountLoginLogMapper;

@Repository("AccountLoginLogDao")
public class AccountLoginLogDaoImpl implements AccountLoginLogDao{

	
	@Autowired
	AccountLoginLogMapper accountLoginLogMapper;
	
	@Override
	public void insertAccountLoginLog(int userId, String ipAddress, Timestamp loginDate) {
	    accountLoginLogMapper.insertAccountLoginLog(userId, ipAddress, loginDate);
	}

}
