package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.XjlRenewalLogDao;
import com.jbb.mgt.core.dao.mapper.XjlRenewalLogMapper;
import com.jbb.mgt.core.domain.XjlRenewalLog;
@Repository("XjlRenewalLogDao")
public class XjlRenewalLogDaoImpl implements XjlRenewalLogDao {

	@Autowired
	private XjlRenewalLogMapper mapper;
	
	@Override
	public List<XjlRenewalLog> queryRenewalLogList(String applyId,String renewalAmount, Integer userId, Timestamp creationDate) {
		return mapper.queryRenewalLogList(applyId,renewalAmount, userId, creationDate);
	}

	@Override
	public XjlRenewalLog selectXjlRenewalLogByLogId(int logId) {		
		return mapper.selectXjlRenewalLogByLogId(logId);
	}

	@Override
	public int updateXjlRenewalLog(XjlRenewalLog xjlRenewalLog) {
		return mapper.updateXjlRenewalLog(xjlRenewalLog);
	}

	@Override
	public XjlRenewalLog insertXjlRenewalLog(XjlRenewalLog xjlRenewalLog) {
		mapper.insertXjlRenewalLog(xjlRenewalLog);
		return xjlRenewalLog;
	}

	@Override
	public int deleteXjlRenewalLog(int logId) {	
		return mapper.deleteXjlRenewalLog(logId);
	}

}
