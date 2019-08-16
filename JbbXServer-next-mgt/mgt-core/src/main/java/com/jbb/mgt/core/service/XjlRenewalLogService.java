package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.XjlRenewalLog;

public interface XjlRenewalLogService {
   

	List<XjlRenewalLog> queryRenewalLogList(String applyId,String renewalAmount, Integer userId, Timestamp creationDate);
		
	XjlRenewalLog selectXjlRenewalLogByLogId(int logId);
	
	int updateXjlRenewalLog(XjlRenewalLog xjlRenewalLog);
	
	XjlRenewalLog insertXjlRenewalLog(XjlRenewalLog xjlRenewalLog);
	
	int deleteXjlRenewalLog(int logId);
}
