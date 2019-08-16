package com.jbb.server.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.jbb.server.core.dao.UserLoginLogsDao;
import com.jbb.server.core.dao.mapper.UserLoginLogsMapper;
import com.jbb.server.core.domain.UserLoginLog;
@Repository("UserLoginLogsDao")
public class UserLoginLogsDaoImpl implements UserLoginLogsDao {
@Autowired
private UserLoginLogsMapper userLoginLogsMapper;
	@Override
	public int insert(UserLoginLog record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(UserLoginLog record) {
		// TODO Auto-generated method stub
		return userLoginLogsMapper.insertSelective(record);
	}

}
