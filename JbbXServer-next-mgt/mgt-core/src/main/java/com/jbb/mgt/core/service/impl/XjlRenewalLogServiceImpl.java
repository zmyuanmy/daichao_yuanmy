package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.XjlRenewalLogDao;
import com.jbb.mgt.core.domain.XjlRenewalLog;
import com.jbb.mgt.core.service.XjlRenewalLogService;

@Service("XjlRenewalLogService")
public class XjlRenewalLogServiceImpl implements XjlRenewalLogService {

	@Autowired
	private XjlRenewalLogDao dao; 
	
	@Override
	public List<XjlRenewalLog> queryRenewalLogList(String applyId,String renewalAmount, Integer userId, Timestamp creationDate) {
		return dao.queryRenewalLogList(applyId,renewalAmount, userId, creationDate);
	}

	@Override
	public XjlRenewalLog selectXjlRenewalLogByLogId(int logId) {
		return dao.selectXjlRenewalLogByLogId(logId);
	}

	@Override
	public int updateXjlRenewalLog(XjlRenewalLog xjlRenewalLog) {	
		return dao.updateXjlRenewalLog(xjlRenewalLog);
	}

	@Override
	public XjlRenewalLog insertXjlRenewalLog(XjlRenewalLog xjlRenewalLog) {
	      dao.insertXjlRenewalLog(xjlRenewalLog);
		return xjlRenewalLog;
	}

	@Override
	public int deleteXjlRenewalLog(int logId) {
		return dao.deleteXjlRenewalLog(logId);
	}

}
